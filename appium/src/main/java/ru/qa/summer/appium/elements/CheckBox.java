package ru.qa.summer.appium.elements;

import ru.qa.summer.appium.AppiumAnchor;
import ru.qa.summer.appium.AppiumElement;

import static java.util.Optional.ofNullable;

public class CheckBox extends AppiumElement {
    public CheckBox(AppiumAnchor anchor) {
        super(anchor);
    }

    public void setState(boolean value) {
        boolean isChecked = isChecked();
        if ((isChecked && !value) || (!isChecked && value)) {
            click();
        }
    }

    public boolean isChecked() {
        return ofNullable(getAttribute("checked")).map(Boolean::new).orElse(false);
    }
}
