import model.data.Country;
import model.iterator.key.TwoCityKey;
import model.lookup.AbstractBuilderAlgorithm;
import model.lookup.Circuit;
import model.lookup.Lookup;
import model.lookup.impl.GreedyAlgorithm;
import model.lookup.impl.SimpleModifierAlgorithm;
import model.lookup.impl.Tabu;
import model.parser.TspLibParser;
import model.service.TimeService;
import model.service.distance.AbstractDistanceService;
import model.service.distance.EuclidianDistanceService;
import model.service.landscape.TwoOptLandscapeService;
import tools.Constant;
import tools.DataSources;
import view.Window;

import javax.swing.*;
import java.io.File;
import java.io.IOException;

public class Main {

	public static void main(String[] args) throws IOException {
		TimeService time = new TimeService();
		
		Country country = DataSources.fromParser(1);
		System.out.println(country.getDimension());
		System.out.println(country.getCities());

		System.out.println("Name: " + country.getName());
		System.out.println("Dim: " + country.getDimension());
		System.out.println();

//		AbstractDistanceService distanceService = new ManhattanDistanceService(country.getCities());
		AbstractDistanceService distanceService = new EuclidianDistanceService(country.getCities());

		AbstractBuilderAlgorithm g = new GreedyAlgorithm(distanceService, country.getCities());
//		AbstractBuilderAlgorithm g = new SimpleBuilderAlgorithm(decoratedDistanceService, country.getCities());

		time.start();
		Circuit c = g.run();
		System.out.println("Time: " + time.tickInSecond() + "s");

		System.out.println("size: " + c.getCities().size() + " - " + c.getCircuit().size());
		System.out.println("length: " + c.getLength());
		System.out.println("Check length: " + (distanceService.checkLength(c)));
		System.out.println(c); 

		JFrame initWindow = new Window(c);
		
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

		
		Circuit result = null;
		
		for(int i = 0; i<1; i++) {
			Lookup tabu = new Tabu<TwoCityKey>(landscapeService, c, (i+1) * 30, 100);
			time.start();
			result = tabu.run();
			System.out.println(i);
			System.out.println("Tabu Time: " + time.tickInSecond());
			System.out.println("Tabu lengh: " + result.getLength() + " - " + 27603);
			System.out.println("Tabu Percentage: " + (((float)result.getLength()-27603.f)/27603.f * 100) + "%");
			System.out.println("Check lengh: " + distanceService.checkLength(result));
			System.out.println(c);
			System.out.println(result);
			System.out.println();
		}
		

		JFrame resultWindow = new Window(result);

		Lookup simpleAlgo = new SimpleModifierAlgorithm<TwoCityKey>(landscapeService, c);
		time.start();
		result = simpleAlgo.run();
		System.out.println("Simple Time: " + time.tickInSecond());
		System.out.println("Simple Length: " + result.getLength()  + " - " + 27603);
		System.out.println("Simple Percentage: " + (((float)result.getLength()-27603.f)/27603.f * 100) + "%");
		System.out.println("Check lengh: " + distanceService.checkLength(result));
		System.out.println(c);
		System.out.println(result);
		
	}
}
