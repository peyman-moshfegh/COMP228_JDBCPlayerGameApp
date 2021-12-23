package exercise1;

public class Game {
    public String gameTitle;
    public String playingDate;
    public String score;

    public Game() {
    }

    public Game(String gameTitle, String playingDate, String score) {
        this.gameTitle = gameTitle;
        this.playingDate = playingDate;
        this.score = score;
    }

    // getter methods are used by tableView and/or its columns in ListController!!
    public String getGameTitle() {
        return gameTitle;
    }

    public String getPlayingDate() {
        return playingDate;
    }

    public String getScore() {
        return score;
    }

    public void setGameTitle(String gameTitle) {
        this.gameTitle = gameTitle;
    }

    public void setPlayingDate(String playingDate) {
        this.playingDate = playingDate;
    }

    public void setScore(String score) {
        this.score = score;
    }
}