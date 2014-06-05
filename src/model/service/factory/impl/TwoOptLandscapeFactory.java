package model.service.factory.impl;

import model.iterator.key.TwoCityKey;
import model.service.DistanceService;
import model.service.factory.LandscapeFactory;
import model.service.landscape.TwoOptLandscapeService;

public class TwoOptLandscapeFactory implements LandscapeFactory<TwoCityKey>{

	DistanceService distanceService;
	
	public TwoOptLandscapeFactory(DistanceService distanceService) {
		this.distanceService = distanceService;
	}

	@Override
	public TwoOptLandscapeService manufacture() {
		return new TwoOptLandscapeService(distanceService);
	}

}
