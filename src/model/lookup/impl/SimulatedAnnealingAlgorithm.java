package model.lookup.impl;

import javax.swing.JFrame;

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
import tools.DataSources;
import view.Window;
import benchmark.parameter.BuilderParameter;
import benchmark.parameter.impl.GeneticParameter;
import benchmark.parameter.impl.SimulatedAnnealingParameter;

public class SimulatedAnnealingAlgorithm<K extends Key> extends AbstractModifierAlgorithm<K, SimulatedAnnealingParameter<K>> {

	private double temperature;
	private double temperatureBreakpoint;
	private double lambda;

	public SimulatedAnnealingAlgorithm(SimulatedAnnealingParameter<K> parameter) {
		super(parameter);

		this.temperature = parameter.getTemperature();
		this.temperatureBreakpoint = parameter.getTemperatureBreakpoint();
		
		if (parameter.getLambda() >= 1) {
			this.lambda = parameter.getLambda() - Math.floor(parameter.getLambda());
		} else {
			this.lambda = parameter.getLambda();
		}
	}

	@Override
	public Circuit run() {
		Circuit result = initialCircuit;
		int resultLength = initialCircuit.getLength();
		
		int length;
		int currentLength = initialCircuit.getLength();

		while(temperature > temperatureBreakpoint) {
			K key = landscapeService.randomKey();
			
			length = landscapeService.getNeighborLength(key);
			if(length < currentLength || metropolisRule(length, currentLength)) {
				Circuit circuit = landscapeService.getNeighbor(key);
				currentLength = circuit.getLength();
				landscapeService.setCircuit(circuit);
				
				if(currentLength < resultLength) {
					result = circuit;
					resultLength = currentLength;
				}
			}

			temperature *= lambda;
		}

		return result;
	}

	private boolean metropolisRule(int testedLength, int currentLength) {
		return Math.random() < Math.exp(-(testedLength - currentLength)/temperature);
	}
	
	public static void main(String[] args) {
		Country country = DataSources.fromParser(1); // 0: Western Sahara - 1: Zimbabwe - 2: Canada...
		
		DistanceService distanceService = new EuclidianDistanceService(country.getCities());
		LandscapeService<TwoCityKey> landscape = new TwoOptLandscapeService(distanceService);
		
		Circuit initial = new RandomAlgorithm(new BuilderParameter(distanceService, country.getCities())).run();
		Window initWin = new Window(initial);
		
		SimulatedAnnealingParameter<TwoCityKey> params = new SimulatedAnnealingParameter<>(initial, landscape, 300, 0.9999, 0.005);
		
		Lookup<SimulatedAnnealingParameter<TwoCityKey>> algo = new SimulatedAnnealingAlgorithm<>(params);
		
		Window win = new Window(algo.run());
		win.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
