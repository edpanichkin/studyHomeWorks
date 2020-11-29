package main;

import main.model.TaskModel;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Storage {
    private static int currentId = 1;
    private static HashMap<Integer, TaskModel> tasks = new HashMap<>();

    public static List<TaskModel> getAllTasks() {
        return new ArrayList<>(tasks.values());
    }
    public static int addTask(TaskModel task) {
        int id = currentId++;
        task.setId(id);
        tasks.put(id, task);
        return id;
    }

    public static TaskModel getTask (int taskId) {
        if(tasks.containsKey(taskId)) {
            return tasks.get(taskId);
        }
        return null;
    }
    public static void deleteTask (int taskId) {
        tasks.remove(taskId);
    }
    public static void updateTask (int taskId, TaskModel task) {
        tasks.replace(taskId, task);
    }
}
