package model.tools;

import model.data.City;

public class Tools {

	public static int manhattan(City city1, City city2) {
		return (int) Math.round(
				Math.abs(city1.getPosition().getX() - city2.getPosition().getX()) +
						Math.abs(city1.getPosition().getY() - city2.getPosition().getY())
		);
	}
}
