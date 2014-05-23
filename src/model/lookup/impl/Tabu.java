package model.lookup.impl;

import java.util.LinkedList;
import java.util.Queue;

import model.iterator.key.TwoCityKey;
import model.lookup.AbstractModifierAlgorithm;
import model.lookup.Circuit;
import model.service.LandscapeService;

public class Tabu<Key> extends AbstractModifierAlgorithm<Key> {

	private int iterationCount;
	private int tabuSize;
	private Queue<Circuit> tabu = new LinkedList<>();

	public Tabu(LandscapeService<Key> landscapeService, Circuit initialCircuit, int tabuSize, int iterationCount) {
		super(landscapeService, initialCircuit);
		this.tabuSize = tabuSize;
		this.iterationCount = iterationCount;
	}

	@Override
	public Circuit run() {
		Circuit result = circuit;
		int currentLength = circuit.getLength();
		int length = currentLength;
		Object currentKey = null;
		Circuit currentCircuit = null;
		boolean run = true;

		landscapeService.setCircuit(result);

		for (int i = 0; i < iterationCount; i++) {

			// Go down into landscape
			run = true;
			while (run) {
				currentKey = null;
				currentCircuit = null;

				for(Key key : landscapeService) {
					length = landscapeService.getNeighborLength(key);
					if (length < currentLength) {
						Circuit test = landscapeService.getNeighbor(key);
						if(!tabu.contains(test)) {
							currentCircuit = test;
							currentLength = length;
							currentKey = key;
						}
					}
				}

			
				if (null == currentCircuit) {
					run = false;
				} else {
					landscapeService.setCircuit(currentCircuit);
				}
			} // Arrived in a local minimum
			
			putTabuList(currentCircuit);
			if(currentCircuit.getLength() < result.getLength()) {
				result = currentCircuit;
			}
		}

		return result;
	}
	
	private void putTabuList(Circuit c) {
		tabu.add(c);
		if(tabu.size() > tabuSize) {
			tabu.poll();
		}
	}

	public int getTabuSize() {
		return tabuSize;
	}

	public void setTabuSize(int tabuSize) {
		this.tabuSize = tabuSize;
	}
}
