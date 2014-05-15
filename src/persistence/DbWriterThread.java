package persistence;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.concurrent.LinkedBlockingQueue;

public class DbWriterThread extends Thread {

	private LinkedBlockingQueue<String> queue;

	public DbWriterThread(LinkedBlockingQueue<String> queue) {
		this.queue = queue;
	}

	@Override
	public void run() {
		while(true) {//TODO: gérer la fin du thread
			String sql = null;
			try {
				sql = queue.take();
				Statement statement = DbConnection.getConnection().createStatement();
				statement.executeUpdate(sql);
			} catch(InterruptedException e) {
				e.printStackTrace();
			} catch(SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
