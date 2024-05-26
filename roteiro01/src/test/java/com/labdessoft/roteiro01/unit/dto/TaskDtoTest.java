package com.labdessoft.roteiro01.unit.dto;

import org.junit.jupiter.api.Test;

import com.labdessoft.roteiro01.dto.TaskCreateDto;
import com.labdessoft.roteiro01.dto.TaskPriorityEnum;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class TaskDtoTest {

    @Test
    void testTaskCreateDto() {
        TaskCreateDto dto = new TaskCreateDto();
        dto.setDescription("Test Task");
        dto.setPriority("Alta");
        dto.setCompletionDate(LocalDate.now().plusDays(5));
        dto.setDaysToCompletion(5);
        dto.setType("DATA");

        assertEquals("Test Task", dto.getDescription());
        assertEquals("Alta", dto.getPriority());
        assertEquals(LocalDate.now().plusDays(5), dto.getCompletionDate());
        assertEquals(5, dto.getDaysToCompletion().intValue());
        assertEquals("DATA", dto.getType());

        dto.setDescription("Updated Task");
        assertEquals("Updated Task", dto.getDescription());
    }

    @Test
    void testTaskCreateDtoAllArgsConstructor() {
        TaskCreateDto dto = new TaskCreateDto();
        dto.setDescription("Test Task");
        dto.setPriority("Alta");
        dto.setCompletionDate(LocalDate.now().plusDays(5));
        dto.setDaysToCompletion(5);
        dto.setType("DATA");

        TaskCreateDto allArgsDto = new TaskCreateDto();
        allArgsDto.setDescription(dto.getDescription());
        allArgsDto.setPriority(dto.getPriority());
        allArgsDto.setCompletionDate(dto.getCompletionDate());
        allArgsDto.setDaysToCompletion(dto.getDaysToCompletion());
        allArgsDto.setType(dto.getType());

        assertEquals("Test Task", allArgsDto.getDescription());
        assertEquals("Alta", allArgsDto.getPriority());
        assertEquals(LocalDate.now().plusDays(5), allArgsDto.getCompletionDate());
        assertEquals(5, allArgsDto.getDaysToCompletion().intValue());
        assertEquals("DATA", allArgsDto.getType());
    }

    @Test
    void testTaskPriorityEnum() {
        assertEquals("Alta", TaskPriorityEnum.HIGH.getValue());
        assertEquals("Media", TaskPriorityEnum.MEDIUM.getValue());
        assertEquals("Baixa", TaskPriorityEnum.LOW.getValue());
        assertEquals(TaskPriorityEnum.HIGH, TaskPriorityEnum.fromString("ALTA"));
        assertEquals(TaskPriorityEnum.MEDIUM, TaskPriorityEnum.fromString("MEDIA"));
        assertEquals(TaskPriorityEnum.LOW, TaskPriorityEnum.fromString("BAIXA"));
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> TaskPriorityEnum.fromString("INVALID"));
        assertEquals("Tipo de tarefa inválido: INVALID", exception.getMessage());
    }
}
