package ru.qa.summer.selenium.elements;

import org.openqa.selenium.Keys;
import ru.qa.summer.selenium.SeleniumAnchor;
import ru.qa.summer.selenium.SeleniumElement;
import ru.qa.summer.selenium.util.ClipboardUtil;

public class Input extends SeleniumElement {
    public Input(SeleniumAnchor anchor) {
        super(anchor);
    }

    public Input(Object parent, int index, String xpath) {
        super(parent, index, xpath);
    }

    public Input(Object parent, String xpath) {
        super(parent, xpath);
    }

    public Input(String xpath) {
        super(xpath);
    }

    /**
     * Используется для имитации заполнения поля через Ctrl+c/Ctrl+v.
     *
     * @param string строка, которой заполняется поле через команду Ctrl+v.
     */
    public void sendStringFromClipboard(String string) {
        ClipboardUtil.copyStringToClipboard(string);
        this.sendKeys(Keys.CONTROL, "v");
    }
}
