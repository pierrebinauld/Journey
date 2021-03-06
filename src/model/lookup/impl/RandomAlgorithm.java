package model.lookup.impl;

import benchmark.parameter.BuilderParameter;
import model.lookup.AbstractBuilderAlgorithm;
import model.lookup.Circuit;
import tools.Tools;

import java.util.ArrayList;
import java.util.List;

public class RandomAlgorithm extends AbstractBuilderAlgorithm<BuilderParameter> {

	public RandomAlgorithm(BuilderParameter parameter) {
		super(parameter);
	}

	@Override
	public Circuit run() {
		Circuit circuit = new Circuit();
		circuit.setCities(cities);

		int size = cities.size();

		List<Integer> randomCityIndexes = new ArrayList<>();

		for (int i = 0; i < size; i++) {
			int random = Tools.random(0, randomCityIndexes.size() + 1);
			if(random == randomCityIndexes.size()) {
				randomCityIndexes.add(i);
			} else {
				randomCityIndexes.add(random, i);
			}
		}
		
		circuit.add(randomCityIndexes.get(0), -1);
		for (int i = 1; i < size; i++) {
			circuit.add(randomCityIndexes.get(i), distanceService.getDistance(randomCityIndexes.get(i - 1), randomCityIndexes.get(i)));
		}

		circuit.close(distanceService.getDistance(randomCityIndexes.get(0), randomCityIndexes.get(size - 1)));

		return circuit;
	}
}
