package model.lookup;

import model.data.City;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Circuit {

	List<City> cities = new ArrayList<>();

	LinkedList<Integer> circuit = new LinkedList<>();
	List<Integer> distances = new ArrayList<>();

	int length = 0;

	public Circuit() {
	}

	public List<City> getCities() {
		return cities;
	}

	public LinkedList<Integer> getCircuit() {
		return circuit;
	}

	public void setCities(int origin, List<City> cities) {
		this.cities = cities;

		circuit.add(origin);

		int count = cities.size();
		for(int i = 0; i < count; i++) {
			distances.add(0);
		}
	}

	public void setCities(List<City> cities) {
		this.cities = cities;

		int count = cities.size();
		for(int i = 0; i < count; i++) {
			distances.add(0);
		}
	}

	public void add(int cityIndex, int distanceBefore) {
		circuit.add(cityIndex);
		distances.set(cityIndex, distanceBefore);
	}

	public void add(int index, int cityIndex, int distanceBefore, int distanceAfter) {
		circuit.add(index, cityIndex);
		distances.set(cityIndex, distanceBefore);
		if(circuit.size() > index+1) {
			distances.set(circuit.get(index+1), distanceAfter);
		}
	}

	public void setDistance(int cityIndex, int distance) {
		distances.set(cityIndex, distance);
	}

	public void close(int distance) {

		distances.set(circuit.get(0), distance);

		length = 0;
		for(int d : distances) {
			length += d;
		}
	}

	public int size() {
		return circuit.size();
	}
	
	public int getLength() {
		return length;
	}

	@Override
	public String toString() {
		return circuit.toString();
	}

	public int getOrigin() {
		return circuit.get(0);
	}

	public List<Integer> getDistances() {
		return distances;
	}

}
