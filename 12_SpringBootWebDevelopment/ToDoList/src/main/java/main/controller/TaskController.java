package main.controller;

import main.Storage;
import main.model.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
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
    public ResponseEntity add(Task task){
        Task newTask = taskRepository.save(task);
        return new ResponseEntity<>(newTask, HttpStatus.CREATED);
    }
    @DeleteMapping("/tasks/{id}")
    public ResponseEntity delete(@PathVariable int id){
        if(taskRepository.findById(id).isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        taskRepository.deleteById(id);
        return new ResponseEntity<>("DELETED" + id, HttpStatus.OK);
    }
    @PutMapping("/tasks/{id}")
    public ResponseEntity update(@PathVariable int id, Task task){

        if(taskRepository.findById(id).isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Task oldTask = taskRepository.findById(id).get();
        taskRepository.save(task.update(task, oldTask));
        return new ResponseEntity<>(taskRepository.findById(id),HttpStatus.OK);
    }
    @GetMapping("/tasks/{id}")
    public ResponseEntity get(@PathVariable int id){
        Optional<Task> optionalTask = taskRepository.findById(id);
        if(!optionalTask.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(optionalTask.get(), HttpStatus.OK);
    }

}
