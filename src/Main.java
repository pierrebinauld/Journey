import benchmark.impl.SimulatedAnnealingBenchmark;
import benchmark.parameter.BuilderParameter;
import benchmark.parameter.set.impl.SimulatedAnnealingParameterSet;
import model.data.Country;
import model.lookup.impl.GreedyAlgorithm;
import model.lookup.impl.RandomAlgorithm;
import model.service.TimeService;
import model.service.distance.EuclidianDistanceService;
import model.service.factory.impl.TwoOptLandscapeFactory;
import tools.Constant;
import tools.DataSources;

import java.io.IOException;

public class Main {

	public static void main(String[] args) throws IOException {
		TimeService timeService = new TimeService();
		timeService.start();

		int executionCount = 10;

		for(int countryId=0; countryId<6; countryId++) {
			Country country = DataSources.fromParser(countryId);

			System.out.println("\t+---------------------------------------+");
			System.out.println("\t| ---\t\t" + Constant.COUNTRY_NAMES[countryId]);
			System.out.println("\t+---------------------------------------+");
/*
			{
				System.out.println("\t+-------------------------------+");
				System.out.println("\t| ---         Tabu          --- |");
				System.out.println("\t|  --    Random Algoritm    --  |");
				System.out.println("\t+-------------------------------+");

				EuclidianDistanceService distanceService = new EuclidianDistanceService(country.getCities());
				RandomAlgorithm initialCircuitBuilder = new RandomAlgorithm(new BuilderParameter(distanceService, country.getCities()));
				TwoOptLandscapeFactory landscapeFactory = new TwoOptLandscapeFactory(distanceService);

				int[] tabuSize = { 1, 2, 3, 4, 5, 10, 20, 30, 40, 50, 100, 200, 300, 400, 500, 1000, 5000, 10000 };
				int[] iterationCount = { 1000, 2000, 3000, 4000, 5000, 10000, 100000 }; // arret a ic=10000,ts=1000

				TabuParameterSet parameterSet = new TabuParameterSet(initialCircuitBuilder, landscapeFactory, tabuSize, iterationCount);

				TabuBenchmark benchmark = new TabuBenchmark(Constant.COUNTRY_NAMES[countryId], Constant.OPTIMUM[countryId], executionCount, parameterSet);
				benchmark.run();
			}
//*/
/*
			{
				System.out.println("\t+-------------------------------+");
				System.out.println("\t| ---         Tabu          --- |");
				System.out.println("\t|  --    Greedy Algoritm    --  |");
				System.out.println("\t+-------------------------------+");

				EuclidianDistanceService distanceService = new EuclidianDistanceService(country.getCities());
				GreedyAlgorithm initialCircuitBuilder = new GreedyAlgorithm(new BuilderParameter(distanceService, country.getCities()));
				TwoOptLandscapeFactory landscapeFactory = new TwoOptLandscapeFactory(distanceService);

				int[] tabuSize = { 1, 2, 3, 4, 5, 10, 20, 30, 40, 50, 100, 200, 300, 400, 500, 1000, 5000, 10000 };
				int[] iterationCount = { 1000, 2000, 3000, 4000, 5000, 10000, 100000 };

				TabuParameterSet parameterSet = new TabuParameterSet(initialCircuitBuilder, landscapeFactory, tabuSize,
						iterationCount);

				TabuBenchmark benchmark = new TabuBenchmark(Constant.COUNTRY_NAMES[countryId], Constant.OPTIMUM[countryId], executionCount,
						parameterSet);
				benchmark.run();
			}
//*/
//*
			{
				System.out.println("\t+-------------------------------+");
				System.out.println("\t| ---  Simulated Annealing  --- |");
				System.out.println("\t|  --    Random Algoritm    --  |");
				System.out.println("\t+-------------------------------+");

				EuclidianDistanceService distanceService = new EuclidianDistanceService(country.getCities());
				RandomAlgorithm initialCircuitBuilder = new RandomAlgorithm(new BuilderParameter(distanceService, country.getCities()));
				TwoOptLandscapeFactory landscapeFactory = new TwoOptLandscapeFactory(distanceService);

				double[] temperature = { 10, 20, 30, 50, 100, 200, 300, 500, 1000, 2000, 3000, 4000, 5000 };
				double[] lambda = { 0.99999 };
				double[] temperatureBreakpoint = { 1, 0.5, 0.05, 0.005, 0.0005, 0.00005, 0.000005, 0.0000005 };

				SimulatedAnnealingParameterSet parameterSet = new SimulatedAnnealingParameterSet(initialCircuitBuilder,
						landscapeFactory, temperature, lambda, temperatureBreakpoint);

				SimulatedAnnealingBenchmark benchmark = new SimulatedAnnealingBenchmark(Constant.COUNTRY_NAMES[countryId],
						Constant.OPTIMUM[countryId], executionCount, parameterSet);
				benchmark.run();
			}
//*/
//*
			{
				System.out.println("\t+-------------------------------+");
				System.out.println("\t| ---  Simulated Annealing  --- |");
				System.out.println("\t|  --    Greedy Algoritm    --  |");
				System.out.println("\t+-------------------------------+");

				EuclidianDistanceService distanceService = new EuclidianDistanceService(country.getCities());
				GreedyAlgorithm initialCircuitBuilder = new GreedyAlgorithm(new BuilderParameter(distanceService, country.getCities()));
				TwoOptLandscapeFactory landscapeFactory = new TwoOptLandscapeFactory(distanceService);

				double[] temperature = { 10, 20, 30, 50, 100, 200, 300, 500, 1000, 2000, 3000, 4000, 5000 };
				double[] lambda = { 0.99999 };
				double[] temperatureBreakpoint = { 1, 0.5, 0.05, 0.005, 0.0005, 0.00005, 0.000005, 0.0000005 };

				SimulatedAnnealingParameterSet parameterSet = new SimulatedAnnealingParameterSet(initialCircuitBuilder,
						landscapeFactory, temperature, lambda, temperatureBreakpoint);

				SimulatedAnnealingBenchmark benchmark = new SimulatedAnnealingBenchmark(Constant.COUNTRY_NAMES[countryId],
						Constant.OPTIMUM[countryId], executionCount, parameterSet);
				benchmark.run();
			}
//*/
		}
		System.out.println(timeService.tickInSecond() + " sec");
	}
}
