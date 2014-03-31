package model.repo;

import java.awt.geom.Point2D;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import persistance.DbDialog;
import model.data.City;
import model.data.Country;

public class CountryRepo implements Repo<Country>{

	@Override
	public Country find(String name) {
		Country country = new Country();
		DbDialog db = new DbDialog();
		ResultSet rsCountry = db.executeRequest("select * from country");
		try {
			if(rsCountry.next()){
				ResultSet rsCities = db.executeRequest("select * from city where idcountry = '" + rsCountry.getInt("idcountry") + "'");
				country.setName(rsCountry.getString("nomcountry"));
				country.setDescription(rsCountry.getString("description"));
				country.setDimension(rsCountry.getInt("dimension"));
				country.setCities(new ArrayList<City>());
				while(rsCities.next()){
					City city = new City();
					city.setId(rsCities.getInt("idcity"));
					city.setPosition(new Point2D.Double(rsCities.getDouble("latitude"),rsCities.getDouble("longitude")));
					country.getCities().add(city);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public void save(Country obj) {
		// TODO Auto-generated method stub
		
	}

	
}
