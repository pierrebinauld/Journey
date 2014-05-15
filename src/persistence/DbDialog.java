package persistence;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.concurrent.LinkedBlockingQueue;

public class DbDialog {

	private static Thread dbThread;
	private static LinkedBlockingQueue<String> queue = new LinkedBlockingQueue<>();

	public static ResultSet executeRequest(String sql) {
		try {
			Statement statement = DbConnection.getConnection().createStatement();
			return statement.executeQuery(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static void executeUpdate(final String sql) {
		queue.add(sql);
		try {
			Statement statement = DbConnection.getConnection().createStatement();
			statement.executeUpdate(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void beginTransaction() {

	}
}
