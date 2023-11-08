package Kanban;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

public class StageTest {

    private Stage stage;

    @BeforeEach
    public void setUp() {

        stage = new Stage("Test Stage", new ArrayList<>());
    }

    @Test
    public void getNameTest01() {

        String expected = "Test Stage";
        String actual = stage.getName();

        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void setNameTest01() {

        String expected = "Edited Name";
        stage.setName(expected);
        String actual = stage.getName();

        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void addTaskTest01() {

        Task task = new Task("Test Task");
        stage.addTask(task);

        boolean actual = stage.getTasks().contains(task);
        Assertions.assertTrue(actual);
    }

    @Test
    public void removeTaskTest01() {

        Task task = new Task("Test Task");
        stage.addTask(task);
        stage.removeTask(task);

        boolean actual = stage.getTasks().contains(task);
        Assertions.assertFalse(actual);
    }
}
