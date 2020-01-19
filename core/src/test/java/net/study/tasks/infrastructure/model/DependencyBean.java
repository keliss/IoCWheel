package net.study.tasks.infrastructure.model;

import java.util.Objects;

public class DependencyBean {

    private int someInt;
    private String someString;

    public DependencyBean() {
    }

    public DependencyBean(int someInt, String someString) {
        this.someInt = someInt;
        this.someString = someString;
    }

    public int getSomeInt() {
        return someInt;
    }

    public void setSomeInt(int someInt) {
        this.someInt = someInt;
    }

    public String getSomeString() {
        return someString;
    }

    public void setSomeString(String someString) {
        this.someString = someString;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DependencyBean that = (DependencyBean) o;
        return someInt == that.someInt &&
                Objects.equals(someString, that.someString);
    }

    @Override
    public int hashCode() {
        return Objects.hash(someInt, someString);
    }
}
