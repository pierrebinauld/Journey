package benchmark;

import benchmark.parameter.LookupParameter;
import benchmark.parameter.set.ParameterSet;
import model.lookup.Circuit;
import model.lookup.Lookup;
import persistence.CsvFile;
import tools.Constant;
import tools.Tools;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public abstract class Benchmark<U extends LookupParameter> {

	protected int executionCount;
//	protected ExecutorService executor;
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
//		this.executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
		this.parameterSet = parameterSet;
	}

	public void run() {

		System.out.println("\t\t +------------------------+ ");
		System.out.println("\t\t | - Starting benchmark - | ");
		System.out.println("\t\t +------------------------+ ");
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
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println("\t\t+--------------------------+ ");
			System.out.println("\t\t| - Benchmark terminated - | ");
			System.out.println("\t\t+--------------------------+ ");
	
			csvFile.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
		double percentage = (double)(result.getLength() - optimum)/optimum*100;
		ArrayList<String> csvRow = parameters.toStringArrayList();
		csvRow.add(Integer.toString(result.getLength()));
		csvRow.add(Integer.toString(optimum));
		csvRow.add(String.format("%.3g", percentage)+"%");
		csvRow.add(String.format("%.3g", executionTime)+"s");
		String display = "";
		for(String str : csvRow) {
			display += "\t" + str;
		}
		System.out.println(display);
		csvFile.write(csvRow);
	}

	public abstract Lookup initializeAlgorithm(U parameters);
}
