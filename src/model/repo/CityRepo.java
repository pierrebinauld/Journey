package model.repo;

import java.awt.geom.Point2D;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import persistance.DbDialog;
import model.data.City;

public class CityRepo implements Repo<City>{

	@Override
	public City find(String id) {
		City c = new City();
		DbDialog db = new DbDialog();
		ResultSet rsCity = db.executeRequest("select * from city where idcity='"+id+"'");
		try {
			if(rsCity.next()){
				c.setId(rsCity.getInt("idcity"));
				c.setPosition(new Point2D.Double(rsCity.getDouble("latitude"),rsCity.getDouble("longitude")));
				return c;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public List<City> findByCountry(int idcountry){
		DbDialog db = new DbDialog();
		List<City> list = new ArrayList<City>();
		CityRepo cityrepo = new CityRepo();
		ResultSet rsCities = db.executeRequest("select idcity from city where idcountry = '" + idcountry + "'");
		try {
			while(rsCities.next()){
				City city = cityrepo.find(rsCities.getString("idcity"));
				list.add(city);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public void save(City obj) {
		// TODO Auto-generated method stub
		
	}

}
