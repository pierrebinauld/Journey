package model.service.landscape;

import model.lookup.Circuit;
import model.service.DistanceService;
import model.service.LandscapeService;

import java.util.List;

public abstract class AbstractLandscapeService<Key> implements LandscapeService<Key> {

	protected DistanceService distanceService;
	protected Circuit circuit;
	protected List<Integer> distances;
	protected int citiesSize;
	
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
		this.citiesSize = this.circuit.getCities().size();
	}
	
	@Override
	public DistanceService getDistanceService() {
		return distanceService;
	}
}
