package ru.qa.summer.scene.adapters;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestWatcher;
import ru.qa.summer.scene.Scene;
import ru.qa.summer.scene.testrail.TestRailFactory;

import static ru.qa.summer.scene.SceneConfiguration.INSTANCE;

public class SceneJUnitTestWatcher implements TestWatcher {
    @Override
    public void testSuccessful(ExtensionContext context) {
        if (INSTANCE.getSceneTestrailEnableReporting()) {
            Scene scene = getScene(context);
            new TestRailFactory().create().addResultAsPassed(scene);
        }
    }

    @Override
    public void testFailed(ExtensionContext context, Throwable cause) {
        if (INSTANCE.getSceneTestrailEnableReporting()) {
            Scene scene = getScene(context);
            new TestRailFactory().create().addResultAsFailed(scene, cause);
        }
    }

    private Scene getScene(ExtensionContext context) {
        return context.getTestClass().map(Scene::new).orElse(null);
    }
}
