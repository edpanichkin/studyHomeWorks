package main.model;
import javax.persistence.*;

@Entity
public class Task{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String name;
    private String about;
    private boolean done;
    public Task() {
        this.done = false;
    }

    public Task(String name, String about){
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
    public Task update (Task task, Task oldTask) {
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
