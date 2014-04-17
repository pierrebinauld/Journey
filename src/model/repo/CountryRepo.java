package model.repo;

import model.data.City;
import model.data.Country;
import persistence.DbDialog;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CountryRepo implements Repo<Country> {

	@Override
	public Country findById(int id) {
		Country country = new Country();
		DbDialog db = new DbDialog();
		CityRepo cityrepo = new CityRepo();
		ResultSet rsCountry = db.executeRequest("select * from country where idcountry=" + id);
		try {
			if(rsCountry.next()) {
				country.setId(id);
				country.setName(rsCountry.getString("namecountry"));
				country.setDescription(rsCountry.getString("description"));
				country.setDimension(rsCountry.getInt("dimension"));
				country.setCities(cityrepo.findByCountryId(rsCountry.getInt("idcountry")));
				return country;
			}
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void save(Country obj) {
		DbDialog db = new DbDialog();
		ResultSet rsExists = db.executeRequest("select count(idcountry) as idexists from country where idcountry=" + obj.getId());
		try {
			String sql = "";
			if(rsExists.next()) {
				if(0 != rsExists.getInt("idexists")) {//update
					sql = "update country set namecountry=" + obj.getName() + ", description=" + obj.getDescription() + ", dimension=" + obj.getDimension() + " where idcountry=" + obj.getId();
				} else {//insert
					ResultSet rsMax = db.executeRequest("select coalesce(max(idcountry), 0) as idcountrymax from country");
					sql = "insert into country(idcountry, namecountry, description, dimension) values(" + (rsMax.getInt("idcountrymax") + 1) + ", " + obj.getName() + ", " + obj.getDescription() + ", " + obj.getDimension() + ")";
				}
			}
			db.executeRequest(sql);

			CityRepo cr = new CityRepo();
			for(City c : obj.getCities()) {
				cr.save(c);
			}
		} catch(SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
