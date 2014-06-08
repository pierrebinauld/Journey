package benchmark.impl;

import benchmark.Benchmark;
import benchmark.parameter.impl.SimulatedAnnealingParameter;
import benchmark.parameter.set.impl.SimulatedAnnealingParameterSet;
import model.data.Country;
import model.lookup.Lookup;
import model.lookup.impl.RandomAlgorithm;
import model.lookup.impl.SimulatedAnnealingAlgorithm;
import model.service.distance.EuclidianDistanceService;
import model.service.factory.impl.TwoOptLandscapeFactory;
import tools.Constant;
import tools.DataSources;

public class SimulatedAnnealingBenchmark extends Benchmark<SimulatedAnnealingParameter> {

	public SimulatedAnnealingBenchmark(String country, int optimum, int executionCount,
			SimulatedAnnealingParameterSet parameterSet) {
		super("Simulated_annealing", country, optimum, executionCount, parameterSet);
	}

	@Override
	public Lookup initializeAlgorithm(SimulatedAnnealingParameter parameter) {
		SimulatedAnnealingAlgorithm lookup = new SimulatedAnnealingAlgorithm(parameter.getLandscapeService(),
				parameter.getInitialCircuit(), parameter.getTemperature(), parameter.getLambda(),
				parameter.getTemperatureBreakpoint());

		return lookup;
	}

	public static void main(String[] args) {
		for (int i = 0; i < 3; i++) {
			int countryId = i;
			Country country = DataSources.fromParser(countryId);
			
			EuclidianDistanceService distanceService = new EuclidianDistanceService(country.getCities());
			RandomAlgorithm initialCircuitBuilder = new RandomAlgorithm(distanceService, country.getCities());
			String algoName = "Random";
			TwoOptLandscapeFactory landscapeFactory = new TwoOptLandscapeFactory(distanceService);
			
			// TODO: initialize parameters
			double[] temperature = { 10, 20, 30,40,50,100, 200, 300, 400,500,1000,5000 };
			double[] lambda = {0.5,0.6,0.7,0.8,0.9,0.99, 0.999, 0.9999, 0.99999 };
			double[] temperatureBreakpoint = { 0.05, 0.005, 0.0005 };
			
			SimulatedAnnealingParameterSet parameterSet = new SimulatedAnnealingParameterSet(
					initialCircuitBuilder,
					landscapeFactory, 
					temperature, 
					lambda, 
					temperatureBreakpoint);
			int executionCount = 1;
			SimulatedAnnealingBenchmark benchmark = new SimulatedAnnealingBenchmark(Constant.COUNTRY_NAMES[countryId]+"/"+algoName,
					Constant.OPTIMUM[countryId], executionCount, parameterSet);
			benchmark.run();

		}
	}
}
