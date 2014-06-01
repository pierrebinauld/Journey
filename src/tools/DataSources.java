package tools;

import model.data.Country;
import model.parser.TspLibParser;
import model.repo.CountryRepo;
import persistence.DbConnection;

import java.io.File;
import java.io.IOException;

public class DataSources {
	public static Country fromParser(int fileId) {
		try {
			File file = new File(Constant.TSP_PATH + Constant.TSP_FILES[fileId] + ".tsp");
			return (new TspLibParser()).parse(file);
		} catch(IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static Country fromDb(DbConnection db, String countryName) {
		CountryRepo r = new CountryRepo(db);
		return r.findByName(countryName);
	}
}
