package benchmark;

import benchmark.parameterset.ParameterSet;
import benchmark.parameterset.builder.AbstractParameterSetBuilder;
import benchmark.parameterset.iterator.ParameterSetIterator;
import model.lookup.Lookup;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public abstract class AbstractBenchmark<T extends AbstractParameterSetBuilder<U>, U extends ParameterSet> {

	protected int executionCount;
	protected ExecutorService executor;
	protected T parameterSetBuilder;

	public AbstractBenchmark(int executionCount, T parameterSetBuilder) {
		this.executionCount = executionCount;
		this.executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
		this.parameterSetBuilder = parameterSetBuilder;
	}

	public void run() {
		ParameterSetIterator<U> paramIterator = parameterSetBuilder.iterator();
		for(int i=0; i<executionCount; i++) {
			U next = paramIterator.next();
			Lookup algorithm = initializeAlgorithm(next);
			AlgorithmThread algorithmThread = new AlgorithmThread(algorithm);
			executor.execute(algorithmThread);
		}
	}

	public abstract Lookup initializeAlgorithm(U parameterSet);

//	public static void main(String[] args) {
//		AbstractBenchmark b = new AbstractBenchmark();
//		try {
//			for(String arg : args) {
//				if(arg.startsWith("-t")) {          //thread count
//					b.setThreadCount(Integer.valueOf(arg.substring(2)));
//				} else if(arg.startsWith("-i")) {   //iteration count
//					b.setIterationCount(Integer.valueOf(arg.substring(2)));
//				//} else if(arg.startsWith("-f")) {   //file to parse
//				//	b.setFile(new File(arg.substring(2)));
//				} else if(arg.startsWith("-c")) {   //country name
//					b.setCountryName(arg.substring(2));
//				} else if(arg.startsWith("-a")) {   //algorithm
//					b.setAlgorithm(arg.substring(2));
//				}
//			}
//		} catch(NumberFormatException e) {
//			System.err.println("Invalid argument.");
//		}
//	}
}
