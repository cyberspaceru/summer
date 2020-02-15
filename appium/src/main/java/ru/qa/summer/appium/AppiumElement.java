package ru.qa.summer.appium;

import org.openqa.selenium.*;
import ru.qa.summer.Element;

import java.util.List;

public class AppiumElement extends Element<AppiumAnchor> implements WebElement {
    public AppiumElement(AppiumAnchor anchor) {
        super(anchor, AppiumFieldDecorator.class);
    }

    @Override
    public void click() {
        getAnchor().getLocator().locate().click();
    }

    @Override
    public void submit() {
        getAnchor().getLocator().locate().submit();
    }

    @Override
    public void sendKeys(CharSequence... keysToSend) {
        getAnchor().getLocator().locate().sendKeys(keysToSend);
    }

    @Override
    public void clear() {
        getAnchor().getLocator().locate().clear();
    }

    @Override
    public String getTagName() {
        return getAnchor().getLocator().locate().getTagName();
    }

    @Override
    public String getAttribute(String name) {
        return getAnchor().getLocator().locate().getAttribute(name);
    }

    @Override
    public boolean isSelected() {
        return getAnchor().getLocator().locate().isSelected();
    }

    @Override
    public boolean isEnabled() {
        return getAnchor().getLocator().locate().isEnabled();
    }

    @Override
    public String getText() {
        return getAnchor().getLocator().locate().getText();
    }

    @Override
    public <T extends WebElement> List<T> findElements(By by) {
        return getAnchor().getLocator().locate().findElements(by);
    }

    @Override
    public <T extends WebElement> T findElement(By by) {
        return getAnchor().getLocator().locate().findElement(by);
    }

    @Override
    public boolean isDisplayed() {
        return getAnchor().getLocator().locate().isDisplayed();
    }

    @Override
    public Point getLocation() {
        return getAnchor().getLocator().locate().getLocation();
    }

    @Override
    public Dimension getSize() {
        return getAnchor().getLocator().locate().getSize();
    }

    @Override
    public Rectangle getRect() {
        return getAnchor().getLocator().locate().getRect();
    }

    @Override
    public String getCssValue(String propertyName) {
        return getAnchor().getLocator().locate().getCssValue(propertyName);
    }

    @Override
    public <X> X getScreenshotAs(OutputType<X> target) throws WebDriverException {
        return getAnchor().getLocator().locate().getScreenshotAs(target);
    }

    public boolean exists() {
        return getAnchor().getLocator().exists();
    }
}
