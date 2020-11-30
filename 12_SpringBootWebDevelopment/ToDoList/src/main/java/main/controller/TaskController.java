package main.controller;

import main.controller.exception.EntityNotFoundException;
import main.dao.TaskService;
import main.model.TaskMapper;
import org.springframework.web.bind.annotation.*;
import main.model.TaskModel;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class TaskController  {
    final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping("/tasks/")
    public List<TaskModel> list(){
        return taskService.taskList().stream().map(TaskMapper::map).collect(Collectors.toList());
    }

    @PostMapping("/tasks/")
    public TaskModel addTask(@Valid TaskModel task) {
        return TaskMapper.map(taskService.addTask(task));
    }
    @DeleteMapping("/tasks/{id}")
    public TaskModel deleteTask(@PathVariable int id) throws EntityNotFoundException{
        return TaskMapper.map(taskService.deleteTask(id));
    }
    @PutMapping("/tasks/{id}")
    public TaskModel updateTask (@PathVariable int id, TaskModel task) throws EntityNotFoundException{
        return TaskMapper.map(taskService.updateTask(id, task));
    }
    @GetMapping("/tasks/{id}")
    public TaskModel getTask (@PathVariable int id) throws EntityNotFoundException {
        return TaskMapper.map(taskService.getTask(id));
    }

}
