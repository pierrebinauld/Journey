package model.parser;

import java.awt.Point;
import java.awt.geom.Point2D;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import model.data.City;
import model.data.Country;

public class TSPLIBParser {

	public Country parse(File file) throws IOException {
		Country country = new Country();
		BufferedReader buff = new BufferedReader(new FileReader(file));

		try {
			String line;
			int i = 0;

			while ((line = buff.readLine()) != null) {

				if (i < 7) {
					String[] datas = line.split("NAME : ");
					if (1 == datas.length) {
						country.setName(datas[0]);
					}
				} else {
					String[] datas = line.split("\\s");
					if (3 == datas.length) {
						City city = new City();
						city.setId(Integer.parseInt(datas[0]));

						Point2D.Double position = new Point2D.Double();
						position.x = Double.parseDouble(datas[1]);
						position.y = Double.parseDouble(datas[2]);
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
