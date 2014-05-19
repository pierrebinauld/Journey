package model.service.impl;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import model.data.City;
import model.data.Country;
import model.lookup.Circuit;
import model.lookup.impl.SimpleAlgorithm;
import model.parser.TspLibParser;
import model.service.DistanceService;
import model.service.LandscapeService;
import model.service.TimeService;
import model.service.impl.decorator.LocalStorageDistanceService;

public class TwoOptLandscapeService extends AbstractLandscapeService {

	public TwoOptLandscapeService(DistanceService distanceService, List<City> cities) {
		super(distanceService, cities);
	}

	@Override
	public Circuit getNeighbor(Circuit circuit, int cityIndex1, int cityIndex2) {

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

//		closeResultCircuit(result, cityIndex1, cityIndex2, pivotIndex);
		result.close(distanceService.getDistance(result.getCircuit().get(0), result.getCircuit().get(size-1)));
		System.out.println(result.getCircuit().get(0));
		System.out.println(result.getCircuit().get(size-1));
		return result;
	}
	
	private void addDistanceOfLastInversionPart(Circuit result, int pivotIndex) {
		if(0 != pivotIndex) {
		int reverseCityIndex1 = result.getCircuit().get(pivotIndex);
		int reverseCityIndex2;
//		if(0 != pivotIndex) {
			reverseCityIndex2 = result.getCircuit().get(pivotIndex - 1);
//		} else {
//			reverseCityIndex2 = result.getCircuit().get(result.size() - 1);
//		}
		int distance = distanceService.getDistance(reverseCityIndex1, reverseCityIndex2);
		result.setDistance(reverseCityIndex1, distance);
		}
	}

	@Override
	public Collection<Circuit> getNeighborhood(Circuit circuit) {
		// TODO Auto-generated method stub
		return null;
	}


	public static void main(String[] args) throws IOException {
		TimeService time = new TimeService();

		 File file = new File("/home/pierre/git/Journey/data/wi29.tsp");
//		 File file = new File("/home/pierre/git/Journey/data/ch71009.tsp");
//		File file = new File("/home/pierre/git/Journey/data/ja9847.tsp");

		Country country = TspLibParser.parse(file);

		System.out.println("Name: " + country.getName());
		System.out.println("Desc: " + country.getDescription());
		System.out.println("Dim: " + country.getDimension());
System.out.println();
		AbstractDistanceService distanceService = new ManhattanDistanceService(country.getCities());
//		AbstractDistanceService distanceService = new EuclidianDistanceService(country.getCities());
		DistanceService decoratedDistanceService  = new LocalStorageDistanceService(distanceService);
		
		LandscapeService landscapeService = new TwoOptLandscapeService(decoratedDistanceService, country.getCities());

		SimpleAlgorithm g = new SimpleAlgorithm(decoratedDistanceService, country.getCities());

		time.start();
		Circuit c = g.run();
		System.out.println("Time: " + time.tickInSecond() + "s");

		System.out.println("size: " + c.getCities().size() + " - " + c.getCircuit().size());
		System.out.println("length: " + c.getLength());
		System.out.println();
		System.out.println("Check length: " + (landscapeService.checkLength(c)));
		System.out.println(c);
		
		Circuit c2 = landscapeService.getNeighbor(c, 27,0);
		System.out.println("Check length: " + (landscapeService.checkLength(c2)));
		System.out.println(c2);
		System.out.println(c2.getDistances());
	}
}
