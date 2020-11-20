package main.model;

import javax.persistence.*;

@Entity
public class Task{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String name;
    private boolean done = false;

    public boolean getDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTaskName() {
        return name;
    }
    public Task update (Task task, Task oldTask) {
        if(task.getTaskName() == null) {
            task.setTaskName(oldTask.getTaskName());
        }
        return task;
    }

    public void setTaskName(String taskName) {
        this.name = taskName;
    }

}
