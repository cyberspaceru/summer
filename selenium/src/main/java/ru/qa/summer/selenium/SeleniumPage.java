package ru.qa.summer.selenium;

import ru.qa.summer.Page;
import ru.qa.summer.selenium.services.DriverService;

public abstract class SeleniumPage extends Page<SeleniumPageAnnotator, SeleniumFieldDecorator> {
    private static Class<?> lastInstantiated;

    public SeleniumPage() {
        super(SeleniumPageAnnotator.class, SeleniumFieldDecorator.class);
        if (getAnnotator().isOpen() && !getClass().equals(lastInstantiated)) {
            refresh();
        }
        lastInstantiated = getClass();
    }

    public String getXPath() {
        return annotator.getXpath();
    }

    public void refresh() {
        DriverService.getInstance().open(annotator.getUrl());
    }

    public SeleniumElement getAsSeleniumElement(String name) {
        return this.get(name, SeleniumElement.class);
    }
}
