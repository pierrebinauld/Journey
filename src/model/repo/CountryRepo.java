package model.repo;

import java.sql.ResultSet;
import java.sql.SQLException;

import persistance.DbDialog;
import model.data.Country;

public class CountryRepo implements Repo<Country>{

	@Override
	public Country find(String name) {
		Country country = new Country();
		DbDialog db = new DbDialog();
		CityRepo cityrepo = new CityRepo();
		ResultSet rsCountry = db.executeRequest("select * from country");
		try {
			if(rsCountry.next()){
				country.setName(rsCountry.getString("nomcountry"));
				country.setDescription(rsCountry.getString("description"));
				country.setDimension(rsCountry.getInt("dimension"));
				country.setCities(cityrepo.findByCountry(rsCountry.getInt("idcountry")));
				return country;
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
