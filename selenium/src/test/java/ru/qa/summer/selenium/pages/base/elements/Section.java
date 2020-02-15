package ru.qa.summer.selenium.pages.base.elements;

import ru.qa.summer.annotations.ElementName;
import ru.qa.summer.selenium.SeleniumAnchor;
import ru.qa.summer.selenium.SeleniumElement;
import ru.qa.summer.selenium.SeleniumElementList;
import ru.qa.summer.selenium.annotations.XPath;
import ru.qa.summer.selenium.elements.TextArea;

public class Section extends SeleniumElement {
    @ElementName("Label")
    @XPath(".//div[@id = 'section-label']")
    private SeleniumElement label;

    @ElementName("Text Area")
    @XPath(".//textarea[@id = 'section-textarea']")
    private TextArea textArea;

    @ElementName("Sub-Section")
    @XPath(".//div[@id = 'sub-section']")
    private SubSection subSection;

    @ElementName("List")
    @XPath(".//ol[@id = 'section-list']/li")
    private SeleniumElementList<SeleniumElement> list;

    public Section(SeleniumAnchor anchor) {
        super(anchor);
    }

    public SeleniumElement getLabel() {
        return label;
    }

    public TextArea getTextArea() {
        return textArea;
    }

    public SubSection getSubSection() {
        return subSection;
    }

    public SeleniumElementList<SeleniumElement> getList() {
        return list;
    }
}
