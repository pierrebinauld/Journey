package model.service;

import java.util.HashSet;
import java.util.List;

import model.lookup.Circuit;

public interface DistanceService {

	int getDistance(int cityIndex1, int cityIndex2);
	
	boolean checkLength(Circuit circuit);
}
