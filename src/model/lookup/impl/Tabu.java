package model.lookup.impl;

import model.lookup.AbstractModifierAlgorithm;
import model.lookup.Circuit;
import model.service.LandscapeService;

import java.util.LinkedList;
import java.util.Queue;

import java.util.LinkedList;
import java.util.Queue;

public class Tabu<Key> extends AbstractModifierAlgorithm<Key> {

	private int iterationCount;
	private int tabuSize;
	private Queue<Integer> tabu = new LinkedList<>();

	public Tabu(LandscapeService<Key> landscapeService, Circuit initialCircuit, int tabuSize, int iterationCount) {
		super(landscapeService, initialCircuit);
		this.tabuSize = tabuSize;
		this.iterationCount = iterationCount;
	}

	@Override
	public Circuit run() {
		Circuit result = initialCircuit;

		int count = 0;
		int testedLength;
		int currentLength = -1;
		Circuit testedCircuit = null;
		Circuit currentCircuit = null;
		Circuit currentIterationCircuit = initialCircuit;
		landscapeService.setCircuit(initialCircuit);

		while(count < iterationCount) {
			landscapeService.setCircuit(currentIterationCircuit);
			
			for (Key key : landscapeService) {
				testedLength = landscapeService.getNeighborLength(key);
				if (testedLength <= currentLength || -1 == currentLength) {
					testedCircuit = landscapeService.getNeighbor(key);

					if (!tabu.contains(testedCircuit.hashCode())) {
						currentCircuit = testedCircuit;
						currentLength = testedLength;
					}
				}
			}
			
			if(currentIterationCircuit.getLength() <= currentCircuit.getLength()) {
				if(currentIterationCircuit.getLength() < result.getLength()) {
					result = currentCircuit;
				}
				putTabuList(currentIterationCircuit.hashCode());
				count++;
			}
			currentLength = -1;
			currentIterationCircuit = currentCircuit;
		}

		return result;
	}

	private void putTabuList(Integer circuitHasCode) {
		tabu.add(circuitHasCode);
		if (tabu.size() > tabuSize) {
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
