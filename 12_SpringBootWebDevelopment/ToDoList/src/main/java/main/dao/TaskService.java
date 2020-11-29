package main.dao;

import main.controller.exception.EntityNotFoundException;
import main.model.TaskModel;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@Service
public class TaskService {
    final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public List<TaskModel> taskList() {
        ArrayList<TaskModel> tasks = new ArrayList<>();
        taskRepository.findAll().forEach(tasks::add);
        return tasks;
    }

    public TaskModel addTask(TaskModel task) {
        return taskRepository.save(task);
    }

    public TaskModel deleteTask(int id) throws EntityNotFoundException {
        Optional<TaskModel> task = taskRepository.findById(id);
        if (task.isEmpty()) {
            throw new EntityNotFoundException();
        }
        taskRepository.deleteById(id);
        return task.get();
    }
    public TaskModel updateTask (int id, TaskModel task) throws EntityNotFoundException{
        Optional<TaskModel> optionalTask = taskRepository.findById(id);
        if(optionalTask.isEmpty()) {
            throw new EntityNotFoundException();
        }
        taskRepository.save(task.update(task, optionalTask.get()));
        return optionalTask.get();
    }
    public TaskModel getTask (int id) throws EntityNotFoundException {
        Optional<TaskModel> optionalTask = taskRepository.findById(id);
        if(optionalTask.isEmpty()) {
            throw new EntityNotFoundException();
        }
        return optionalTask.get();
    }
}
