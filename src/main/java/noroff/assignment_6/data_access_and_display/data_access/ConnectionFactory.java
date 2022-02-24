package noroff.assignment_6.data_access_and_display.data_access;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {


    static public Connection getConnection() {
        try {
            return DriverManager.getConnection("jdbc:sqlite::resource:data.sqlite");
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
