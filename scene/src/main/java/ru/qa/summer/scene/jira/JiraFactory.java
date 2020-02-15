package ru.qa.summer.scene.jira;

import net.rcarz.jiraclient.BasicCredentials;
import net.rcarz.jiraclient.JiraClient;

import static ru.qa.summer.scene.SceneConfiguration.INSTANCE;

public class JiraFactory {
    private SceneJira sceneJiraInstance;

    public SceneJira create() {
        if (sceneJiraInstance == null) {
            BasicCredentials credentials = new BasicCredentials(INSTANCE.getSceneJiraUsername(), INSTANCE.getSceneJiraPassword());
            sceneJiraInstance = new SceneJira(new JiraClient(INSTANCE.getSceneJiraUrl(), credentials));
        }
        return sceneJiraInstance;
    }
}
