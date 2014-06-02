import java.io.File;
import java.io.IOException;

import javax.swing.JFrame;

import model.data.Country;
import model.iterator.key.TwoCityKey;
import model.lookup.AbstractBuilderAlgorithm;
import model.lookup.Circuit;
import model.lookup.Lookup;
import model.lookup.impl.GreedyAlgorithm;
import model.lookup.impl.SimulatedAnnealing;
import model.parser.TspLibParser;
import model.service.TimeService;
import model.service.distance.AbstractDistanceService;
import model.service.distance.EuclidianDistanceService;
import model.service.landscape.TwoOptLandscapeService;
import view.Window;

public class Main {

	public static void main(String[] args) throws IOException {
		TimeService time = new TimeService();

//		File file = new File("/home/pierre/git/Journey/data/wi29.tsp");
//		double optimum = 27603;
//		 File file = new File("/home/pierre/git/Journey/data/ch71009.tsp");
		 File file = new File("/home/pierre/git/Journey/data/ja9847.tsp");
		 double optimum = 491924;
//		 File file = new File("/home/pierre/git/Journey/data/ca4663.tsp");
//		 double optimum = 1290319;
//		 File file = new File("/home/pierre/git/Journey/data/zi929.tsp");
//		 double optimum = 95345;

		TspLibParser parser = new TspLibParser();
		
		Country country = parser.parse(file);
		System.out.println(country.getDimension());
		System.out.println(country.getCities());

	
		
	}
}
