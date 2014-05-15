package model.repo;

import model.data.Distance;
import persistence.DbDialog;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;

public class DistanceRepo {

	public HashMap<Integer, Integer> findByCityId(int cityId) {
		HashMap<Integer, Integer> neighborhood = new HashMap<>();
		ResultSet rsDistances = DbDialog.executeRequest("select idcity2 as idcity, distance from distance where idcity1=" + cityId + " union select idcity1 as idcity, distance from distance where idcity2=" + cityId);
		try {
			while(rsDistances.next()) {
				neighborhood.put(rsDistances.getInt("idcity"), rsDistances.getInt("distance"));
			}
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return neighborhood;
	}

	public class DistanceSaver extends Thread {

		private AtomicBoolean ended;
		private LinkedBlockingQueue<Distance> queue;

		private DistanceSaver(LinkedBlockingQueue<Distance> queue) {
			ended = new AtomicBoolean(false);
			this.queue = queue;
		}

		public void setEnded() {
			this.ended.set(true);
		}

		@Override
		public void run() {
			DbDialog db = new DbDialog();
			Distance d;
			String sql;
			try {
				while(!ended.get()) {
					d = queue.take();
					sql = "insert into distance(idcountry, idcity1, idcity2, distance) values(" + d.getIdCountry() + ", " + d.getIdCity1() + ", " + d.getIdCity2() + ", " + d.getDistance() + ")";
					db.executeUpdate(sql);
//	      		Thread.sleep(200);
				}
			} catch(InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
