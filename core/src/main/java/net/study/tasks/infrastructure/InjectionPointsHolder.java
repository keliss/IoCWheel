package net.study.tasks.infrastructure;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Set;

public class InjectionPointsHolder {

    private Set<Constructor> constructors;
    private Set<Field> fields;
    private Set<Method> methods;

    public InjectionPointsHolder(Set<Constructor> constructors, Set<Field> fields, Set<Method> methods) {
        this.constructors = constructors;
        this.fields = fields;
        this.methods = methods;
    }

    public Set<Constructor> getConstructors() {
        return constructors;
    }

    public void setConstructors(Set<Constructor> constructors) {
        this.constructors = constructors;
    }

    public Set<Field> getFields() {
        return fields;
    }

    public void setFields(Set<Field> fields) {
        this.fields = fields;
    }

    public Set<Method> getMethods() {
        return methods;
    }

    public void setMethods(Set<Method> methods) {
        this.methods = methods;
    }
}
