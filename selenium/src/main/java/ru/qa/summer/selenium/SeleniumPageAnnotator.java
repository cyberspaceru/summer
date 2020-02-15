package ru.qa.summer.selenium;

import ru.qa.summer.PageAnnotator;
import ru.qa.summer.selenium.annotations.WebDriver;
import ru.qa.summer.selenium.annotations.XPath;

import java.lang.annotation.Annotation;
import java.util.List;

public class SeleniumPageAnnotator extends PageAnnotator {
    private final String xpath;
    private final String url;
    private final boolean open;

    public SeleniumPageAnnotator(List<Annotation> annotations) {
        super(annotations);
        this.xpath = find(annotations, XPath.class).map(XPath::value).orElse(null);
        WebDriver webDriver = find(annotations, WebDriver.class).orElse(null);
        this.url = webDriver == null ? null : webDriver.url();
        this.open = webDriver != null && webDriver.open();
    }

    public String getXpath() {
        return xpath;
    }

    public String getUrl() {
        return url;
    }

    public boolean isOpen() {
        return open;
    }
}
