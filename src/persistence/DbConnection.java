package persistence;

import java.sql.*;
import java.util.concurrent.LinkedBlockingQueue;

public class DbConnection {

    private static final String DB_NAME = "journey";
    private static final String USER = "journey";
    private static final String PASSWORD = "journey";

	private static DbConnection instance;

    private Connection connection;
	private LinkedBlockingQueue<String> queue;
	private Thread dbWriterThread;

    public DbConnection() {
        try {
//            connection = DriverManager.getConnection("jdbc:mysql://94.23.195.14/" + DB_NAME, USER, PASSWORD);
	        connection = DriverManager.getConnection("jdbc:mysql://localhost/" + DB_NAME, USER, PASSWORD);
	        connection.setAutoCommit(false);
	        queue  = new LinkedBlockingQueue<>();
	        dbWriterThread = new DbWriterThread(connection, queue);
	        dbWriterThread.run();
        } catch(SQLException e) {
            e.printStackTrace();
        }
    }

    public static DbConnection getConnection() {
        if(instance == null) {
            instance = new DbConnection();
        }
        return instance;
    }

	public ResultSet executeRequest(String sql) {
		try {
			Statement statement = connection.createStatement();
			return statement.executeQuery(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public void executeUpdate(final String sql) {
		queue.add(sql);
	}
}
