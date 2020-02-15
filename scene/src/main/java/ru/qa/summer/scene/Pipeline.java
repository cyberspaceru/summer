package ru.qa.summer.scene;

public interface Pipeline<SELF extends Pipeline> {
    @SuppressWarnings("unchecked")
    default SELF self() {
        return (SELF) this;
    }
}
