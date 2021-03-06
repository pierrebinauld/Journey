package benchmark.parameter.set.impl;

import benchmark.parameter.impl.TabuParameter;
import benchmark.parameter.iterator.impl.TabuParameterIterator;
import benchmark.parameter.set.AbstractModifierParameterSet;
import model.lookup.AbstractBuilderAlgorithm;
import model.service.factory.LandscapeFactory;

public class TabuParameterSet extends AbstractModifierParameterSet<TabuParameter> {
	private int[] tabuSize;
	private int[] iterationCount;

	public TabuParameterSet(AbstractBuilderAlgorithm initialCircuitBuilder, LandscapeFactory landscapeFactory, int[] tabuSize, int[] iterationCount) {
		super(initialCircuitBuilder, landscapeFactory);
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
	public TabuParameterIterator iterator() {
		return new TabuParameterIterator(this);
	}
}
