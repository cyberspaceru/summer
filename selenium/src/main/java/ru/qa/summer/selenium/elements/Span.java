package ru.qa.summer.selenium.elements;

import ru.qa.summer.selenium.SeleniumAnchor;
import ru.qa.summer.selenium.SeleniumElement;

public class Span extends SeleniumElement {
    public Span(SeleniumAnchor anchor) {
        super(anchor);
    }

    public Span(Object parent, int index, String xpath) {
        super(parent, index, xpath);
    }

    public Span(Object parent, String xpath) {
        super(parent, xpath);
    }

    public Span(String xpath) {
        super(xpath);
    }
}
