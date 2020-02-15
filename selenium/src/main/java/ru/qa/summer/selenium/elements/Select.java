package ru.qa.summer.selenium.elements;

import ru.qa.summer.annotations.ElementName;
import ru.qa.summer.selenium.SeleniumAnchor;
import ru.qa.summer.selenium.SeleniumElement;
import ru.qa.summer.selenium.SeleniumElementList;
import ru.qa.summer.selenium.annotations.XPath;

import static ru.qa.summer.support.util.WaitingUtil.loadable;

public class Select extends SeleniumElement {
    @ElementName("Options")
    @XPath(".//option")
    private SeleniumElementList<SeleniumElement> options;

    public Select(SeleniumAnchor anchor) {
        super(anchor);
    }

    public Select(Object parent, int index, String xpath) {
        super(parent, index, xpath);
    }

    public Select(Object parent, String xpath) {
        super(parent, xpath);
    }

    public Select(String xpath) {
        super(xpath);
    }

    public void selectByVisibleText(String visibleText) {
        if (visibleText == null) {
            throw new IllegalArgumentException("Visible text to choose can't be null");
        }
        click();
        loadable(() -> {
            SeleniumElement option = options.first(x -> visibleText.equalsIgnoreCase(x.getText().trim()));
            if (option == null) {
                throw new AssertionError("Can't select the '" + visibleText + "' value");
            }
            option.click();
        }).execute();
    }

}
