package ru.qa.summer.scene.plugin;

import com.codepine.api.testrail.model.Case;
import com.intellij.openapi.fileEditor.impl.EditorTabTitleProvider;
import com.intellij.openapi.fileEditor.impl.EditorWindow;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.qa.summer.scene.plugin.services.TestRailService;
import ru.qa.summer.scene.plugin.util.FileUtil;
import ru.qa.summer.scene.plugin.util.SceneUtil;

import static com.intellij.openapi.components.ServiceManager.getService;

public class SceneEditorTabTitleProvider implements EditorTabTitleProvider {
    @Nullable
    @Override
    public String getEditorTabTitle(@NotNull Project project, @NotNull VirtualFile file) {
        return null;
    }

    @Nullable
    @Override
    public String getEditorTabTitle(@NotNull Project project, @NotNull VirtualFile file, @Nullable EditorWindow editorWindow) {
        String name = null;
        String prefix = null;
        if (FileUtil.isJava(file)) {
            name = file.getName().split("\\.")[0];
        } else if (FileUtil.isYaml(file)) {
            String tName = file.getName().split("\\.")[0];
            if (tName.contains("$")) {
                String[] parts = tName.split("\\$");
                name = parts[0];
                prefix = parts[1] + ": ";
            } else {
                name = tName;
            }
        }
        if (name != null) {
            int caseId = SceneUtil.getCaseId(name);
            if (caseId != -1) {
                TestRailService testRailService = getService(project, TestRailService.class);
                if (testRailService.getTestRail() != null) {
                    Case testCase = testRailService.getTestCase(name, caseId);
                    if (testCase != null) {
                        return (prefix == null ? "" : prefix) + testCase.getTitle();
                    }
                }
            }
        }
        return null;
    }

    @Nullable
    @Override
    public String getEditorTabTooltipText(@NotNull Project project, @NotNull VirtualFile virtualFile) {
        return null;
    }
}
