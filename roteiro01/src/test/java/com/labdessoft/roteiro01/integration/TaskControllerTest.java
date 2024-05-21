package com.labdessoft.roteiro01.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.labdessoft.roteiro01.controller.TaskController;
import com.labdessoft.roteiro01.dto.TaskCreateDto;
import com.labdessoft.roteiro01.entity.DateTask;
import com.labdessoft.roteiro01.entity.DeadlineTask;
import com.labdessoft.roteiro01.entity.FreeTask;
import com.labdessoft.roteiro01.service.TaskService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TaskController.class)
public class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TaskService taskService;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(new TaskController()).build();
    }

    @Test
    public void testListAll() throws Exception {
        List<Map<String, Object>> tasks = new ArrayList<>();
        when(taskService.listAllTasksWithStatus()).thenReturn(tasks);

        mockMvc.perform(get("/task"))
                .andExpect(status().isNoContent());

        tasks.add(Map.of("id", 1L, "description", "Test Task", "status", "Prevista"));
        when(taskService.listAllTasksWithStatus()).thenReturn(tasks);

        mockMvc.perform(get("/task"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1))
                .andExpect(jsonPath("$[0].description").value("Test Task"));
    }

    @Test
    public void testCreateDateTask() throws Exception {
        TaskCreateDto taskCreateDto = new TaskCreateDto();
        taskCreateDto.setType("DATA");
        taskCreateDto.setDescription("New Date Task");
        taskCreateDto.setCompletionDate(LocalDate.now().plusDays(1));
        taskCreateDto.setPriority("High");

        doNothing().when(taskService).createTask(any(TaskCreateDto.class));

        mockMvc.perform(post("/task")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(taskCreateDto)))
                .andExpect(status().isCreated())
                .andExpect(content().string("Tarefa criada com sucesso"));
    }

    @Test
    public void testCreateDeadlineTask() throws Exception {
        TaskCreateDto taskCreateDto = new TaskCreateDto();
        taskCreateDto.setType("PRAZO");
        taskCreateDto.setDescription("New Deadline Task");
        taskCreateDto.setDaysToCompletion(5);
        taskCreateDto.setPriority("Medium");

        doNothing().when(taskService).createTask(any(TaskCreateDto.class));

        mockMvc.perform(post("/task")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(taskCreateDto)))
                .andExpect(status().isCreated())
                .andExpect(content().string("Tarefa criada com sucesso"));
    }

    @Test
    public void testCreateFreeTask() throws Exception {
        TaskCreateDto taskCreateDto = new TaskCreateDto();
        taskCreateDto.setType("LIVRE");
        taskCreateDto.setDescription("New Free Task");
        taskCreateDto.setPriority("Low");

        doNothing().when(taskService).createTask(any(TaskCreateDto.class));

        mockMvc.perform(post("/task")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(taskCreateDto)))
                .andExpect(status().isCreated())
                .andExpect(content().string("Tarefa criada com sucesso"));
    }

    @Test
    public void testFinishTask() throws Exception {
        FreeTask task = new FreeTask();
        task.setId(1L);
        task.setDescription("Test Task");
        task.setDone(true);

        when(taskService.finishTask(1L)).thenReturn(task);

        mockMvc.perform(patch("/task/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.description").value("Test Task"))
                .andExpect(jsonPath("$.done").value(true));
    }

    @Test
    public void testDeleteTask() throws Exception {
        doNothing().when(taskService).deleteTask(1L);

        mockMvc.perform(delete("/task/1"))
                .andExpect(status().isNoContent());
    }
}
