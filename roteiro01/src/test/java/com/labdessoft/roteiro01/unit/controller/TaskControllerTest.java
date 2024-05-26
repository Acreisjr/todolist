package com.labdessoft.roteiro01.unit.controller;

import com.labdessoft.roteiro01.controller.TaskController;
import com.labdessoft.roteiro01.dto.TaskCreateDto;
import com.labdessoft.roteiro01.entity.Task;
import com.labdessoft.roteiro01.service.TaskService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TaskControllerTest {

    @InjectMocks
    private TaskController taskController;

    @Mock
    private TaskService taskService;

    private TaskCreateDto taskCreateDto;
    private Task task;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        taskCreateDto = new TaskCreateDto();
        taskCreateDto.setDescription("Task Description");
        taskCreateDto.setPriority("Alta");
        taskCreateDto.setType("DATA");
        taskCreateDto.setCompletionDate(LocalDate.now().plusDays(5));

        task = new Task(taskCreateDto) {};
        task.setId(1L);
    }

    @Test
    void testCreateTask() throws Exception {
        ResponseEntity<String> response = taskController.createTask(taskCreateDto);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("Tarefa criada com sucesso", response.getBody());
        verify(taskService, times(1)).createTask(taskCreateDto);
    }

    @Test
    void testCreateTaskWithException() throws Exception {
        doThrow(new RuntimeException("Erro ao criar tarefa")).when(taskService).createTask(any(TaskCreateDto.class));
        ResponseEntity<String> response = taskController.createTask(taskCreateDto);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("Erro ao criar tarefa: Erro ao criar tarefa", response.getBody());
    }

    @Test
    void testListAllTasks() throws Exception {
        List<Map<String, Object>> tasks = Collections.singletonList(
                Map.of("id", 1L, "description", "Task Description", "status", "Prevista")
        );
        when(taskService.listAllTasksWithStatus()).thenReturn(tasks);

        ResponseEntity<List<Map<String, Object>>> response = taskController.listAll();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
        assertEquals("Task Description", response.getBody().get(0).get("description"));
    }

    @Test
    void testListAllTasksNoContent() throws Exception {
        when(taskService.listAllTasksWithStatus()).thenReturn(Collections.emptyList());
        ResponseEntity<List<Map<String, Object>>> response = taskController.listAll();
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    void testListAllTasksWithException() throws Exception {
        doThrow(new RuntimeException("Erro ao listar tarefas")).when(taskService).listAllTasksWithStatus();
        ResponseEntity<List<Map<String, Object>>> response = taskController.listAll();
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    void testFinishTask() throws Exception {
        when(taskService.finishTask(1L)).thenReturn(task);

        ResponseEntity<Task> response = taskController.finishTask(1L);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1L, response.getBody().getId());
        assertEquals("Task Description", response.getBody().getDescription());
    }

    @Test
    void testFinishTaskWithException() throws Exception {
        doThrow(new RuntimeException("Erro ao finalizar tarefa")).when(taskService).finishTask(anyLong());
        ResponseEntity<Task> response = taskController.finishTask(1L);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    void testDeleteTask() throws Exception {
        ResponseEntity<String> response = taskController.deleteTask(1L);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(taskService, times(1)).deleteTask(1L);
    }

    @Test
    void testDeleteTaskWithException() throws Exception {
        doThrow(new RuntimeException("Erro ao deletar tarefa")).when(taskService).deleteTask(anyLong());
        ResponseEntity<String> response = taskController.deleteTask(1L);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("Erro ao deletar tarefa: Erro ao deletar tarefa", response.getBody());
    }
}
