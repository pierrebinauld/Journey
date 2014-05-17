package model.lookup.impl;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import model.data.City;
import model.data.Country;
import model.lookup.Circuit;
import model.lookup.Lookup;
import model.parser.TspLibParser;
import model.service.DistanceService;
import model.service.EuclidianDistanceService;
import model.service.TimeService;
import model.service.impl.decorator.LocalStorageDistanceService;
import model.tools.Tools;

public class GreedyAlgorithm implements Lookup {

	private DistanceService distanceService;
	
	private int sampleSize;
	private List<Integer> sample = new LinkedList<>();
	private Circuit circuit = new Circuit();

	public GreedyAlgorithm(DistanceService distanceService, int sampleSize) {
		this.distanceService = distanceService;
		this.sampleSize = sampleSize;
	}

	@Override
	public Circuit run(List<City> cities) {
		int count = cities.size();
		int origin = random(0, count);
		int root = origin;
		circuit.setCities(root, cities);
		List<Integer> indexes = buildCitiesIndex(count);
		indexes.remove(root);
		count = indexes.size();
		System.out.println(indexes.size());

		while (indexes.size() > sampleSize) {
//			System.out.println(indexes);
			while (sample.size() < sampleSize) {
				int neighbor = random(0, count);
				sample.add(neighbor);
				indexes.remove(neighbor);
				count = indexes.size();
			}
			root = updateCircuitWithNearestNeighbor(root, sample, cities);
		}
		while (indexes.size() > 0) {
//			System.out.println(indexes);
			root = updateCircuitWithNearestNeighbor(root, indexes, cities);
		}

		circuit.close(Tools.manhattan(cities.get(origin), cities.get(root)));

		return circuit;
	}

	/**
	 * Return an integer random value in the range [min, max), where max is not
	 * included.
	 * 
	 * @param min
	 * @param max
	 * @return
	 */
	public int random(int min, int max) {
		return min + (int) (Math.random() * (max - min));
	}

	private int updateCircuitWithNearestNeighbor(int root, List<Integer> neighbors, List<City> cities) {
//		City rootCity = cities.get(root);
		int nearest = 0;
		int distance = 0;
		int test;
		int count = neighbors.size();
		int indexNearest = 0;

		for (int n = 0; n < count; n++) {
//			test = Tools.manhattan(rootCity, cities.get(neighbors.get(n)));
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
		
//		File file = new File("/home/pierre/git/Journey/data/wi29.tsp");
//		File file = new File("/home/pierre/git/Journey/data/ch71009.tsp");
		File file = new File("/home/pierre/git/Journey/data/ja9847.tsp");

		Country country = TspLibParser.parse(file);

		System.out.println("Name: " + country.getName());
		System.out.println("Desc: " + country.getDescription());
		System.out.println("Dim: " + country.getDimension());

//		DistanceService distanceService = new ManhattanDistanceService(country.getCities());
//		DistanceService distanceService = new LocalStorageDistanceService(new ManhattanDistanceService(country.getCities()));
//		DistanceService distanceService = new EuclidianDistanceService(country.getCities());
		DistanceService distanceService = new LocalStorageDistanceService(new EuclidianDistanceService(country.getCities()));
		
		GreedyAlgorithm g = new GreedyAlgorithm(distanceService, 10);
		
		time.start();
		Circuit c = g.run(country.getCities());
		System.out.println("Time: " + time.tickInSecond() + "s");

		System.out.println("length: " + c.getLength());
//		System.out.println(c);
	}
}
