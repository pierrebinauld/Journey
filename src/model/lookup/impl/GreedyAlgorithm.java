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
	private int sampleSize;
	private List<Integer> sample = new LinkedList<>();
	private List<Integer> indexes = new LinkedList<>();
	private Circuit circuit = new Circuit();

	public GreedyAlgorithm(DistanceService distanceService, List<City> cities, int sampleSize) {
		super(distanceService, cities);
		this.sampleSize = sampleSize;
	}

	@Override
	public Circuit run() {
		
		int root = init();

		while (indexes.size() > 0) {
			updateIndexesSampleList();
			root = updateCircuitWithNearestNeighbor(root, sample);
		}
		
		while (sample.size() > 0) {
			root = updateCircuitWithNearestNeighbor(root, sample);
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

	private void updateIndexesSampleList() {
		while (sample.size() < sampleSize) {
			int neighbor = Tools.random(0, indexes.size());
			sample.add(indexes.get(neighbor));
			indexes.remove(neighbor);
		}
	}

	private int updateCircuitWithNearestNeighbor(int root, List<Integer> neighbors) {
		int nearest = 0;
		int distance = 0;
		int test;
		int count = neighbors.size();
		int indexNearest = 0;

		for (int n = 0; n < count; n++) {
			test = distanceService.getDistance(root, neighbors.get(n));
			if (test < distance || distance == 0) {
				distance = test;
				nearest = neighbors.get(n);
				indexNearest = n;
			}
		}

		circuit.add(nearest, distance);
		neighbors.remove(indexNearest);
		return nearest;
	}

	private List<Integer> buildCitiesIndex(int size) {
		List<Integer> cities = new LinkedList<>();
		for (int i = 0; i < size; i++) {
			cities.add(i);
		}
		return cities;
	}
}
