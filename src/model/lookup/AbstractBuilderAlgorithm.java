package model.lookup;

import benchmark.parameter.BuilderParameter;
import model.data.City;
import model.service.DistanceService;

import java.util.List;

public abstract class AbstractBuilderAlgorithm<P extends BuilderParameter> extends Lookup<P> {

	protected List<City> cities;
	protected DistanceService distanceService;

	protected AbstractBuilderAlgorithm(P parameter) {
		super(parameter);
		this.distanceService = parameter.getDistanceService();
		this.cities = parameter.getCities();
	}
}
