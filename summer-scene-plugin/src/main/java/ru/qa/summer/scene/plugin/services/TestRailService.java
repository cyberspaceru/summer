package ru.qa.summer.scene.plugin.services;

import com.codepine.api.testrail.TestRail;
import com.codepine.api.testrail.model.Case;
import com.codepine.api.testrail.model.CaseField;
import com.codepine.api.testrail.model.Section;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiFile;
import com.intellij.psi.search.GlobalSearchScope;

import java.io.IOException;
import java.io.StringReader;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

import static com.intellij.psi.search.FilenameIndex.getFilesByName;
import static ru.qa.summer.scene.SceneConfiguration.*;

public class TestRailService {
    private final Map<String, Case> cases = new ConcurrentHashMap<>();
    private final Map<String, Section> sections = new ConcurrentHashMap<>();
    private final Project myProject;
    private PsiFile propertiesFile;
    private TestRail testRail;
    private String lastPropertiesContent;
    private List<CaseField> caseFields;

    public TestRailService(Project myProject) {
        this.myProject = myProject;
    }

    public TestRail getTestRail() {
        if (propertiesFile == null || !propertiesFile.isValid()) {
            PsiFile[] files = getFilesByName(myProject, "scene.properties", GlobalSearchScope.projectScope(myProject));
            propertiesFile = files.length == 0 ? null : files[0];
        }
        if (propertiesFile != null) {
            try {
                String propertiesContent = new String(propertiesFile.getVirtualFile().contentsToByteArray());
                if (testRail != null && propertiesContent.equalsIgnoreCase(lastPropertiesContent)) {
                    return testRail;
                }
                lastPropertiesContent = propertiesContent;
                Properties properties = new Properties();
                properties.load(new StringReader(propertiesContent));
                String url = properties.getProperty(SCENE_TESTRAIL_URL_PROPERTY_NAME);
                String username = properties.getProperty(SCENE_TESTRAIL_USERNAME_PROPERTY_NAME);
                String password = properties.getProperty(SCENE_TESTRAIL_PASSWORD_PROPERTY_NAME);
                testRail = TestRail.builder(url, username, password).build();
                cases.clear();
                sections.clear();
                return testRail;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public List<CaseField> getCaseFields() {
        if (caseFields == null) {
            caseFields = testRail.caseFields().list().execute();
        }
        return caseFields;
    }

    public Case getTestCase(String name, int testCaseId) {
        TestRail testRail = getTestRail();
        if (testRail == null) {
            return null;
        }
        if (!cases.containsKey(name)) {
            Case testCase = testRail.cases().get(testCaseId, getCaseFields()).execute();
            if (testCase != null) {
                cases.put(name, testCase);
            }
        }
        return cases.get(name);
    }

    public Section getSection(String name, int testCaseId) {
        TestRail testRail = getTestRail();
        if (testRail == null) {
            return null;
        }
        if (!sections.containsKey(name)) {
            Section section = testRail.sections().get(testCaseId).execute();
            if (section != null) {
                sections.put(name, section);
            }
        }
        return sections.get(name);
    }
}
