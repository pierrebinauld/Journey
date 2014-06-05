package benchmark.parameter.set.impl;

import benchmark.parameter.PopulationFactory;
import benchmark.parameter.impl.GeneticParameter;
import benchmark.parameter.iterator.impl.GeneticParameterIterator;
import benchmark.parameter.set.ParameterSet;
import model.service.LandscapeService;
import model.service.factory.LandscapeFactory;

public class GeneticParameterSet implements ParameterSet<GeneticParameter> {
	private LandscapeFactory         landscapeFactory;
	private PopulationFactory        initialPopulationFactory;
	private int[]                    initialPopulationSize;
	private double[]                 mutationProbability;
	private int[]                    iterationCount;

	public GeneticParameterSet(LandscapeFactory landscapeFactory, PopulationFactory initialPopulationFactory, int[] initialPopulationSize, double[] mutationProbability, int[] iterationCount) {
		this.landscapeFactory = landscapeFactory;
		this.initialPopulationFactory = initialPopulationFactory;
		this.initialPopulationSize = initialPopulationSize;
		this.mutationProbability = mutationProbability;
		this.iterationCount = iterationCount;
	}

	public int getInitialPopulationSizeLength() {
		return initialPopulationSize.length;
	}

	public int getMutationProbabilityLength() {
		return mutationProbability.length;
	}

	public int getIterationCountLength() {
		return iterationCount.length;
	}

	public int getInitialPopulationSize(int index) {
		return initialPopulationSize[index];
	}

	public double getMutationProbability(int index) {
		return mutationProbability[index];
	}

	public int getIterationCount(int index) {
		return iterationCount[index];
	}

	public PopulationFactory getInitialPopulationFactory() {
		return initialPopulationFactory;
	}

	public LandscapeService getLandscapeService() {
		return landscapeFactory.manufacture();
	}

	@Override
	public GeneticParameterIterator iterator() {
		return new GeneticParameterIterator(this);
	}
}
