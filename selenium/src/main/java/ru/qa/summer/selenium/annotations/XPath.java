package ru.qa.summer.selenium.annotations;

import java.lang.annotation.*;

@Repeatable(XPaths.class)
@Target({ElementType.FIELD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface XPath {
    String value();

    String language() default "eng";
}
