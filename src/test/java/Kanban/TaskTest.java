package Kanban;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

public class TaskTest {

    private Task task;

    @BeforeEach
    public void setUp() {

        task = new Task("Test Task");
    }

    @Test
    public void getDescriptionTest01() {

        String expected = "Test Task";
        String actual = task.getDescription();

        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void setDescriptionTest01() {

        String expected = "Edited Description";
        task.setDescription(expected);
        String actual = task.getDescription();

        Assertions.assertEquals(expected, actual);
    }
}
