package ru.qa.summer.scene.adapters;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.qa.summer.context.decorators.CachedContextFieldDecorator;
import ru.qa.summer.scene.Scene;
import ru.qa.summer.support.exceptions.AccessException;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@ExtendWith(SceneJUnitTestWatcher.class)
public abstract class JUnitSceneTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(JUnitSceneTest.class);
    private final Map<String, Runnable> after = new HashMap<>();

    @BeforeEach
    public final void before() throws AccessException {
        new Scene(getClass()).patchContext();
        new CachedContextFieldDecorator().decorate(this);
        doBefore();
    }

    protected void doBefore() {

    }

    public final void doAfter(String message, Runnable runnable) {
        after.put(message, runnable);
    }

    public final void doAfter(Runnable runnable) {
        after.put("do_after|" + UUID.randomUUID().toString(), runnable);
    }

    @AfterEach
    public final void after() {
        after.forEach((k, v) -> {
            try {
                v.run();
            } catch (Throwable throwable) {
                LOGGER.warn(k, throwable);
            }
        });
        doAfter();
    }

    protected void doAfter() {

    }
}
