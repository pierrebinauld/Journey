package model.repo;

import model.data.City;
import model.data.Country;
import persistence.DbConnection;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CountryRepo {

	private DbConnection db;

	public CountryRepo(DbConnection db) {
		this.db = db;
	}

	public Country findById(int id) {
		Country country = new Country();
		CityRepo cityrepo = new CityRepo(db);
		ResultSet rsCountry = db.executeRequest("select * from country where idcountry=" + id);
		try {
			if(rsCountry.next()) {
				country.setId(id);
				country.setName(rsCountry.getString("namecountry"));
				country.setDescription(rsCountry.getString("description"));
				country.setCities(cityrepo.findByCountryId(rsCountry.getInt("idcountry")));
				rsCountry.close();
				return country;
			}
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public Country findByName(String name) {
		Country country = new Country();
		CityRepo cityrepo = new CityRepo(db);
		ResultSet rsCountry = db.executeRequest("select * from country where namecountry='" + name + "'");
		try {
			if(rsCountry.next()) {
				country.setId(rsCountry.getInt("idcountry"));
				country.setName(rsCountry.getString("namecountry"));
				country.setDescription(rsCountry.getString("description"));
				country.setCities(cityrepo.findByCountryId(rsCountry.getInt("idcountry")));
				rsCountry.close();
				return country;
			}
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public void save(Country country) {
		ResultSet rsExists = db.executeRequest("select count(idcountry) as idexists from country where idcountry=" + country.getId());
		try {
			String sql = "";
			if(rsExists.next()) {
				boolean idExists = (rsExists.getInt("idexists") != 0);
				rsExists.close();
				if(idExists) {//update
					sql = "update country set namecountry='" + country.getName() + "', description='" + country.getDescription() + "', where idcountry=" + country.getId();
				} else {//insert
					ResultSet rsMax = db.executeRequest("select coalesce(max(idcountry), 0) as idcountrymax from country");
					rsMax.next();
					country.setId(rsMax.getInt("idcountrymax") + 1);
					rsMax.close();
					sql = "insert into country(idcountry, namecountry, description) values(" + country.getId() + ", '" + country.getName() + "', '" + country.getDescription() + "')";
				}
			}
			db.executeUpdate(sql);

			CityRepo cr = new CityRepo(db);
			for(City c : country.getCities()) {
				cr.save(c, country.getId());
			}
			db.commit();
		} catch(SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
