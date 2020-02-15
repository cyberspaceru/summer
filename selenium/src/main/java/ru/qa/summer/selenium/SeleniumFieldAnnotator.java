package ru.qa.summer.selenium;

import ru.qa.summer.FieldAnnotator;
import ru.qa.summer.exceptions.AnnotationNotFoundException;
import ru.qa.summer.selenium.annotations.XPath;
import ru.qa.summer.selenium.annotations.XPaths;
import ru.qa.summer.selenium.exceptions.XPathLanguageDuplicationException;

import java.lang.annotation.Annotation;
import java.util.List;
import java.util.UUID;

import static ru.qa.summer.selenium.SeleniumConfiguration.SELENIUM_CONFIGURATION;

public class SeleniumFieldAnnotator extends FieldAnnotator {
    private String xpath;

    public SeleniumFieldAnnotator(List<Annotation> annotations) {
        super(annotations);
        XPath[] xPaths = find(annotations, XPaths.class)
                .map(XPaths::value)
                .orElseGet(() -> find(annotations, XPath.class).map(x -> new XPath[]{x})
                        .orElseThrow(() -> new AnnotationNotFoundException(XPath.class)));
        this.xpath = resolveXPathAnnotation(xPaths);
    }

    private SeleniumFieldAnnotator(String name, String xpath) {
        super(name);
        this.xpath = xpath;
    }

    public String getXPath() {
        return xpath;
    }

    private static String resolveXPathAnnotation(XPath[] xPaths) {
        if (xPaths.length == 1) {
            return xPaths[0].value();
        }
        XPath result = null;
        for (XPath x : xPaths) {
            for (XPath y : xPaths) {
                if (x != y && x.language().equalsIgnoreCase(y.language())) {
                    throw new XPathLanguageDuplicationException("XPath language '" + y.language() + "' duplicated as minimal twice");
                }
            }
            if (SELENIUM_CONFIGURATION.getXPathLanguage().equalsIgnoreCase(x.language())) {
                result = x;
                break;
            }
        }
        return result == null ? xPaths[0].value() : result.value();
    }

    public static class SeleniumAnnotatorBuilder {
        private String name;
        private String xpath;

        private SeleniumAnnotatorBuilder(String xpath) {
            this.name = "undeclared-" + UUID.randomUUID().toString();
            this.xpath = xpath;
        }

        public SeleniumAnnotatorBuilder setName(String name) {
            this.name = name;
            return this;
        }

        public SeleniumFieldAnnotator build() {
            return new SeleniumFieldAnnotator(name, xpath);
        }

        public static SeleniumAnnotatorBuilder create(String xpath) {
            return new SeleniumAnnotatorBuilder(xpath);
        }
    }
}
