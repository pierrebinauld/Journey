package benchmark.parameter.impl;

import benchmark.parameter.LookupParameter;
import benchmark.parameter.PopulationFactory;
import model.service.LandscapeService;

import java.util.ArrayList;

public class GeneticParameter implements LookupParameter {
	private LandscapeService         landscapeService;
	private PopulationFactory        initialPopulationFactory;
	private int                      initialPopulationSize;
	private double                   mutationProbability;
	private int                      iterationCount;

	public GeneticParameter(LandscapeService landscapeService, PopulationFactory initialPopulationFactory, int initialPopulationSize, double mutationProbability, int iterationCount) {
		this.landscapeService = landscapeService;
		this.initialPopulationFactory = initialPopulationFactory;
		this.initialPopulationSize = initialPopulationSize;
		this.mutationProbability = mutationProbability;
		this.iterationCount = iterationCount;
	}

	@Override
	public ArrayList<String> titlesToStringArrayList() {
		ArrayList<String> result = new ArrayList<>();

		result.add("Nb itérations");
		result.add("Probabilité de mutation");
		result.add("Taille de la population");
		
		return result;
	}
	
	@Override
	public ArrayList<String> toStringArrayList() {
		ArrayList<String> result = new ArrayList<>();

		result.add(Integer.toString(getIterationCount()));
		result.add(Double.toString(getMutationProbability()));
		result.add(Integer.toString(getInitialPopulationSize()));
		
		return result;
	}
	public PopulationFactory getInitialPopulationFactory() {
		return initialPopulationFactory;
	}

	public int getInitialPopulationSize() {
		return initialPopulationSize;
	}

	public LandscapeService getLandscapeService() {
		return landscapeService;
	}

	public double getMutationProbability() {
		return mutationProbability;
	}

	public int getIterationCount() {
		return iterationCount;
	}
}
