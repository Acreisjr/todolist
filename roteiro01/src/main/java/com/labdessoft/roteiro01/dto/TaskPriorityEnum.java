package com.labdessoft.roteiro01.dto;

import lombok.Getter;

@Getter
public enum TaskPriorityEnum {
    HIGH("Alta"),
    MEDIUM("Media"),
    LOW("Baixa");

    private String value;

    private TaskPriorityEnum(String value) {
        this.value = value;
    }

    public static TaskPriorityEnum fromString(String value) {
        for (TaskPriorityEnum priority : TaskPriorityEnum.values()) {
            if (priority.getValue().equalsIgnoreCase(value)) {
                return priority;
            }
        }
        throw new IllegalArgumentException("Tipo de tarefa inválido: " + value);
    }
}
