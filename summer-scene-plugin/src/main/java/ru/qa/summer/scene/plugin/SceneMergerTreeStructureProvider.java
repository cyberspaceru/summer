package ru.qa.summer.scene.plugin;

import com.intellij.ide.projectView.TreeStructureProvider;
import com.intellij.ide.projectView.ViewSettings;
import com.intellij.ide.projectView.impl.nodes.PsiDirectoryNode;
import com.intellij.ide.projectView.impl.nodes.PsiFileNode;
import com.intellij.ide.util.treeView.AbstractTreeNode;
import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiFile;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.qa.summer.scene.plugin.services.TestRailService;

import java.util.*;

import static ru.qa.summer.scene.util.SceneUtil.SCENE_NAME_REGEXP;
import static ru.qa.summer.scene.util.SceneUtil.isFileNameBelongingToSceneName;

public class SceneMergerTreeStructureProvider implements TreeStructureProvider {
    private final Project myProject;
    private final TestRailService testRailService;

    public SceneMergerTreeStructureProvider(Project project) {
        this.myProject = project;
        this.testRailService = ServiceManager.getService(project, TestRailService.class);
    }

    @NotNull
    @Override
    public Collection<AbstractTreeNode> modify(@NotNull AbstractTreeNode parent, @NotNull Collection<AbstractTreeNode> children, ViewSettings settings) {
        if (!(parent instanceof PsiDirectoryNode)) {
            return children;
        }
        return replaceSceneBundles(children, myProject, settings);
    }

    @Nullable
    @Override
    public Object getData(@NotNull Collection<AbstractTreeNode> selected, @NotNull String dataId) {
        return null;
    }

    private static List<AbstractTreeNode> replaceSceneBundles(Collection<AbstractTreeNode> children, Project project, ViewSettings settings) {
        List<AbstractTreeNode> result = new ArrayList<>(children);
        for (Map.Entry<String, PsiFileNode> entry : findJavaSceneFiles(children).entrySet()) {
            List<PsiFileNode> yamls = new ArrayList<>();
            for (AbstractTreeNode node : children) {
                if (node != entry.getValue() && node instanceof PsiFileNode) {
                    PsiFileNode fileNode = (PsiFileNode) node;
                    String fileName = fileNode.getValue().getName();
                    if (isFileNameBelongingToSceneName(entry.getKey(), fileName)) {
                        if (fileName.endsWith(".yaml") || fileName.endsWith(".yml")) {
                            yamls.add(fileNode);
                        }
                    }
                }
            }
            SceneBundle bundle = new SceneBundle(entry.getKey(), entry.getValue(), yamls);
            SceneBundleNode node = new SceneBundleNode(project, bundle, settings);
            result.removeAll(bundle.getContent());
            result.add(node);
        }
        return result;
    }

    private static Map<String, PsiFileNode> findJavaSceneFiles(Collection<AbstractTreeNode> children) {
        Map<String, PsiFileNode> result = new HashMap<>();
        for (AbstractTreeNode node : children) {
            if (node instanceof PsiFileNode) {
                PsiFileNode fileNode = (PsiFileNode) node;
                PsiFile file = fileNode.getValue();
                if (!file.getName().endsWith(".java")) {
                    continue;
                }
                String sceneName = getSceneName(fileNode);
                if (sceneName != null && sceneName.matches(SCENE_NAME_REGEXP)) {
                    result.put(sceneName, fileNode);
                }
            }
        }
        return result;
    }

    private static String getSceneName(@NotNull PsiFileNode sceneFile) {
        PsiFile file = sceneFile.getValue();
        if (file == null) {
            return null;
        }
        int end = file.getName().indexOf('.');
        return end != -1 ? file.getName().substring(0, end) : null;
    }
}
