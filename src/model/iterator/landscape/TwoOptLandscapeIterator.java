package model.iterator.landscape;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;

import model.data.City;
import model.data.Country;
import model.iterator.landscape.keys.TwoCityKey;
import model.lookup.Circuit;
import model.lookup.Lookup;
import model.lookup.impl.SimpleBuilderAlgorithm;
import model.lookup.impl.SimpleModifierAlgorithm;
import model.parser.TspLibParser;
import model.service.DistanceService;
import model.service.TimeService;
import model.service.distance.AbstractDistanceService;
import model.service.distance.ManhattanDistanceService;
import model.service.distance.decorator.LocalStorageDistanceService;

public class TwoOptLandscapeIterator extends AbstractLandscapeIterator<TwoCityKey> {

	private int i = 0;
	private int j = 0;
	private int citiesSize;
	private Circuit circuit;
	List<Integer> distances;

	public TwoOptLandscapeIterator(DistanceService distanceService, Circuit circuit, List<City> cities) {
		super(distanceService, cities);

		setCircuit(circuit);
	}

	@Override
	public void reset() {
		i = 0;
		j = 2;
		citiesSize = cities.size();
	}

	@Override
	public void setCircuit(Circuit circuit) {
		this.circuit = circuit;
		this.distances = circuit.getDistances();
		reset();
	}

	@Override
	public TwoCityKey getCurrentKey() {
		return new TwoCityKey(i, j);
	}

	@Override
	public void remove() {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean hasNext() {
		
		return !(i == citiesSize - 3 && j == citiesSize - 1 || null == circuit);
	}

	@Override
	public Integer next() {

		if (i == citiesSize - 3 && j == citiesSize - 1 || null == circuit) {
			throw new NoSuchElementException();
		} else if (j == citiesSize - 1) {
			i++;
			j = i + 2;
		} else {
			j++;

			if (0 == i && j == citiesSize - 1) {
				return next();
			}
		}

		return getNeighborLength(i, j);
	}

	@Override
	public Circuit getNeighbor(Object key) {
		TwoCityKey k = (TwoCityKey) key; // T.T
		
		int cityIndex1 = k.getCityIndex1();
		int cityIndex2 = k.getCityIndex2();

		// Circuit init
		LinkedList<Integer> c = circuit.getCircuit();
		List<Integer> distances = circuit.getDistances();
		int size = c.size();

		// Result init
		Circuit result = new Circuit();
		result.setCities(circuit.getCities());

		// Iteration through the initial circuit
		int pivotIndex = -1;
		int distance;
		for (int i = 0; i < size; i++) {
			int cityIndex = c.get(i);
			distance = distances.get(cityIndex);

			// Manage 2-opt inversion
			if (cityIndex == cityIndex1 || cityIndex == cityIndex2) {
				if (-1 == pivotIndex) { // Inversion begining
					pivotIndex = i;

				} else { // Inversion ending -> Calcul of the 2 new distances.
					addDistanceOfLastInversionPart(result, pivotIndex);
					distance = distanceService.getDistance(cityIndex1, cityIndex2);
					pivotIndex = -1;
				}
			}

			if (-1 == pivotIndex) { // Distance adding when it does not in
									// inversion circuit part
				result.add(cityIndex, distance);
			} else { // Distance adding when it does in inversion circuit part
				result.add(pivotIndex, cityIndex, -1, distance);
			}
		}

		result.close(distanceService.getDistance(result.getCircuit().get(0), result.getCircuit().get(size - 1)));

		return result;
	}

	private void addDistanceOfLastInversionPart(Circuit result, int pivotIndex) {
		if (0 != pivotIndex) {
			int reverseCityIndex1 = result.getCircuit().get(pivotIndex);
			int reverseCityIndex2;
			// if(0 != pivotIndex) {
			reverseCityIndex2 = result.getCircuit().get(pivotIndex - 1);
			// } else {
			// reverseCityIndex2 = result.getCircuit().get(result.size() - 1);
			// }
			int distance = distanceService.getDistance(reverseCityIndex1, reverseCityIndex2);
			result.setDistance(reverseCityIndex1, distance);
		}
	}

	public int getNeighborLength(int cityIndex1, int cityIndex2) {
		int length = circuit.getLength();
		length -= distances.get(cityIndex1);
		length -= distances.get(cityIndex2);

		int previousityIndex1 = (cityIndex1 - 1 < 0) ? citiesSize - 1 : cityIndex1 - 1;
		int previousityIndex2 = (cityIndex2 - 1 < 0) ? citiesSize - 1 : cityIndex2 - 1;

//		System.out.println(cityIndex1 + " - " + cityIndex2);
//		System.out.println(previousityIndex1 + " - " + previousityIndex2);

		length += distanceService.getDistance(cityIndex1, cityIndex2);
		length += distanceService.getDistance(previousityIndex1, previousityIndex2);

		return length;
	}

	public static void main(String[] args) throws IOException {
		TimeService time = new TimeService();

		File file = new File("/home/pierre/git/Journey/data/wi29.tsp");
		// File file = new File("/home/pierre/git/Journey/data/ch71009.tsp");
		// File file = new File("/home/pierre/git/Journey/data/ja9847.tsp");

		Country country = TspLibParser.parse(file);

		System.out.println("Name: " + country.getName());
		System.out.println("Dim: " + country.getDimension());
		System.out.println();

		AbstractDistanceService distanceService = new ManhattanDistanceService(country.getCities());
		// AbstractDistanceService distanceService = new
		// EuclidianDistanceService(country.getCities());
		DistanceService decoratedDistanceService = new LocalStorageDistanceService(distanceService);

		SimpleBuilderAlgorithm g = new SimpleBuilderAlgorithm(decoratedDistanceService, country.getCities());

		time.start();
		Circuit c = g.run();
		System.out.println("Time: " + time.tickInSecond() + "s");

		System.out.println("size: " + c.getCities().size() + " - " + c.getCircuit().size());
		System.out.println("length: " + c.getLength());
		System.out.println("Check length: " + (decoratedDistanceService.checkLength(c)));
		System.out.println(c);

		TwoOptLandscapeIterator landscapeIterator = new TwoOptLandscapeIterator(decoratedDistanceService, c, country.getCities());

		time.start();
		int calculatedLength = landscapeIterator.getNeighborLength(2, 6);
		Circuit buildCircuit = landscapeIterator.getNeighbor(new TwoCityKey(2, 6));
		System.out.println(buildCircuit);
		System.out.println("time: " + time.tickInSecond());
		System.out.println("check length: " + decoratedDistanceService.checkLength(buildCircuit));
		System.out.println("build length: \t\t" + buildCircuit.getLength());
		System.out.println("calculated length: \t" + calculatedLength);
		System.out.println("check 2:  " + (calculatedLength == buildCircuit.getLength()));
		
		Lookup algo = new SimpleModifierAlgorithm(landscapeIterator, c);
		
		time.start();
		Circuit result = algo.run();
		System.out.println("Time: "+time.tickInSecond());
		System.out.println(result);

	}
}
