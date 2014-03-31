package model.data;

import java.awt.Point;
import java.awt.geom.Point2D;

public class City {

	private int id;
	private Point2D.Double position;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Point2D.Double getPosition() {
		return position;
	}
	public void setPosition(Point2D.Double position) {
		this.position = position;
	}
}
