package ru.qa.summer.support.data;


import ru.qa.summer.support.exceptions.AccessException;

public interface AccessObject {
    String load() throws AccessException;

    void save(String s) throws AccessException;
}
