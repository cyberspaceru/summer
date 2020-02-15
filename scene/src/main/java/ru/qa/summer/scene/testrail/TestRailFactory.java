package ru.qa.summer.scene.testrail;

import com.codepine.api.testrail.TestRail;

import static ru.qa.summer.scene.SceneConfiguration.INSTANCE;

public class TestRailFactory {
    private static SceneTestRail testRailInstance;

    public SceneTestRail create() {
        if (testRailInstance == null) {
            testRailInstance = new SceneTestRail(TestRail.builder(INSTANCE.getSceneTestrailUrl(),
                    INSTANCE.getSceneTestrailUsername(), INSTANCE.getSceneTestrailPassword()).build());
        }
        return testRailInstance;
    }
}
