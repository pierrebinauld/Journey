import java.io.File;
import java.io.IOException;

import javax.swing.JFrame;

import model.data.Country;
import model.iterator.key.TwoCityKey;
import model.lookup.AbstractBuilderAlgorithm;
import model.lookup.Circuit;
import model.lookup.Lookup;
import model.lookup.impl.GreedyAlgorithm;
import model.lookup.impl.SimulatedAnnealing;
import model.parser.TspLibParser;
import model.service.DistanceService;
import model.service.LandscapeService;
import model.service.TimeService;
import model.service.distance.AbstractDistanceService;
import model.service.distance.EuclidianDistanceService;
import model.service.landscape.TwoOptLandscapeService;
import view.Window;

public class Main {

	public static void main(String[] args) throws IOException {
		TimeService time = new TimeService();

		File file = new File("/home/pierre/git/Journey/data/wi29.tsp");
		double optimum = 27603;
//		 File file = new File("/home/pierre/git/Journey/data/ch71009.tsp");
//		 File file = new File("/home/pierre/git/Journey/data/ja9847.tsp");
//		 double optimum = 491924;
//		 File file = new File("/home/pierre/git/Journey/data/ca4663.tsp");
//		 double optimum = 1290319;
//		 File file = new File("/home/pierre/git/Journey/data/zi929.tsp");
//		 double optimum = 95345;

		TspLibParser parser = new TspLibParser();
		
		Country country = parser.parse(file);
		System.out.println(country.getDimension());
		System.out.println(country.getCities());

		System.out.println("Name: " + country.getName());
		System.out.println("Dim: " + country.getDimension());
		System.out.println();
		
		DistanceService distanceService = new EuclidianDistanceService(country.getCities());
		
		GreedyAlgorithm greedy = new GreedyAlgorithm(distanceService, country.getCities());
		Circuit initialCircuit = greedy.run();

		LandscapeService<TwoCityKey> landscapeService = new TwoOptLandscapeService(distanceService);
		
		SimulatedAnnealing<TwoCityKey> algo = new SimulatedAnnealing<>(landscapeService, initialCircuit, 300, 0.99999, 0.005);
		Circuit result = algo.run();
		
		System.out.println();
		System.out.println(result.getLength() + " - " + distanceService.checkLength(result));
		System.out.println(result.getCircuit());
		System.out.println(result.getDistances());
		
//		System.out.println("Simulated Annealing Percentage: " + (((float)result.getLength()-optimum)/optimum * 100) + "%");
	
		
	}
}
