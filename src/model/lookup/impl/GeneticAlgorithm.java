package model.lookup.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JFrame;

import model.data.Country;
import model.iterator.key.CircuitPair;
import model.iterator.key.Key;
import model.iterator.key.TwoCityKey;
import model.lookup.Circuit;
import model.lookup.Lookup;
import model.service.DistanceService;
import model.service.LandscapeService;
import model.service.distance.EuclidianDistanceService;
import model.service.landscape.TwoOptLandscapeService;
import tools.DataSources;
import tools.Tools;
import view.Window;
import benchmark.PopulationFactory;
import benchmark.parameter.BuilderParameter;
import benchmark.parameter.impl.GeneticParameter;

public class GeneticAlgorithm<K extends Key> extends Lookup<GeneticParameter> {

	List<Circuit> population;
	int randomPossibilityCount;
	int iterationCount;
	double mutationProbability;
	Circuit result;

	DistanceService distanceService;
	LandscapeService<K> landscapeService;

	// GeneticSelectionService selectionService;
	// GeneticMutationService mutationService;

	public GeneticAlgorithm(GeneticParameter<K> parameter) {
		super(parameter);

		this.population = parameter.getInitialPopulationFactory().manufacture(parameter.getInitialPopulationSize());

		if (0 != this.population.size() % 2) {
			population.remove(population.size() - 1);
		}

		this.randomPossibilityCount = (population.size() * (population.size() + 1)) / 2;
		this.iterationCount = parameter.getIterationCount();
		this.mutationProbability = parameter.getMutationProbability();

		this.landscapeService = parameter.getLandscapeService();
		this.distanceService = landscapeService.getDistanceService();
	}

	@Override
	public Circuit run() {
		result = initResultCircuit(population);
		List<Circuit> oldPopulation;
		List<Circuit> newPopulation = population;
		List<CircuitPair> selectedPopulation = null;

		for (int i = 0; i < iterationCount; i++) {

			oldPopulation = newPopulation;

			oldPopulation = ranking(oldPopulation);

			selectedPopulation = selection(oldPopulation);

			newPopulation = crossing(selectedPopulation);

			newPopulation = mutation(newPopulation);

			if (i % 100 == 0) {
				System.out.println(i + "/" + iterationCount + "\t" + result.getLength());
			}
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
	 * 
	 * @param population
	 * @return
	 */
	private List<Circuit> ranking(List<Circuit> population) {
		Collections.sort(population);
		return population;
	}

	private List<CircuitPair> selection(List<Circuit> sortedPopulation) {
		List<CircuitPair> selectedPopulation = new ArrayList<>();
		int halfPopulationSize = population.size() / 2;
		int selectedIndex, random;

		while (selectedPopulation.size() < halfPopulationSize) {
			random = Tools.random(0, randomPossibilityCount);
			selectedIndex = (int) (population.size() - Math
					.ceil((-1 + Math.sqrt(1 + 8 * randomPossibilityCount - 8 * (random))) / 2));
			Circuit first = sortedPopulation.get(selectedIndex);

			random = Tools.random(0, randomPossibilityCount);
			selectedIndex = (int) (population.size() - Math
					.ceil((-1 + Math.sqrt(1 + 8 * randomPossibilityCount - 8 * (random))) / 2));
			Circuit second = sortedPopulation.get(selectedIndex);

			selectedPopulation.add(new CircuitPair(first, second));
		}

		return selectedPopulation;
	}

	private List<Circuit> crossing(List<CircuitPair> selectedPopulation) {
		List<Circuit> newPopulation = new ArrayList<>();
		for (CircuitPair pair : selectedPopulation) {
			CircuitPair pairCrossed = atomicCrossing(pair);
			newPopulation.add(pairCrossed.first());
			newPopulation.add(pairCrossed.second());
		}
		return newPopulation;
	}

	private List<Circuit> mutation(List<Circuit> population) {
		int size = population.size();
		for (int i = 0; i < size; i++) {
			if (Math.random() < mutationProbability) {
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

		if (c3.getLength() < result.getLength()) {
			result = c3;
		}

		if (c4.getLength() < result.getLength()) {
			result = c4;
		}

		return new CircuitPair(c3, c4);
	}

	public static void main(String[] args) {
		Country country = DataSources.fromParser(1); // 0: Western Sahara - 1:
														// Zimbabwe - 2:
														// Canada...

		DistanceService distanceService = new EuclidianDistanceService(country.getCities());
		LandscapeService<TwoCityKey> landscape = new TwoOptLandscapeService(distanceService);

		PopulationFactory populationFactory = new PopulationFactory(new RandomAlgorithm(new BuilderParameter(distanceService,
				country.getCities())));

		GeneticParameter<TwoCityKey> params = new GeneticParameter<>(landscape, populationFactory, 100, 0.1, 1000);

		Lookup<GeneticParameter<TwoCityKey>> algo = new GeneticAlgorithm(params);

		Window win = new Window(algo.run());
		win.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
