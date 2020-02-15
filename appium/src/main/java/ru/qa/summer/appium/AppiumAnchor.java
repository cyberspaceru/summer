package ru.qa.summer.appium;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.openqa.selenium.WebElement;
import ru.qa.summer.Anchor;
import ru.qa.summer.ElementLocator;

public class AppiumAnchor extends Anchor<WebElement, AppiumFieldAnnotator> {
    public AppiumAnchor(@Nullable Object parent, int index, @NotNull AppiumFieldAnnotator annotator) {
        super(parent, index, annotator);
    }

    @Override
    protected @NotNull ElementLocator<WebElement> createElementLocator() {
        return new AppiumElementLocator(getParent(), getAnnotator().getXpath(), getAnnotator().getId(), getIndex());
    }
}
