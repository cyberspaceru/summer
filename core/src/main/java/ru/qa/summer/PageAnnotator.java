package ru.qa.summer;

import ru.qa.summer.annotations.PageName;
import ru.qa.summer.exceptions.AnnotationNotFoundException;

import java.lang.annotation.Annotation;
import java.util.List;

public class PageAnnotator extends Annotator {
    private final String name;

    public PageAnnotator(List<Annotation> annotations) {
        this.name = find(annotations, PageName.class)
                .map(PageName::value)
                .orElseThrow(() -> new AnnotationNotFoundException(PageName.class));
    }

    public String getName() {
        return name;
    }
}
