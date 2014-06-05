package benchmark.parameter.iterator.impl;

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
		TabuParameter tabuParameter = new TabuParameter(
				parameterSet.getInitialCircuitBuilder().run(),
				parameterSet.getLandscapeService(),
				parameterSet.getTabuSize(iTabuSize),
				parameterSet.getIterationCount(iIterationCount));

		iTabuSize++;
		if(iTabuSize == parameterSet.getTabuSizeLength()) { // end of the 1st loop
			iIterationCount++;
			if(iIterationCount == parameterSet.getIterationCountLength()) { // end of the 2nd and last loop
				hasNext = false;
			} else {
				iTabuSize = 0;
			}
		}

		return tabuParameter;
	}

	@Override
	public void remove() {
		throw new UnsupportedOperationException();
	}
}
