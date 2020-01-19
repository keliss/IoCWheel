package net.study.tasks.infrastructure.model;

import java.util.Objects;

public class TestBean {

    private String someStringField;
    private int someIntField;
    private DependencyBean dependency;

    public TestBean() {
    }

    public TestBean(DependencyBean dependency) {
        this.dependency = dependency;
    }

    public void setSomeStringField(String someStringField) {
        this.someStringField = someStringField;
    }

    public void setSomeIntField(int someIntField) {
        this.someIntField = someIntField;
    }

    public void setDependency(DependencyBean dependency) {
        this.dependency = dependency;
    }

    public String getSomeStringField() {
        return someStringField;
    }

    public int getSomeIntField() {
        return someIntField;
    }

    public DependencyBean getDependency() {
        return dependency;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TestBean testBean = (TestBean) o;
        return someIntField == testBean.someIntField &&
                Objects.equals(someStringField, testBean.someStringField) &&
                Objects.equals(dependency, testBean.dependency);
    }

    @Override
    public int hashCode() {
        return Objects.hash(someStringField, someIntField, dependency);
    }
}
