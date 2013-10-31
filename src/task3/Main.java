package task3;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;


public class Main {
	public static ArrayList<Package> packages;

	public static void main(String[] args) throws IOException {
		packages = new ArrayList<Package>();
		buildPackages();
		printPackages();
	}

	private static void printPackages() {
		for (int i = 0; i < packages.size(); i++) {
			System.out.println(i + " " + packages.get(i));
		}
	}

	/**
	 * Read line from "package.txt" and build package objects
	 * 
	 * @throws IOException
	 */
	public static void buildPackages() throws IOException {
		InputStream fis = new FileInputStream("packages.txt");
		BufferedReader br = new BufferedReader(new InputStreamReader(fis,
				Charset.forName("UTF-8")));
		String line;

		while ((line = br.readLine()) != null) {
			String[] param = line.split(",");
			packages.add(new Package(Double.parseDouble(param[0]), Double
					.parseDouble(param[1])));
		}

		br.close();
		br = null;
		fis = null;
	}
}
