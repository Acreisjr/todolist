package com.labdessoft.roteiro01.controller;

import com.labdessoft.roteiro01.dto.TaskCreateDto;
import com.labdessoft.roteiro01.entity.Task;
import com.labdessoft.roteiro01.service.TaskService;

import io.swagger.v3.oas.annotations.Operation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TaskController {

    @Autowired
    TaskService taskService;

    @GetMapping("/task")
    @Operation(summary = "Listar todas as tarefas da lista")
    public ResponseEntity<List<Task>> listAll() throws Exception {
        try{
            List<Task> taskList = taskService.listAll();
            
            if(taskList.isEmpty()){
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            
            return ResponseEntity.ok(taskList);
        }catch(Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/task")
    @Operation(summary = "Criar uma nova tarefa")
    public ResponseEntity<Void> createTask(@RequestBody TaskCreateDto taskCreateDto) throws Exception {
        try{
            taskService.createTask(taskCreateDto);
            return new ResponseEntity<>(HttpStatus.CREATED);
        }catch(Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PatchMapping("/task/{id}")
    @Operation(summary = "Finalizar uma tarefa")
    public ResponseEntity<Task> finishTask(@PathVariable Long id) throws Exception {
        try{
            Task task = taskService.finishTask(id);
            return ResponseEntity.ok(task);
        }catch(Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/task/{id}")
    @Operation(summary = "Deletar uma tarefa")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id) throws Exception {
        try{
            taskService.deleteTask(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }catch(Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}