package benchmark;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import model.lookup.Circuit;
import model.lookup.Lookup;
import model.service.distance.EuclidianDistanceService;
import persistence.CsvFile;
import tools.Constant;
import tools.Tools;
import benchmark.parameter.LookupParameter;
import benchmark.parameter.set.ParameterSet;

public abstract class Benchmark<U extends LookupParameter> {

	protected int executionCount;
	protected ExecutorService executor;
	protected ParameterSet<U> parameterSet;

	private String name;
	private String country;
	private int optimum;
	private CsvFile csvFile;

	public Benchmark(String name, String country, int optimum, int executionCount, ParameterSet<U> parameterSet) {
		this.name = name;
		this.country = country;
		this.optimum = optimum;
		this.executionCount = executionCount;
		this.executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
		this.parameterSet = parameterSet;
	}

	public void run() {

		System.out.println(" --- Starting benchmark --- ");
		csvFile = new CsvFile();
		try {
			csvFile.open(Constant.BENCH_PATH + country + "/" + name + "/", Tools.getStringDateTime() + "-" + name + ".csv");
		
			List<AlgorithmThread<U>> threads = new ArrayList<>();
	
			for (U parameter : parameterSet) {
				Lookup algorithm = initializeAlgorithm(parameter);
				AlgorithmThread<U> algorithmThread = new AlgorithmThread<>(parameter, algorithm);
				threads.add(algorithmThread);
				algorithmThread.start();
			}
	
			try {
				boolean init = false;
				for (AlgorithmThread<U> thread : threads) {
					if(!init) {
						init = true;
						initFile(thread.getParameter());
					}
					thread.join();
					store(thread.getParameter(), thread.getResult(), thread.getExecutionTime());
				}
				System.out.println(" --- Benchmark terminated --- ");
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
	
			csvFile.close();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	private void initFile(U parameters) throws IOException {
		ArrayList<String> csvRow = parameters.titlesToStringArrayList();
		csvRow.add("Longueur finale");
		csvRow.add("Optimum");
		csvRow.add("% d'erreur");
		csvRow.add("Temps d'execution");
		csvFile.write(csvRow);
	}

	private void store(U parameters, Circuit result, float executionTime) throws IOException {
		ArrayList<String> csvRow = parameters.toStringArrayList();
		csvRow.add(Integer.toString(result.getLength()));
		csvRow.add(Integer.toString(optimum));
		csvRow.add(Double.toString((double)(result.getLength() - optimum)/optimum*100).substring(0, 3)+"%");
		csvRow.add(Float.toString(executionTime).substring(0, 3));
		String display = "";
		for(String str : csvRow) {
			display += "\t" + str;
		}
		System.out.println(display);
		csvFile.write(csvRow);
	}

	public abstract Lookup initializeAlgorithm(U parameters);
}
