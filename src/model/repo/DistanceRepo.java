package model.repo;

import model.data.Distance;
import persistence.DbConnection;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

public class DistanceRepo {

	private DbConnection db;

	public DistanceRepo(DbConnection db) {
		this.db = db;
	}

	public HashMap<Integer, Integer> findByCityId(int cityId) {
		HashMap<Integer, Integer> neighborhood = new HashMap<>();
		ResultSet rsDistances = db.executeRequest("select idcity2 as idcity, distance from distance where idcity1=" + cityId + " union select idcity1 as idcity, distance from distance where idcity2=" + cityId);
		try {
			while(rsDistances.next()) {
				neighborhood.put(rsDistances.getInt("idcity"), rsDistances.getInt("distance"));
			}
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return neighborhood;
	}

	public void save(Distance distance) {
		db.executeUpdate("insert into distance(idcountry, idcity1, idcity2, distance) values(" + distance.getIdCountry() + ", " + distance.getIdCity1() + ", " + distance.getIdCity2() + ", " + distance.getDistance() + ")");
	}
}
