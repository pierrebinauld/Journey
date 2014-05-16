package persistance;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;

import model.data.Country;

public class ImageBuilder {
	
	public ImageBuilder() {
		
	}
	
	public void build(String path, Country country) {
		Image img = new BufferedImage(10000, 10000, BufferedImage.TYPE_INT_RGB);
		Graphics g = img.getGraphics();
		g.drawRect(1, 1, 2, 2);
		
		
	}
}
