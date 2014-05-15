package model.data;

import java.awt.geom.Point2D;

public class City {

	private int id;
	private Point2D.Double position;

	public int getId() {
		return id;
	}

	public City setId(int id) {
		this.id = id;
		return this;
	}

	public Point2D.Double getPosition() {
		return position;
	}

	public City setPosition(Point2D.Double position) {
		this.position = position;
		return this;
	}

	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("CITY")
				.append("Id: ").append(id).append('\t')
				.append("X: ").append(position.getX()).append('\t')
				.append("Y: ").append(position.getY()).append('\n');
		return builder.toString();
	}
}
