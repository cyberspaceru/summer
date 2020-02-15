package ru.qa.summer.appium;

import ru.qa.summer.ElementList;

public class AppiumElementList<T extends AppiumElement> extends ElementList<T, AppiumAnchor> {
    public AppiumElementList(AppiumAnchor anchor, Class<T> tClass) {
        super(anchor, tClass);
    }

    @Override
    protected AppiumAnchor copyAnchor(int index) {
        return new AppiumAnchor(getAnchor().getParent(), index, getAnchor().getAnnotator());
    }
}
