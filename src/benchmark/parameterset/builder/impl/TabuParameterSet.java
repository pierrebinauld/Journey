package benchmark.parameterset.builder.impl;

import java.util.Iterator;

import model.lookup.AbstractBuilderAlgorithm;
import model.service.LandscapeService;
import benchmark.parameterset.builder.AbstractModifierParameterSet;
import benchmark.parameterset.impl.TabuParameter;
import benchmark.parameterset.iterator.impl.TabuParameterIterator;

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
