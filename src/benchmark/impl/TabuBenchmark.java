package benchmark.impl;

import java.util.ArrayList;

import model.data.Country;
import model.lookup.Lookup;
import model.lookup.impl.RandomAlgorithm;
import model.lookup.impl.Tabu;
import model.service.distance.EuclidianDistanceService;
import model.service.landscape.TwoOptLandscapeService;
import tools.DataSources;
import benchmark.Benchmark;
import benchmark.parameterset.builder.impl.TabuParameterSet;
import benchmark.parameterset.impl.TabuParameter;

public class TabuBenchmark extends Benchmark<TabuParameter> {

	public TabuBenchmark(int executionCount, TabuParameterSet parameterSet) {
		super("Tabu", executionCount, parameterSet);
	}


	@Override
	public Lookup initializeAlgorithm(TabuParameter parameter) {
		System.out.println(parameter.getLandscapeService());
		System.out.println(parameter.getInitialCircuit());
		System.out.println(parameter.getTabuSize());
		System.out.println(parameter.getIterationCount());
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
		TwoOptLandscapeService landscapeService = new TwoOptLandscapeService(distanceService);
		int[] tabuSize = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
		int[] iterationCount = {1000};
		TabuParameterSet parameterSet = new TabuParameterSet(initialCircuitBuilder, landscapeService, tabuSize, iterationCount);
		int executionCount = 1;
		TabuBenchmark benchmark = new TabuBenchmark(executionCount, parameterSet);
		benchmark.run();
	}


	@Override
	public ArrayList<String> toStringArrayList(TabuParameter parameters) {
		ArrayList<String> result = new ArrayList<>();

		result.add(Integer.toString(parameters.getIterationCount()));
		result.add(Integer.toString(parameters.getTabuSize()));
		result.add(Integer.toString(parameters.getInitialCircuit().getLength()));
		
		return result;
	}
}
