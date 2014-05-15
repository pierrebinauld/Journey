import model.data.City;
import model.data.Country;
import model.data.Distance;
import model.parser.TspLibParser;
import model.repo.CountryRepo;
import model.repo.DistanceRepo;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.ListIterator;
import java.util.concurrent.LinkedBlockingQueue;

public class Main {

	public static void main(String[] args) {
		int fileId = 0;
//		Country country = fromParser(fileId);
//		testDb(country);
		Country country = fromDb(Constants.TSP_FILES[fileId]);
		testDistance(country);
	}

	private static Country fromParser(int fileId) {
		try {
			File file = new File(Constants.TSP_PATH + Constants.TSP_FILES[fileId] + ".tsp");
			return TspLibParser.parse(file);
		} catch(IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	private static Country fromDb(String countryName) {
		CountryRepo r = new CountryRepo();
		return r.findByName(countryName);
	}

	private static void testDb(Country country) {
		CountryRepo r = new CountryRepo();
		r.save(country);
	}

	private static void testDistance(Country country) {
		List<City> cities = country.getCities();
		DistanceRepo repo = new DistanceRepo();

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
}
