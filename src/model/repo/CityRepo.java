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
				c.setPosition(new Point2D.Double(rsCity.getDouble("y"),rsCity.getDouble("x")));
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
		DbDialog db = new DbDialog();
		ResultSet rsExist = db.executeRequest("select * from city where idcity = "+obj.getId());
		try {
			String sql="";
			if(rsExist.next()){//update
				sql = "Update city set x ="+obj.getPosition().x+", y ="+obj.getPosition().y+" where idcity = "+obj.getId();
			}
			else{//insert
				ResultSet rsMax = db.executeRequest("select max(idcity) from city");
				sql = "insert into city(idcity,x,y,idcountry)values("+(rsMax.getInt("max(idcity)")+1)+", "+obj.getPosition().x+", "+obj.getPosition().y+", "+obj.getIdCountry()+")";
			}
			db.executeRequest(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
