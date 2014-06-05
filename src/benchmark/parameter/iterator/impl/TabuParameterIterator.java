package benchmark.parameter.iterator.impl;

import java.util.ArrayList;

import benchmark.parameter.impl.TabuParameter;
import benchmark.parameter.iterator.LookupParameterIterator;
import benchmark.parameter.set.impl.TabuParameterSet;

public class TabuParameterIterator implements LookupParameterIterator<TabuParameter> {

	private TabuParameterSet parameterSet;
	private int iTabuSize;
	private int iIterationCount;
	private boolean hasNext;

	public TabuParameterIterator(TabuParameterSet parameterSet) {
		this.parameterSet = parameterSet;
		this.iTabuSize = 0;
		this.iIterationCount = 0;
		this.hasNext = true;
	}

	@Override
	public boolean hasNext() {
		return this.hasNext;
	}

	@Override
	public TabuParameter next() {
		TabuParameter tps = new TabuParameter(
				parameterSet.getInitialCircuitBuilder().run(),
				parameterSet.getLandscapeService(),
				parameterSet.getTabuSize(iTabuSize),
				parameterSet.getIterationCount(iIterationCount));

		iTabuSize++;
		if(iTabuSize == parameterSet.getTabuSizeLength()) { // end of the first loop
			iIterationCount++;
			if(iIterationCount == parameterSet.getIterationCountLength()) { // end of the second loop
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
