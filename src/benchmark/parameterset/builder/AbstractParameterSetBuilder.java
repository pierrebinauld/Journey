package benchmark.parameterset.builder;

import benchmark.parameterset.ParameterSet;
import benchmark.parameterset.iterator.ParameterSetIterator;

import java.util.Iterator;

public interface AbstractParameterSetBuilder<T extends ParameterSet> extends Iterable<T> {

	@Override
	ParameterSetIterator<T> iterator();
}
