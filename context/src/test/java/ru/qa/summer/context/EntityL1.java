package ru.qa.summer.context;

import ru.qa.summer.context.annotations.ContextFieldName;
import ru.qa.summer.context.annotations.ContextObjectName;

import java.util.List;

@ContextObjectName("Entity L1")
public interface EntityL1 {
    @ContextFieldName("Entity L2")
    EntityL2 entityL2();

    @ContextFieldName("Int L1")
    List<Integer> intL1();
}
