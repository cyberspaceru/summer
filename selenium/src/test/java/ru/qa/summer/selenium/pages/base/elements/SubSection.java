package ru.qa.summer.selenium.pages.base.elements;

import ru.qa.summer.annotations.ElementName;
import ru.qa.summer.selenium.SeleniumAnchor;
import ru.qa.summer.selenium.SeleniumElement;
import ru.qa.summer.selenium.annotations.XPath;

public class SubSection extends SeleniumElement {
    @ElementName("Label")
    @XPath(".//div[@id = 'sub-section-label']")
    private SeleniumElement label;

    public SubSection(SeleniumAnchor anchor) {
        super(anchor);
    }

    public SeleniumElement getLabel() {
        return label;
    }
}
