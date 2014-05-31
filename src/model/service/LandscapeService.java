package model.service;

import model.lookup.Circuit;

public interface LandscapeService<Key> extends Iterable<Key> {
	
	void setCircuit(Circuit circuit);
	
	Key randomKey();
	
	Circuit getNeighbor(Key key);

	int getNeighborLength(Key key);

	DistanceService getDistanceService();
}
