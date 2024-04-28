package com.labdessoft.roteiro01.dto;

public enum TaskPriorityEnum {
    HIGH("Alta"),
    MEDIUM("Media"),
    LOW("Baixa");

    private String value;

    private TaskPriorityEnum(String value) {
        this.value = value;
    }

    public static TaskTypeEnum fromString(String value) {
        for (TaskTypeEnum type : TaskTypeEnum.values()) {
            if (type.getValue().equalsIgnoreCase(value)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Tipo de tarefa inválido: " + value);
    }
}
