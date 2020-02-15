package ru.qa.summer.selenium.pages.base;

import ru.qa.summer.annotations.ElementName;
import ru.qa.summer.annotations.PageName;
import ru.qa.summer.selenium.SeleniumElement;
import ru.qa.summer.selenium.SeleniumPage;
import ru.qa.summer.selenium.annotations.XPath;
import ru.qa.summer.selenium.pages.base.elements.Section;

@XPath("//body")
@PageName("Base Page")
public class BasePage extends SeleniumPage {
    @ElementName("Title")
    @XPath(".//p[@id = 'page-title']")
    private SeleniumElement title;

    @ElementName("Section")
    @XPath(".//div[@id = 'section']")
    private Section section;

    public SeleniumElement getTitle() {
        return title;
    }

    public Section getSection() {
        return section;
    }
}
