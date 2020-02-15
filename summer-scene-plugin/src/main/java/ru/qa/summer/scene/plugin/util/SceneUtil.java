package ru.qa.summer.scene.plugin.util;

import com.intellij.ide.projectView.impl.nodes.PsiDirectoryNode;
import com.intellij.ide.util.treeView.AbstractTreeNode;

import static ru.qa.summer.scene.util.SceneUtil.SECTION_NAME_REGEXP;

public class SceneUtil {
    public static boolean isSection(AbstractTreeNode<?> node) {
        if (node instanceof PsiDirectoryNode) {
            String name = node.getName();
            if (name == null) {
                return false;
            }
            int dot = name.lastIndexOf('.');
            name = dot == -1 ? name : name.substring(dot);
            return name.matches(SECTION_NAME_REGEXP);
        }
        return false;
    }

    public static int getCaseId(String name) {
        try {
            if (name.length() >= 2 && name.startsWith("C")) {
                return Integer.parseInt(name.substring(1));
            }
        } catch (Exception e) {
            // ignored
        }
        return -1;
    }

    public static boolean isSectionName(String name) {
        try {
            if (name.length() >= 2 && name.startsWith("S")) {
                Integer.parseInt(name.substring(1));
                return true;
            }
        } catch (Exception e) {
            // ignored
        }
        return false;
    }
}
