package com.labdessoft.roteiro01.dto;

public enum TaskPriorityEnum {
    HIGH("Alto"),
    MEDIUM("Medio"),
    LOW("Baixo");

    private final String displayName;

    TaskPriorityEnum(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
