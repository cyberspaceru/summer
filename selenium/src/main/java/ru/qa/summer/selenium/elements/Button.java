package ru.qa.summer.selenium.elements;

import ru.qa.summer.selenium.SeleniumAnchor;
import ru.qa.summer.selenium.SeleniumElement;

public class Button extends SeleniumElement {
    public Button(SeleniumAnchor anchor) {
        super(anchor);
    }

    public Button(Object parent, int index, String xpath) {
        super(parent, index, xpath);
    }

    public Button(Object parent, String xpath) {
        super(parent, xpath);
    }

    public Button(String xpath) {
        super(xpath);
    }
}
