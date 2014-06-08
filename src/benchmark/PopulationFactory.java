package benchmark;

import model.lookup.AbstractBuilderAlgorithm;
import model.lookup.Circuit;

import java.util.ArrayList;
import java.util.List;

public class PopulationFactory {
	private AbstractBuilderAlgorithm lookup;

	public PopulationFactory(AbstractBuilderAlgorithm lookup) {
		this.lookup = lookup;
	}

	public List<Circuit> manufacture(int populationSize) {
		ArrayList<Circuit> population = new ArrayList<>();
		for(int i = 0; i < populationSize; i++) {
			population.add(lookup.run());
		}
		return population;
	}
}
