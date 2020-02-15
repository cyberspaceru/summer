package ru.qa.summer.scene;

import org.aeonbits.owner.Config;
import org.aeonbits.owner.ConfigFactory;

import static ru.qa.summer.scene.SceneConfiguration.PROPERTY_PATH;

@Config.Sources("classpath:" + PROPERTY_PATH)
public interface SceneConfiguration extends Config {
    String PROPERTY_PATH = "scene.properties";
    String SCENE_TESTRAIL_URL_PROPERTY_NAME = "scene.testrail.url";
    String SCENE_TESTRAIL_USERNAME_PROPERTY_NAME = "scene.testrail.username";
    String SCENE_TESTRAIL_PASSWORD_PROPERTY_NAME = "scene.testrail.password";
    String SCENE_JIRA_URL_PROPERTY_NAME = "scene.jira.url";
    String SCENE_JIRA_USERNAME_PROPERTY_NAME = "scene.jira.username";
    String SCENE_JIRA_PASSWORD_PROPERTY_NAME = "scene.jira.password";

    SceneConfiguration INSTANCE = ConfigFactory.create(SceneConfiguration.class);

    @Key(SCENE_TESTRAIL_URL_PROPERTY_NAME)
    String getSceneTestrailUrl();

    @Key(SCENE_TESTRAIL_USERNAME_PROPERTY_NAME)
    String getSceneTestrailUsername();

    @Key(SCENE_TESTRAIL_PASSWORD_PROPERTY_NAME)
    String getSceneTestrailPassword();

    @Key("scene.testrail.enable-reporting")
    @DefaultValue("false")
    Boolean getSceneTestrailEnableReporting();

    @Key(SCENE_JIRA_URL_PROPERTY_NAME)
    String getSceneJiraUrl();

    @Key(SCENE_JIRA_USERNAME_PROPERTY_NAME)
    String getSceneJiraUsername();

    @Key(SCENE_JIRA_PASSWORD_PROPERTY_NAME)
    String getSceneJiraPassword();

    @Key("scene.request")
    String getSceneRequest();
}
