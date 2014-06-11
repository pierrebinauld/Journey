package benchmark.impl;

import benchmark.Benchmark;
import benchmark.PopulationFactory;
import benchmark.parameter.BuilderParameter;
import benchmark.parameter.impl.GeneticParameter;
import benchmark.parameter.set.impl.GeneticParameterSet;
import model.data.Country;
import model.lookup.impl.GeneticAlgorithm;
import model.lookup.impl.RandomAlgorithm;
import model.service.distance.EuclidianDistanceService;
import model.service.factory.impl.TwoOptLandscapeFactory;
import tools.Constant;
import tools.DataSources;

public class GeneticBenchmark extends Benchmark<GeneticParameter, GeneticAlgorithm> {

	public GeneticBenchmark(String country, int optimum, int executionCount, GeneticParameterSet parameterSet) {
		super("Genetic", country, optimum, executionCount, parameterSet);
	}

	@Override
	public GeneticAlgorithm initializeAlgorithm(GeneticParameter parameter) {
		return new GeneticAlgorithm(parameter);
	}

	public static void main(String[] args) {
		int countryId = 0;
		Country country = DataSources.fromParser(countryId);
		EuclidianDistanceService distanceService = new EuclidianDistanceService(country.getCities());
		RandomAlgorithm initialCircuitBuilder = new RandomAlgorithm(new BuilderParameter(distanceService, country.getCities()));
		PopulationFactory populationFactory = new PopulationFactory(initialCircuitBuilder);
		TwoOptLandscapeFactory landscapeFactory = new TwoOptLandscapeFactory(distanceService);
		int[] initialPopulationSize = {20, 50, 100};
		double[] mutationProbability = {.05, .1, .2};
		int[] iterationCount = {1000, 2000};
		GeneticParameterSet parameterSet = new GeneticParameterSet(landscapeFactory, populationFactory, initialPopulationSize, mutationProbability, iterationCount);
		int executionCount = 100;
		GeneticBenchmark benchmark = new GeneticBenchmark(Constant.COUNTRY_NAMES[countryId], Constant.OPTIMUM[countryId], executionCount, parameterSet);
		benchmark.run();
	}
}
