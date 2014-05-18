package model.tools;


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
}
