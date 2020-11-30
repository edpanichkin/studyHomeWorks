package main.model;

public class TaskMapper {
    public static TaskModel map(TaskModel item) {
        TaskModel taskModel = new TaskModel(item.getName(), item.getAbout());
        taskModel.setId(item.getId());
        taskModel.setDone(item.getDone());
        return taskModel;
    }
}
