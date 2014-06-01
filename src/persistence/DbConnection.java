package persistence;


import java.sql.*;
import java.util.concurrent.LinkedBlockingQueue;

public class DbConnection {

	private static final String HOST     = "localhost";    /* <-- comment this line to switch host
	private static final String HOST     = "94.23.195.14"; // */
	private static final String DB_NAME  = "journey";
	private static final String USER     = "journey";
	private static final String PASSWORD = "journey";

	private Connection connection;
	private LinkedBlockingQueue<String> queue;
	private DbWriterThread dbWriterThread;

	public DbConnection() {
		try {
			connection = DriverManager.getConnection("jdbc:mysql://" + HOST + "/" + DB_NAME, USER, PASSWORD);
			connection.setAutoCommit(false);
			queue = new LinkedBlockingQueue<>();
			dbWriterThread = new DbWriterThread(connection, queue);
			dbWriterThread.start();
		} catch(SQLException e) {
			e.printStackTrace();
		}
	}

	public ResultSet executeRequest(String sql) {
		try {
			Statement statement = connection.createStatement();
			ResultSet rs = statement.executeQuery(sql);
			return rs;
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public void executeUpdate(final String sql) {
		queue.add(sql);
	}

	public void commit() {
		dbWriterThread.interrupt();
	}

	public void close() {
		try {
			dbWriterThread.join();
			connection.close();
		} catch(InterruptedException e) {
			e.printStackTrace();
		} catch(SQLException e) {
			System.err.println("Could not close database connection.");
//			System.exit(ExitStatus.DB_ERROR.code()); //TODO: Est-ce qu'on quitte ?
		}
	}
}
