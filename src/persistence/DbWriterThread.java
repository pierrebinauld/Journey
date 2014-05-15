package persistence;

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
		while(true) {//TODO: gérer la fin du thread
			String sql = null;
			try {
				sql = queue.take();
				Statement statement = connection.createStatement();
				statement.executeUpdate(sql);
			} catch(InterruptedException e) {
				e.printStackTrace();
			} catch(SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
