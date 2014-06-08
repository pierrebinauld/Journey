package model.lookup;

import benchmark.parameter.LookupParameter;

public abstract class Lookup<P extends LookupParameter> {

	protected P parameter;

	public Lookup(P parameter) {
		this.parameter = parameter;
	}

	abstract public Circuit run();
}
