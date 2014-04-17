package model.data;

import java.util.ArrayList;
import java.util.List;

public class Country {

	private int id;
	private String name;
	private String description;
	private int dimension;
	private List<City> cities = new ArrayList<City>();

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getDimension() {
		return dimension;
	}

	public void setDimension(int dimension) {
		this.dimension = dimension;
	}

	public void addCity(City city) {
		cities.add(city);
	}

	public List<City> getCities() {
		return cities;
	}

	public void setCities(List<City> cities) {
		this.cities = cities;
	}


	public String toString() {
		String citiesInCountry = "";
		for(City c : cities) {
			citiesInCountry += c.toString();
		}
		return "Pays : " + name + "\ndescription : " + description + "\ndimension : " + dimension + "\nvilles : " + citiesInCountry;
	}
}
