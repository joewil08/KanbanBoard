package Kanban;

import java.io.Serializable;

public class Task implements Serializable {

    private String description;

    public Task(String description) {

        this.description = description;
    }

    public String getDescription() {

        return description;
    }

    public void setDescription(String description) {

        this.description = description;
    }
}
