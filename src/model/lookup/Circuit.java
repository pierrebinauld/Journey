package model.lookup;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import model.data.City;

public class Circuit {

	int origin = 0;
	List<City> cities = new ArrayList<>();
	
	LinkedList<Integer> circuit = new LinkedList<>();
	List<Integer> distances = new ArrayList<>();
	
	int length = 0;
	
	public Circuit() {
	}

	public List<City> getCities() {
		return cities;
	}

	public void setCities(int origin, List<City> cities) {
		this.cities = cities;
		
		circuit.add(origin);
		
		int count = cities.size();
		for(int i = 0; i<count; i++) {
			distances.add(0);
		}
	}
	
	public void add(int city, int distance) {
		circuit.add(city);
		distances.set(city, distance);
	}
	
	public void close(int distance) {

		distances.set(origin, distance);
		
		length = 0;
		for(int d : distances) {
			length += d;
		}
	}

	public int getLength() {
		return length;
	}
	
	@Override
	public String toString() {
		return circuit.toString();
	}
}