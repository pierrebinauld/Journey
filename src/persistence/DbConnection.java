package persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConnection {

    private static final String DB_NAME = "journey";
    private static final String USER = "journey";
    private static final String PASSWORD = "journey";

    private static Connection connection;

    public DbConnection() {
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost/" + DB_NAME, USER, PASSWORD);
        } catch(SQLException e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection() {
        if(connection == null) {
            new DbConnection();
        }
        return connection;
    }
}
