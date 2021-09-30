package com.nagarro.util;

import java.io.Serializable;

public class Field implements Serializable {
    protected String fieldName;
    protected String fieldType;

    public Field() {
    }

    public Field(String name, String type) {
        fieldName = name;
        fieldType = type;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getFieldType() {
        return fieldType;
    }

    public void setFieldType(String fieldType) {
        this.fieldType = fieldType;
    }

    @Override
    public String toString() {
        return "Field{" +
                "fieldName='" + fieldName + '\'' +
                ", fieldType='" + fieldType + '\'' +
                '}';
    }
}