package benchmark.parameter.set;

import java.util.Iterator;

import benchmark.parameter.LookupParameter;

public interface ParameterSet<T extends LookupParameter> extends Iterable<T> {

	@Override
	public Iterator<T> iterator();
}
