package com.labdessoft.roteiro01.dto;

import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class TaskCreateDto {

    private String description;
    private Integer daysToCompletion;
    private LocalDate completionDate;
    private String type;
    private String priority;
}
