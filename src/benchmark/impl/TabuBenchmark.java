package benchmark.impl;

import benchmark.AbstractBenchmark;
import benchmark.parameterset.builder.impl.TabuParameterSetBuilder;
import benchmark.parameterset.impl.TabuParameterSet;
import model.data.Country;
import model.lookup.Lookup;
import model.lookup.impl.RandomAlgorithm;
import model.lookup.impl.Tabu;
import model.service.distance.EuclidianDistanceService;
import model.service.landscape.TwoOptLandscapeService;
import tools.DataSources;

public class TabuBenchmark extends AbstractBenchmark<TabuParameterSet> {

	public TabuBenchmark(int executionCount, TabuParameterSetBuilder builder) {
		super(executionCount, builder);
	}


	@Override
	public Lookup initializeAlgorithm(TabuParameterSet parameterSet) {
		Tabu lookup = new Tabu(parameterSet.getLandscapeService(), parameterSet.getInitialCircuit(), parameterSet.getTabuSize(), parameterSet.getIterationCount());

		return lookup;
	}

	public static void main(String[] args) {
		Country country = DataSources.fromParser(0);
		EuclidianDistanceService distanceService = new EuclidianDistanceService(country.getCities());
		RandomAlgorithm initialCircuitBuilder = new RandomAlgorithm(distanceService, country.getCities());
		TwoOptLandscapeService landscapeService = new TwoOptLandscapeService(distanceService);
		int[] tabuSize = {10};
		int[] iterationCount = {1000};
		TabuParameterSetBuilder psBuilder = new TabuParameterSetBuilder(initialCircuitBuilder, landscapeService, tabuSize, iterationCount);
		int executionCount = 1;
		TabuBenchmark benchmark = new TabuBenchmark(executionCount, psBuilder);
		benchmark.run();
	}
}
