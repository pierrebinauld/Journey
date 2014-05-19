package model.service.impl.decorator;

import java.util.ArrayList;
import java.util.List;

import model.service.impl.AbstractDistanceService;

public class LocalStorageDistanceService extends DecoratorDistanceService {

	protected List<List<Integer>> distances = new ArrayList<>();

	public LocalStorageDistanceService(AbstractDistanceService distanceService) {
		super(distanceService);

		initDistances(distanceService.getCities().size());
	}

	public void initDistances(int count) {
		for(int i = 0; i < count - 1; i++) {
			List<Integer> secondCities = new ArrayList<>();

			for(int j = i + 1; j < count; j++) {
				secondCities.add(-1);
			}

			distances.add(secondCities);
		}
	}

	@Override
	public int getDistance(int indexCity1, int indexCity2) {
		int first, second;
		if(indexCity1 < indexCity2) {
			first = indexCity1;
			second = indexCity2;
		} else if(indexCity1 > indexCity2) {
			first = indexCity2;
			second = indexCity1;
		} else { // indexCity1 == indexCity2
			return 0;
		}

		// System.out.println("first:" + first);
		// System.out.println("second:" + second);
		// System.out.println("second-first:" + (second-first));
		// System.out.println("count:" + distances.size());
		int d = distances.get(first).get(second - first - 1);

		if(-1 == d) {
			d = distanceService.getDistance(first, second);
			distances.get(first).set(second - first - 1, d);
		}

		return d;
	}
}
