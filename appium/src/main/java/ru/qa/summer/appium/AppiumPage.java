package ru.qa.summer.appium;

import ru.qa.summer.Page;

public class AppiumPage extends Page<AppiumPageAnnotator, AppiumFieldDecorator> {

    public AppiumPage() {
        super(AppiumPageAnnotator.class, AppiumFieldDecorator.class);
    }

    public String getXPath() {
        return annotator.getXpath();
    }

    public String getId() {
        return annotator.getId();
    }
}
