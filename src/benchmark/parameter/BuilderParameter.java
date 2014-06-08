package benchmark.parameter;

import model.data.City;
import model.service.DistanceService;

import java.util.ArrayList;
import java.util.List;

public class BuilderParameter implements LookupParameter {
	protected DistanceService distanceService;
	protected List<City> cities;

	public BuilderParameter(DistanceService distanceService, List<City> cities) {
		this.distanceService = distanceService;
		this.cities = cities;
	}

	public DistanceService getDistanceService() {
		return distanceService;
	}

	public List<City> getCities() {
		return cities;
	}

	@Override
	public ArrayList<String> titlesToStringArrayList() {
		ArrayList<String> result = new ArrayList<>();
//		result.add();
		return result;
	}

	@Override
	public ArrayList<String> toStringArrayList() {
		ArrayList<String> result = new ArrayList<>();
//		result.add();
		return result;
	}
}
