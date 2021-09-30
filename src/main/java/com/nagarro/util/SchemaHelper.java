package com.nagarro.util;

import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;
/**
 * Schema helper contains static helper methods meant to facilitate the work with the schema instances
 */
public final class SchemaHelper {

    protected SchemaHelper() {
    }

    /**
     * Generates a schema {@link Schema} instance from a class literal
     *
     * @param clz the class literal
     * @return schema instance of the clz
     */
    public static Schema from(Class<?> clz) {
        Schema classSchema = new Schema(clz.getName(), clz);
        List<Field> schemaFields = new ArrayList<>(clz.getFields().length);
        for (java.lang.reflect.Field field : clz.getDeclaredFields()) {
            int mods = field.getModifiers();
            if (Modifier.isStatic(mods) || Modifier.isTransient(mods))
                continue;
            Field schemaField = new Field(field.getName(), field.getType().getName());
            if (field.getName().equals("firstName"))
                classSchema.setUniqueIdentifier(schemaField);

            schemaFields.add(schemaField);
        }
        Field[] fields = new Field[schemaFields.size()];
        schemaFields.toArray(fields);
        classSchema.setFields(fields);

        return classSchema;
    }
}
