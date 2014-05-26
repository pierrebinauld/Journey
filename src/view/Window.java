package view;

import javax.swing.JFrame;
import javax.swing.JPanel;

import model.lookup.Circuit;

public class Window extends JFrame {

	private static final long serialVersionUID = -3739008754324139579L;

	public Window(Circuit circuit) {
		super();
		
		JPanel builder = new CircuitView(circuit);
	    
	    setSize(800, 800);
	    setLocationRelativeTo(null);           
	    setVisible(true);
	    setContentPane(builder);
	}
}
