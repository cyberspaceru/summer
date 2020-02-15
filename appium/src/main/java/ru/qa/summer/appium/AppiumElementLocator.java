package ru.qa.summer.appium;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.RemoteWebElement;
import ru.qa.summer.ElementLocator;
import ru.qa.summer.appium.services.DriverService;
import ru.qa.summer.exceptions.UndefinedParentException;
import ru.qa.summer.support.primitives.LoadableComponent;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public class AppiumElementLocator extends ElementLocator<WebElement> {
    private static final Supplier<SearchContext> BASE_SEARCH_CONTEXT_SUPPLIER = () -> DriverService.getInstance().getDriver();
    private final String xpath;
    private final String id;
    private Supplier<SearchContext> searchContextSupplier;
    private LoadableComponent<WebElement> webElementLoader;
    private LoadableComponent<List<WebElement>> webElementsLoader;

    public AppiumElementLocator(Object parent, String xpath, String id, int index) {
        super(parent, index);
        this.xpath = xpath;
        this.id = id;
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

    private LoadableComponent<WebElement> createWebElementLoader() {
        return new LoadableComponent<WebElement>() {
            @Override
            public long waitFor() {
                return DriverService.getInstance().getImplicitlyTimeout();
            }

            @Override
            public WebElement makeTry() {
                SearchContext searchContext = getSearchContextSupplier().get();
                if (RemoteWebDriver.class.isAssignableFrom(searchContext.getClass())) {
                    if (xpath != null) {
                        return ((RemoteWebDriver) searchContext).findElementByXPath(xpath);
                    }
                    return ((RemoteWebDriver) searchContext).findElementById(id);
                }
                if (xpath != null) {
                    return ((RemoteWebElement) searchContext).findElementByXPath(xpath);
                }
                return ((RemoteWebElement) searchContext).findElementById(id);
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
                SearchContext searchContext = getSearchContextSupplier().get();
                if (RemoteWebDriver.class.isAssignableFrom(searchContext.getClass())) {
                    if (xpath != null) {
                        return ((RemoteWebDriver) searchContext).findElementsByXPath(xpath);
                    }
                    return ((RemoteWebDriver) searchContext).findElementsById(id);
                }
                if (xpath != null) {
                    return ((RemoteWebElement) searchContext).findElementsByXPath(xpath);
                }
                return ((RemoteWebElement) searchContext).findElementsById(id);
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
        if (AppiumElement.class.isAssignableFrom(pClass)) {
            // Make a link to the parent element
            return () -> ((AppiumElement) getParent()).getAnchor().getLocator().locate();
        }
        if (AppiumElementList.class.isAssignableFrom(pClass)) {
            // Make a link to the parent element when it is a list
            return () -> (WebElement) ((AppiumElementList) getParent()).get(getIndex()).getAnchor().getLocator().locate();
        } else if (AppiumPage.class.isAssignableFrom(pClass)) {
            AppiumPage page = (AppiumPage) getParent();
            if (page.getXPath() != null) {
                // Create context using the page locator
                AppiumElementLocator locator = new AppiumElementLocator(null, page.getXPath(), page.getId(), -1);
                return locator::findElement;
            }
            // No suggestion if the page does not have a locator
            return null;
        }
        throw new UndefinedParentException(pClass.getCanonicalName() + " is not acceptable for " + getClass().getSimpleName());
    }
}
