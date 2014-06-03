package benchmark.parameterset.builder;

import java.util.Iterator;

import benchmark.parameterset.LookupParameter;

public interface ParameterSet<T extends LookupParameter> extends Iterable<T> {

	@Override
	public Iterator<T> iterator();
}
