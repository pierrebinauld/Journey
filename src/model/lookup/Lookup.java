package model.lookup;


import java.util.List;

import model.data.City;

public interface Lookup {
	public Circuit run(List<City> cities);
}
