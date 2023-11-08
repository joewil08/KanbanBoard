package Kanban;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.io.*;

public class UI {

    private boolean running = true;
    private Board board;
    private HashMap<String, Board> savedBoards = new HashMap<>();

    UI(Board board) {
        this.board = board;
    }

    public boolean isRunning() {
        return running;
    }

    public void printUserOptions() {

        // print all the main options
        System.out.println("""
                Options:
                \tPB - Print Board\t\tPS - Print Stage
                \tAT - Add Task\t\t\tAS - Add Stage
                \tMT - Move Task\t\t\tMS - Move Stage
                \tET - Edit Task\t\t\tES - Edit Stage
                \tDT - Delete Task\t\tDS - Delete Stage
                \tSB - Save Board\t\t\tOB - Open Board
                \tCB - Clear Board\t\tDB - Delete Board
                \tQ  - Quit""");
    }

    public String askUserStringInput() {

        // user enters anything, and it is converted to and returned as a string
        Scanner scan = new Scanner(System.in);
        return scan.nextLine();
    }

    public int askUserIntInput(ArrayList<Integer> validResponses) {

        Scanner scan = new Scanner(System.in);
        int input;
        // try catch in case user enters a non-integer
        try {
            input = scan.nextInt();
        } catch (InputMismatchException e) {
            System.out.println("Invalid Input - Try Again");
            input = askUserIntInput(validResponses);
        }
        // check if input is not a valid response, asks for input again if true
        if (!validResponses.contains(input)) {
            System.out.println("Invalid Input - Try Again");
            input = askUserIntInput(validResponses);
        }
        return input;
    }

    public Stage askUserStageInput() {

        // user can select any of the stages
        ArrayList<Integer> possibleStages = new ArrayList<>();
        for (int i = 0; i < board.getStages().size(); i++) {
            possibleStages.add(i);
        }
        int stageIndex = askUserIntInput(possibleStages);
        return board.getStages().get(stageIndex);
    }

    public Task askUserTaskInput(Stage stage) {

        // user can select any of the tasks in a stage
        ArrayList<Integer> possibleTasks = new ArrayList<>();
        for (int i = 0; i < stage.getTasks().size(); i++) {
            possibleTasks.add(i);
        }
        int taskIndex = askUserIntInput(possibleTasks);
        return stage.getTasks().get(taskIndex);
    }

    public int askUserStageMovementInput() {

        // user can select the index to put the stage at
        ArrayList<Integer> possibleIndexes = new ArrayList<>();
        for (int i = 0; i < board.getStages().size() + 1; i++) {
            possibleIndexes.add(i);
        }
        return askUserIntInput(possibleIndexes);
    }

    public void printStageList() {

        // prints every stage in a numbered list
        for (int i = 0; i < board.getStages().size(); i++) {
            System.out.printf("\t%d - %s\n", i, board.getStages().get(i).getName());
        }
    }

    public void printTaskList(Stage stage) {

        // prints every task in a stage in a numbered list
        for (int i = 0; i < stage.getTasks().size(); i++) {
            System.out.printf("\t%d - %s\n", i, stage.getTasks().get(i).getDescription());
        }
    }

    public void printStageMovementOptions() {

        // prints every stage with numbered locations where a stage can be put
        System.out.println("\t0 - *HERE*");
        for (int i = 0; i < board.getStages().size(); i++) {
            System.out.printf("\t%s\n", board.getStages().get(i).getName());
            System.out.printf("\t%d - *HERE*\n", i + 1);
        }
    }

    public HashMap<String, Board> readSavedBoardsFile() {

        // obtain hashmap containing boards from file
        HashMap<String, Board> deserialized = new HashMap<>();
        try (FileInputStream fis = new FileInputStream("SavedKanbanBoards.dat");
             ObjectInputStream ois = new ObjectInputStream(fis)) {
            deserialized = (HashMap<String, Board>) ois.readObject();
        } catch (FileNotFoundException e) {
            System.out.println("There are no saved boards");
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return deserialized;
    }

    public void processUserInput(String input) {

        // takes user input and performs the corresponding task
        if (input.equalsIgnoreCase("PB")) {
            printBoard();
        }
        else if (input.equalsIgnoreCase("PS")) {
            printStage();
        }
        else if (input.equalsIgnoreCase("AT")) {
            addTask();
        }
        else if (input.equalsIgnoreCase("MT")) {
            moveTask();
        }
        else if (input.equalsIgnoreCase("ET")) {
            editTask();
        }
        else if (input.equalsIgnoreCase("DT")) {
            deleteTask();
        }
        else if (input.equalsIgnoreCase("AS")) {
            addStage();
        }
        else if (input.equalsIgnoreCase("MS")) {
            moveStage();
        }
        else if (input.equalsIgnoreCase("ES")) {
            editStage();
        }
        else if (input.equalsIgnoreCase("DS")) {
            deleteStage();
        }
        else if (input.equalsIgnoreCase("SB")) {
            saveBoard();
        }
        else if (input.equalsIgnoreCase("OB")) {
            openBoard();
        }
        else if (input.equalsIgnoreCase("CB")) {
            clearBoard();
        }
        else if (input.equalsIgnoreCase("DB")) {
            deleteBoard();
        }
        else if (input.equalsIgnoreCase("Q")) {
            running = false;
        }
    }

    public void printBoard() {

        if (board.getStages().isEmpty()) {
            System.out.println("There are no stages to print");
        } else {
            for (Stage stage : board.getStages()) {
                System.out.printf("--- %s ---\n", stage.getName());
                for (Task task : stage.getTasks()) {
                    System.out.printf("\t> %s\n", task.getDescription());
                }
                System.out.println();
            }
        }
    }

    public void printStage() {

        // check if there are no stages
        if (board.getStages().isEmpty()) {
            System.out.println("There are no stages to print");
            return;
        }
        // user selects which stage to print
        System.out.println("Select stage to print:");
        printStageList();
        Stage stageSelection = askUserStageInput();
        // print stage name and tasks
        System.out.printf("--- %s ---\n", stageSelection.getName());
        for (Task task : stageSelection.getTasks()) {
            System.out.printf("\t> %s\n", task.getDescription());
        }
        System.out.println();
    }

    public void addTask() {

        // check if there are any stages
        if (board.getStages().isEmpty()) {
            System.out.println("There are no stages to add a task to");
            return;
        }
        // user creates new task
        System.out.println("Enter new task description:");
        String newTaskDescription = askUserStringInput();
        Task newTask = new Task(newTaskDescription);
        // user selects stage to add task
        System.out.println("Select stage to add new task to:");
        printStageList();
        Stage stageSelection = askUserStageInput();
        // add new task to stage
        stageSelection.addTask(newTask);
    }

    public void deleteTask() {

        // check if all the stages are empty
        if (board.checkAllStagesAreEmpty()) {
            System.out.println("There are no tasks to delete");
            return;
        }
        // user selects stage
        System.out.println("Select stage to delete task from:");
        printStageList();
        Stage stageSelection = askUserStageInput();
        // check if the selected stage is empty
        if (stageSelection.getTasks().isEmpty()) {
            System.out.println("There are no tasks in this stage");
            deleteTask(); //  ask the user again
            return;
        }
        // user selects task from stage
        System.out.println("Select task to delete:");
        printTaskList(stageSelection);
        Task taskSelection = askUserTaskInput(stageSelection);
        // delete task if user confirms
        System.out.println("""
                This will delete the task!
                \tY - Confirm
                \tN - Return""");
        String response = askUserStringInput();
        if (response.equalsIgnoreCase("Y")) {
            stageSelection.removeTask(taskSelection);
        }
    }

    public void moveTask() {

        // check if all the stages are empty
        if (board.checkAllStagesAreEmpty()) {
            System.out.println("There are no tasks to move");
            return;
        }
        // user selects stage to move task from
        System.out.println("Select stage to move task from:");
        printStageList();
        Stage oldStageSelection = askUserStageInput();
        // check if the selected stage is empty
        if (oldStageSelection.getTasks().isEmpty()) {
            System.out.println("There are no tasks in this stage");
            moveTask(); //  ask the user again
            return;
        }
        // user selects task from stage
        System.out.println("Select task to move:");
        printTaskList(oldStageSelection);
        Task taskSelection = askUserTaskInput(oldStageSelection);
        // user selects stage to move task to
        System.out.println("Select stage to move task to:");
        printStageList();
        Stage newStageSelection = askUserStageInput();
        //  add task to new stage and delete task from old stage
        newStageSelection.addTask(taskSelection);
        oldStageSelection.removeTask(taskSelection);
    }

    public void editTask() {

        // check if all the stages are empty
        if (board.checkAllStagesAreEmpty()) {
            System.out.println("There are no tasks to edit");
            return;
        }
        // user selects stage
        System.out.println("Select stage to edit task:");
        printStageList();
        Stage stageSelection = askUserStageInput();
        // check if the selected stage is empty
        if (stageSelection.getTasks().isEmpty()) {
            System.out.println("There are no tasks in this stage");
            deleteTask(); //  ask the user again
            return;
        }
        // user selects task from stage
        System.out.println("Select task to edit:");
        printTaskList(stageSelection);
        Task taskSelection = askUserTaskInput(stageSelection);
        // edit task description
        System.out.println("Enter new task description:");
        String newDescription = askUserStringInput();
        taskSelection.setDescription(newDescription);
    }

    public void addStage() {

        // user enters stage name
        System.out.println("Enter stage name:");
        String stageName = askUserStringInput();
        // user enters where to add stage
        System.out.println("Select where to add stage:");
        printStageMovementOptions();
        int stageIndex = askUserStageMovementInput();
        // add new stage
        Stage newStage = new Stage(stageName, new ArrayList<>());
        board.addStage(stageIndex, newStage);
    }

    public void moveStage() {

        // check if there are any stages
        if (board.getStages().isEmpty()) {
            System.out.println("There are no stages to move");
            return;
        }
        // user selects stage
        System.out.println("Select stage to move:");
        printStageList();
        Stage stageSelection = askUserStageInput();
        board.removeStage(stageSelection);
        // user enters where to add stage
        System.out.println("Select where to add stage:");
        printStageMovementOptions();
        int stageIndex = askUserStageMovementInput();
        // move stage to new location
        board.addStage(stageIndex, stageSelection);
    }

    public void editStage() {

        // check if there are any stages
        if (board.getStages().isEmpty()) {
            System.out.println("There are no stages to edit");
            return;
        }
        // user selects stage
        System.out.println("Select stage to edit name:");
        printStageList();
        Stage stageSelection = askUserStageInput();
        // user edits stage name
        System.out.println("Enter new stage name:");
        String newName = askUserStringInput();
        stageSelection.setName(newName);
    }


    public void deleteStage() {

        // check if there are any stages
        if (board.getStages().isEmpty()) {
            System.out.println("There are no stages to delete");
            return;
        }
        // user selects stage
        System.out.println("Select stage to delete:");
        printStageList();
        Stage stageSelection = askUserStageInput();
        // delete stage if user confirms
        System.out.println("""
                This will delete the stage and all the tasks contained!
                \tY - Confirm
                \tN - Return""");
        String response = askUserStringInput();
        if (response.equalsIgnoreCase("Y")) {
            board.removeStage(stageSelection);
        }
    }

    public void clearBoard() {

        // ask user to confirm
        System.out.println("""
                This will delete all stages and tasks!
                \tY - Confirm
                \tN - Return""");
        String response = askUserStringInput();
        // clear the board
        if (response.equalsIgnoreCase("Y")) {
            board = new Board(new ArrayList<>());
        }
    }

    public void saveBoard() {

        // user enters name for board
        System.out.println("Enter a name for this board to be saved as:");
        String boardName = askUserStringInput();
        // put key: name and value: board into hashmap
        savedBoards.put(boardName, board);
        // save hashmap in file
        try (FileOutputStream fos = new FileOutputStream("SavedKanbanBoards.dat");
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(savedBoards);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void openBoard() {

        // obtain hashmap containing boards from file
        savedBoards = readSavedBoardsFile();
        // user enters name of board to open
        System.out.println("Enter the name of the board you want to open:");
        for (String boardName : savedBoards.keySet()) {
            System.out.printf("\t%s\n", boardName);
        }
        String input = askUserStringInput();
        if (savedBoards.containsKey(input)) {
            board = savedBoards.get(input);
        } else {
            System.out.println("This board does not exist");
        }


    }

    public void deleteBoard() {

        // obtain hashmap containing boards from file
        savedBoards = readSavedBoardsFile();
        // user enters name of board to delete
        System.out.println("Enter the name of the board you want to delete:");
        for (String boardName : savedBoards.keySet()) {
            System.out.printf("\t%s\n", boardName);
        }
        String boardNameInput = askUserStringInput();
        if (savedBoards.containsKey(boardNameInput)) {
            board = savedBoards.get(boardNameInput);
            System.out.println("""
                    This will delete the entire board!
                    \tY - Confirm
                    \tN - Return""");
            String response = askUserStringInput();
            // remove board from saved boards and clear the board
            if (response.equalsIgnoreCase("Y")) {
                savedBoards.remove(boardNameInput);
                board = new Board(new ArrayList<>());
            }
        } else {
            System.out.println("This board does not exist");
        }
    }
}
