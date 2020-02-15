package ru.qa.summer.selenium.elements;

import ru.qa.summer.selenium.SeleniumAnchor;
import ru.qa.summer.selenium.SeleniumElement;

public class RadioButton extends SeleniumElement {
    public RadioButton(SeleniumAnchor anchor) {
        super(anchor);
    }

    public RadioButton(Object parent, int index, String xpath) {
        super(parent, index, xpath);
    }

    public RadioButton(Object parent, String xpath) {
        super(parent, xpath);
    }

    public RadioButton(String xpath) {
        super(xpath);
    }
}
