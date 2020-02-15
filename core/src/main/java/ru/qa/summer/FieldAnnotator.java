package ru.qa.summer;

import ru.qa.summer.annotations.ElementName;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.List;

public abstract class FieldAnnotator extends Annotator {
    private final String name;

    public FieldAnnotator(List<Annotation> annotations) {
        this.name = find(annotations, ElementName.class)
                .map(ElementName::value)
                .orElse(null);
    }

    public FieldAnnotator(List<Annotation> annotations, Field field) {
        this.name = find(annotations, ElementName.class)
                .map(ElementName::value)
                .orElse(field.getName());
    }

    protected FieldAnnotator(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
