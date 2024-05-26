package com.labdessoft.roteiro01.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.labdessoft.roteiro01.dto.TaskCreateDto;
import com.labdessoft.roteiro01.entity.Task;
import com.labdessoft.roteiro01.service.TaskService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class TaskControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TaskService taskService;

    @Autowired
    private ObjectMapper objectMapper;

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

        task = new Task(taskCreateDto) {
        };
        task.setId(1L);
    }

    @Test
    void shouldCreateTask() throws Exception {
        mockMvc.perform(post("/task")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(taskCreateDto)))
                .andExpect(status().isCreated())
                .andExpect(content().string("Tarefa criada com sucesso"));
    }

    @Test
    void shouldListAllTasksWithStatus() throws Exception {
        List<Map<String, Object>> tasks = Collections.singletonList(
                Map.of("id", 1L, "description", "Task Description", "status", "Prevista")
        );
        when(taskService.listAllTasksWithStatus()).thenReturn(tasks);

        mockMvc.perform(get("/task")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].description").value("Task Description"))
                .andExpect(jsonPath("$[0].status").value("Prevista"));
    }

    @Test
    void shouldFinishTask() throws Exception {
        when(taskService.finishTask(1L)).thenReturn(task);

        mockMvc.perform(patch("/task/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.description").value("Task Description"));
    }

    @Test
    void shouldDeleteTask() throws Exception {
        mockMvc.perform(delete("/task/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }
}
