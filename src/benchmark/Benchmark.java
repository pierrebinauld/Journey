package benchmark;

import benchmark.parameter.LookupParameter;
import benchmark.parameter.set.ParameterSet;
import model.lookup.Lookup;
import persistence.CsvFile;
import tools.Constant;
import tools.Tools;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public abstract class Benchmark<P extends LookupParameter, L extends Lookup> {

	protected int executionCount;
	protected ExecutorService pool;
	protected ParameterSet<P> parameterSet;
	protected List<Future<ExecutionResult<P>>> futures = new ArrayList<>();

	private String algorithmName;
	private String countryName;
	private int optimum;
	private CsvFile csvFile;

	public Benchmark(String algorithmName, String countryName, int optimum, int executionCount, ParameterSet<P> parameterSet) {
		this.algorithmName = algorithmName;
		this.countryName = countryName;
		this.optimum = optimum;
		this.executionCount = executionCount;
		this.pool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
		this.parameterSet = parameterSet;
	}

	public void run() {

			System.out.println("\t\t +------------------------+ ");
			System.out.println("\t\t | - Starting benchmark - | ");
			System.out.println("\t\t +------------------------+ ");
			csvFile = new CsvFile();
			try {
				csvFile.open(Constant.BENCHMARK_PATH + countryName + "/" + algorithmName + "/", Tools.getStringDateTime() + "-" + algorithmName + ".csv");

				for(int iExecution = 0; iExecution < executionCount; iExecution++) {
					for(P parameter : parameterSet) {
						L algorithm = initializeAlgorithm(parameter);
						AlgorithmThread<P> algorithmThread = new AlgorithmThread<>(parameter, algorithm);
						futures.add(pool.submit(algorithmThread));
					}
				}

				boolean init = false;
				for(Future<ExecutionResult<P>> future : futures) {
					try {
						ExecutionResult<P> result = future.get();
						if(!init) {
							init = true;
							initFile(result.getParameter());
						}
						store(result);
					} catch(InterruptedException | ExecutionException e) {
						e.printStackTrace();
					}
				}
				System.out.println("\t\t+--------------------------+ ");
				System.out.println("\t\t| - Benchmark terminated - | ");
				System.out.println("\t\t+--------------------------+ ");

				csvFile.close();
				pool.shutdown();
			} catch(IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	private void initFile(P parameter) throws IOException {
		ArrayList<String> csvRow = parameter.titlesToStringArrayList();
		csvRow.add("Longueur finale");
		csvRow.add("Optimum");
		csvRow.add("% d'erreur");
		csvRow.add("Temps d'exécution (sec.)");
		csvFile.write(csvRow);
	}

	private void store(ExecutionResult result) throws IOException {
		double percentage = (double)(result.getCircuit().getLength() - optimum) / optimum * 100;
		ArrayList<String> csvRow = result.getParameter().toStringArrayList();
		csvRow.add(Integer.toString(result.getCircuit().getLength()));
		csvRow.add(Integer.toString(optimum));
		csvRow.add(String.format("%.3g", percentage));
		csvRow.add(String.format("%.3g", result.getExecutionTime()));
		StringBuilder display = new StringBuilder();
		for(String str : csvRow) {
			display.append("\t").append(str);
		}
		System.out.println(display);
		csvFile.write(csvRow);
	}

	public abstract L initializeAlgorithm(P parameter);
}
