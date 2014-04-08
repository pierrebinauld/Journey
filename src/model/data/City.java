package model.data;

import java.awt.geom.Point2D;

public class City {

	private int id;
	private Point2D.Double position;
	private int idCountry;
	
	public int getIdCountry() {
		return idCountry;
	}
	public void setIdCountry(int idCountry) {
		this.idCountry = idCountry;
	}
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
	
	public String toString(){
		return "Ville numéro "+id+"; x="+position.x+" ; y="+position.y+"\n";
	}
}
