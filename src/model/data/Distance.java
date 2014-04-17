package model.data;

public class Distance {

	private City city1;
	private City city2;
	private int distance;

	protected Distance(City city1, City city2, int distance) {
		this.city1 = city1;
		this.city2 = city2;
		this.distance = distance;
	}

	public static Distance Euclidian(City city1, City city2) {
		return new Distance(
				city1,
				city2,
				(int)Math.round(city1.getPosition().distance(city2.getPosition()))
		);
	}

	public static Distance Manhattan(City city1, City city2) {
		return new Distance(
				city1,
				city2,
				(int)Math.round(
					Math.abs(city1.getPosition().getX() - city2.getPosition().getX()) +
					Math.abs(city1.getPosition().getY() - city2.getPosition().getY())
				)
		);
	}

	public int getDistance() {
		return distance;
	}
}
