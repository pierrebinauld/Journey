package model.lookup.impl;

import model.data.City;
import model.lookup.AbstractBuilderAlgorithm;
import model.lookup.Circuit;
import model.service.DistanceService;

import java.util.List;

public class SimpleBuilderAlgorithm extends AbstractBuilderAlgorithm {

	public SimpleBuilderAlgorithm(DistanceService distanceService, List<City> cities) {
		super(distanceService, cities);
	}

	@Override
	public Circuit run() {
		Circuit circuit = new Circuit();
		circuit.setCities(0, cities);
		
		int size = cities.size();
		
		for(int i=1; i<size; i++) {
			circuit.add(i, distanceService.getDistance(i-1, i));
		}
		
		circuit.close(distanceService.getDistance(0, size-1));
		
		return circuit;
	}

}
