package tools;

public class Constant {

//	public static String TSP_PATH = "/home/tom/Documents/polytech/OD/dev/Journey/data/"; /* <-- comment this line to switch path
	public static String TSP_PATH = "/home/pierre/git/Journey/data/"; //*/
	
	public static String BENCHMARK_PATH = "./benchmark/";
	
	public static String[] TSP_FILES = {
			"wi29",
			"zi929",
			"ca4663",
			"ja9847",
			"it16862",
			"ch71009",
	};

	public static String[] COUNTRY_NAMES = {
			"western_sahara",
			"zimbabwe",
			"canada",
			"japan",
			"italy",
			"china",
	};

	public static int[] OPTIMUM = {
			27_603,
			95_345,
			1_290_319,
			491_924,
			557_315,
			4_566_563, // Not really the optimal... But the closest we have.
	};
}
