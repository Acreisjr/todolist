package com.labdessoft.roteiro01.dto;

import lombok.Getter;

@Getter
public enum TaskTypeEnum {
    DATA("Data"),
    PRAZO("Prazo"),
    LIVRE("Livre");

    private String value;

    private TaskTypeEnum(String value) {
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
