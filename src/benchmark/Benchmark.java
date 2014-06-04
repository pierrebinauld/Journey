package benchmark;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import tools.Constant;
import tools.Tools;
import model.lookup.Circuit;
import model.lookup.Lookup;
import benchmark.parameterset.LookupParameter;
import benchmark.parameterset.builder.ParameterSet;

public abstract class Benchmark<U extends LookupParameter> {

	protected int executionCount;
	protected ExecutorService executor;
	protected ParameterSet<U> parameterSet;
	
	private String name;
	private CsvFile csvFile;

	public Benchmark(String name, int executionCount, ParameterSet<U> parameterSet) {
		this.name = name;
		this.executionCount = executionCount;
		this.executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
		this.parameterSet = parameterSet;
	}

	public void run() {

		System.out.println(" --- Starting benchmark --- ");
		csvFile = new CsvFile(Constant.BENCH_PATH+Tools.getStringDateTime()+"-"+name);
		List<AlgorithmThread<U>> threads = new ArrayList<>();
		
		for (U parameter : parameterSet) {
			Lookup algorithm = initializeAlgorithm(parameter);
			AlgorithmThread<U> algorithmThread = new AlgorithmThread<>(parameter, algorithm);
			threads.add(algorithmThread);
			
		}

		try {
			for(AlgorithmThread<U> thread : threads) {
				thread.join();
				storeResult(thread.getParameter(), thread.getResult());
			}
			System.out.println(" --- Benchmark terminated --- ");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		csvFile.close();
	}

	private void storeResult(U parameters, Circuit result) {
		ArrayList<String> csvRow = toStringArrayList(parameters);
		csvRow.add(Integer.toString(result.getLength()));
		csvFile.write(csvRow);
	}

	public abstract Lookup initializeAlgorithm(U parameters);
	
	public abstract ArrayList<String> toStringArrayList(U parameters);
}
