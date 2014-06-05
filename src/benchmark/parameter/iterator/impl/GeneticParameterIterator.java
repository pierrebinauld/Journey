package benchmark.parameter.iterator.impl;

import benchmark.parameter.impl.GeneticParameter;
import benchmark.parameter.iterator.LookupParameterIterator;
import benchmark.parameter.set.impl.GeneticParameterSet;

public class GeneticParameterIterator implements LookupParameterIterator<GeneticParameter> {

	private GeneticParameterSet parameterSet;
	private int iPopulationSize;
	private int iMutationProbability;
	private int iIterationCount;
	private boolean hasNext;

	public GeneticParameterIterator(GeneticParameterSet parameterSet) {
		this.parameterSet = parameterSet;
		this.iMutationProbability = 0;
		this.iIterationCount = 0;
		this.hasNext = true;
	}

	@Override
	public boolean hasNext() {
		return this.hasNext;
	}

	@Override
	public GeneticParameter next() {
		GeneticParameter geneticParameter = new GeneticParameter(
				parameterSet.getLandscapeService(),
				parameterSet.getInitialPopulationFactory(),
				parameterSet.getInitialPopulationSize(iPopulationSize),
				parameterSet.getMutationProbability(iMutationProbability),
				parameterSet.getIterationCount(iIterationCount));

		iPopulationSize++;
		if(iPopulationSize == parameterSet.getInitialPopulationSizeLength()) { // end of the 1st loop
			iMutationProbability++;
			if(iMutationProbability == parameterSet.getMutationProbabilityLength()) { // end of the 2nd loop
				iIterationCount++;
				if(iIterationCount == parameterSet.getIterationCountLength()) { // end of the 3rd and last loop
					hasNext = false;
				} else {
					iPopulationSize = 0;
					iMutationProbability = 0;
				}
			} else {
				iPopulationSize = 0;
			}
		}

		return geneticParameter;
	}

	@Override
	public void remove() {
		throw new UnsupportedOperationException();
	}
}
