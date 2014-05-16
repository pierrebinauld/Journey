package model.lookup;

public abstract class AbstractModifierAlgorithm implements Lookup {

	private Circuit circuit;

	protected AbstractModifierAlgorithm(Circuit initialCircuit) {
		this.circuit = initialCircuit;
	}
}
