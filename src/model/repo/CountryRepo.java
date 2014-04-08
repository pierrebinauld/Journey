package model.repo;

import java.sql.ResultSet;
import java.sql.SQLException;

import persistance.DbDialog;
import model.data.Country;
import model.data.City;

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
		DbDialog db = new DbDialog();
		ResultSet rsExist = db.executeRequest("select * from country where idcountry ="+obj.getId());
		try {
			String sql="";
			if(rsExist.next()){//update
				sql = "Update country set nomcountry="+obj.getName()+", description ="+obj.getDescription()+", dimension ="+obj.getDimension()+" where idcountry ="+obj.getId();
			}
			else{//insert
				ResultSet rsMax = db.executeRequest("select max(idcountry) from country");
				sql = "insert into country(idcountry, nomcountry,description,dimension)values("+(rsMax.getInt("max(idcountry)")+1)+", "+obj.getName()+", "+obj.getDescription()+", "+obj.getDimension()+")";
			}
			db.executeRequest(sql);
			for(City c: obj.getCities()) {
				CityRepo cr = new CityRepo();
				cr.save(c);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	
}
