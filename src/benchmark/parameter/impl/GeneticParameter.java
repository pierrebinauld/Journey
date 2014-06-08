package benchmark.parameter.impl;

import benchmark.PopulationFactory;
import benchmark.parameter.LookupParameter;
import model.iterator.key.Key;
import model.service.LandscapeService;

import java.util.ArrayList;

public class GeneticParameter<K extends Key> implements LookupParameter {
	private LandscapeService<K>      landscapeService;
	private PopulationFactory        initialPopulationFactory;
	private int                      initialPopulationSize;
	private double                   mutationProbability;
	private int                      iterationCount;

	public GeneticParameter(LandscapeService<K> landscapeService, PopulationFactory initialPopulationFactory, int initialPopulationSize, double mutationProbability, int iterationCount) {
		this.landscapeService = landscapeService;
		this.initialPopulationFactory = initialPopulationFactory;
		this.initialPopulationSize = initialPopulationSize;
		this.mutationProbability = mutationProbability;
		this.iterationCount = iterationCount;
	}

	@Override
	public ArrayList<String> titlesToStringArrayList() {
		ArrayList<String> result = new ArrayList<>();

		result.add("Nombre d'itérations");
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

	public LandscapeService<K> getLandscapeService() {
		return landscapeService;
	}

	public double getMutationProbability() {
		return mutationProbability;
	}

	public int getIterationCount() {
		return iterationCount;
	}
}
