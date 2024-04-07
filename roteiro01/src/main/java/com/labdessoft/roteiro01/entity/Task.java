package com.labdessoft.roteiro01.entity;

import com.labdessoft.roteiro01.dto.TaskCreateDto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Entidade que representa uma tarefa")
public abstract class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Schema(name = "Descrição da tarefa deve possuir pelo menos 10 caracteres")
    @Size(min = 10, message = "Descrição da tarefa deve possuir pelo menos 10")
    private String description;
    @Column(name = "ISDONE", nullable = false)
    private boolean isDone;
    @Column(nullable = false)
    private String priority;

    public Task(TaskCreateDto taskCreateDto){
        this.description = taskCreateDto.getDescription();
    }
}
