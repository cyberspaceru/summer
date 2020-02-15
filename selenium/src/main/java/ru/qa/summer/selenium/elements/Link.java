package ru.qa.summer.selenium.elements;

import ru.qa.summer.selenium.SeleniumAnchor;
import ru.qa.summer.selenium.SeleniumElement;

public class Link extends SeleniumElement {
    public Link(SeleniumAnchor anchor) {
        super(anchor);
    }

    public Link(Object parent, int index, String xpath) {
        super(parent, index, xpath);
    }

    public Link(Object parent, String xpath) {
        super(parent, xpath);
    }

    public Link(String xpath) {
        super(xpath);
    }
}
