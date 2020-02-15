package ru.qa.summer.selenium.elements;

import org.openqa.selenium.InvalidArgumentException;
import ru.qa.summer.selenium.SeleniumAnchor;
import ru.qa.summer.selenium.SeleniumElement;
import ru.qa.summer.selenium.fluents.Fluent;

public class CheckBox extends SeleniumElement {
    public CheckBox(SeleniumAnchor anchor) {
        super(anchor);
    }

    public CheckBox(Object parent, int index, String xpath) {
        super(parent, index, xpath);
    }

    public CheckBox(Object parent, String xpath) {
        super(parent, xpath);
    }

    public CheckBox(String xpath) {
        super(xpath);
    }

    public Fluent<CheckBox> optionalChoose(Boolean input) {
        if (input != null) {
            choose(input);
            return new Fluent<>(this, false);
        }
        return new Fluent<>(this, true);
    }

    public void choose(Boolean input) {
        if (input == null) {
            throw new InvalidArgumentException("Can't check CheckBox because of input is null");
        }
        boolean isSelected = isSelected();
        if ((isSelected && !input) || (!isSelected && input)) {
            click();
        }
    }
}
