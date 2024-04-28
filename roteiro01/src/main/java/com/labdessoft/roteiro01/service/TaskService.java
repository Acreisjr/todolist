package com.labdessoft.roteiro01.service;

import com.labdessoft.roteiro01.dto.TaskCreateDto;
import com.labdessoft.roteiro01.dto.TaskTypeEnum;
import com.labdessoft.roteiro01.entity.DateTask;
import com.labdessoft.roteiro01.entity.DeadlineTask;
import com.labdessoft.roteiro01.entity.FreeTask;
import com.labdessoft.roteiro01.entity.Task;
import com.labdessoft.roteiro01.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TaskService{

    @Autowired
    TaskRepository taskRepository;

    private String calculateStatus(Task task) {
        if (task.isDone()) {
            return "Concluída";
        }

        if (task instanceof DateTask) {
            LocalDate completionDate = ((DateTask) task).getCompletionDate();
            LocalDate today = LocalDate.now();
            if (today.isAfter(completionDate)) {
                long daysBetween = java.time.temporal.ChronoUnit.DAYS.between(completionDate, today);
                return daysBetween + " dias de atraso";
            }
        } else if (task instanceof DeadlineTask) {
            Integer daysToCompletion = ((DeadlineTask) task).getDaysToCompletion();
            LocalDate dueDate = LocalDate.now().plusDays(daysToCompletion);
            LocalDate today = LocalDate.now();
            if (today.isAfter(dueDate)) {
                long daysBetween = java.time.temporal.ChronoUnit.DAYS.between(dueDate, today);
                return daysBetween + " dias de atraso";
            }
        }

        return "Prevista";
    }


    public List<Map<String, Object>> listAllTasksWithStatus() {
        List<Task> taskList = taskRepository.findAll();
        return taskList.stream().map(task -> {
            Map<String, Object> taskMap = new HashMap<>();
            taskMap.put("id", task.getId());
            taskMap.put("description", task.getDescription());
            taskMap.put("status", calculateStatus(task));
            return taskMap;
        }).collect(Collectors.toList());
    }

    
    public void createTask(TaskCreateDto taskCreateDto) {
        Task task;
        
        switch(TaskTypeEnum.fromString(taskCreateDto.getType())){
            case DATA:
                task = new DateTask(taskCreateDto);
                ((DateTask) task).setCompletionDate(taskCreateDto.getCompletionDate());
                break;
            case PRAZO:
                task = new DeadlineTask(taskCreateDto);
                ((DeadlineTask) task).setDaysToCompletion(taskCreateDto.getDaysToCompletion());
                break;
            case LIVRE:
                task = new FreeTask(taskCreateDto);
                break;
            default:
                throw new IllegalArgumentException("Tipo de tarefa inválido");
        }
        taskRepository.save(task);
    }

    public Task finishTask(Long id) throws Exception {
        Optional<Task> taskOptional = taskRepository.findById(id);

        if(!taskOptional.isPresent()){
            throw new Exception("Tarefa não encontrada");
        }

        Task task = taskOptional.get();

        task.setDone(true);

        taskRepository.save(task);

        return task;
    }

    public void deleteTask(Long id) throws Exception {
        Optional<Task> taskOptional = taskRepository.findById(id);

        if(!taskOptional.isPresent()){
            throw new Exception("Tarefa não encontrada");
        }

        taskRepository.deleteById(id);
    }
}
