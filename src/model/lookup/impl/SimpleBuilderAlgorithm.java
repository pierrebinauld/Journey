package model.lookup.impl;

import benchmark.parameter.BuilderParameter;
import model.lookup.AbstractBuilderAlgorithm;
import model.lookup.Circuit;

public class SimpleBuilderAlgorithm extends AbstractBuilderAlgorithm<BuilderParameter> {

	public SimpleBuilderAlgorithm(BuilderParameter parameter) {
		super(parameter);
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
