package pso;

import java.io.*;
import java.nio.charset.Charset;
import java.util.ArrayList;

public class PackageSwarm {

    public final boolean volume;
    public final double weightLimit;
    public final double volumeLimit;
    public ArrayList<Package> packages;

    public PackageSwarm(double weightLimit, double volumeLimit, boolean volume) {
        packages = new ArrayList<Package>();
        this.weightLimit = weightLimit;
        this.volumeLimit = volumeLimit;
        this.volume = volume;

        try {
            buildPackages();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void buildPackages() throws IOException {
        InputStream fis = new FileInputStream("packages.txt");
        BufferedReader br = new BufferedReader(new InputStreamReader(fis,
                Charset.forName("UTF-8")));
        String line;

        while ((line = br.readLine()) != null) {
            String[] param = line.split(",");
            packages.add(new Package(Double.parseDouble(param[0]), Double
                    .parseDouble(param[1]), volume));
        }

        br.close();
        br = null;
        fis = null;
    }

    private void printPackages() {
        for (int i = 0; i < packages.size(); i++) {
            System.out.println(i + " " + packages.get(i));
        }
    }

}
