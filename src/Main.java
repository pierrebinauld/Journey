import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.ListIterator;

import model.data.City;
import model.data.Country;
import model.data.Distance;
import model.iterator.key.TwoCityKey;
import model.lookup.AbstractBuilderAlgorithm;
import model.lookup.Circuit;
import model.lookup.Lookup;
import model.lookup.impl.GreedyAlgorithm;
import model.lookup.impl.SimpleBuilderAlgorithm;
import model.lookup.impl.SimpleModifierAlgorithm;
import model.lookup.impl.Tabu;
import model.parser.TspLibParser;
import model.repo.CountryRepo;
import model.repo.DistanceRepo;
import model.service.DistanceService;
import model.service.TimeService;
import model.service.distance.AbstractDistanceService;
import model.service.distance.ManhattanDistanceService;
import model.service.distance.decorator.LocalStorageDistanceService;
import model.service.landscape.TwoOptLandscapeService;
import persistence.DbConnection;
import utils.Constant;

public class Main {

	private static Country fromParser(int fileId) {
		try {
			File file = new File(Constant.TSP_PATH + Constant.TSP_FILES[fileId] + ".tsp");
			return TspLibParser.parse(file);
		} catch(IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	private static Country fromDb(DbConnection db, String countryName) {
		CountryRepo r = new CountryRepo(db);
		return r.findByName(countryName);
	}

	private static void testDb(DbConnection db, Country country) {
		CountryRepo r = new CountryRepo(db);
		r.save(country);
	}

	private static void testDistance(DbConnection db, Country country) {
		List<City> cities = country.getCities();
		DistanceRepo repo = new DistanceRepo(db);

		ListIterator<City> it1 = cities.listIterator(0);
		ListIterator<City> it2 = null;
		City city1 = null;
//		City city2 = null;

		while(it1.hasNext()) {
			city1 = it1.next();
			it2 = cities.listIterator(it1.nextIndex());
			while(it2.hasNext()) {
				repo.save(Distance.Euclidian(country.getId(), city1, it2.next()));
			}
		}
	}
	
	public static void main(String[] args) throws IOException {
		TimeService time = new TimeService();

		File file = new File("/home/pierre/git/Journey/data/wi29.tsp");
		// File file = new File("/home/pierre/git/Journey/data/ch71009.tsp");
		// File file = new File("/home/pierre/git/Journey/data/ja9847.tsp");

		Country country = TspLibParser.parse(file);

		System.out.println("Name: " + country.getName());
		System.out.println("Dim: " + country.getDimension());
		System.out.println();

		AbstractDistanceService distanceService = new ManhattanDistanceService(country.getCities());
		// AbstractDistanceService distanceService = new
		// EuclidianDistanceService(country.getCities());
		DistanceService decoratedDistanceService = new LocalStorageDistanceService(distanceService);

		AbstractBuilderAlgorithm g = new GreedyAlgorithm(decoratedDistanceService, country.getCities(), 10);
//		AbstractBuilderAlgorithm g = new SimpleBuilderAlgorithm(decoratedDistanceService, country.getCities());

		time.start();
		Circuit c = g.run();
		System.out.println("Time: " + time.tickInSecond() + "s");

		System.out.println("size: " + c.getCities().size() + " - " + c.getCircuit().size());
		System.out.println("length: " + c.getLength());
		System.out.println("Check length: " + (decoratedDistanceService.checkLength(c)));
		System.out.println(c);

		TwoOptLandscapeService landscapeService = new TwoOptLandscapeService(decoratedDistanceService, c);

		time.start();
		int calculatedLength = landscapeService.getNeighborLength(new TwoCityKey(2, 28));
		Circuit buildCircuit = landscapeService.getNeighbor(new TwoCityKey(2, 28));
		System.out.println(buildCircuit);
		System.out.println();
		System.out.println(c.getDistances());
		System.out.println(buildCircuit.getDistances());
		System.out.println();
		System.out.println("time: " + time.tickInSecond());
		System.out.println("check length: " + decoratedDistanceService.checkLength(buildCircuit));
		System.out.println("build length: \t\t" + buildCircuit.getLength());
		System.out.println("calculated length: \t" + calculatedLength);
		System.out.println("check 2:  " + (calculatedLength == buildCircuit.getLength()));
		System.out.println();

		Lookup simpleAlgo = new SimpleModifierAlgorithm<TwoCityKey>(landscapeService, c);
		Lookup tabu = new Tabu(landscapeService, c, 300, 1000);

		time.start();
		Circuit result = simpleAlgo.run();
		System.out.println("Simple Time: " + time.tickInSecond());
		System.out.println("Simple Length: " + result.getLength()  + " - " + 27603);
		System.out.println("Simple Percentage: " + (((float)result.getLength()-27603.f)/27603.f * 100) + "%");
		System.out.println("Check lengh: " + decoratedDistanceService.checkLength(result));
		System.out.println(result);
		
		time.start();
		result = tabu.run();
		System.out.println("Tabu Time: " + time.tickInSecond());
		System.out.println("Tabu lengh: " + result.getLength() + " - " + 27603);
		System.out.println("Simple Percentage: " + (((float)result.getLength()-27603.f)/27603.f * 100) + "%");
		System.out.println("Check lengh: " + decoratedDistanceService.checkLength(result));
		System.out.println(result);

	}
}
