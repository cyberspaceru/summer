package ru.qa.summer.context;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.qa.summer.context.annotations.ContextObject;
import ru.qa.summer.support.exceptions.AccessException;

import java.util.List;

public class YAMLTest {
    private EntityL1 one;
    @ContextObject
    private List<EntityL1> list;
    private String content;

    @BeforeEach
    public void setup() throws AccessException {
//        content = new ResourceAccessObject("test.yaml").load();
    }

    @Test
    public void test() {
//        YamlEntityProvider provider = new YamlEntityProvider(content);
//        Context.current().add(provider);
//        new CachedContextFieldDecorator().decorate(this);
//        one.intL1();
    }
}
