package com.labdessoft.roteiro01.unit.service;

import com.labdessoft.roteiro01.dto.TaskCreateDto;
import com.labdessoft.roteiro01.entity.DateTask;
import com.labdessoft.roteiro01.entity.DeadlineTask;
import com.labdessoft.roteiro01.entity.FreeTask;
import com.labdessoft.roteiro01.entity.Task;
import com.labdessoft.roteiro01.repository.TaskRepository;
import com.labdessoft.roteiro01.service.TaskService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class TaskServiceTest {

    @InjectMocks
    private TaskService taskService;

    @Mock
    private TaskRepository taskRepository;

    private TaskCreateDto taskCreateDto;
    private Task dateTask;
    private Task deadlineTask;
    private Task freeTask;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        taskCreateDto = new TaskCreateDto();
        taskCreateDto.setDescription("Task Description");
        taskCreateDto.setPriority("Alta");
        taskCreateDto.setType("DATA");
        taskCreateDto.setCompletionDate(LocalDate.now().plusDays(5));

        dateTask = new DateTask(taskCreateDto);
        dateTask.setId(1L);

        taskCreateDto.setType("PRAZO");
        taskCreateDto.setDaysToCompletion(10);
        deadlineTask = new DeadlineTask(taskCreateDto);
        deadlineTask.setId(2L);

        taskCreateDto.setType("LIVRE");
        freeTask = new FreeTask(taskCreateDto);
        freeTask.setId(3L);
    }

    @Test
    void testCreateDateTask() {
        taskService.createTask(taskCreateDto);
        verify(taskRepository, times(1)).save(any(FreeTask.class));
    }

    @Test
    void testCreateDeadlineTask() {
        taskCreateDto.setType("PRAZO");
        taskCreateDto.setDaysToCompletion(10);
        taskService.createTask(taskCreateDto);
        verify(taskRepository, times(1)).save(any(DeadlineTask.class));
    }

    @Test
    void testCreateFreeTask() {
        taskCreateDto.setType("LIVRE");
        taskService.createTask(taskCreateDto);
        verify(taskRepository, times(1)).save(any(FreeTask.class));
    }

    @Test
    void testCreateTaskWithInvalidDate() {
        taskCreateDto.setType("DATA");
        taskCreateDto.setCompletionDate(LocalDate.now().minusDays(1));
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> taskService.createTask(taskCreateDto));
        assertEquals("A data de conclusão deve ser hoje ou uma data futura.", exception.getMessage());
    }

    @Test
    void testCreateTaskWithInvalidType() {
        taskCreateDto.setType("INVALID");
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> taskService.createTask(taskCreateDto));
        assertEquals("Tipo de tarefa inválido: INVALID", exception.getMessage());
    }

    @Test
    void testListAllTasksWithStatus() {
        when(taskRepository.findAll()).thenReturn(Arrays.asList(dateTask, deadlineTask, freeTask));
        List<Map<String, Object>> result = taskService.listAllTasksWithStatus();
        assertEquals(3, result.size());
    }

    @Test
    void testFinishTask() throws Exception {
        when(taskRepository.findById(1L)).thenReturn(Optional.of(dateTask));
        Task finishedTask = taskService.finishTask(1L);
        assertTrue(finishedTask.isDone());
        verify(taskRepository, times(1)).save(dateTask);
    }

    @Test
    void testFinishTaskNotFound() {
        when(taskRepository.findById(1L)).thenReturn(Optional.empty());
        Exception exception = assertThrows(Exception.class, () -> taskService.finishTask(1L));
        assertEquals("Tarefa não encontrada", exception.getMessage());
    }

    @Test
    void testDeleteTask() throws Exception {
        when(taskRepository.findById(1L)).thenReturn(Optional.of(dateTask));
        taskService.deleteTask(1L);
        verify(taskRepository, times(1)).deleteById(1L);
    }

    @Test
    void testDeleteTaskNotFound() {
        when(taskRepository.findById(1L)).thenReturn(Optional.empty());
        Exception exception = assertThrows(Exception.class, () -> taskService.deleteTask(1L));
        assertEquals("Tarefa não encontrada", exception.getMessage());
    }
}
