package ru.qa.summer.appium;

import lombok.Getter;
import ru.qa.summer.FieldAnnotator;
import ru.qa.summer.appium.annotations.Id;
import ru.qa.summer.appium.annotations.XPath;
import ru.qa.summer.exceptions.AnnotationNotFoundException;

import java.lang.annotation.Annotation;
import java.util.List;

@Getter
public class AppiumFieldAnnotator extends FieldAnnotator {
    private String id;
    private String xpath;

    public AppiumFieldAnnotator(List<Annotation> annotations) {
        super(annotations);
        this.xpath = find(annotations, XPath.class).map(XPath::value).orElse(null);
        if (xpath == null) {
            this.id = find(annotations, Id.class).map(Id::value)
                    .orElseThrow(() -> new AnnotationNotFoundException(Id.class));
        }
    }
}
