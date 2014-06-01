package model.service;

import model.lookup.Circuit;

public interface DistanceService {

	int getDistance(int cityIndex1, int cityIndex2);
	
	boolean checkLength(Circuit circuit);
}
