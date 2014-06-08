package model.lookup.impl;

import benchmark.parameter.ModifierParameter;
import model.iterator.key.Key;
import model.lookup.AbstractModifierAlgorithm;
import model.lookup.Circuit;

public class SimpleModifierAlgorithm<K extends Key> extends AbstractModifierAlgorithm<K, ModifierParameter<K>> {

	public SimpleModifierAlgorithm(ModifierParameter<K> parameter) {
		super(parameter);
	}

	@Override
	public Circuit run() {
		Circuit result = initialCircuit;
		int currentLength = initialCircuit.getLength();
		int length;
		K currentKey;
		boolean run = true;

		while(run){
			currentKey = null;
			landscapeService.setCircuit(result);
			
			for(K key : landscapeService) {
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
