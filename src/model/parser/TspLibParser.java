package model.parser;

import model.data.City;
import model.data.Country;

import java.awt.geom.Point2D;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class TspLibParser {

	public static Country parse(File file) throws IOException {
		Country country = new Country();
		BufferedReader buff = new BufferedReader(new FileReader(file));

		try {
			String line;
			int i = 0;

			while((line = buff.readLine()) != null) {
				if(i < 7) {
					String[] data = line.split("NAME : ");
					if(1 == data.length) {
						country.setName(data[0]);
					}
				} else {
					String[] data = line.split("\\s");
					if(3 == data.length) {
						City city = new City();
						city.setId(Integer.parseInt(data[0]));

						Point2D.Double position = new Point2D.Double(
								Double.parseDouble(data[1]),
								Double.parseDouble(data[2])
						);
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

		Country country = TspLibParser.parse(file);

		for(City city : country.getCities()) {
			System.out.println(city.getPosition());
		}
		System.out.println("Size: " + country.getCities().size());
		System.out.println("Name: " + country.getName());
		System.out.println("Desc: " + country.getDescription());
		System.out.println("Dim: " + country.getDimension());
	}
}
