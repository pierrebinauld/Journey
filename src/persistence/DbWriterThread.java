package persistence;

import tools.ExitStatus;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.concurrent.LinkedBlockingQueue;

public class DbWriterThread extends Thread {

	private Connection connection;
	private LinkedBlockingQueue<String> queue;

	public DbWriterThread(Connection connection, LinkedBlockingQueue<String> queue) {
		this.connection = connection;
		this.queue = queue;
	}

	@Override
	public void run() {
		boolean goOn = true;
		boolean forceClose = false;

		try {
			while(goOn || !queue.isEmpty()) {
				String sql = null;
				try {
					sql = queue.take();
					Statement statement = connection.createStatement();
					statement.executeUpdate(sql);
					statement.close();
				} catch(InterruptedException e) {
					goOn = false;
				}
			}

			try {
				connection.commit();
			} catch(SQLException e) {
				forceClose = true;
				System.err.println("Could not commit. Closing.");
			}
		} catch(SQLException e2) {
			try {
				connection.rollback();
			} catch(SQLException e1) {
				forceClose = true;
				System.err.println("Could not rollback. Closing.");
			}
		} finally {
			if(forceClose) {
				try {
					connection.close();
				} catch(SQLException e3) {
					System.err.println("Could not close database connection.");
				}

				System.exit(ExitStatus.DB_ERROR.code());
			}
		}
	}
}
