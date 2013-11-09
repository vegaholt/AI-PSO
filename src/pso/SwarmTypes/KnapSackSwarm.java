package pso.SwarmTypes;

import pso.Particle;
import pso.Position;
import pso.SwarmType;

import java.io.*;
import java.util.ArrayList;

public class KnapSackSwarm extends SwarmType<Boolean> {
    public final double weightLimit;
    public final double volumeLimit;
    public ArrayList<Package> packages = new ArrayList<Package>();

    public KnapSackSwarm(double weightLimit, double volumeLimit) throws FileNotFoundException {
        this(new FileInputStream("packages.txt"), weightLimit, volumeLimit);
    }

    public KnapSackSwarm(InputStream stream, double weightLimit, double volumeLimit) {
        super();
        this.weightLimit = weightLimit;
        this.volumeLimit = volumeLimit;
        try {
            buildPackages(stream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public double convertType(Boolean value) {
        return value ? 1 : 0;
    }

    @Override
    public String getCurrentStats() {
        double totalValue = 0;
        double totalWeight = 0;
        int count = 0;
        for (int i = 0; i < swarm.dimensions; i++) {
            if (swarm.bestPosition.getAxis(i)) {
                totalValue += packages.get(i).getValue();
                totalWeight += packages.get(i).getWeight();
                count++;
            }
        }
        return String.format("Selected pacakges:%d  Sum Weight:%.3f  Sum Value:%.3f", count, totalWeight, totalValue);
    }

    @Override
    public double getFitness(Position<Boolean> position) {
        double totWeight = 0;
        double totValue = 0;
        for (int i = 0; i < swarm.dimensions; i++) {
            if (position.getAxis(i)) {
                Package p = packages.get(i);
                totValue += p.getValue();
                totWeight += p.getWeight();
            }
        }
        double value = 1/totValue+ (totWeight > weightLimit ? Math.abs(totWeight - weightLimit) + 1 : 1);
        //System.out.printf("Totweight:%.2f, weightLimit:%.2f fitness:%.6f\n", totWeight, weightLimit, value);
        return value;
    }

    @Override
    public double getDistance(Particle p1, Particle p2) {
        int distance = 0;
        for (int i = 0; i < swarm.dimensions; i++) {
            if (p1.getPosition() != p2.getPosition().getAxis(i))
                distance++;
        }
        return distance;
    }

    @Override
    public Boolean getRandomPosition(int axisIndex) {
        return Math.random() < 0.5;
    }

    @Override
    public Boolean getNewPosition(double currentVelocity, Boolean currentPos) {
        //return Math.random() >= 1/(1+currentVelocity);
        return Math.random() >= Math.exp(-1.0 * currentVelocity);

       // return (0.5 + 0.5 * Math.random()) <= (1 / (1 + Math.exp(-currentVelocity)));
    }

    public void buildPackages(InputStream fis) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(fis));
        String line;

        while ((line = br.readLine()) != null) {
            String[] param = line.split(",");
            packages.add(new Package(Double.parseDouble(param[0]), Double
                    .parseDouble(param[1]), false));
        }

        br.close();
    }

    public void printStats() {
        double totalValue = 0;
        double totalWeight = 0;
        int count = 0;
        for (int i = 0; i < swarm.dimensions; i++) {
            if (swarm.bestPosition.getAxis(i)) {
                totalValue += packages.get(i).getValue();
                totalWeight += packages.get(i).getWeight();
                count++;
            }
        }
        System.out.printf("Number:%d  Weight:%.3f  Value:%.3f", count, totalWeight, totalValue);
    }

    public class Package {

        private final double value;
        private final double weight;
        private final double volume;

        public Package(double value, double weight, boolean volume) {
            this.value = value;
            this.weight = weight;
            this.volume = (volume) ? 1 + Math.random() * 4 : 0;
        }

        public double getValue() {
            return value;
        }

        public double getWeight() {
            return weight;
        }

        public double getVolume() {
            return volume;
        }

        public String toString() {
            return "[Package [Value " + value + "] [Weight " + weight + "] [Volume " + volume + "]]";
        }
    }
}
