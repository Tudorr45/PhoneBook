package com.nagarro.util;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Schema implements Serializable {
    protected static transient Map<String, Class> transientClasses = new ConcurrentHashMap<>();
    protected String name;
    protected Field uniqueIdentifier;
    protected Field[] fields;

    public Schema() {
    }

    public Schema(String name) {
        this.name = name;
    }

    public Schema(String name, Class c) {
        this.name = name;
        transientClasses.putIfAbsent(name, c);
    }
    public Class getJavaClass() throws ClassNotFoundException {
        try {
            return transientClasses.containsKey(name) ?
                    transientClasses.get(name) :
                    this.getClass().forName(name);
        } catch (ClassNotFoundException e) {
            throw new ClassNotFoundException("Error occurred while loading class: " + name);
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Field[] getFields() {
        return fields;
    }

    public void setFields(Field[] fields) {
        this.fields = fields;
    }

    public Field getUniqueIdentifier() {
        return uniqueIdentifier;
    }

    public void setUniqueIdentifier(Field uniqueIdentifier) {
        this.uniqueIdentifier = uniqueIdentifier;
    }

    @Override
    public String toString() {
        return "Schema{" +
                "name='" + name + '\'' +
                ", uniqueIdentifier=" + uniqueIdentifier +
                ", fields=" + Arrays.toString(fields) +
                '}';
    }
}
