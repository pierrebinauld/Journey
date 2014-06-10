package model.lookup.impl;

import benchmark.parameter.BuilderParameter;
import benchmark.parameter.impl.SimulatedAnnealingParameter;
import benchmark.parameter.impl.TabuParameter;
import model.data.Country;
import model.iterator.key.Key;
import model.iterator.key.TwoCityKey;
import model.lookup.AbstractModifierAlgorithm;
import model.lookup.Circuit;
import model.lookup.Lookup;
import model.service.DistanceService;
import model.service.LandscapeService;
import model.service.distance.EuclidianDistanceService;
import model.service.landscape.TwoOptLandscapeService;

import java.util.LinkedList;
import java.util.Queue;

import javax.swing.JFrame;

import tools.DataSources;
import view.Window;

public class TabuAlgorithm<K extends Key> extends AbstractModifierAlgorithm<K, TabuParameter<K>> {

	private int iterationCount;
	private int tabuSize;
	private Queue<Integer> tabu = new LinkedList<>();

	public TabuAlgorithm(TabuParameter<K> parameter) {
		super(parameter);
		this.tabuSize = parameter.getTabuSize();
		this.iterationCount = parameter.getIterationCount();
	}

	@Override
	public Circuit run() {
		Circuit result = initialCircuit;

		int count = 0;
		int testedLength;
		int currentLength = -1;
		Circuit testedCircuit = null;
		Circuit currentCircuit = null;
		Circuit currentIterationCircuit = initialCircuit;
		landscapeService.setCircuit(initialCircuit);

		while(count < iterationCount) {
			landscapeService.setCircuit(currentIterationCircuit);
			
			for (K key : landscapeService) {
				testedLength = landscapeService.getNeighborLength(key);
				if (testedLength <= currentLength || -1 == currentLength) {
					testedCircuit = landscapeService.getNeighbor(key);

					if (!tabu.contains(testedCircuit.hashCode())) {
						currentCircuit = testedCircuit;
						currentLength = testedLength;
					}
				}
			}
			
			if(currentIterationCircuit.getLength() <= currentCircuit.getLength()) {
				if(currentIterationCircuit.getLength() < result.getLength()) {
					result = currentCircuit;
				}
				putTabuList(currentIterationCircuit.hashCode());
				count++;
			}
			currentLength = -1;
			currentIterationCircuit = currentCircuit;
		}

		return result;
	}

	private void putTabuList(Integer circuitHashCode) {
		tabu.add(circuitHashCode);
		if (tabu.size() > tabuSize) {
			tabu.poll();
		}
	}

	public int getTabuSize() {
		return tabuSize;
	}

	public void setTabuSize(int tabuSize) {
		this.tabuSize = tabuSize;
	}
	
	public static void main(String[] args) {
		Country country = DataSources.fromParser(0); // 0: Western Sahara - 1: Zimbabwe - 2: Canada...
		
		DistanceService distanceService = new EuclidianDistanceService(country.getCities());
		LandscapeService<TwoCityKey> landscape = new TwoOptLandscapeService(distanceService);
		
		Circuit initial = new GreedyAlgorithm(new BuilderParameter(distanceService, country.getCities())).run();
		Window initWin = new Window(initial);
		
		TabuParameter<TwoCityKey> params = new TabuParameter<>(initial, landscape, 3, 100);
		
		Lookup<TabuParameter<TwoCityKey>> algo = new TabuAlgorithm<>(params);
		
		Window win = new Window(algo.run());
		win.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
