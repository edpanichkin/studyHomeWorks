package main.controller;

import main.controller.exception.EntityNotFoundException;
import main.model.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import main.model.Task;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
public class TaskController  {
    @Autowired
    private TaskRepository taskRepository;

    @GetMapping("/tasks/")
    public List<Task> list(){
        ArrayList<Task> tasks = new ArrayList<>();
        taskRepository.findAll().forEach(tasks::add);
        return tasks;
    }

    @PostMapping("/tasks/")
    public Task addTask(Task task) {
        return taskRepository.save(task);
    }
    @DeleteMapping("/tasks/{id}")
    public Task deleteTask(@PathVariable int id) throws EntityNotFoundException{
        Optional<Task> task = taskRepository.findById(id);
        if(task.isEmpty()) {
            throw new EntityNotFoundException();
        }
        taskRepository.deleteById(id);
        return task.get();
    }
    @PutMapping("/tasks/{id}")
    public Task updateTask (@PathVariable int id, Task task) throws EntityNotFoundException{
        Optional<Task> optionalTask = taskRepository.findById(id);
        if(optionalTask.isEmpty()) {
            throw new EntityNotFoundException();
        }
        taskRepository.save(task.update(task, optionalTask.get()));
        return optionalTask.get();
    }
    @GetMapping("/tasks/{id}")
    public Task getTask (@PathVariable int id) throws EntityNotFoundException {
        Optional<Task> optionalTask = taskRepository.findById(id);
        if(optionalTask.isEmpty()) {
            throw new EntityNotFoundException();
        }
        return optionalTask.get();
    }

}
