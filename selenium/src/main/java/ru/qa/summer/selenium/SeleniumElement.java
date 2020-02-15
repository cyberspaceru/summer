package ru.qa.summer.selenium;

import org.jetbrains.annotations.Nullable;
import org.openqa.selenium.*;
import ru.qa.summer.Element;
import ru.qa.summer.selenium.SeleniumFieldAnnotator.SeleniumAnnotatorBuilder;
import ru.qa.summer.selenium.fluents.Fluent;
import ru.qa.summer.selenium.util.SeleniumElementUtil;
import ru.qa.summer.utils.InstantiateCoreUtils;

import java.util.List;

import static java.util.Optional.ofNullable;
import static ru.qa.summer.support.util.WaitingUtil.loadable;

public class SeleniumElement extends Element<SeleniumAnchor> implements WebElement {
    public SeleniumElement(SeleniumAnchor anchor) {
        super(anchor, SeleniumFieldDecorator.class);
    }

    public SeleniumElement(Object parent, int index, String xpath) {
        super(new SeleniumAnchor(parent, index, SeleniumAnnotatorBuilder.create(xpath).build()));
    }

    public SeleniumElement(Object parent, String xpath) {
        this(parent, -1, xpath);
    }

    public SeleniumElement(String xpath) {
        this(null, -1, xpath);
    }

    /**
     * {@link #click()} will be invoked if {@param isRequired} is true otherwise not be.
     */
    public Fluent<SeleniumElement> optionalClick(@Nullable Boolean isRequired) {
        if (isRequired != null && isRequired) {
            click();
            return new Fluent<>(this, false);
        }
        return new Fluent<>(this, true);
    }

    /**
     * {@link #sendKeys(CharSequence...)} will be invoked if {@param l} is not null otherwise not be.
     */
    public Fluent<SeleniumElement> optionalSendKeys(@Nullable Long l) {
        return optionalSendKeys(l == null ? null : l.toString());
    }

    /**
     * {@link #sendKeys(CharSequence...)} will be invoked if {@param keysToSend} is not null and items of it are not empty too otherwise not be.
     */
    public Fluent<SeleniumElement> optionalSendKeys(@Nullable CharSequence... keysToSend) {
        if (SeleniumElementUtil.isValid(keysToSend)) {
            sendKeys(keysToSend);
            return new Fluent<>(this, false);
        }
        return new Fluent<>(this, true);
    }

    /**
     * {@link #clearAndSendKeys(CharSequence...)} will be invoked if {@param keysToSend} is not null and items of it are not empty too otherwise not be.
     */
    public Fluent<SeleniumElement> optionalClearAndSendKeys(@Nullable CharSequence... keysToSend) {
        if (SeleniumElementUtil.isValid(keysToSend)) {
            clearAndSendKeys(keysToSend);
            return new Fluent<>(this, false);
        }
        return new Fluent<>(this, true);
    }

    /**
     * Invoke {@link #clear()} and then {@link #sendKeys(CharSequence...)}} with {@param keysToSend}.
     */
    public void clearAndSendKeys(@Nullable CharSequence... keysToSend) {
        clear();
        sendKeys(keysToSend);
    }

    public boolean exists() {
        return getAnchor().getLocator().exists();
    }

    public void waitForClassContains(String value) {
        loadable(() -> ofNullable(getClassAttribute())
                .filter(x -> x.contains(value))
                .orElseThrow(() -> new AssertionError("'" + getAnchor().getAnnotator().getName() +
                        "' element doesn't contain '" + value + "' in the class attribute(" + getClassAttribute() + ")")));
    }

    public String getClassAttribute() {
        return getAttribute("class");
    }

    public String getValueAttribute() {
        return getAttribute("value");
    }

    // region WebElement methods
    @Override
    public void click() {
        ((SeleniumElementLocator) getAnchor().getLocator()).nativeConsumer(WebElement::click);
    }

    @Override
    public void submit() {
        ((SeleniumElementLocator) getAnchor().getLocator()).nativeConsumer(WebElement::submit);
    }

    @Override
    public void sendKeys(CharSequence... keysToSend) {
        ((SeleniumElementLocator) getAnchor().getLocator()).nativeConsumer(x -> x.sendKeys(keysToSend));
    }

    @Override
    public void clear() {
        ((SeleniumElementLocator) getAnchor().getLocator()).nativeConsumer(WebElement::clear);
    }

    @Override
    public String getTagName() {
        return ((SeleniumElementLocator) getAnchor().getLocator()).nativeFunction(WebElement::getTagName);
    }

    @Override
    public String getAttribute(String name) {
        return ((SeleniumElementLocator) getAnchor().getLocator()).nativeFunction(x -> x.getAttribute(name));
    }

    @Override
    public boolean isSelected() {
        return ((SeleniumElementLocator) getAnchor().getLocator()).nativeFunction(WebElement::isSelected);
    }

    @Override
    public boolean isEnabled() {
        return ((SeleniumElementLocator) getAnchor().getLocator()).nativeFunction(WebElement::isEnabled);
    }

    @Override
    public String getText() {
        return ((SeleniumElementLocator) getAnchor().getLocator()).nativeFunction(WebElement::getText);
    }

    @Override
    public List<WebElement> findElements(By by) {
        return getAnchor().getLocator().locate().findElements(by);
    }

    @Override
    public WebElement findElement(By by) {
        return getAnchor().getLocator().locate().findElement(by);
    }

    @Override
    public boolean isDisplayed() {
        return ((SeleniumElementLocator) getAnchor().getLocator()).nativeFunction(WebElement::isDisplayed);
    }

    @Override
    public Point getLocation() {
        return ((SeleniumElementLocator) getAnchor().getLocator()).nativeFunction(WebElement::getLocation);
    }

    @Override
    public Dimension getSize() {
        return ((SeleniumElementLocator) getAnchor().getLocator()).nativeFunction(WebElement::getSize);
    }

    @Override
    public Rectangle getRect() {
        return ((SeleniumElementLocator) getAnchor().getLocator()).nativeFunction(WebElement::getRect);
    }

    @Override
    public String getCssValue(String propertyName) {
        return ((SeleniumElementLocator) getAnchor().getLocator()).nativeFunction(x -> x.getCssValue(propertyName));
    }

    @Override
    public <X> X getScreenshotAs(OutputType<X> target) throws WebDriverException {
        return ((SeleniumElementLocator) getAnchor().getLocator()).nativeFunction(x -> x.getScreenshotAs(target));
    }
    // endregion

    // region Reflection methods
    public <T extends Element> T to(Class<T> tClass) {
        return InstantiateCoreUtils.instantiateElement(tClass, getAnchor());
    }

    @SuppressWarnings("unchecked")
    public <T extends SeleniumElement> SeleniumElementList<T> toList(Class<T> tClass) {
        // Because a list will copy an anchor with another index (when .get(int index)) we have to instantiate a stub
        // And the list can copy it safety
        SeleniumFieldAnnotator annotator = SeleniumAnnotatorBuilder.create(".//*").build();
        SeleniumAnchor anchor = new SeleniumAnchor(this, -1, annotator);
        return InstantiateCoreUtils.instantiateElementList(SeleniumElementList.class, anchor, tClass);
    }
    // endregion
}
