package main.controller;

import main.controller.exception.EntityNotFoundException;
import main.dao.TaskService;
import org.springframework.web.bind.annotation.*;
import main.model.Task;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
public class TaskController  {
    final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping("/tasks/")
    public List<Task> list(){
        return taskService.taskList();
    }

    @PostMapping("/tasks/")
    public Task addTask(Task task) {
        return taskService.addTask(task);
    }
    @DeleteMapping("/tasks/{id}")
    public Task deleteTask(@PathVariable int id) throws EntityNotFoundException{
        return taskService.deleteTask(id);
    }
    @PutMapping("/tasks/{id}")
    public Task updateTask (@PathVariable int id, Task task) throws EntityNotFoundException{
        return taskService.updateTask(id, task);
    }
    @GetMapping("/tasks/{id}")
    public Task getTask (@PathVariable int id) throws EntityNotFoundException {
        return taskService.getTask(id);
    }

}
