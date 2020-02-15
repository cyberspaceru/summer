package ru.qa.summer.scene.testrail;

import com.codepine.api.testrail.TestRail;
import com.codepine.api.testrail.model.*;
import lombok.Getter;
import org.junit.jupiter.api.Assertions;
import ru.qa.summer.scene.Scene;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static java.util.Optional.ofNullable;
import static org.apache.commons.lang3.exception.ExceptionUtils.getMessage;
import static org.apache.commons.lang3.exception.ExceptionUtils.getStackTrace;

public class SceneTestRail {
    @Getter
    private final TestRail testRail;
    private final Map<String, Project> projects = new ConcurrentHashMap<>();
    private final Map<String, Run> runs = new ConcurrentHashMap<>();
    private final Map<Integer, Case> cases = new ConcurrentHashMap<>();
    private List<Status> statuses;
    private List<CaseField> caseFields;
    private List<ResultField> resultFields;

    public SceneTestRail(TestRail testRail) {
        this.testRail = testRail;
    }

    public void addResultAsPassed(Scene scene) {
        addResult(scene, getStatus("passed"), null);
    }

    public void addResultAsFailed(Scene scene, Throwable e) {
        addResult(scene, getStatus("failed"), getMessage(e) + "\n" + getStackTrace(e));
    }

    public void addResult(Scene scene, Status status, String additionalComment) {
        Run run = getRun(scene);
        Assertions.assertNotNull(run, "Run not found in '" + scene.getName() + "' Project" +
                " to report about " + scene.toString());
        String comment = String.format("**Location:** %s", scene.getLocation().toString()) + "\n"
                + String.format("**Additional Comment:** %s", additionalComment);
        Result result = new Result().setStatusId(status.getId())
                .setComment(comment);
        testRail.results().addForCase(run.getId(), scene.getId(), result, getResultFields()).execute();
    }

    public List<CaseField> getCaseFields() {
        if (caseFields == null) {
            caseFields = testRail.caseFields().list().execute();
        }
        return caseFields;
    }

    public List<ResultField> getResultFields() {
        if (resultFields == null) {
            resultFields = testRail.resultFields().list().execute();
        }
        return resultFields;
    }

    public Case getCase(Integer id) {
        if (id == null) {
            return null;
        }
        if (!cases.containsKey(id)) {
            ofNullable(testRail.cases().get(id, getCaseFields()).execute())
                    .ifPresent(x -> cases.put(id, x));
        }
        return cases.get(id);
    }

    public Status getStatus(String name) {
        return getStatuses().stream().filter(x -> x.getName().equalsIgnoreCase(name))
                .findFirst()
                .orElse(null);
    }

    public List<Status> getStatuses() {
        if (statuses == null) {
            statuses = testRail.statuses().list().execute();
        }
        return statuses;
    }

    public Project getProject(Scene scene) {
        return getProject(scene.getProjectName());
    }

    public Project getProject(String projectName) {
        if (!projects.containsKey(projectName)) {
            testRail.projects().list().execute()
                    .stream()
                    .filter(x -> x.getName().equalsIgnoreCase(projectName))
                    .findFirst().ifPresent(project -> projects.put(projectName, project));
        }
        return projects.get(projectName);
    }

    public Run getRun(Scene scene) {
        return getRun(scene.getProjectName());
    }

    public Run getRun(String projectName) {
        if (!projects.containsKey(projectName)) {
            Project project = getProject(projectName);
            testRail.runs().list(project.getId()).execute()
                    .stream()
                    .filter(x -> x.getName().equals("summer"))
                    .findFirst()
                    .ifPresent(x -> runs.put(projectName, x));
        }
        return runs.get(projectName);
    }
}
