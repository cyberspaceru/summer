package ru.qa.summer.support.data;

import org.apache.commons.io.FileUtils;
import ru.qa.summer.support.exceptions.AccessException;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class FileAccessObject implements AccessObject {
    private final File file;

    public FileAccessObject(File file) {
        this.file = file;
    }

    @Override
    public String load() throws AccessException {
        try {
            return FileUtils.readFileToString(file, StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new AccessException(e);
        }
    }

    @Override
    public void save(String s) throws AccessException {
        try {
            FileUtils.write(file, s, "UTF-8");
        } catch (IOException e) {
            throw new AccessException(e);
        }
    }

    @Override
    public String toString() {
        return "FileAccessObject{" +
                "file=" + file +
                '}';
    }
}
