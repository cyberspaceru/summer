package ru.qa.summer.scene.plugin;

import com.codepine.api.testrail.model.Section;
import com.intellij.coverage.AbstractCoverageProjectViewNodeDecorator;
import com.intellij.ide.projectView.PresentationData;
import com.intellij.ide.projectView.ProjectViewNode;
import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.project.Project;
import com.intellij.packageDependencies.ui.PackageDependenciesNode;
import com.intellij.ui.ColoredTreeCellRenderer;
import org.jetbrains.annotations.NotNull;
import ru.qa.summer.scene.plugin.services.TestRailService;
import ru.qa.summer.scene.plugin.util.SceneUtil;

public class SceneProjectViewNodeDecorator extends AbstractCoverageProjectViewNodeDecorator {
    private final TestRailService testRailService;

    public SceneProjectViewNodeDecorator(@NotNull Project project) {
        super(project);
        testRailService = ServiceManager.getService(project, TestRailService.class);
    }

    @Override
    public void decorate(ProjectViewNode node, PresentationData data) {
        if (SceneUtil.isSection(node)) {
            String name = node.getName();
            if (name == null) {
                return;
            }
            if (testRailService.getTestRail() == null) {
                data.setLocationString("TestRail not found");
            } else {
                int sectionId = Integer.parseInt(name.substring(1));
                Section section = testRailService.getSection(name, sectionId);
                if (section != null) {
                    data.setLocationString(section.getName());
                } else {
                    data.setLocationString("Section not found");
                }
            }
        }
    }

    @Override
    public void decorate(PackageDependenciesNode node, ColoredTreeCellRenderer cellRenderer) {

    }
}
