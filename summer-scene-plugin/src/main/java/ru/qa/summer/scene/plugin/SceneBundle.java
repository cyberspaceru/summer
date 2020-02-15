package ru.qa.summer.scene.plugin;

import com.intellij.ide.projectView.impl.nodes.PsiFileNode;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.pom.Navigatable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SceneBundle implements Navigatable {
    private final String name;
    private final int testCaseId;
    private final PsiFileNode java;
    private final List<PsiFileNode> yamls;
    private final List<PsiFileNode> content;

    public SceneBundle(String name, PsiFileNode java, List<PsiFileNode> yamls) {
        this.name = name;
        this.testCaseId = Integer.parseInt(name.substring(1));
        this.java = java;
        this.yamls = Collections.unmodifiableList(yamls);
        List<PsiFileNode> tContent = new ArrayList<>();
        tContent.add(java);
        tContent.addAll(yamls);
        this.content = Collections.unmodifiableList(tContent);
    }

    public String getName() {
        return name;
    }

    public int getTestCaseId() {
        return testCaseId;
    }

    public PsiFileNode getJava() {
        return java;
    }

    public List<PsiFileNode> getYamls() {
        return yamls;
    }

    public List<PsiFileNode> getContent() {
        return content;
    }

    @Override
    public void navigate(boolean requestFocus) {
        java.navigate(requestFocus);
    }

    @Override
    public boolean canNavigate() {
        return java.canNavigate();
    }

    @Override
    public boolean canNavigateToSource() {
        return java.canNavigateToSource();
    }

    public boolean contains(VirtualFile file) {
        if (java.getVirtualFile() != null && java.getVirtualFile().equals(file)) {
            return true;
        }
        for (PsiFileNode yaml : yamls) {
            if (yaml.getVirtualFile() != null && yaml.getVirtualFile().equals(file)) {
                return true;
            }
        }
        return false;
    }
}
