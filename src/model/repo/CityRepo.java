package model.repo;

import model.data.City;
import persistence.DbDialog;

import java.awt.geom.Point2D;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CityRepo {

	public City findById(int id) {
		City c = new City();
		DbDialog db = new DbDialog();
		ResultSet rsCity = db.executeRequest("select * from city where idcity=" + id);
		try {
			if(rsCity.next()) {
				c.setId(id);
				c.setPosition(new Point2D.Double(rsCity.getDouble("x"), rsCity.getDouble("y")));
				return c;
			}
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public List<City> findByCountryId(int idcountry) {
		DbDialog db = new DbDialog();
		List<City> list = new ArrayList<City>();
		CityRepo cityrepo = new CityRepo();
		ResultSet rsCities = db.executeRequest("select idcity from city where idcountry=" + idcountry);
		try {
			while(rsCities.next()) {
				City city = cityrepo.findById(rsCities.getInt("idcity"));
				list.add(city);
			}
		} catch(SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	public void save(City city, int idCountry) {
		DbDialog db = new DbDialog();
		ResultSet rsExist = db.executeRequest("select count(*) as idexists from city where idcity=" + city.getId() + " and idcountry=" + idCountry);
		try {
			String sql = "";
			if(rsExist.next()) {
				if(0 != rsExist.getInt("idexists")) {//update
					sql = "update city set x=" + city.getPosition().getX() + ", y=" + city.getPosition().getY() + " where idcity=" + city.getId() + " and idcountry=" + idCountry;
				} else {//insert
					sql = "insert into city(idcity, idcountry, x, y) values(" + city.getId() + ", " + idCountry + ", " + city.getPosition().getX() + ", " + city.getPosition().getY() + ")";
				}
			}
			db.executeUpdate(sql);
		} catch(SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
