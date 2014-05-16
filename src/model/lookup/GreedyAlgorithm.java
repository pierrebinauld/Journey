package model.lookup;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import model.data.City;
import model.data.Country;
import model.parser.TspLibParser;
import model.tools.Tools;

public class GreedyAlgorithm implements Lookup {

	private int sampleSize;
	private List<Integer> sample = new LinkedList<>();
	private Circuit circuit = new Circuit();

	public GreedyAlgorithm(int sampleSize) {
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
		System.out.println(" - - - - - -");
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
		City rootCity = cities.get(root);
		int nearest = 0;
		int distance = 0;
		int test;
		int count = neighbors.size();
		int indexNearest = 0;

		for (int n = 0; n < count; n++) {
			test = Tools.manhattan(rootCity, cities.get(neighbors.get(n)));
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
		File file = new File("/home/pierre/git/Journey/data/wi29.tsp");

		Country country = TspLibParser.parse(file);

		for (City city : country.getCities()) {
			System.out.println(city.getPosition());
		}

		System.out.println("Name: " + country.getName());
		System.out.println("Desc: " + country.getDescription());
		System.out.println("Dim: " + country.getDimension());

		GreedyAlgorithm g = new GreedyAlgorithm(10);
		
		long start; 
		start = System.nanoTime();
		Circuit c = g.run(country.getCities());
		long duree = System.nanoTime() - start;
		System.out.println(duree/1000000000);

		System.out.println("length: " + c.getLength());
		System.out.println(c);
	}
}
