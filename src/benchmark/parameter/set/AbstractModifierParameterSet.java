package benchmark.parameter.set;

import benchmark.parameter.LookupParameter;
import model.lookup.AbstractBuilderAlgorithm;
import model.service.LandscapeService;
import model.service.factory.LandscapeFactory;

public abstract class AbstractModifierParameterSet<T extends LookupParameter> implements ParameterSet<T> {

	private AbstractBuilderAlgorithm initialCircuitBuilder;
	private LandscapeFactory landscapeFactory;

	protected AbstractModifierParameterSet(AbstractBuilderAlgorithm initialCircuitBuilder, LandscapeFactory landscapeFactory) {
		this.initialCircuitBuilder = initialCircuitBuilder;
		this.landscapeFactory = landscapeFactory;
	}

	public AbstractBuilderAlgorithm getInitialCircuitBuilder() {
		return initialCircuitBuilder;
	}

	public LandscapeService getLandscapeService() {
		return landscapeFactory.manufacture();
	}
}
