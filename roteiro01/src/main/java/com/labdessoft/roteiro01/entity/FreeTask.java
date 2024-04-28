package com.labdessoft.roteiro01.entity;

import com.labdessoft.roteiro01.dto.TaskCreateDto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@DiscriminatorValue("LIVRE")
@Schema(description = "Entidade que representa uma tarefa livre")
public class FreeTask extends Task{
    public FreeTask(TaskCreateDto taskCreateDto) {
        super(taskCreateDto);
    }
}
