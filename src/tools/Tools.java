package tools;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;


public class Tools {

	/**
	 * Return an integer random value in the range [min, max), where max is not
	 * included.
	 * 
	 * @param min
	 * @param max
	 * @return
	 */
	public static int random(int min, int max) {
		return min + (int) (Math.random() * (max - min));
	}
	
	public static String getStringDateTime() {
		Date now = new Date();
		DateFormat df = new SimpleDateFormat("MMddyyyyHHmmss");
		return df.format(now);
	}
}
