package model.lookup.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import tools.Tools;
import model.iterator.key.CircuitPair;
import model.lookup.Circuit;
import model.lookup.Lookup;
import model.service.DistanceService;
import model.service.LandscapeService;

public class GeneticAlgorithm<Key> implements Lookup {
	
	List<Circuit> population;
	int populationSize;
	int randomPossibilityCount;
	int iterationCount;
	double mutationProbability;
	Circuit result;

	DistanceService distanceService;
	LandscapeService<Key> landscapeService;

	// GeneticSelectionService selectionService;
	// GeneticMutationService mutationService;

	public GeneticAlgorithm(LandscapeService<Key> landscapeService, List<Circuit> initialPopulation, double mutationProbability, int iterationCount) {

		this.population = initialPopulation;

		if(0 != this.population.size()%2) {
			population.remove(population.size()-1);
		}
		
		this.populationSize = population.size();
		this.randomPossibilityCount = (populationSize * (populationSize+1)) / 2;
		this.iterationCount = iterationCount;
		this.mutationProbability = mutationProbability;
		
		this.landscapeService = landscapeService;
		this.distanceService = landscapeService.getDistanceService();
	}

	@Override
	public Circuit run() {
		result = initResultCircuit(population);
		List<Circuit> oldPopulation;
		List<Circuit> newPopulation = population;
		List<CircuitPair> selectedPopulation = null;

		for (int i = 0; i < iterationCount; i++) {

			System.out.println(i+1+"/"+iterationCount);
			oldPopulation = newPopulation;
			newPopulation = new ArrayList<>();
			selectedPopulation = new ArrayList<>();
			
			oldPopulation = ranking(oldPopulation);
			
			selectedPopulation = selection(oldPopulation);
			
			newPopulation = crossing(selectedPopulation);
			
			newPopulation = mutation(newPopulation);

			System.out.println(result.getLength());
		}
		return result;
	}

	private Circuit initResultCircuit(List<Circuit> population) {
		Circuit result = null;

		int length = -1;
		for (Circuit c : population) {
			if (-1 == length || c.getLength() < length) {
				result = c;
				length = result.getLength();
			}
		}

		return result;
	}

	/**
	 * Return a sorted list by circuit size
	 * @param population
	 * @return
	 */
	private List<Circuit> ranking(List<Circuit> population) {
		Collections.sort(population);
		return population;
	}

	private List<CircuitPair> selection(List<Circuit> sortedPopulation) {
		List<CircuitPair> selectedPopulation = new ArrayList<>();
		int halfPopulationSize = populationSize / 2;
		int selectedIndex, random;
		
		while(selectedPopulation.size() < halfPopulationSize) {
			random = Tools.random(0, randomPossibilityCount);
			selectedIndex = (int) (populationSize - Math.ceil(( -1 + Math.sqrt(1 + 8 * randomPossibilityCount - 8*( random )) ) / 2));
			Circuit first = sortedPopulation.get(selectedIndex);

			random = Tools.random(0, randomPossibilityCount);
			selectedIndex = (int) (populationSize - Math.ceil(( -1 + Math.sqrt(1 + 8 * randomPossibilityCount - 8*( random )) ) / 2));
			Circuit second = sortedPopulation.get(selectedIndex);

			selectedPopulation.add(new CircuitPair(first, second));
		}
		
		return selectedPopulation;
	}

	private List<Circuit> crossing(List<CircuitPair> selectedPopulation) {
		List<Circuit> newPopulation = new ArrayList<>();
		for(CircuitPair pair : selectedPopulation) {
			CircuitPair pairCrossed = atomicCrossing(pair);
			newPopulation.add(pairCrossed.first());
			newPopulation.add(pairCrossed.second());
		}
		return newPopulation;
	}

	private List<Circuit> mutation(List<Circuit> population) {
		int size = population.size();
		for(int i = 0; i<size; i++) {
			if(Math.random() < mutationProbability) {
				landscapeService.setCircuit(population.get(i));
				Circuit mutatedCircuit = landscapeService.getNeighbor(landscapeService.randomKey());
				population.set(i, mutatedCircuit);
			}
		}
		return population;
	}
	
	private CircuitPair atomicCrossing(CircuitPair pair) {

		LinkedList<Integer> circuit1 = pair.first().getCircuit();
		List<Integer> distances1 = pair.first().getDistances();

		LinkedList<Integer> circuit2 = pair.second().getCircuit();
		List<Integer> distances2 = pair.second().getDistances();

		Circuit c3 = new Circuit(pair.first().getCities());
		Circuit c4 = new Circuit(pair.second().getCities());

		int size = circuit1.size();
		int spanning = Tools.random(2, circuit1.size() - 2);

		int c3City;
		int lastC3City = 0;

		int c4City;
		int lastC4City = 0;

		for (int i = 0; i < spanning; i++) {
			c3City = circuit1.get(i);
			c3.add(c3City, distances1.get(c3City));
			lastC3City = c3City;

			c4City = circuit2.get(i);
			c4.add(c4City, distances2.get(c4City));
			lastC4City = c4City;
		}

		for (int i = 0; i < size; i++) {
			c3City = circuit2.get(i);
			if (!c3.getCircuit().contains(c3City)) {
				c3.add(c3City, distanceService.getDistance(c3City, lastC3City));
				lastC3City = c3City;
			}

			c4City = circuit1.get(i);
			if (!c4.getCircuit().contains(c4City)) {
				c4.add(c4City, distanceService.getDistance(c4City, lastC4City));
				lastC4City = c4City;
			}
		}

		c3.close(distanceService.getDistance(lastC3City, c3.getCircuit().get(0)));
		c4.close(distanceService.getDistance(lastC4City, c4.getCircuit().get(0)));

		if(c3.getLength() < result.getLength()) {
			result = c3;
		}

		if(c4.getLength() < result.getLength()) {
			result = c4;
		}
		
		return new CircuitPair(c3, c4);
	}
}
