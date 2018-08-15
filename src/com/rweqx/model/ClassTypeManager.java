package com.rweqx.model;

public class ClassTypeManager {
    private static ClassTypeManager ourInstance = new ClassTypeManager();

    public static ClassTypeManager getInstance() {
        return ourInstance;
    }

    private ClassTypeManager() {

    }


}
