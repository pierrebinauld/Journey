package model.service.landscape;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import tools.Tools;
import model.iterator.TwoOptLandscapeIterator;
import model.iterator.key.TwoCityKey;
import model.lookup.Circuit;
import model.service.DistanceService;

public class TwoOptLandscapeService extends AbstractLandscapeService<TwoCityKey> {

	public TwoOptLandscapeService(DistanceService distanceService) {
		super(distanceService);
	}
	
	@Override
	public Iterator<TwoCityKey> iterator() {
		return new TwoOptLandscapeIterator(circuit);
	}

	@Override
	public TwoCityKey randomKey() {
		int index1 = Tools.random(0, citiesSize-2);
		int index2 = Tools.random(index1 + 2, citiesSize);
		return new TwoCityKey(index1, index2);
	}

	@Override
	public Circuit getNeighbor(TwoCityKey key) {
		
		int index1 = key.index1;
		int index2 = key.index2;

		// Circuit init
		LinkedList<Integer> c = circuit.getCircuit();
		List<Integer> distances = circuit.getDistances();
		int size = c.size();

		// Result init
		Circuit result = new Circuit();
		result.setCities(circuit.getCities());

		// Iteration through the initial circuit
		boolean reverse = false;
		int pivotIndex = -1;
		int distance;
		for (int i = 0; i < size; i++) {
			int cityIndex = c.get(i);
			distance = distances.get(cityIndex);

			// Manage 2-opt inversion
			if (i == index1 || i == index2) {
				if (-1 == pivotIndex) { // Inversion begining
					pivotIndex = i;
					reverse = true;
					
				} else { // Inversion ending -> Calcul of the 2 new distances.
					result.setDistance(cityIndex, distance);
					distance = distanceService.getDistance(c.get(index1), c.get(index2));
					reverse = false;
				}
			}

			if (!reverse) { // Distance adding when it does not in
									// inversion circuit part
				result.add(cityIndex, distance);
			} else { // Distance adding when it does in inversion circuit part
				result.add(pivotIndex, cityIndex, -1, distance);
			}
		}

		if (0 != pivotIndex) {
			distance = distanceService.getDistance(result.getCircuit().get(pivotIndex), result.getCircuit().get(pivotIndex-1));
			result.setDistance(result.getCircuit().get(pivotIndex), distance);
			result.close(circuit.getDistances().get(c.get(0)));
		}
		else {
			result.close(distanceService.getDistance(result.getCircuit().get(0), result.getCircuit().get(size - 1)));
		}
		return result;
	}

	@Override
	public int getNeighborLength(TwoCityKey key) {
		int length = circuit.getLength();
		LinkedList<Integer> c = circuit.getCircuit();
		int cityIndex1 = c.get(key.index1);
		int cityIndex2 = c.get(key.index2);
		
		length -= distances.get(cityIndex1);
		length -= distances.get(cityIndex2);

		int previousCityIndex1 = c.get((key.index1 - 1 < 0) ? citiesSize - 1 : key.index1 - 1);
		int previousCityIndex2 = c.get((key.index2 - 1 < 0) ? citiesSize - 1 : key.index2 - 1);

		length += distanceService.getDistance(cityIndex1, cityIndex2);
		length += distanceService.getDistance(previousCityIndex1, previousCityIndex2);

		return length;
	}
}
