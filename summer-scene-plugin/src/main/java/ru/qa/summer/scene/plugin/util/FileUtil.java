package ru.qa.summer.scene.plugin.util;

import com.intellij.openapi.vfs.VirtualFile;

public class FileUtil {
    public static boolean isJava(VirtualFile file) {
        if (file.isDirectory()) {
            return false;
        }
        return file.getName().toLowerCase().endsWith(".java");
    }

    public static boolean isYaml(VirtualFile file) {
        if (file.isDirectory()) {
            return false;
        }
        String name = file.getName().toLowerCase();
        return name.endsWith(".yaml") || name.endsWith(".yml");
    }
}
