package persistance;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnexionBDD {

    private static final String DB_NAME = "journey";
    private static final String USER = "journey";
    private static final String PASSWORD = "journey";

    private static Connection connexion;

    public ConnexionBDD() {
        try {
            connexion = DriverManager.getConnection("jdbc:mysql://localhost/" + DB_NAME + "?user=" + USER + "&password=" + PASSWORD);
        } catch(SQLException e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnexion() {
        if(connexion == null) {
            new ConnexionBDD();
        }
        return connexion;
    }
}
