package model.parser;

import java.awt.geom.Point2D;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import model.data.City;
import model.data.Country;

public class TSPLIBParser {

	private Pattern namePattern;
	private Pattern commentPattern;
	private Pattern dimensionPattern;
	private Pattern coordinatePattern;

	public TSPLIBParser() {
		init();
	}

	public void init() {

		namePattern = Pattern.compile("NAME : (.+)");
		commentPattern = Pattern.compile("COMMENT : (.+)");
		dimensionPattern = Pattern.compile("DIMENSION : (.+)");
		coordinatePattern = Pattern.compile("(\\d+)\\s+(\\d+\\.\\d{4})\\s+(\\d+\\.\\d{4})");
	}

	public Country parse(File file) throws IOException {
		Country country = new Country();
		BufferedReader buff = new BufferedReader(new FileReader(file));

		try {
			String line = "";
			int i = 0;

			while ((line = buff.readLine()) != null && !"EOF".equals(line)) {
				boolean matched = false;
				if (i < 7) {
					if (!matched) {
						Matcher m = namePattern.matcher(line);
						if (m.matches()) {
							country.setName(m.group(1));
							matched = true;
						}
					}
					if (!matched) {
						Matcher m = commentPattern.matcher(line);
						if (m.matches()) {
							if(null == country.getDescription()) {
								country.setDescription(m.group(1));
							} else if (m.matches()){
								country.setDescription(country.getDescription() + "\n" + m.group(1));
							}
							matched = true;
						}
					}
					if (!matched) {
						Matcher m = dimensionPattern.matcher(line);
						if (m.matches()) {
							country.setDimension(m.group(1));
							matched = true;
						}
					}
				} else {
					Matcher m = coordinatePattern.matcher(line);
					if (m.matches()) {
						City city = new City();
						city.setId(Integer.parseInt(m.group(1)));

						Point2D.Double position = new Point2D.Double();
						String g2 = m.group(2);
						position.x = Double.parseDouble(m.group(2));
						position.y = Double.parseDouble(m.group(3));
						city.setPosition(position);

						country.addCity(city);
					}
				}

				i++;
			}
		} finally {
			buff.close();
		}

		return country;
	}

	public static void main(String[] args) throws IOException {
		File file = new File("/home/pierre/git/Journey/data/ch71009.tsp");
//		File file = new File("http://www.math.uwaterloo.ca/tsp/world/ch71009.tsp");

		TSPLIBParser parser = new TSPLIBParser();
		Country country = parser.parse(file);

		for (City city : country.getCities()) {
			System.out.println(city.getPosition());
		}
		System.out.println("Size: " + country.getCities().size());
		System.out.println("Name: " + country.getName());
		System.out.println("Desc: " + country.getDescription());
		System.out.println("Dim: " + country.getDimension());
	}
}
