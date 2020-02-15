package ru.qa.summer;

import ru.qa.summer.support.FieldDecorator;

import static ru.qa.summer.utils.InstantiateCoreUtils.instantiateFieldDecorator;

public abstract class Element<T extends Anchor> {
    private final T anchor;

    public Element(T anchor, Class<? extends FieldDecorator> dClass) {
        this.anchor = anchor;
        instantiateFieldDecorator(dClass, this).decorate(this);
    }

    /**
     * It can be used to instantiate an element through the new operator by a user.
     */
    protected Element(T anchor) {
        this.anchor = anchor;
    }

    public T getAnchor() {
        return anchor;
    }
}
