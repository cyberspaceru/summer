package ru.qa.summer.selenium;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.openqa.selenium.By;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebElement;
import ru.qa.summer.ElementLocator;
import ru.qa.summer.exceptions.UndefinedParentException;
import ru.qa.summer.selenium.services.DriverService;
import ru.qa.summer.support.primitives.LoadableComponent;

import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

import static ru.qa.summer.selenium.SeleniumConfiguration.SELENIUM_CONFIGURATION;
import static ru.qa.summer.utils.XPathConcatenateUtil.concat;

public class SeleniumElementLocator extends ElementLocator<WebElement> {
    private static final Supplier<SearchContext> BASE_SEARCH_CONTEXT_SUPPLIER = () -> DriverService.getInstance().getDriver();
    private final String xpath;
    private By by;
    private By concatenated;
    private Supplier<SearchContext> searchContextSupplier;
    private LoadableComponent<WebElement> webElementLoader;
    private LoadableComponent<List<WebElement>> webElementsLoader;

    public SeleniumElementLocator(Object parent, String xpath, int index) {
        super(parent, index);
        this.xpath = xpath;
    }

    @Override
    public List<WebElement> locateAll() {
        return findElements();
    }

    @Override
    public WebElement locate() {
        if (getIndex() != -1) {
            return findElements().get(getIndex());
        }
        return findElement();
    }

    public void nativeConsumer(Consumer<WebElement> consumer) {
        new LoadableComponent<Void>() {
            @Override
            public long waitFor() {
                return DriverService.getInstance().getImplicitlyTimeout();
            }

            @Override
            public Void makeTry() {
                WebElement webElement = createWebElementLoader().makeTry();
                consumer.accept(webElement);
                return null;
            }
        }.load();
    }

    public <T> T nativeFunction(Function<WebElement, T> function) {
        return new LoadableComponent<T>() {
            @Override
            public long waitFor() {
                return DriverService.getInstance().getImplicitlyTimeout();
            }

            @Override
            public T makeTry() {
                WebElement webElement = createWebElementLoader().makeTry();
                return function.apply(webElement);
            }
        }.load();
    }

    @Override
    public boolean exists() {
        return !findElements().isEmpty();
    }

    @Override
    public int count() {
        return findElements().size();
    }

    @NotNull
    public List<SimpleEntry<String, Integer>> getXPathChain() {
        List<SimpleEntry<String, Integer>> result = new ArrayList<>();
        SeleniumElementLocator cursor = this;
        while (cursor != null) {
            Object parent = cursor.getParent();
            result.add(new SimpleEntry<>(cursor.xpath, cursor.getIndex()));
            if (parent == null) {
                cursor = null;
            } else {
                Class<?> pClass = parent.getClass();
                if (SeleniumPage.class.isAssignableFrom(pClass)) {
                    SeleniumPage page = (SeleniumPage) parent;
                    if (page.getXPath() != null) {
                        result.add(new SimpleEntry<>(page.getXPath(), -1));
                    }
                    cursor = null;
                } else if (SeleniumElementList.class.isAssignableFrom(pClass)) {
                    cursor = (SeleniumElementLocator) ((SeleniumElementList<?>) parent).get(getIndex()).getAnchor().getLocator();
                } else if (SeleniumElement.class.isAssignableFrom(pClass)) {
                    cursor = (SeleniumElementLocator) ((SeleniumElement) parent).getAnchor().getLocator();
                } else {
                    throw new UndefinedParentException(pClass.getCanonicalName() + " is not acceptable for " + getClass().getSimpleName());
                }
            }
        }
        return result;
    }

    public String getXpath() {
        return xpath;
    }

    public By getXPathAsBy() {
        if (by == null) {
            by = By.xpath(xpath);
        }
        return by;
    }

    private WebElement findElement() {
        if (webElementLoader == null) {
            webElementLoader = createWebElementLoader();
        }
        return webElementLoader.load();
    }

    private List<WebElement> findElements() {
        if (webElementsLoader == null) {
            webElementsLoader = createWebElementsLoader();
        }
        return webElementsLoader.load();
    }

    private By getConcatenated() {
        if (concatenated == null) {
            concatenated = By.xpath(concat(getXPathChain()));
        }
        return concatenated;
    }

    private LoadableComponent<WebElement> createWebElementLoader() {
        return new LoadableComponent<WebElement>() {
            @Override
            public long waitFor() {
                return DriverService.getInstance().getImplicitlyTimeout();
            }

            @Override
            public WebElement makeTry() {
                if (SELENIUM_CONFIGURATION.isXPathConcatenationEnabled()) {
                    return BASE_SEARCH_CONTEXT_SUPPLIER.get().findElement(getConcatenated());
                }
                return getSearchContextSupplier().get().findElement(getXPathAsBy());
            }
        };
    }

    private LoadableComponent<List<WebElement>> createWebElementsLoader() {
        return new LoadableComponent<List<WebElement>>() {
            @Override
            public long waitFor() {
                return DriverService.getInstance().getImplicitlyTimeout();
            }

            @Override
            public List<WebElement> makeTry() {
                if (SELENIUM_CONFIGURATION.isXPathConcatenationEnabled()) {
                    return BASE_SEARCH_CONTEXT_SUPPLIER.get().findElements(getConcatenated());
                }
                return getSearchContextSupplier().get().findElements(getXPathAsBy());
            }
        };
    }

    @NotNull
    private Supplier<SearchContext> getSearchContextSupplier() {
        if (searchContextSupplier == null) {
            searchContextSupplier = BASE_SEARCH_CONTEXT_SUPPLIER;
            if (getParent() == null) {
                // If no parent, then we have to use searching through the drive
                return searchContextSupplier;
            } else {
                Supplier<SearchContext> suggestion = suggestNativeContextSupplier();
                if (suggestion != null) {
                    searchContextSupplier = suggestion;
                }
            }
        }
        return searchContextSupplier;
    }

    /**
     * Suggest a Search Context which rely on searching by a parent context through the drive.
     *
     * @return Suggested Search Context, it can be null if no suggestion
     */
    @Nullable
    private Supplier<SearchContext> suggestNativeContextSupplier() {
        Class<?> pClass = getParent().getClass();
        if (SeleniumElement.class.isAssignableFrom(pClass)) {
            // Make a link to the parent element
            return () -> ((SeleniumElement) getParent()).getAnchor().getLocator().locate();
        }
        if (SeleniumElementList.class.isAssignableFrom(pClass)) {
            // Make a link to the parent element when it is a list
            return () -> (WebElement) ((SeleniumElementList) getParent()).get(getIndex()).getAnchor().getLocator().locate();
        } else if (SeleniumPage.class.isAssignableFrom(pClass)) {
            SeleniumPage page = (SeleniumPage) getParent();
            if (page.getXPath() != null) {
                // Create context using the page locator
                SeleniumElementLocator locator = new SeleniumElementLocator(null, page.getXPath(), -1);
                return locator::findElement;
            }
            // No suggestion if the page does not have a locator
            return null;
        }
        throw new UndefinedParentException(pClass.getCanonicalName() + " is not acceptable for " + getClass().getSimpleName());
    }
}
