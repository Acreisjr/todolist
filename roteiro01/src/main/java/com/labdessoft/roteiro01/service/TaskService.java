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

import java.util.List;
import java.util.Optional;

@Service
public class TaskService{

    @Autowired
    TaskRepository taskRepository;

    public List<Task> listAll() {
        return taskRepository.findAll();
    }

    public void createTask(TaskCreateDto taskCreateDto) {
        Task task;
        
        switch(TaskTypeEnum.valueOf(taskCreateDto.getType())){
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
