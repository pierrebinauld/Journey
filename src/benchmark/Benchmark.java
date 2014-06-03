package benchmark;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import model.lookup.Lookup;
import benchmark.parameterset.LookupParameter;
import benchmark.parameterset.builder.ParameterSet;

public abstract class Benchmark<U extends LookupParameter> {

	protected int executionCount;
	protected ExecutorService executor;
	protected ParameterSet<U> parameterSet;

	public Benchmark(int executionCount, ParameterSet<U> parameterSet) {
		this.executionCount = executionCount;
		this.executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
		this.parameterSet = parameterSet;
	}

	public void run() {

		System.out.println(" --- Starting benchmark --- ");
		List<AlgorithmThread> threads = new ArrayList<>();
		
		for (U parameter : parameterSet) {
			Lookup algorithm = initializeAlgorithm(parameter);
			AlgorithmThread algorithmThread = new AlgorithmThread(algorithm);
			threads.add(algorithmThread);
			
		}

		try {
			for(AlgorithmThread thread : threads) {
				thread.join();
				//TODO Save result;
			}
			System.out.println(" --- Benchmark terminated --- ");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public abstract Lookup initializeAlgorithm(U parameterSet);
	
	// public static void main(String[] args) {
	// AbstractBenchmark b = new AbstractBenchmark();
	// try {
	// for(String arg : args) {
	// if(arg.startsWith("-t")) { //thread count
	// b.setThreadCount(Integer.valueOf(arg.substring(2)));
	// } else if(arg.startsWith("-i")) { //iteration count
	// b.setIterationCount(Integer.valueOf(arg.substring(2)));
	// //} else if(arg.startsWith("-f")) { //file to parse
	// // b.setFile(new File(arg.substring(2)));
	// } else if(arg.startsWith("-c")) { //country name
	// b.setCountryName(arg.substring(2));
	// } else if(arg.startsWith("-a")) { //algorithm
	// b.setAlgorithm(arg.substring(2));
	// }
	// }
	// } catch(NumberFormatException e) {
	// System.err.println("Invalid argument.");
	// }
	// }
}
