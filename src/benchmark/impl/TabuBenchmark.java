package benchmark.impl;

import benchmark.Benchmark;
import benchmark.parameterset.builder.impl.TabuParameterSet;
import benchmark.parameterset.impl.TabuParameter;
import model.data.Country;
import model.lookup.Lookup;
import model.lookup.impl.RandomAlgorithm;
import model.lookup.impl.Tabu;
import model.service.distance.EuclidianDistanceService;
import model.service.landscape.TwoOptLandscapeService;
import tools.Constant;
import tools.DataSources;

public class TabuBenchmark extends Benchmark<TabuParameter> {

	public TabuBenchmark(String country, int optimum, int executionCount, TabuParameterSet parameterSet) {
		super("Tabu", country, optimum, executionCount, parameterSet);
	}


	@Override
	public Lookup initializeAlgorithm(TabuParameter parameter) {
		Tabu lookup = new Tabu(
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
		TwoOptLandscapeService landscapeService = new TwoOptLandscapeService(distanceService);
		int[] tabuSize = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
		int[] iterationCount = {1000};
		TabuParameterSet parameterSet = new TabuParameterSet(initialCircuitBuilder, landscapeService, tabuSize, iterationCount);
		int executionCount = 1;
		TabuBenchmark benchmark = new TabuBenchmark(Constant.COUNTRY_NAMES[countryId], Constant.OPTIMUM[countryId], executionCount, parameterSet);
		benchmark.run();
	}
}
