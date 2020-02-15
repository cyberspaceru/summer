package ru.qa.summer.selenium.pages.base.elements;

import ru.qa.summer.annotations.ElementName;
import ru.qa.summer.selenium.SeleniumAnchor;
import ru.qa.summer.selenium.SeleniumElement;
import ru.qa.summer.selenium.annotations.XPath;

public class ListItemWithSection extends SeleniumElement {
    @ElementName("Label")
    @XPath(".//strong")
    private SeleniumElement label;

    @ElementName("Button")
    @XPath(".//button")
    private SeleniumElement button;

    public ListItemWithSection(SeleniumAnchor anchor) {
        super(anchor);
    }

    public SeleniumElement getLabel() {
        return label;
    }

    public SeleniumElement getButton() {
        return button;
    }
}
