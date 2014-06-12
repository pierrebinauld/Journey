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

public class GeneticBenchmark extends Benchmark<GeneticParameter> {

	public GeneticBenchmark(String country, int optimum, int executionCount, GeneticParameterSet parameterSet) {
		super("Genetic", country, optimum, executionCount, parameterSet);
	}

	public GeneticBenchmark(String country, int optimum, int executionCount, GeneticParameterSet parameterSet, int threadCount) {
		super("Genetic", country, optimum, executionCount, parameterSet, threadCount);
	}

	@Override
	public GeneticAlgorithm initializeAlgorithm(GeneticParameter parameter) {
		return new GeneticAlgorithm(parameter);
	}

	public static void main(String[] args) {
		int executionCount = 1;
		int maxCountryId = 1;
		for(int countryId=0; countryId<maxCountryId; countryId++) {

			Country country = DataSources.fromParser(countryId);
			EuclidianDistanceService distanceService = new EuclidianDistanceService(country.getCities());
			
			RandomAlgorithm initialCircuitBuilder = new RandomAlgorithm(new BuilderParameter(distanceService, country.getCities()));
			String algoName = "Random";
//			GreedyAlgorithm initialCircuitBuilder = new GreedyAlgorithm(new BuilderParameter(distanceService, country.getCities()));
//			String algoName = "Greedy";
			PopulationFactory populationFactory = new PopulationFactory(initialCircuitBuilder);
			TwoOptLandscapeFactory landscapeFactory = new TwoOptLandscapeFactory(distanceService);
			
			
			int[] initialPopulationSize = {10/*, 20, 50, 100,200, 500, 1000, 2000, /*5000, 10000/**/};
			double[] mutationProbability = {.01/*, .02, .03, .05, .1, .2, .5, .8/**/};
			int[] iterationCount = {1000/*, 2000, 5000, 10000, 50000/**/};
			
			
			GeneticParameterSet parameterSet = new GeneticParameterSet(landscapeFactory, populationFactory, initialPopulationSize, mutationProbability, iterationCount);

			GeneticBenchmark benchmark = new GeneticBenchmark(Constant.COUNTRY_NAMES[countryId] + "/" + algoName, Constant.OPTIMUM[countryId], executionCount, parameterSet, 1);
			benchmark.run();

		}
	}
}
