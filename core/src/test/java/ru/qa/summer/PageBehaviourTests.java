package ru.qa.summer;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.qa.summer.annotations.ElementName;
import ru.qa.summer.annotations.MethodName;
import ru.qa.summer.annotations.PageName;
import ru.qa.summer.exceptions.MethodExecutionException;

public class PageBehaviourTests {
    private static final String FIELD_NAME = "Field Name";
    private static final String PARENT_FIELD_NAME = "Parent Field Name";
    private static final String PROTECTED_FIELD = "Protected Field";
    private static final String PRIVATE_FIELD = "Private Field";

    private static final String METHOD_NAME = "Method Name";
    private static final String METHOD_NAME_MESSAGE = "Method '" + METHOD_NAME + "' was executed";
    private static final String PARENT_METHOD_NAME_MESSAGE = "Method '" + METHOD_NAME + "' was executed: overwritten";

    @PageName("ParentOfSimplePage")
    public static class SimplePage extends Page<PageAnnotator, CoreFieldDecorator.Stub> {
        @ElementName(FIELD_NAME)
        public String field = "My field as <" + FIELD_NAME + ">";

        @ElementName(PROTECTED_FIELD)
        protected String protectedField = "My field as <" + PROTECTED_FIELD + ">";

        @ElementName(PRIVATE_FIELD)
        private String privateField = "My field as <" + PRIVATE_FIELD + ">";

        public SimplePage() {
            super(PageAnnotator.class, CoreFieldDecorator.Stub.class);
        }

        @MethodName(METHOD_NAME)
        public void method() {
            throw new RuntimeException(METHOD_NAME_MESSAGE);
        }
    }

    @PageName("ParentOfSimplePage")
    public static class ParentOfSimplePage extends SimplePage {
        @ElementName(PARENT_FIELD_NAME)
        public String fieldOfParent = "My field as <" + PARENT_FIELD_NAME + ">";

        @MethodName(METHOD_NAME)
        public void parentMethod() {
            throw new RuntimeException(PARENT_METHOD_NAME_MESSAGE);
        }
    }

    @PageName("PageWithFieldDuplication")
    public static class PageWithFieldDuplication extends Page<PageAnnotator, CoreFieldDecorator.Stub> {
        @ElementName(FIELD_NAME)
        public Object fieldOne;

        @ElementName(FIELD_NAME)
        public Object fieldTwo;

        public PageWithFieldDuplication() {
            super(PageAnnotator.class, CoreFieldDecorator.Stub.class);
        }
    }

    @PageName("PageWithLongTypeField")
    public static class PageWithLongTypeField extends Page<PageAnnotator, CoreFieldDecorator.Stub> {
        @ElementName(FIELD_NAME)
        public Long fieldOne = 1L;

        public PageWithLongTypeField() {
            super(PageAnnotator.class, CoreFieldDecorator.Stub.class);
        }
    }

    @Test
    public void accessToFieldByName() {
        SimplePage page = new SimplePage();
        String found = page.get(FIELD_NAME, String.class);
        Assertions.assertSame(page.field, found);
    }

    @Test
    public void accessToProtectedField() {
        SimplePage page = new SimplePage();
        String found = page.get(PROTECTED_FIELD, String.class);
        Assertions.assertSame(page.protectedField, found);
    }

    @Test
    public void accessToPrivateField() {
        SimplePage page = new SimplePage();
        String found = page.get(PRIVATE_FIELD, String.class);
        Assertions.assertSame(page.privateField, found);
    }

    @Test
    public void accessToInheritField() {
        ParentOfSimplePage page = new ParentOfSimplePage();
        String found = page.get(FIELD_NAME, String.class);
        Assertions.assertSame(page.field, found);
    }

    @Test
    public void accessToMethod() {
        SimplePage page = new SimplePage();
        try {
            page.invoke(METHOD_NAME);
            Assertions.fail("No exception was thrown");
        } catch (Throwable e) {
            Assertions.assertTrue(e instanceof MethodExecutionException);
            Assertions.assertEquals(METHOD_NAME_MESSAGE, e.getCause().getMessage());
        }
    }

    @Test
    public void accessToOverwrittenMethod() {
        ParentOfSimplePage page = new ParentOfSimplePage();
        try {
            page.invoke(METHOD_NAME);
            Assertions.fail("No exception was thrown");
        } catch (Throwable e) {
            Assertions.assertTrue(e instanceof MethodExecutionException);
            Assertions.assertEquals(PARENT_METHOD_NAME_MESSAGE, e.getCause().getMessage());
        }
    }

    @Test
    public void fieldDuplicationNotAllowed() {
        String message = "Field with '" + FIELD_NAME + "' name duplicated in '" + PageWithFieldDuplication.class.getCanonicalName() + "' page";
        try {
            new PageWithFieldDuplication();
            Assertions.fail("No exception was thrown");
        } catch (Throwable e) {
            Assertions.assertEquals(message, e.getMessage());
        }
    }

    @Test
    public void tryToGetFieldWithIncorrectType() {
        String message = String.format("Expected type for '%s' field is '%s' but actually was '%s'",
                FIELD_NAME,
                Double.class.getCanonicalName(),
                Long.class.getCanonicalName());
        try {
            PageWithLongTypeField page = new PageWithLongTypeField();
            page.get(FIELD_NAME, Double.class);
            Assertions.fail("No exception was thrown");
        } catch (Throwable e) {
            Assertions.assertEquals(message, e.getMessage());
        }
    }
}
