package ru.qa.summer.selenium;

import org.jetbrains.annotations.NotNull;
import org.openqa.selenium.WebElement;
import ru.qa.summer.Anchor;
import ru.qa.summer.ElementLocator;

public class SeleniumAnchor extends Anchor<WebElement, SeleniumFieldAnnotator> {
    public SeleniumAnchor(Object parent, int index, SeleniumFieldAnnotator annotator) {
        super(parent, index, annotator);
    }

    @NotNull
    @Override
    protected ElementLocator<WebElement> createElementLocator() {
        return new SeleniumElementLocator(getParent(), getAnnotator().getXPath(), getIndex());
    }
}
