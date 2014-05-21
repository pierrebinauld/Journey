package model.service.distance.decorator;

import model.lookup.Circuit;
import model.service.DistanceService;

public class DbStorageDistanceService extends DecoratorDistanceService {

	public DbStorageDistanceService(DistanceService distanceService) {
		super(distanceService);
		// TODO Auto-generated constructor stub
	}

	@Override
	public int getDistance(int indexCity1, int indexCity2) {
		// TODO Auto-generated method stub
		return distanceService.getDistance(indexCity1, indexCity2);
	}

	@Override
	public boolean checkLength(Circuit circuit) {
		return distanceService.checkLength(circuit);
	}
}
