package model.lookup;

import model.data.City;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Circuit {

	int origin = 0;
	List<City> cities = new ArrayList<>();

	List<Integer> circuit = new LinkedList<>();
	List<Integer> distances = new ArrayList<>();

	int length = 0;

	public Circuit() {
	}

	public List<City> getCities() {
		return cities;
	}

	public List<Integer> getCircuit() {
		return circuit;
	}

	public void setCities(int origin, List<City> cities) {
		this.origin = origin;
		this.cities = cities;

		circuit.add(origin);

		int count = cities.size();
		for(int i = 0; i < count; i++) {
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
