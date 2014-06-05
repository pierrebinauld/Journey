package benchmark.impl;

import benchmark.Benchmark;
import benchmark.parameter.impl.TabuParameter;
import benchmark.parameter.set.impl.TabuParameterSet;
import model.data.Country;
import model.lookup.Lookup;
import model.lookup.impl.RandomAlgorithm;
import model.lookup.impl.TabuAlgorithm;
import model.service.distance.EuclidianDistanceService;
import model.service.factory.impl.TwoOptLandscapeFactory;
import tools.Constant;
import tools.DataSources;

public class TabuBenchmark extends Benchmark<TabuParameter> {

	public TabuBenchmark(String country, int optimum, int executionCount, TabuParameterSet parameterSet) {
		super("Tabu", country, optimum, executionCount, parameterSet);
	}


	@Override
	public Lookup initializeAlgorithm(TabuParameter parameter) {
		TabuAlgorithm lookup = new TabuAlgorithm(
				parameter.getLandscapeService(), 
				parameter.getInitialCircuit(), 
				parameter.getTabuSize(), 
				parameter.getIterationCount());

		return lookup;
	}

	public static void main(String[] args) {
		int countryId = 0;
		Country country = DataSources.fromParser(countryId);
		EuclidianDistanceService distanceService = new EuclidianDistanceService(country.getCities());
		RandomAlgorithm initialCircuitBuilder = new RandomAlgorithm(distanceService, country.getCities());
		TwoOptLandscapeFactory landscapeFactory = new TwoOptLandscapeFactory(distanceService);
		int[] tabuSize = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
		int[] iterationCount = {1000, 2000, 3000, 4000, 5000, 10000};
		TabuParameterSet parameterSet = new TabuParameterSet(initialCircuitBuilder, landscapeFactory, tabuSize, iterationCount);
		int executionCount = 1;
		TabuBenchmark benchmark = new TabuBenchmark(country.getName(), Constant.OPTIMUM[countryId], executionCount, parameterSet);
		benchmark.run();
	}
}
