package model.lookup.impl;

import java.util.LinkedList;
import java.util.List;

import model.data.City;
import model.lookup.AbstractBuilderAlgorithm;
import model.lookup.Circuit;
import model.service.DistanceService;
import model.tools.Tools;

public class GreedyAlgorithm extends AbstractBuilderAlgorithm {

	private int origin;
	private List<Integer> indexes = new LinkedList<>();
	private Circuit circuit = new Circuit();

	public GreedyAlgorithm(DistanceService distanceService, List<City> cities) {
		super(distanceService, cities);
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
			System.out.println(count);
			
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
		
		origin = Tools.random(0, count);
		indexes = buildCitiesIndex(count);
		circuit.setCities(origin, cities);
		indexes.remove(origin);
		
		return origin;
	}

	private void closeCircuit(int root) {
		circuit.close(distanceService.getDistance(origin, root));
	}

	private List<Integer> buildCitiesIndex(int size) {
		List<Integer> cities = new LinkedList<>();
		for (int i = 0; i < size; i++) {
			cities.add(i);
		}
		return cities;
	}
	
}
