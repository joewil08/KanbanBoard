package Kanban;

import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {

        // create the default stages and add them to a board
        Stage notStarted = new Stage("Not Started", new ArrayList<>());
        Stage inProgress = new Stage("In Progress", new ArrayList<>());
        Stage completed = new Stage("Completed", new ArrayList<>());
        Board board = new Board(new ArrayList<>());
        board.addStage(0, notStarted);
        board.addStage(1, inProgress);
        board.addStage(2, completed);

        // UI
        System.out.println("Welcome to Kanban Board Maker!");
        UI ui = new UI(board);
        while (ui.isRunning()) {
            ui.printUserOptions();
            String input = ui.askUserStringInput();
            ui.processUserInput(input);
        }
        System.out.println("Thanks for using Kanban Board Maker!");
    }
}