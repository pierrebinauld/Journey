package benchmark.parameterset.builder.impl;

import benchmark.parameterset.builder.AbstractModifierParameterSetBuilder;
import benchmark.parameterset.impl.TabuParameterSet;
import benchmark.parameterset.iterator.impl.TabuParameterSetIterator;
import model.lookup.AbstractBuilderAlgorithm;
import model.service.LandscapeService;

public class TabuParameterSetBuilder extends AbstractModifierParameterSetBuilder<TabuParameterSet> {
	private int[] tabuSize;
	private int[] iterationCount;

	public TabuParameterSetBuilder(AbstractBuilderAlgorithm initialCircuitBuilder, LandscapeService landscapeService, int[] tabuSize, int[] iterationCount) {
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
	public TabuParameterSetIterator iterator() {
		return new TabuParameterSetIterator(this);
	}
}
