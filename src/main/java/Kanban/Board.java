package Kanban;

import java.io.Serializable;
import java.util.ArrayList;

public class Board implements Serializable {

    private ArrayList<Stage> stages;

    public Board(ArrayList<Stage> stages) {

        this.stages = stages;
    }

    public ArrayList<Stage> getStages() {

        return stages;
    }

    public void addStage(int index, Stage stage) {

        stages.add(index, stage);
    }

    public void removeStage(Stage stage) {

        stages.remove(stage);
    }

    public boolean checkAllStagesAreEmpty() {

        int emptyCount = 0;
        for (Stage stage : stages) {
            if (stage.getTasks().isEmpty()) {
                emptyCount++;
            }
        }
        return emptyCount == stages.size();
    }
}
