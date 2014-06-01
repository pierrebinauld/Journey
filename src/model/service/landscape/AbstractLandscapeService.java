package model.service.landscape;

import java.util.List;

import model.lookup.Circuit;
import model.service.DistanceService;
import model.service.LandscapeService;

public abstract class AbstractLandscapeService<Key> implements LandscapeService<Key> {

	protected DistanceService distanceService;
	protected Circuit circuit;
	protected List<Integer> distances;
	
	public AbstractLandscapeService(DistanceService distanceService) {
		this.distanceService = distanceService;
	}

	public AbstractLandscapeService(DistanceService distanceService, Circuit circuit) {
		this(distanceService);
		this.setCircuit(circuit);
	}


	@Override
	public void setCircuit(Circuit circuit) {
		this.circuit = circuit;
		this.distances = circuit.getDistances();
	}
	
}
