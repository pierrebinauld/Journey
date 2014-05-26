package view;

import java.awt.Graphics;
import java.awt.Polygon;
import java.awt.geom.Point2D;

import javax.swing.JPanel;

import model.data.City;
import model.lookup.Circuit;

public class CircuitView extends JPanel {

	public static final int padding = 20;
	
	private static final long serialVersionUID = -4343580478763862261L;

	private Circuit circuit;

	private Point2D.Double min;
	private Point2D.Double max;

	public CircuitView(Circuit circuit) {
		this.circuit = circuit;

		min = new Point2D.Double(-1, -1);
		max = new Point2D.Double(0, 0);

		for (City city : circuit.getCities()) {
			if (city.getPosition().x < min.x || -1 == min.x) {
				min.x = city.getPosition().x;
			}
			if (city.getPosition().y < min.y || -1 == min.y) {
				min.y = city.getPosition().y;
			}

			if (city.getPosition().x > max.x) {
				max.x = city.getPosition().x;
			}
			if (city.getPosition().y > max.y) {
				max.y = city.getPosition().y;
			}
		}
	}

	public void paintComponent(Graphics g) {

		Point2D.Double p = null;
		
		Polygon polygon = new Polygon();
		for (int i = 0; i < circuit.getCities().size(); i++) {
			p = circuit.getCities().get(circuit.getCircuit().get(i)).getPosition();
			
			polygon.addPoint(
					(int)( (p.x - min.x) / (max.x - min.x) * (this.getWidth() - padding * 2)) + padding,
					(int)( (p.y - min.y) / (max.y - min.y) * (this.getHeight() - padding * 2)) + padding
					);
		}
		g.drawPolygon(polygon);
	}
}
