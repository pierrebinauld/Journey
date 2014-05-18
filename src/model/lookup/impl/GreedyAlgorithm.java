package model.lookup.impl;

import model.data.City;
import model.data.Country;
import model.lookup.AbstractBuilderAlgorithm;
import model.lookup.Circuit;
import model.parser.TspLibParser;
import model.service.DistanceService;
import model.service.EuclidianDistanceService;
import model.service.TimeService;
import model.service.impl.decorator.LocalStorageDistanceService;
import model.tools.Tools;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class GreedyAlgorithm extends AbstractBuilderAlgorithm {

	private DistanceService distanceService;

	private int origin;
	private int sampleSize;
	private List<Integer> sample = new LinkedList<>();
	private List<Integer> indexes = new LinkedList<>();
	private Circuit circuit = new Circuit();

	public GreedyAlgorithm(DistanceService distanceService, List<City> cities, int sampleSize) {
		super(cities);
		this.distanceService = distanceService;
		this.sampleSize = sampleSize;
	}

	@Override
	public Circuit run() {
		
		int root = init();

		while (indexes.size() > sampleSize) {
			updateIndexesSampleList();
			root = updateCircuitWithNearestNeighbor(root, sample);
		}
		
		while (indexes.size() > 0) {
			root = updateCircuitWithNearestNeighbor(root, indexes);
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
			sample.add(neighbor);
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

	public static void main(String[] args) throws IOException {
		TimeService time = new TimeService();

		// File file = new File("/home/pierre/git/Journey/data/wi29.tsp");
		// File file = new File("/home/pierre/git/Journey/data/ch71009.tsp");
		File file = new File("/home/pierre/git/Journey/data/ja9847.tsp");

		Country country = TspLibParser.parse(file);

		System.out.println("Name: " + country.getName());
		System.out.println("Desc: " + country.getDescription());
		System.out.println("Dim: " + country.getDimension());

		// DistanceService distanceService = new
		// ManhattanDistanceService(country.getCities());
		// DistanceService distanceService = new LocalStorageDistanceService(new
		// ManhattanDistanceService(country.getCities()));
		// DistanceService distanceService = new
		// EuclidianDistanceService(country.getCities());
		DistanceService distanceService = new LocalStorageDistanceService(new EuclidianDistanceService(country.getCities()));

		GreedyAlgorithm g = new GreedyAlgorithm(distanceService, country.getCities(), 10);

		time.start();
		Circuit c = g.run();
		System.out.println("Time: " + time.tickInSecond() + "s");

		System.out.println("length: " + c.getLength());
		// System.out.println(c);
	}
}
