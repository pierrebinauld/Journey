package benchmark.parameter.set;

import benchmark.parameter.LookupParameter;

import java.util.Iterator;

public interface ParameterSet<T extends LookupParameter> extends Iterable<T> {

	@Override
	public Iterator<T> iterator();
}
