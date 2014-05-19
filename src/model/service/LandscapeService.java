package model.service;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import model.lookup.Circuit;

public interface LandscapeService {
	
	Circuit getNeighbor(Circuit circuit, int indexCity1, int indexCity2);

	Collection<Circuit> getNeighborhood(Circuit circuit);
	
	boolean checkLength(Circuit circuit);
}
