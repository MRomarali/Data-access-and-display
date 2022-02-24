package noroff.assignment_6.data_access_and_display.data_access;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {
    // Pull a file from the resource folder: Northwind_small.sqlite
    static final String URL = "jdbc:sqlite:src/main/resources/data.sqlite";

    static public Connection getConnection() {
        Connection connection = null;
        try {
            return connection = DriverManager.getConnection(URL);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            return connection = DriverManager.getConnection("jdbc:sqlite::resource:data.sqlite");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }
}
