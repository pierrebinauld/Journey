package model.lookup.impl;

import model.lookup.AbstractModifierAlgorithm;
import model.lookup.Circuit;
import model.service.LandscapeService;

public class SimpleModifierAlgorithm<Key> extends AbstractModifierAlgorithm<Key> {

	public SimpleModifierAlgorithm(LandscapeService<Key> landscapeService, Circuit initialCircuit) {
		super(landscapeService, initialCircuit);
	}

	@Override
	public Circuit run() {
		Circuit result = initialCircuit;
		int currentLength = initialCircuit.getLength();
		int length = currentLength;
		Key currentKey = null;
		boolean run = true;

		while(run){
			currentKey = null;
			landscapeService.setCircuit(result);
			
			for(Key key : landscapeService) {
				length = landscapeService.getNeighborLength(key);
				if (length < currentLength) {
					currentLength = length;
					currentKey = key;
				}
			}
			if (null != currentKey) {
				result = landscapeService.getNeighbor(currentKey);
			} else {
				run = false;
			}
		}

		return result;
	}
}
