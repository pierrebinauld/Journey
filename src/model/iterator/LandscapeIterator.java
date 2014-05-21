package model.iterator;

import java.util.Iterator;

import model.lookup.Circuit;

public interface LandscapeIterator<Key> extends Iterator<Integer> {
	
	void reset();
	
	void setCircuit(Circuit circuit);

	Key getCurrentKey();
	
	Circuit getNeighbor(Object key);
}
