package Kanban;

import java.io.Serializable;
import java.util.ArrayList;

public class Stage implements Serializable {

    private String name;
    private final ArrayList<Task> tasks;

    public Stage(String name, ArrayList<Task> tasks) {

        this.name = name;
        this.tasks = tasks;
    }

    public String getName() {

        return name;
    }

    public void setName(String name) {

        this.name = name;
    }

    public ArrayList<Task> getTasks() {

        return tasks;
    }

    public void addTask(Task task) {

        tasks.add(task);
    }

    public void removeTask(Task task) {

        tasks.remove(task);
    }
}
