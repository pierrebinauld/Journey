package persistence;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DbDialog {
	public ResultSet executeRequest(String sql) {
		try {
			Statement statement = DbConnection.getConnection().createStatement();
			return statement.executeQuery(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public int executeUpdate(String sql) {
		try {
			Statement statement = DbConnection.getConnection().createStatement();
			return statement.executeUpdate(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}
}
