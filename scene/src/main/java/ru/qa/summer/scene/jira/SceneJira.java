package ru.qa.summer.scene.jira;

import net.rcarz.jiraclient.Issue;
import net.rcarz.jiraclient.JiraClient;
import net.rcarz.jiraclient.JiraException;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class SceneJira {
    private final JiraClient jira;
    private static Map<String, Issue> issues = new ConcurrentHashMap<>();

    public SceneJira(JiraClient jira) {
        this.jira = jira;
    }

    public List<Issue> searchBySummary(String summary) throws JiraException {
        return jira.searchIssues("summary ~ \"" + summary + "\"").issues;
    }
}
