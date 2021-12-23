package exercise1;

import com.sun.rowset.*;

import javax.sql.rowset.CachedRowSet;
import javax.sql.rowset.RowSetProvider;
import java.sql.*;

public class PlayerGameModel {
    private static Connection connection = null;
    private static Statement statement = null;

    public static void dbConnect() throws SQLException{
        //Connect to oracle database
        String dbURL = "jdbc:oracle:thin:@199.212.26.208:1521:SQLD";
        String username = "COMP122W21_008_P_18";
        String password = "password";
        try {
            connection = DriverManager.getConnection(dbURL, username, password);
            System.out.println("Database is connected!");
            statement = connection.createStatement();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            System.out.println("Database is not connected!");
        }
    }

    //Close Connection
    public static void dbDisconnect() throws SQLException {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (Exception e){
            throw e;
        }
    }

    // dropping a table
    public static void dropTable(String tableName) throws SQLException {
        //open db connection
        dbConnect();
        String sql = "DROP TABLE " + tableName;
        try {
            statement.execute(sql);
            System.out.println("Table " + tableName + " is dropped!");

        } catch (SQLException e) {
            System.out.println("Table is not dropped!");
            System.out.println(e.getMessage());
        }
        if (statement != null) {
            //Close Statement
            statement.close();
        }
        //Close connection
        dbDisconnect();
    }

    // creating a table
    public static void createTable(String tableName) throws SQLException {
        //open db connection
        dbConnect();

        String sql = "CREATE TABLE " + tableName + " (s_id INTEGER PRIMARY KEY, s_name VARCHAR2(100))";
        try {
            statement.execute(sql);
            System.out.println("Table " + tableName + " is created!");

        } catch (SQLException e) {
            System.out.println("Table is not created!");
            System.out.println(e.getMessage());
        }
        if (statement != null) {
            //Close Statement
            statement.close();
        }
        //Close connection
        dbDisconnect();
    }


    // insert data into player table
    public static void insertPlayer(Integer PLAYER_ID,
                                    String FIRST_NAME,
                                    String LAST_NAME,
                                    String ADDRESS,
                                    String POSTAL_CODE,
                                    String PROVINCE,
                                    Long PHONE_NUMBER) throws SQLException {
        //open db connection
        dbConnect();

        try {
            String sql = "INSERT INTO PLAYER VALUES(" +
                    PLAYER_ID +
                    ",'" + FIRST_NAME +
                    "','" + LAST_NAME +
                    "','" + ADDRESS +
                    "','" + POSTAL_CODE +
                    "','" + PROVINCE +
                    "'," + PHONE_NUMBER + ")";
            statement.executeUpdate(sql);
            System.out.println(sql + " is entered!");

        } catch (SQLException e) {
            System.out.println("Data is not entered!");
            System.out.println(e.getMessage());
        }
        if (statement != null) {
            //Close Statement
            statement.close();
        }
        //Close connection
        dbDisconnect();
    }
    // update data in player table
    public static void updatePlayer(Integer PLAYER_ID,
                                    String FIRST_NAME,
                                    String LAST_NAME,
                                    String ADDRESS,
                                    String POSTAL_CODE,
                                    String PROVINCE,
                                    Long PHONE_NUMBER) throws SQLException {
        //open db connection
        dbConnect();

        try {
            String sql = String.format("UPDATE PLAYER " +
                            "SET FIRST_NAME='%s', LAST_NAME='%s', ADDRESS='%s', POSTAL_CODE='%s', PROVINCE='%s', PHONE_NUMBER=%d" +
                            " WHERE PLAYER_ID=%d",
                    FIRST_NAME,
                    LAST_NAME,
                    ADDRESS,
                    POSTAL_CODE,
                    PROVINCE,
                    PHONE_NUMBER,
                    PLAYER_ID);
            statement.executeUpdate(sql);
            System.out.println(sql + " is entered!");

        } catch (SQLException e) {
            System.out.println("Data is not updated!");
            System.out.println(e.getMessage());
        }
        if (statement != null) {
            //Close Statement
            statement.close();
        }
        //Close connection
        dbDisconnect();
    }
    // insert data into game table
    public static void insertGame(Integer GAME_ID, String GAME_TITLE) throws SQLException {
        //open db connection
        dbConnect();

        try {
            String sql = "INSERT INTO GAME VALUES(" + GAME_ID + ",'" + GAME_TITLE + "')";
            statement.executeUpdate(sql);
            System.out.println(sql + " is entered!");

        } catch (SQLException e) {
            System.out.println("Data is not entered!");
            System.out.println(e.getMessage());
        }
        if (statement != null) {
            //Close Statement
            statement.close();
        }
        //Close connection
        dbDisconnect();
    }
    // insert data into player and game table
    public static void insertPlayerGame(Integer PLAYER_GAME_ID,
                                        Integer GAME_ID,
                                        Integer PLAYER_ID,
                                        String PLAYING_DATE,
                                        Integer SCORE) throws SQLException {
        //open db connection
        dbConnect();

        try {
            String sql = "INSERT INTO PLAYER_AND_GAME VALUES(" +
                    PLAYER_GAME_ID +
                    "," + GAME_ID +
                    "," + PLAYER_ID +
                    ",'" + PLAYING_DATE +
                    "'," + SCORE + ")";
            statement.executeUpdate(sql);
            System.out.println(sql + " is entered!");

        } catch (SQLException e) {
            System.out.println("Data is not entered!");
            System.out.println(e.getMessage());
        }
        if (statement != null) {
            //Close Statement
            statement.close();
        }
        //Close connection
        dbDisconnect();
    }

    public static ResultSet query(String sql) throws SQLException {
        CachedRowSet crs = RowSetProvider.newFactory().createCachedRowSet();

//        CachedRowSetImpl crs = new CachedRowSetImpl();
        //open db connection
        dbConnect();

        ResultSet resultSet = null;
        try {
            resultSet = statement.executeQuery(sql);
            crs.populate(resultSet);

/*
            while (resultSet.next()) {
                String s_id = resultSet.getString("s_id");
                String s_name = resultSet.getString("s_name");
                System.out.println(s_id + " , " + s_name);
            }
*/

        } catch (SQLException e) {
            System.out.println("Query did not run!");
            System.out.println(e.getMessage());
        }

        if (statement != null) {
            //Close Statement
            statement.close();
        }
        //Close connection
        dbDisconnect();
        return crs;
    }

    public static void delete(String tableName, Integer s_id) throws SQLException {
        //open db connection
        dbConnect();
        // delete the first row from the table
        String sql = "DELETE FROM " + tableName +" WHERE s_id =" + s_id;
        try {
            statement.executeUpdate(sql);
            System.out.println("Data is deleted!");

        } catch (SQLException e) {
            System.out.println("Data cannot be deleted!");
            System.out.println(e.getMessage());
        }
        if (statement != null) {
            //Close Statement
            statement.close();
        }
        //Close connection
        dbDisconnect();
    }
}