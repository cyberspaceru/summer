package ru.qa.summer.appium.elements;

import ru.qa.summer.appium.AppiumAnchor;
import ru.qa.summer.appium.AppiumElement;

import static java.util.Optional.ofNullable;

public class Toggle extends AppiumElement {
    public Toggle(AppiumAnchor anchor) {
        super(anchor);
    }

    public void setState(boolean value) {
        boolean isSwitched = isSwitched();
        if ((isSwitched && !value) || (!isSwitched && value)) {
            click();
        }
    }

    public boolean isSwitched() {
        return ofNullable(getAttribute("checked")).map(Boolean::new).orElse(false);
    }
}
