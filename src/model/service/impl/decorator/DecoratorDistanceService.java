package model.service.impl.decorator;

import model.service.DistanceService;

public abstract class DecoratorDistanceService implements DistanceService {

	protected DistanceService distanceService;

	public DecoratorDistanceService(DistanceService distanceService) {
		this.distanceService = distanceService;
	}
	
	
}
