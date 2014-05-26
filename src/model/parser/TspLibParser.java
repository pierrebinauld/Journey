package model.parser;

import model.data.City;
import model.data.Country;

import java.awt.geom.Point2D;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class TspLibParser {

	private static String TOKEN_NAME      = "NAME";
	private static String TOKEN_COMMENT   = "COMMENT";
	private static String TOKEN_TYPE      = "TYPE";
	private static String TOKEN_DIMENSION = "DIMENSION";
	private static String TOKEN_EWT       = "EDGE_WEIGHT_TYPE";
	private static String TOKEN_COORD     = "NODE_COORD_SECTION";
	private static String TOKEN_EOF       = "EOF";
	
	public Country parse(File file) throws IOException {
		Country country = new Country();
		BufferedReader reader = new BufferedReader(new FileReader(file));

		readHeader(reader, country);
		readData(reader, country);
		reader.close();
		return country;
	}

	private void readHeader(BufferedReader reader, Country country) throws IOException {
		while(readHeaderLine(reader, country)) ;
	}

	private boolean readHeaderLine(BufferedReader reader, Country country) throws IOException {
		String line = reader.readLine();
		if(line == null) {
			return false;
		}
		String[] parts = line.split(" : ", 2);
		if(parts[0].equals(TOKEN_COORD)) {
			return false;
		} else if(parts[0].equals(TOKEN_NAME)) {
			country.setName(parts[1]);
		} else if(parts[0].equals(TOKEN_COMMENT)) {
			country.addDescriptionLine(parts[1]);
		} else if(parts[0].equals(TOKEN_DIMENSION)) {
		} else if(parts[0].equals(TOKEN_TYPE)) {
		} else if(parts[0].equals(TOKEN_EWT)) {
		} else {
			throw new IOException("Invalid token found in TSP file.");
		}
		return true;
	}

	private void readData(BufferedReader reader, Country country) throws IOException {
		while(readDataLine(reader, country)) ;
	}

	private boolean readDataLine(BufferedReader reader, Country country) throws IOException {
		String line = reader.readLine();
		if(line == null) {
			return false;
		}
		String[] parts = line.split(" ");
		if(parts[0].equals(TOKEN_EOF)) {
			return false;
		} else {
			Point2D.Double position = new Point2D.Double(
					Double.valueOf(parts[1]),
					Double.valueOf(parts[2])
			);
			country.addCity(new City()
							.setId(Integer.valueOf(parts[0]))
							.setPosition(position)
			);
		}
		
		return true;
	}
}
