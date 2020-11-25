package main.dao;

import main.controller.exception.EntityNotFoundException;
import main.model.Task;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@Component
public class TaskService {
    final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public List<Task> taskList() {
        ArrayList<Task> tasks = new ArrayList<>();
        taskRepository.findAll().forEach(tasks::add);
        return tasks;
    }

    public Task addTask(Task task) {
        return taskRepository.save(task);
    }

    public Task deleteTask(int id) throws EntityNotFoundException {
        Optional<Task> task = taskRepository.findById(id);
        if (task.isEmpty()) {
            throw new EntityNotFoundException();
        }
        taskRepository.deleteById(id);
        return task.get();
    }
    public Task updateTask (int id, Task task) throws EntityNotFoundException{
        Optional<Task> optionalTask = taskRepository.findById(id);
        if(optionalTask.isEmpty()) {
            throw new EntityNotFoundException();
        }
        taskRepository.save(task.update(task, optionalTask.get()));
        return optionalTask.get();
    }
    public Task getTask (int id) throws EntityNotFoundException {
        Optional<Task> optionalTask = taskRepository.findById(id);
        if(optionalTask.isEmpty()) {
            throw new EntityNotFoundException();
        }
        return optionalTask.get();
    }
}
