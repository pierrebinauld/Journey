package benchmark.parameter.set.impl;

import java.util.Iterator;

import model.lookup.AbstractBuilderAlgorithm;
import model.service.LandscapeService;
import benchmark.parameter.impl.TabuParameter;
import benchmark.parameter.iterator.impl.TabuParameterIterator;
import benchmark.parameter.set.AbstractModifierParameterSet;

public class TabuParameterSet extends AbstractModifierParameterSet<TabuParameter> {
	private int[] tabuSize;
	private int[] iterationCount;

	public TabuParameterSet(AbstractBuilderAlgorithm initialCircuitBuilder, LandscapeService landscapeService, int[] tabuSize, int[] iterationCount) {
		super(initialCircuitBuilder, landscapeService);
		this.tabuSize = tabuSize;
		this.iterationCount = iterationCount;
	}

	public int getTabuSizeLength() {
		return tabuSize.length;
	}

	public int getIterationCountLength() {
		return iterationCount.length;
	}

	public int getTabuSize(int index) {
		return tabuSize[index];
	}

	public int getIterationCount(int index) {
		return iterationCount[index];
	}

	@Override
	public Iterator<TabuParameter> iterator() {
		return new TabuParameterIterator(this);
	}
}
