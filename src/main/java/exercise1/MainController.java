package exercise1;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;


public class MainController {
    public TextField playerID;
    public TextField firstName;
    public TextField lastName;
    public TextField address;
    public TextField postalCode;
    public TextField province;
    public TextField phoneNumber;

    public TextField playerGameID;
    public TextField gameID;
    public TextField gameTitle;
    public DatePicker playingDate;
    public TextField score;

    public Button addPlayer;
    public Button updatePlayer;
    public Button viewListOfGames;
    public Button searchPlayer;
    public Button addGame;
    public Button addGameToPlayer;

    public void ViewListOfGames(MouseEvent mouseEvent) throws IOException, SQLException {
        Stage stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(PlayerGameApplication.class.getResource("list-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 720, 480);
        ResultSet resultSet = PlayerGameModel.query("select first_name, last_name\n" +
                "from player\n" +
                "where player_id=" + playerID.getText());
        resultSet.next();
        stage.setTitle(resultSet.getString("FIRST_NAME") + " " +
                resultSet.getString("LAST_NAME") + " List of Games");

        ArrayList<Game> arrL = new ArrayList<>();
        resultSet = PlayerGameModel.query("select game_title, playing_date, score\n" +
                "from game\n" +
                "inner join player_and_game\n" +
                "on (game.game_id=player_and_game.game_id and player_and_game.player_id=" +
                playerID.getText() + ")\n" +
                "order by game_title");
        while (resultSet.next()) {
            Game game = new Game();
            game.setGameTitle(resultSet.getString("game_title"));
            game.setPlayingDate(resultSet.getString("playing_date").substring(0, 10));
            game.setScore(resultSet.getString("score"));
            arrL.add(game);
        }

        // fxmlLoader.load() has some side effect because putting
        // the next two statements before that (where fxmlLoader
        // is still defined) throws exceptions
        ListController listController = fxmlLoader.getController();
        listController.populateTable(arrL);

        stage.setScene(scene);
        stage.show();
    }

    public void AddPlayer(MouseEvent mouseEvent) throws SQLException {
        PlayerGameModel.insertPlayer(
                Integer.parseInt(playerID.getText()),
                firstName.getText(),
                lastName.getText(),
                address.getText(),
                postalCode.getText(),
                province.getText(),
                Long.parseLong(phoneNumber.getText()));
    }

    public void UpdatePlayer(MouseEvent mouseEvent) throws SQLException {
        PlayerGameModel.updatePlayer(
                Integer.parseInt(playerID.getText()),
                firstName.getText(),
                lastName.getText(),
                address.getText(),
                postalCode.getText(),
                province.getText(),
                Long.parseLong(phoneNumber.getText()));
    }

    public void SearchPlayer(MouseEvent mouseEvent) throws SQLException {
        ResultSet resultSet = PlayerGameModel.query("select * from player where player_id=" + playerID.getText());
        resultSet.next();
        firstName.setText(resultSet.getString("FIRST_NAME"));
        lastName.setText(resultSet.getString("LAST_NAME"));
        address.setText(resultSet.getString("ADDRESS"));
        postalCode.setText(resultSet.getString("POSTAL_CODE"));
        province.setText(resultSet.getString("PROVINCE"));
        phoneNumber.setText(resultSet.getString("PHONE_NUMBER"));
    }

    public void AddGame(MouseEvent mouseEvent) throws SQLException {
        PlayerGameModel.insertGame(
                Integer.parseInt(gameID.getText()),
                gameTitle.getText());
    }

    public void AddGameToPlayer(MouseEvent mouseEvent) throws SQLException {
        PlayerGameModel.insertPlayerGame(
                Integer.parseInt(playerGameID.getText()),
                Integer.parseInt(gameID.getText()),
                Integer.parseInt(playerID.getText()),
                dateConverter(playingDate.getValue()),
                Integer.parseInt(score.getText()));
    }

    private static String dateConverter (LocalDate date) {
        //Input Format: 2021-12-01
        //Output Format: 01-DEC-21
        String[] Months = new String[]{"JAN", "FEB", "MAR", "APR", "MAY", "JUN",
                "JUL", "AUG", "SEP", "OCT", "NOV", "DEC"};
        String[] arr = date.toString().split("-",3);
        return arr[2] + "-" +  Months[Integer.parseInt(arr[1]) - 1] + "-" + arr[0].substring(2, 4);
    }
}