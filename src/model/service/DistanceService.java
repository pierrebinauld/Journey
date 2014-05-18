package model.service;

import model.lookup.Circuit;

public interface DistanceService {

	boolean checkLength(Circuit circuit);

	int getDistance(int indexCity1, int indexCity2);
}
