package Kanban;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class BoardTest {

    private Board board;

    @BeforeEach
    public void setUp() {

        board = new Board(new ArrayList<>());
    }

    @Test
    public void getStagesTest01() {

        ArrayList<Stage> expected = new ArrayList<>();
        ArrayList<Stage> actual = board.getStages();

        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void addStageTest01() {

        Stage stage = new Stage("Test Stage", new ArrayList<>());

        board.addStage(0, stage);
        boolean actual = board.getStages().contains(stage);

        Assertions.assertTrue(actual);
    }

    @Test
    public void addStageTest02() {

        Stage stage0 = new Stage("Test Stage 0", new ArrayList<>());
        Stage stage1 = new Stage("Test Stage 1", new ArrayList<>());
        Stage stage2 = new Stage("Test Stage 2", new ArrayList<>());
        Stage[] stages = {stage0, stage1, stage2};

        board.addStage(0, stage0);
        board.addStage(1, stage1);
        board.addStage(2, stage2);
        boolean actual = board.getStages().containsAll(List.of(stages));

        Assertions.assertTrue(actual);
    }

    @Test
    public void removeStageTest01() {

        Stage stage = new Stage("Test Stage", new ArrayList<>());

        board.addStage(0, stage);
        board.removeStage(stage);
        boolean actual = board.getStages().contains(stage);

        Assertions.assertFalse(actual);
    }

    @Test
    public void removeStageTest02() {

        Stage stage0 = new Stage("Test Stage 0", new ArrayList<>());
        Stage stage1 = new Stage("Test Stage 1", new ArrayList<>());
        Stage stage2 = new Stage("Test Stage 2", new ArrayList<>());

        board.addStage(0, stage0);
        board.addStage(1, stage1);
        board.addStage(2, stage2);
        board.removeStage(stage1);
        boolean actual = board.getStages().contains(stage1);

        Assertions.assertFalse(actual);
    }

    @Test
    public void checkAllStagesAreEmptyTest01() {

        boolean actual = board.checkAllStagesAreEmpty();

        Assertions.assertTrue(actual);
    }

    @Test
    public void checkAllStagesAreEmptyTest02() {

        Stage stage = new Stage("Test Stage", new ArrayList<>());

        board.addStage(0, stage);
        boolean actual = board.checkAllStagesAreEmpty();

        Assertions.assertTrue(actual);
    }

    @Test
    public void checkAllStagesAreEmptyTest03() {

        Stage stage = new Stage("Test Stage", new ArrayList<>());
        Task task = new Task("Test Task");
        stage.addTask(task);

        board.addStage(0, stage);
        boolean actual = board.checkAllStagesAreEmpty();

        Assertions.assertFalse(actual);
    }

}
