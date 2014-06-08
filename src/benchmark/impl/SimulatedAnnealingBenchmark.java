package benchmark.impl;

import model.data.Country;
import model.lookup.impl.RandomAlgorithm;
import model.lookup.impl.SimulatedAnnealingAlgorithm;
import model.service.distance.EuclidianDistanceService;
import model.service.factory.impl.TwoOptLandscapeFactory;
import tools.Constant;
import tools.DataSources;
import benchmark.Benchmark;
import benchmark.parameter.BuilderParameter;
import benchmark.parameter.impl.SimulatedAnnealingParameter;
import benchmark.parameter.set.impl.SimulatedAnnealingParameterSet;

public class SimulatedAnnealingBenchmark extends Benchmark<SimulatedAnnealingParameter, SimulatedAnnealingAlgorithm> {

	public SimulatedAnnealingBenchmark(String country, int optimum, int executionCount,
			SimulatedAnnealingParameterSet parameterSet) {
		super("Simulated_annealing", country, optimum, executionCount, parameterSet);
	}

	@Override
	public SimulatedAnnealingAlgorithm initializeAlgorithm(SimulatedAnnealingParameter parameter) {
		return new SimulatedAnnealingAlgorithm(parameter);
	}

	public static void main(String[] args) {
		for (int i = 0; i < 3; i++) {
			int countryId = i;
			Country country = DataSources.fromParser(countryId);

			EuclidianDistanceService distanceService = new EuclidianDistanceService(country.getCities());
			RandomAlgorithm initialCircuitBuilder = new RandomAlgorithm(
					new BuilderParameter(distanceService, country.getCities()));
			String algoName = "Random";
			TwoOptLandscapeFactory landscapeFactory = new TwoOptLandscapeFactory(distanceService);

			double[] temperature = { 10, 20, 30, 40, 50, 100, 200, 300, 400, 500, 1000, 5000 };
			double[] lambda = { 0.5, 0.6, 0.7, 0.8, 0.9, 0.99, 0.999, 0.9999, 0.99999 };
			double[] temperatureBreakpoint = { 0.05, 0.005, 0.0005 };

			SimulatedAnnealingParameterSet parameterSet = new SimulatedAnnealingParameterSet(initialCircuitBuilder,
					landscapeFactory, temperature, lambda, temperatureBreakpoint);
			int executionCount = 1;

			SimulatedAnnealingBenchmark benchmark = new SimulatedAnnealingBenchmark(Constant.COUNTRY_NAMES[countryId] + "/"
					+ algoName, Constant.OPTIMUM[countryId], executionCount, parameterSet);

			benchmark.run();
		}
	}
}
