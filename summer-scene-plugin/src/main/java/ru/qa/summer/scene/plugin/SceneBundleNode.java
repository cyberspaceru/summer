package ru.qa.summer.scene.plugin;

import com.codepine.api.testrail.model.Case;
import com.codepine.api.testrail.model.Section;
import com.intellij.ide.projectView.PresentationData;
import com.intellij.ide.projectView.ProjectViewNode;
import com.intellij.ide.projectView.ViewSettings;
import com.intellij.ide.util.treeView.AbstractTreeNode;
import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.ui.JBColor;
import com.intellij.ui.SimpleTextAttributes;
import org.jetbrains.annotations.NotNull;
import ru.qa.summer.scene.plugin.services.TestRailService;
import ru.qa.summer.scene.plugin.util.Icons;
import ru.qa.summer.scene.plugin.util.SceneUtil;

import java.util.Collection;

public class SceneBundleNode extends ProjectViewNode<SceneBundle> {
    private final TestRailService testRailService;

    protected SceneBundleNode(Project project, @NotNull SceneBundle sceneBundle, ViewSettings viewSettings) {
        super(project, sceneBundle, viewSettings);
        testRailService = ServiceManager.getService(project, TestRailService.class);
    }

    @Override
    public boolean contains(@NotNull VirtualFile file) {
        return getValue().contains(file);
    }

    @NotNull
    @Override
    public Collection<? extends AbstractTreeNode<?>> getChildren() {
        return getValue().getContent();
    }

    public String getProjectName() {
        AbstractTreeNode<?> node = getParent();
        while (node != null) {
            if ("org.jetbrains.plugins.gradle.projectView.GradleTreeStructureProvider$GradleModuleDirectoryNode".equals(node.getName())) {
                return node.getName();
            }
            node = node.getParent();
        }
        return null;
    }

    @Override
    protected void update(@NotNull PresentationData presentation) {
        presentation.setPresentableText(getValue().getName());
        presentation.setIcon(Icons.SCENE_ICON);
        boolean isError = true;
        if (testRailService.getTestRail() == null) {
            presentation.setLocationString("TestRail not found");
        } else {
            Case testCase = testRailService.getTestCase(getValue().getName(), getValue().getTestCaseId());
            if (testCase != null) {
                String name = getParent().getName();
                if (!SceneUtil.isSection(getParent()) || name == null) {
                    presentation.setLocationString("Parent must be declared as a Section");
                } else {
                    int sectionId = Integer.parseInt(name.substring(1));
                    if (testCase.getSectionId() != sectionId) {
                        presentation.setLocationString("Actual Section ID is " + testCase.getSectionId());
                    } else {
                        Section section = testRailService.getSection(getValue().getName(), sectionId);
                        if (testCase.getTitle().startsWith(section.getName())) {
                            presentation.setLocationString(testCase.getTitle().replace(section.getName(), "").trim());
                        } else {
                            presentation.setLocationString(testCase.getTitle());
                        }
                        isError = false;
                    }
                }
            } else {
                presentation.setLocationString("Case not found");
            }
        }
        if (isError) {
            presentation.addText(new ColoredFragment(getValue().getName(), new SimpleTextAttributes(SimpleTextAttributes.STYLE_WAVED, JBColor.foreground(), JBColor.red)));
        } else {
            presentation.setPresentableText(getValue().getName());
        }
    }
}
