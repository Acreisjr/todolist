package com.labdessoft.roteiro01.unit.entity;

import com.labdessoft.roteiro01.dto.TaskCreateDto;
import com.labdessoft.roteiro01.entity.DateTask;
import com.labdessoft.roteiro01.entity.DeadlineTask;
import com.labdessoft.roteiro01.entity.FreeTask;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class TaskEntityTest {

    @Test
    void testDateTaskCreation() {
        TaskCreateDto dto = new TaskCreateDto();
        dto.setDescription("Test Task");
        dto.setPriority("Alta");
        dto.setCompletionDate(LocalDate.now().plusDays(5));

        DateTask dateTask = new DateTask(dto);
        assertEquals("Test Task", dateTask.getDescription());
        assertEquals("Alta", dateTask.getPriority());
        assertEquals(LocalDate.now().plusDays(5), dateTask.getCompletionDate());
        assertFalse(dateTask.isDone());

        dateTask.setCompletionDate(LocalDate.now().plusDays(10));
        assertEquals(LocalDate.now().plusDays(10), dateTask.getCompletionDate());

    }

    @Test
    void testDeadlineTaskCreation() {
        TaskCreateDto dto = new TaskCreateDto();
        dto.setDescription("Test Task");
        dto.setPriority("Alta");
        dto.setDaysToCompletion(5);

        DeadlineTask deadlineTask = new DeadlineTask(dto);
        assertEquals("Test Task", deadlineTask.getDescription());
        assertEquals("Alta", deadlineTask.getPriority());
        assertEquals(5, deadlineTask.getDaysToCompletion().intValue());
        assertFalse(deadlineTask.isDone());

        deadlineTask.setDaysToCompletion(10);
        assertEquals(10, deadlineTask.getDaysToCompletion().intValue());

    }

    @Test
    void testFreeTaskCreation() {
        TaskCreateDto dto = new TaskCreateDto();
        dto.setDescription("Test Task");
        dto.setPriority("Alta");

        FreeTask freeTask = new FreeTask(dto);
        assertEquals("Test Task", freeTask.getDescription());
        assertEquals("Alta", freeTask.getPriority());
        assertFalse(freeTask.isDone());

    }
}
