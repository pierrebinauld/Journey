package benchmark.impl;

import model.data.Country;
import model.lookup.Lookup;
import model.lookup.impl.RandomAlgorithm;
import model.lookup.impl.Tabu;
import model.service.distance.EuclidianDistanceService;
import model.service.factory.impl.TwoOptLandscapeFactory;
import tools.DataSources;
import benchmark.Benchmark;
import benchmark.parameter.impl.TabuParameter;
import benchmark.parameter.set.impl.TabuParameterSet;

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
		Country country = DataSources.fromParser(0);
		EuclidianDistanceService distanceService = new EuclidianDistanceService(country.getCities());
		RandomAlgorithm initialCircuitBuilder = new RandomAlgorithm(distanceService, country.getCities());
		TwoOptLandscapeFactory landscapeFactory = new TwoOptLandscapeFactory(distanceService);
		int[] tabuSize = {5000/*, 2, 3, 4, 5, 6, 7, 8, 9, 10*/};
		int[] iterationCount = {1000/*, 2000, 3000, 4000, 5000, 10000*/};
		TabuParameterSet parameterSet = new TabuParameterSet(initialCircuitBuilder, landscapeFactory, tabuSize, iterationCount);
		int executionCount = 1;
		TabuBenchmark benchmark = new TabuBenchmark(country.getName(), 27603, executionCount, parameterSet);
		benchmark.run();
	}
}
