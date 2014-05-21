package model.service.distance;

import java.util.HashSet;
import java.util.List;

import model.data.City;
import model.lookup.Circuit;
import model.service.DistanceService;

public abstract class AbstractDistanceService implements DistanceService {

	protected List<City> cities;

	public AbstractDistanceService(List<City> cities) {
		this.cities = cities;
	}

	public List<City> getCities() {
		return cities;
	}
	
	@Override
	public boolean checkLength(Circuit circuit) {
		
		int length = 0;
		List<Integer> c = circuit.getCircuit();
		int size = c.size();
		
		// Verify number of cities in the circuit.
		if(size != cities.size()) {
			return false;
		}
		
		// Verify city redundance.
		if(size != new HashSet<Integer>(c).size()) {
			return false;
		}
		
		// Verify circuit length
		for(int i = 1; i<size; i++) {
			length += this.getDistance(c.get(i-1), c.get(i));
		}

		length += this.getDistance(c.get(0), c.get(size - 1));
		
		return circuit.getLength() == length;
	}
}
