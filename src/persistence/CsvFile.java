package persistence;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;


public class CsvFile {

	private FileWriter writer;

	public CsvFile() {
	}

	public void open(String path, String name) throws IOException {
		createFolder(path);
		writer = new FileWriter(path+"/"+name);
	}

	public void write(ArrayList<String> csvRow) throws IOException {

		for (String data : csvRow) {
			writer.append(data);
			writer.append(';');
		}

		writer.append('\n');

		writer.flush();
	}

	public void close() throws IOException {
		writer.close();
	}

	private void createFolder(String path) {
		File folder = new File(path);

		if (!folder.exists()) {
			folder.mkdirs();
		}
	}
}
