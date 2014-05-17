package model.data;

import java.util.ArrayList;
import java.util.List;

public class Country {

	private int id;
	private String name;
	private String description;
	private List<City> cities = new ArrayList<City>();

	public int getId() {
		return id;
	}

	public Country setId(int id) {
		this.id = id;
		return this;
	}

	public String getName() {
		return name;
	}

	public Country setName(String name) {
		this.name = name;
		return this;
	}

	public String getDescription() {
		return description;
	}

	public Country setDescription(String description) {
		this.description = description;
		return this;
	}

	public Country addDescriptionLine(String descriptionLine) {
		if(this.description == null) {
			this.description = descriptionLine;
		} else {
			this.description += "\n" + descriptionLine;
		}
		return this;
	}

	public Country addCity(City city) {
		cities.add(city);
		return this;
	}

	public List<City> getCities() {
		return cities;
	}

	public Country setCities(List<City> cities) {
		this.cities = cities;
		return this;
	}

	public int getDimension() {
		return cities.size();
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("COUNTRY\n")
				.append("Size: ").append(getCities().size()).append('\n')
				.append("Name: ").append(getName()).append('\n')
				.append("Desc: ").append(getDescription()).append('\n')
				.append("Dim: ").append(getDimension()).append('\n');
		for(City city : getCities()) {
			builder.append(city);
		}
		return builder.toString();
	}
}
