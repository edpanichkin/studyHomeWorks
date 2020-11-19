package main.model;

import javax.persistence.*;

@Entity
public class Task{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @Column(name ="task_name")
    private String taskName;
    @Column(name = "is_done")
    private boolean isDone = false;

    public boolean isDone() {
        return isDone;
    }

    public void setDone(boolean done) {
        isDone = done;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTaskName() {
        return taskName;
    }
    public Task update (Task task, Task oldTask) {
        if(task.getTaskName() == null) {
            task.setTaskName(oldTask.getTaskName());
        }
        return task;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

}
