package com.labdessoft.roteiro01.dto;

public enum TaskTypeEnum {
    DATA("Data"),
    PRAZO("Prazo"),
    LIVRE("Livre");

    private final String displayName;

    TaskTypeEnum(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
