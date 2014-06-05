package benchmark.impl;

import benchmark.Benchmark;
import benchmark.parameterset.builder.impl.SimulatedAnnealingParameterSet;
import benchmark.parameterset.impl.SimulatedAnnealingParameter;
import model.data.Country;
import model.lookup.Lookup;
import model.lookup.impl.RandomAlgorithm;
import model.lookup.impl.SimulatedAnnealing;
import model.service.distance.EuclidianDistanceService;
import model.service.landscape.TwoOptLandscapeService;
import tools.Constant;
import tools.DataSources;

public class SimulatedAnnealingBenchmark extends Benchmark<SimulatedAnnealingParameter> {

	public SimulatedAnnealingBenchmark(String country, int optimum, int executionCount, SimulatedAnnealingParameterSet parameterSet) {
		super("Simulated_annealing", country, optimum, executionCount, parameterSet);
	}


	@Override
	public Lookup initializeAlgorithm(SimulatedAnnealingParameter parameter) {
		SimulatedAnnealing lookup = new SimulatedAnnealing(
				parameter.getLandscapeService(), 
				parameter.getInitialCircuit(), 
				parameter.getTemperature(),
				parameter.getLambda(),
				parameter.getTemperatureBreakpoint());

		return lookup;
	}

	public static void main(String[] args) {
		int countryId = 0;
		Country country = DataSources.fromParser(countryId);
		EuclidianDistanceService distanceService = new EuclidianDistanceService(country.getCities());
		RandomAlgorithm initialCircuitBuilder = new RandomAlgorithm(distanceService, country.getCities());
		TwoOptLandscapeService landscapeService = new TwoOptLandscapeService(distanceService);
		//TODO: initialize parameters
		double[] temperature = {};
		double[] lambda = {};
		double[] temperatureBreakpoint = {};
		SimulatedAnnealingParameterSet parameterSet = new SimulatedAnnealingParameterSet(initialCircuitBuilder, landscapeService, temperature, lambda, temperatureBreakpoint);
		int executionCount = 1;
		SimulatedAnnealingBenchmark benchmark = new SimulatedAnnealingBenchmark(Constant.COUNTRY_NAMES[countryId], Constant.OPTIMUM[countryId], executionCount, parameterSet);
		benchmark.run();
	}
}
