package ru.qa.summer.appium;

import lombok.Getter;
import ru.qa.summer.PageAnnotator;
import ru.qa.summer.appium.annotations.Id;
import ru.qa.summer.appium.annotations.XPath;

import java.lang.annotation.Annotation;
import java.util.List;

@Getter
public class AppiumPageAnnotator extends PageAnnotator {
    private String id;
    private String xpath;

    public AppiumPageAnnotator(List<Annotation> annotations) {
        super(annotations);
        this.xpath = find(annotations, XPath.class).map(XPath::value).orElse(null);
        if (xpath == null) {
            this.id = find(annotations, Id.class).map(Id::value).orElse(null);
        }
    }
}
