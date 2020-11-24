package com.tanovait.learn_kotlin.ui;

public class JavaProperty {
    private final String name;

    public void setMarried(Boolean married) {
        isMarried = married;
    }

    private Boolean isMarried;

    public JavaProperty(String name, Boolean isMarried) {
        this.name = name;
        this.isMarried = isMarried;
    }

    public String getName() {
        return name;
    }

    public Boolean getMarried() {
        return isMarried;
    }
}
