package persistence;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DbDialog {
	public ResultSet executeRequest(String requete) {
		Statement statement;
		ResultSet rs = null;
		try {
			statement = DbConnection.getConnection().createStatement();
			rs = statement.executeQuery(requete);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rs;
	}
}
