import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;

import model.data.Country;
import model.iterator.key.TwoCityKey;
import model.lookup.AbstractBuilderAlgorithm;
import model.lookup.Circuit;
import model.lookup.Lookup;
import model.lookup.impl.GeneticAlgorithm;
import model.lookup.impl.GreedyAlgorithm;
import model.lookup.impl.RandomAlgorithm;
import model.parser.TspLibParser;
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

//		AbstractDistanceService distanceService = new ManhattanDistanceService(country.getCities());
		AbstractDistanceService distanceService = new EuclidianDistanceService(country.getCities());

		AbstractBuilderAlgorithm g = new GreedyAlgorithm(distanceService, country.getCities());
//		AbstractBuilderAlgorithm g = new SimpleBuilderAlgorithm(distanceService, country.getCities());

		time.start();
		Circuit c = g.run();
		System.out.println("Time: " + time.tickInSecond() + "s");

		System.out.println("size: " + c.getCities().size() + " - " + c.getCircuit().size());
		System.out.println("length: " + c.getLength());
		System.out.println("Check length: " + (distanceService.checkLength(c)));
		System.out.println(c); 

//		JFrame initWindow = new Window(c);
		
		TwoOptLandscapeService landscapeService = new TwoOptLandscapeService(distanceService);
		landscapeService.setCircuit(c);

		time.start();
		int calculatedLength = landscapeService.getNeighborLength(new TwoCityKey(2, 4));
		Circuit buildCircuit = landscapeService.getNeighbor(new TwoCityKey(2, 4));
		System.out.println(buildCircuit);
		System.out.println();
		System.out.println(c.getDistances());
		System.out.println(buildCircuit.getDistances());
		System.out.println();
		System.out.println("time: " + time.tickInSecond());
		System.out.println("check length: " + distanceService.checkLength(buildCircuit));
		System.out.println("build length: \t\t" + buildCircuit.getLength());
		System.out.println("calculated length: \t" + calculatedLength);
		System.out.println("check 2:  " + (calculatedLength == buildCircuit.getLength()));
		System.out.println();

		for(int i = 0; i<10; i++) {
			JFrame resultWindow = new Window(g.run());
		}
		
//		Circuit result = null;
//
//		time.start();
//		Lookup sa = new SimulatedAnnealing<TwoCityKey>(landscapeService, c, 300, 0.99999, 0.005);
//		result = sa.run();
//		System.out.println("Simulated Annealing Time: " + time.tickInSecond());
//		System.out.println("Simulated Annealing lengh: " + c.getLength());
//		System.out.println("Simulated Annealing Percentage: " + (((float)c.getLength()-optimum)/optimum * 100) + "%");
//		System.out.println("Check lengh: " + distanceService.checkLength(c));
//		System.out.println(c);
//		System.out.println(result);
//		System.out.println();

		Circuit result = null;
		

//		Lookup random = new RandomAlgorithm(distanceService, country.getCities());
//		List<Circuit> population = new ArrayList<>();
//		for(int i = 0; i <99; i++) {
//			Circuit r = random.run();
//			population.add(r);
//			System.out.println(r);
//			System.out.println("Check lengh: " + distanceService.checkLength(r));
////			JFrame resultWindow = new Window(r);
//		}
//		population.add(c);
//
//		System.out.println();
//
//		time.start();
//		Lookup ga = new GeneticAlgorithm<TwoCityKey>(landscapeService, population, 0.1, 100);
//		result = ga.run();
//		System.out.println("GA Time: " + time.tickInSecond());
//		System.out.println("GA lengh: " + result.getLength());
//		System.out.println("GA Percentage: " + (((float)result.getLength()-optimum)/optimum * 100) + "%");
//		System.out.println("Check lengh: " + distanceService.checkLength(c));
//		System.out.println(c);
//		System.out.println(result);
//		System.out.println();
//		
//		JFrame resultWindow = new Window(result);
		
		
//		int n = 5;
//		int nn = n * (1+n) / 2;
//		System.out.println("nn: "+nn);
//		System.out.println();
//		
//		for(int m = n; m > 0; m --) {
//			int x = (n * (1+n) / 2) - (m * (1+m) / 2);
//			System.out.println("\tm: "+m+"\tx: "+x);
//		}
//		System.out.println();
//		
//		for(int x = 0; x < nn; x ++) {
//			double y = n - Math.ceil(( -1 + Math.sqrt(1 + 4 * n * (1+n) - 8*( x )) ) / 2);
//			System.out.println("\tx: "+x+"\ty: "+y);
//			y = n - Math.ceil(( -1 + Math.sqrt(1 + 8 * nn - 8*( x )) ) / 2);
//			System.out.println("\tx: "+x+"\ty: "+y);
//		}
		
	}
}
