package ru.qa.summer;

import java.lang.annotation.Annotation;
import java.util.List;
import java.util.Optional;

public class Annotator {

    @SuppressWarnings("unchecked")
    protected static <A extends Annotation> Optional<A> find(List<Annotation> annotations, Class<A> aClass) {
        for (Annotation a : annotations) {
            if (a.annotationType().equals(aClass)) {
                return Optional.of((A) a);
            }
        }
        return Optional.empty();
    }
}
