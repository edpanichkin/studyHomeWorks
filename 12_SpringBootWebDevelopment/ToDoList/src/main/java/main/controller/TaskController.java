package main.controller;

import main.Storage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import response.Task;

import java.util.List;

@RestController
public class TaskController {
    @GetMapping("/tasks/")
    public List<Task> list(){
        return Storage.getAllTasks();
    }
    @PostMapping("/tasks/")
    public int add(Task task){
        return Storage.addToDo(task);
    }
    @DeleteMapping("/tasks/{id}")
    public ResponseEntity delete(@PathVariable int id){
        Task task = Storage.getTask(id);
        if(task == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Storage.deleteTask(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @PutMapping("/tasks/{id}")
    public ResponseEntity update(@PathVariable int id, Task task){
        if(Storage.getTask(id) == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Storage.updateTask(id, task);
        return new ResponseEntity<>(task,HttpStatus.OK);
    }
    @GetMapping("/tasks/{id}")
    public ResponseEntity get(@PathVariable int id){
        Task task = Storage.getTask(id);
        if(task == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
