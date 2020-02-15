package ru.qa.summer.scene;

import ru.qa.summer.context.Context;
import ru.qa.summer.context.providers.YamlEntityProvider;
import ru.qa.summer.scene.util.SceneUtil;
import ru.qa.summer.support.data.FileAccessObject;
import ru.qa.summer.support.exceptions.AccessException;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static ru.qa.summer.scene.util.SceneUtil.isFileNameBelongingToSceneName;

public class Scene {
    private final Class<?> sClass;
    private final String name;
    private final File location;

    public Scene(Class<?> sClass) {
        this.sClass = sClass;
        this.name = SceneUtil.getSceneName(sClass);
        this.location = SceneUtil.getClassLocation(sClass);
    }

    public List<File> getYamls() {
        File parent = getLocation().getParentFile();
        List<File> owns = Arrays.stream(Objects.requireNonNull(getLocation().listFiles()))
                .filter(x -> x.getName().endsWith(".yml") || x.getName().endsWith(".yaml"))
                .filter(x -> x.isFile() && (x.getName().startsWith("$") || isFileNameBelongingToSceneName(name, x.getName())))
                .collect(Collectors.toList());
        List<File> parents = Arrays.stream(Objects.requireNonNull(parent.listFiles()))
                .filter(x -> x.getName().endsWith(".yml") || x.getName().endsWith(".yaml"))
                .filter(x -> x.isFile() && (x.getName().startsWith("$")))
                .collect(Collectors.toList());
        parents.addAll(owns);
        return parents;
    }

    public int getId() {
        return Integer.parseInt(getName().substring(1));
    }

    public String getName() {
        return name;
    }

    public File getLocation() {
        return location;
    }

    public Scene patchContext() throws AccessException {
        for (File yaml : getYamls()) {
            FileAccessObject file = new FileAccessObject(yaml);
            Context.current().add(new YamlEntityProvider(file.load()).provide());
        }
        return this;
    }

    public String getProjectName() {
        return new File(System.getProperty("user.dir")).getName();
    }

    @Override
    public String toString() {
        return "Scene{" +
                "name='" + name + '\'' +
                ", location=" + location +
                '}';
    }
}
