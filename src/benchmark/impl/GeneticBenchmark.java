package benchmark.impl;

import benchmark.Benchmark;
import benchmark.parameter.PopulationFactory;
import benchmark.parameter.impl.GeneticParameter;
import benchmark.parameter.set.impl.GeneticParameterSet;
import model.data.Country;
import model.lookup.Lookup;
import model.lookup.impl.GeneticAlgorithm;
import model.lookup.impl.RandomAlgorithm;
import model.service.distance.EuclidianDistanceService;
import model.service.factory.impl.TwoOptLandscapeFactory;
import tools.Constant;
import tools.DataSources;

public class GeneticBenchmark extends Benchmark<GeneticParameter> {

	public GeneticBenchmark(String country, int optimum, int executionCount, GeneticParameterSet parameterSet) {
		super("Genetic", country, optimum, executionCount, parameterSet);
	}


	@Override
	public Lookup initializeAlgorithm(GeneticParameter parameter) {
		GeneticAlgorithm lookup = new GeneticAlgorithm(
				parameter.getLandscapeService(),
				parameter.getInitialPopulationFactory(),
				parameter.getInitialPopulationSize(),
				parameter.getMutationProbability(),
				parameter.getIterationCount());

		return lookup;
	}

	public static void main(String[] args) {
		int countryId = 0;
		Country country = DataSources.fromParser(countryId);
		EuclidianDistanceService distanceService = new EuclidianDistanceService(country.getCities());
		RandomAlgorithm initialCircuitBuilder = new RandomAlgorithm(distanceService, country.getCities());
		PopulationFactory populationFactory = new PopulationFactory(initialCircuitBuilder);
		TwoOptLandscapeFactory landscapeFactory = new TwoOptLandscapeFactory(distanceService);
		//TODO: set parameters values
		int[] initialPopulationSize = {20, 50, 100};
		double[] mutationProbability = {.05, .1, .2};
		int[] iterationCount = {1000, 2000};
		GeneticParameterSet parameterSet = new GeneticParameterSet(landscapeFactory, populationFactory, initialPopulationSize, mutationProbability, iterationCount);
		int executionCount = 1;
		GeneticBenchmark benchmark = new GeneticBenchmark(country.getName(), Constant.OPTIMUM[countryId], executionCount, parameterSet);
		benchmark.run();
	}
}
