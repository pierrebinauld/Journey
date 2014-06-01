package benchmark.parameterset.builder;

import benchmark.parameterset.ParameterSet;
import model.lookup.AbstractBuilderAlgorithm;
import model.service.LandscapeService;

public abstract class AbstractModifierParameterSetBuilder<T extends ParameterSet> implements AbstractParameterSetBuilder<T> {

	private AbstractBuilderAlgorithm initialCircuitBuilder;
	private LandscapeService landscapeService;

	protected AbstractModifierParameterSetBuilder(AbstractBuilderAlgorithm initialCircuitBuilder, LandscapeService landscapeService) {
		this.initialCircuitBuilder = initialCircuitBuilder;
		this.landscapeService = landscapeService;
	}

	public AbstractBuilderAlgorithm getInitialCircuitBuilder() {
		return initialCircuitBuilder;
	}

	public LandscapeService getLandscapeService() {
		return landscapeService;
	}
}
