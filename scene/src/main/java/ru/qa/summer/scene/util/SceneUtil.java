package ru.qa.summer.scene.util;

import lombok.experimental.UtilityClass;
import org.apache.commons.lang3.SystemUtils;
import org.jetbrains.annotations.NotNull;
import ru.qa.summer.scene.exceptions.IncorrectSceneClassLocationException;
import ru.qa.summer.scene.exceptions.IncorrectSceneNameException;

import java.io.File;
import java.util.regex.Pattern;

@UtilityClass
public class SceneUtil {
    public static final String SCENE_NAME_REGEXP = "C[\\d]+";
    public static final String SECTION_NAME_REGEXP = "S[\\d]+";
    public static final Pattern SCENE_NAME_PATTERN = Pattern.compile(SCENE_NAME_REGEXP);

    public static boolean isFileNameBelongingToSceneName(String sceneName, String fileName) {
        return fileName.startsWith(sceneName) && (
                fileName.startsWith(".", sceneName.length()) || fileName.startsWith("$", sceneName.length())
        );
    }

    @NotNull
    public static String getSceneName(Class<?> clazz) {
        String name = clazz.getSimpleName();
        if (!SCENE_NAME_PATTERN.matcher(name).find()) {
            throw new IncorrectSceneNameException("Class '" + clazz.getCanonicalName() + "' not matches '" + SCENE_NAME_PATTERN + "' pattern");
        }
        return name;
    }

    @NotNull
    public static File getClassLocation(Class<?> clazz) {
        if (!clazz.getProtectionDomain().getCodeSource().getLocation().getFile().endsWith("/test/")) {
            throw new IncorrectSceneClassLocationException(clazz.getCanonicalName() + " must be placed in the test source directory");
        }
        String packagePath = clazz.getCanonicalName().substring(0, clazz.getCanonicalName().lastIndexOf('.'));
        String path = new File("src\\test\\java", packagePath.replaceAll("\\.", "\\\\")).getPath();
        if (!SystemUtils.IS_OS_WINDOWS) {
            path = path.replaceAll("\\\\", "/");
        }
        return new File(path);
    }
}
