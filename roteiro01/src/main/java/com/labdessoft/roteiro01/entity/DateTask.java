package com.labdessoft.roteiro01.entity;

import java.time.LocalDate;

import com.labdessoft.roteiro01.dto.TaskCreateDto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
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
@DiscriminatorValue("DATA")
@Schema(description = "Entidade que representa uma tarefa com data limite")
public class DateTask extends Task {
    @Column(nullable = false)
    private LocalDate completionDate;

    public DateTask(TaskCreateDto taskCreateDto) {
        super(taskCreateDto);
        this.completionDate = taskCreateDto.getCompletionDate();
    }
}