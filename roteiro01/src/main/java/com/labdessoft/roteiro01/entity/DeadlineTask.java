package com.labdessoft.roteiro01.entity;

import com.labdessoft.roteiro01.dto.TaskCreateDto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@DiscriminatorValue("PRAZO")
@Schema(description = "Entidade que representa uma tarefa com prazo")
public class DeadlineTask extends Task {
    private Integer daysToCompletion;
    public DeadlineTask(TaskCreateDto taskCreateDto) {
        super(taskCreateDto);
        this.daysToCompletion = taskCreateDto.getDaysToCompletion();
    }
}