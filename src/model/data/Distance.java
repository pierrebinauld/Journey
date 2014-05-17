package model.data;

public class Distance {

	private int idCountry;
	private int idCity1;
	private int idCity2;
	private int distance;

	private Distance(int idCountry, int idCity1, int idCity2, int distance) {
		this.idCountry = idCountry;
		this.distance = distance;

		if(idCity1 < idCity2) {
			this.idCity1 = idCity1;
			this.idCity2 = idCity2;
		} else {
			this.idCity1 = idCity2;
			this.idCity2 = idCity1;
		}
	}

	public static Distance Euclidian(int idCountry, City city1, City city2) {
		return new Distance(
				idCountry,
				city1.getId(),
				city2.getId(),
				(int) Math.round(city1.getPosition().distance(city2.getPosition()))
		);
	}

	public static Distance Manhattan(int idCountry, City city1, City city2) {
		return new Distance(
				idCountry,
				city1.getId(),
				city2.getId(),
				(int) Math.round(Math.abs(city1.getPosition().getX() - city2.getPosition().getX()) + Math.abs(city1.getPosition().getY() - city2.getPosition().getY()))
		);
	}

	public int getIdCountry() {
		return idCountry;
	}

	public int getIdCity1() {
		return idCity1;
	}

	public int getIdCity2() {
		return idCity2;
	}

	public int getDistance() {
		return distance;
	}
}
