package exercise1;

import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.ArrayList;

public class ListController {
    public TableView<Game> table;
    public TableColumn<Game, String> gameTitle;
    public TableColumn<Game, String> playingDate;
    public TableColumn<Game, String> score;

    public void populateTable(ArrayList<Game> arrL) {
        gameTitle.setCellValueFactory(new PropertyValueFactory<>("gameTitle"));
        playingDate.setCellValueFactory(new PropertyValueFactory<>("playingDate"));
        score.setCellValueFactory(new PropertyValueFactory<>("score"));

        for (Game g: arrL) {
            table.getItems().add(g);
        }
    }
}