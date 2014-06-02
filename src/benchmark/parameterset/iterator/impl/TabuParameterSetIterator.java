package benchmark.parameterset.iterator.impl;

import benchmark.parameterset.builder.impl.TabuParameterSetBuilder;
import benchmark.parameterset.impl.TabuParameterSet;
import benchmark.parameterset.iterator.ParameterSetIterator;

public class TabuParameterSetIterator implements ParameterSetIterator<TabuParameterSet> {

	private TabuParameterSetBuilder builder;
	private int iTabuSize;
	private int iIterationCount;
	private boolean hasNext;

	public TabuParameterSetIterator(TabuParameterSetBuilder builder) {
		this.builder = builder;
		this.iTabuSize = 0;
		this.iIterationCount = 0;
		this.hasNext = true;
	}

	@Override
	public boolean hasNext() {
		return this.hasNext;
	}

	@Override
	public TabuParameterSet next() {
		TabuParameterSet tps = new TabuParameterSet(
				builder.getInitialCircuitBuilder().run(),
				builder.getLandscapeService(),
				builder.getTabuSize(iTabuSize),
				builder.getIterationCount(iIterationCount));

		iTabuSize++;
		if(iTabuSize == builder.getTabuSizeLength() - 1) { // end of the first loop
			iIterationCount++;
			if(iIterationCount == builder.getIterationCountLength() - 1) { // end of the second loop
				hasNext = false;
			} else {
				iTabuSize = 0;
			}
		}

		return tps;
	}

	@Override
	public void remove() {
		throw new UnsupportedOperationException();
	}
}
