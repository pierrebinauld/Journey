package model.lookup.impl;

import java.util.LinkedList;
import java.util.List;

import model.data.City;
import model.lookup.AbstractBuilderAlgorithm;
import model.lookup.Circuit;
import model.service.DistanceService;
import model.tools.Tools;

public class GreedyAlgorithm extends AbstractBuilderAlgorithm {

	private int origin= -1;
	private List<Integer> indexes = new LinkedList<>();
	private Circuit circuit = new Circuit();

	public GreedyAlgorithm(DistanceService distanceService, List<City> cities) {
		super(distanceService, cities);
	}
	
	public void setOrigin(int index) {
		origin = index;
	}

	@Override
	public Circuit run() {
		
		int root = init();

		int nearest;
		int distance;
		int test;
		int count;
		int indexNearest;

		while (indexes.size() > 0) {
			count = indexes.size();
			distance = 0;
			nearest = 0;
			indexNearest = 0;
			
			for (int n = 0; n < count; n++) {
				test = distanceService.getDistance(root, indexes.get(n));
				if (test < distance || distance == 0) {
					distance = test;
					nearest = indexes.get(n);
					indexNearest = n;
				}
			}
	
			circuit.add(nearest, distance);
			indexes.remove(indexNearest);
			root = nearest;
		}
		
		closeCircuit(root);

		return circuit;
	}

	private int init() {
		int count = cities.size();
		int root = -1;
		System.out.println(origin);
		if(-1 == origin) {
			root = Tools.random(0, count);
		} else {
			root = origin;
		}
		indexes = buildCitiesIndex(count);
		circuit = new Circuit();
		circuit.setCities(root, cities);
		indexes.remove(root);
		
		return root;
	}

	private void closeCircuit(int root) {
		circuit.close(distanceService.getDistance(circuit.getOrigin(), root));
	}

	private List<Integer> buildCitiesIndex(int size) {
		List<Integer> cities = new LinkedList<>();
		for (int i = 0; i < size; i++) {
			cities.add(i);
		}
		return cities;
	}
	
}
