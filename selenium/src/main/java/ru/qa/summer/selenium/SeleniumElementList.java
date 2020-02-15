package ru.qa.summer.selenium;

import ru.qa.summer.ElementList;

public class SeleniumElementList<T extends SeleniumElement> extends ElementList<T, SeleniumAnchor> {
    public SeleniumElementList(SeleniumAnchor anchor, Class<T> tClass) {
        super(anchor, tClass);
    }

    @Override
    protected SeleniumAnchor copyAnchor(int index) {
        return new SeleniumAnchor(getAnchor().getParent(), index, getAnchor().getAnnotator());
    }
}
