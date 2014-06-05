package benchmark.parameter.set;

import model.lookup.AbstractBuilderAlgorithm;
import model.service.LandscapeService;
import benchmark.parameter.LookupParameter;

public abstract class AbstractModifierParameterSet<T extends LookupParameter> implements ParameterSet<T> {

	private AbstractBuilderAlgorithm initialCircuitBuilder;
	private LandscapeService landscapeService;

	protected AbstractModifierParameterSet(AbstractBuilderAlgorithm initialCircuitBuilder, LandscapeService landscapeService) {
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
