package model.lookup.impl;

import benchmark.parameter.impl.TabuParameter;
import model.iterator.key.Key;
import model.lookup.AbstractModifierAlgorithm;
import model.lookup.Circuit;

import java.util.LinkedList;
import java.util.Queue;

public class TabuAlgorithm<K extends Key> extends AbstractModifierAlgorithm<K, TabuParameter<K>> {

	private int iterationCount;
	private int tabuSize;
	private Queue<Integer> tabu = new LinkedList<>();

	public TabuAlgorithm(TabuParameter<K> parameter) {
		super(parameter);
		this.tabuSize = parameter.getTabuSize();
		this.iterationCount = parameter.getIterationCount();
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
			
			for (K key : landscapeService) {
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

	private void putTabuList(Integer circuitHashCode) {
		tabu.add(circuitHashCode);
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
