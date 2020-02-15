package ru.qa.summer.selenium.elements;

import ru.qa.summer.selenium.SeleniumAnchor;
import ru.qa.summer.selenium.SeleniumElement;

public class TextArea extends SeleniumElement {
    public TextArea(SeleniumAnchor anchor) {
        super(anchor);
    }

    public TextArea(Object parent, int index, String xpath) {
        super(parent, index, xpath);
    }

    public TextArea(Object parent, String xpath) {
        super(parent, xpath);
    }

    public TextArea(String xpath) {
        super(xpath);
    }

    @Override
    public String getText() {
        String value = getAttribute("value");
        return value != null ? value : "";
    }
}
