package ru.qa.summer.support.data;

import org.apache.commons.io.IOUtils;
import ru.qa.summer.support.exceptions.AccessException;
import ru.qa.summer.support.exceptions.ResourceNotFoundException;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class ResourceAccessObject implements AccessObject {
    private final File file;
    private final InputStream stream;

    public ResourceAccessObject(String path) {
        URL url = getClass().getClassLoader().getResource(path);
        if (url == null) {
            throw new ResourceNotFoundException("Resource not found by path: " + path);
        }
        this.file = new File(url.getFile());
        this.stream = getClass().getClassLoader().getResourceAsStream(path);
    }

    @Override
    public String load() throws AccessException {
        try {
            return IOUtils.toString(stream, StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new AccessException(e);
        }
    }

    @Override
    public void save(String s) throws AccessException {
        throw new UnsupportedOperationException();
    }

    @Override
    public String toString() {
        return "ResourceAccessObject{" +
                "file=" + file +
                '}';
    }

    public File getFile() {
        return file;
    }
}
