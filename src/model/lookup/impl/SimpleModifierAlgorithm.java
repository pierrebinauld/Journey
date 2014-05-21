package model.lookup.impl;

import model.iterator.LandscapeIterator;
import model.lookup.AbstractModifierAlgorithm;
import model.lookup.Circuit;

public class SimpleModifierAlgorithm extends AbstractModifierAlgorithm {

	public SimpleModifierAlgorithm(LandscapeIterator<? extends Object> landscapeIterator, Circuit initialCircuit) {
		super(landscapeIterator, initialCircuit);
	}

	@Override
	public Circuit run() {

		boolean run = true;
		int initialLength = circuit.getLength();
		int length = circuit.getLength();
		int calculatedLength;

		Object key = null;

		Circuit currentCircuit = circuit;

		while (run) {
			landscapeIterator.setCircuit(currentCircuit);
			initialLength = length;
			
			for (;landscapeIterator.hasNext();) {
				calculatedLength = landscapeIterator.next();
				if (calculatedLength <= length) {
					length = calculatedLength;
					key = landscapeIterator.getCurrentKey();
				}
			}
			currentCircuit = landscapeIterator.getNeighbor(key);
			if (length == initialLength) {
				run = false;
			}
			System.out.println(currentCircuit);
		}

		return landscapeIterator.getNeighbor(key);
	}
}
