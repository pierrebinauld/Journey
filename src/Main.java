import model.data.City;
import model.data.Country;
import model.data.Distance;
import model.parser.TspLibParser;
import model.repo.CountryRepo;
import model.repo.DistanceRepo;
import persistence.DbConnection;
import utils.Constant;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.ListIterator;

public class Main {

	public static void main(String[] args) {
		int fileId = 5;
		Country country = fromParser(fileId);
		DbConnection db = new DbConnection();
		testDb(db, country);
//		Country country = fromDb(db, utils.Constants.TSP_FILES[fileId]);
//		testDistance(db, country);
		db.close();
	}

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
}
