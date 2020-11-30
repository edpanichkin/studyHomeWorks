package main.model;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
public class TaskModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @NotNull(message = "Task name is necessary")
    private String name;
    @NotNull(message = "About is necessary")
    private String about;
    private boolean done;

    public TaskModel() {
        this.done = false;
    }

    public TaskModel(String name, String about){
        this.name = name;
        this.about= about;
        this.done = false;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

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

    public String getName() {
        return name;
    }
    public TaskModel update (TaskModel task, TaskModel oldTask) {
        if(task.getName() == null) {
            task.setName(oldTask.getName());
        }
        if(task.getAbout() == null) {
            task.setAbout(oldTask.getAbout());
        }
        if(oldTask.getDone()) {
            task.setDone(true);
        }
        return task;
    }

    public void setName(String name) {
        this.name = name;
    }

}
