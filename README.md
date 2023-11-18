# Kanban Board App

## Description

This is a Java application that empowers users to efficiently organize tasks through the creation, editing, management, and saving of Kanban boards through the terminal. With many options, users can streamline their workflow and stay organized. The app also boasts a robust testing suite implemented with JUnit 5 to ensure reliability and ease of development.

## Features

- **Task Organization:** Add, delete, edit, and move tasks and stages to organize tasks effectively.
- **Saved Boards:** Name and save all your Kanban boards in a single file so they can be accessed anytime.
- **User-Friendly Interface:** The app provides an intuitive and easy-to-use interface for seamless task management.
- **JUnit 5 Unit Tests:** Robust unit tests implemented with JUnit 5 to ensure code reliability and facilitate development.

## Installation

To run the Kanban Board App, follow these steps:

1. Clone this repository: `git clone https://github.com/joewil08/KanbanBoard.git`
2. Navigate to the project directory: `cd KanbanBoard`
3. Compile the Java code: `javac -d bin src/*.java`
4. Run the app: `java -cp bin Main`

## Usage

1. Launch the application by running `Main.java`.
2. View the valid inputs for the different commands.
3. Choose a command and follow the prompts.

Here is a brief description of each command:

```
PB - Print Board / PS - Print Stage
 
AT - Add Task / AS - Add Stage
 
MT - Move Task / MS - Move Stage
 
ET - Edit Task / ES - Edit Stage
 
DT - Delete Task / DS - Delete Stage
 
SB - Save Board
 
OB - Open Board
  
CB - Clear Board
 
DB - Delete Board
  
Q  - Quit
```

## JUnit 5

Unit tests are implemented with [JUnit 5](https://github.com/junit-team/junit5) to facilitate development.
