package pso.SwarmTypes;

import pso.Particle;
import pso.Position;
import pso.SwarmSettings;
import pso.SwarmType;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;

public class KnapSackSwarm extends SwarmType<Boolean> {
    public final double weightLimit;
    public final double volumeLimit;
    public ArrayList<Package> packages = new ArrayList<Package>();
    double[] v0 = new double[2001];
    double[] v1 = new double[2001];
    private boolean useNovelMode;

    public KnapSackSwarm(SwarmSettings settings) throws FileNotFoundException {
        this(settings, new FileInputStream("packages.txt"));
    }

    public KnapSackSwarm(SwarmSettings settings, InputStream stream) {
        super(settings);
        this.useNovelMode = settings.useNovelMode;
        this.weightLimit = Math.floor(settings.dimensions / 2);
        this.volumeLimit = settings.useVolume ? this.weightLimit : 0;
        System.out.printf("KnapSack weight:%f, volum:%f\n", weightLimit, volumeLimit);
        try {
            buildPackages(stream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getCurrentStats() {
        double totalValue = 0;
        double totalWeight = 0;
        double totalVolum = 0;
        int count = 0;
        for (int i = 0; i < swarm.dimensions; i++) {
            if (swarm.bestPosition.getAxis(i)) {
                totalValue += packages.get(i).getValue();
                totalWeight += packages.get(i).getWeight();
                totalVolum += packages.get(i).getVolume();
                count++;
            }
        }
        return String.format("Sum Value:%.3f  Selected pacakges:%d  Sum Weight:%.3f/%.0f Sum Volume:%.3f/%.0f", totalValue, count, totalWeight, weightLimit,
                totalVolum, volumeLimit);
    }

    @Override
    public double getFitness(Position<Boolean> position) {
        double totWeight = 0;
        double totValue = 0;
        double totVolume = 0;
        for (int i = 0; i < swarm.dimensions; i++) {
            if (position.getAxis(i)) {
                Package p = packages.get(i);
                totValue += p.value;
                totWeight += p.weight;
                totVolume += p.volume;
            }
        }

        if (totWeight > weightLimit || totVolume > volumeLimit) {
            int i = swarm.dimensions - 1;
            while (totWeight > weightLimit || totVolume > volumeLimit) {
                if (position.getAxis(i)) {
                    position.setAxis(i, false);
                    Package p = packages.get(i);
                    totValue -= p.value;
                    totWeight -= p.weight;
                    totVolume -= p.volume;
                }
                i--;
            }
        }
        return (1 / totValue) * 1000;
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
    public double getNewVelocity(Boolean globalBest, Boolean localBest, Boolean currentPos, double currentVelocity, double randomC1, double randomC2, int axisIndex) {
        //Standard mode
        if (!useNovelMode)
            return currentVelocity * swarm.currentInertia + randomC1 * (localBest ? 1 : -1) + randomC2 * (globalBest ? 1 : -1);

        //Novel mode
        double localD0, localD1, globalD0, globalD1;
        if (localBest) {
            localD1 = randomC1;
            localD0 = -randomC1;
        } else {
            localD1 = -randomC1;
            localD0 = randomC1;
        }

        if (globalBest) {
            globalD1 = randomC2;
            globalD0 = -randomC2;
        } else {
            globalD1 = -randomC2;
            globalD0 = randomC2;
        }

        v0[axisIndex] = v0[axisIndex] * swarm.currentInertia + localD0 + globalD0;
        v1[axisIndex] = v1[axisIndex] * swarm.currentInertia + localD1 + globalD1;
        return currentPos ? v0[axisIndex] : v1[axisIndex];
    }

    @Override
    public Boolean getRandomPosition(int axisIndex) {
        //Inits velocity for novel mode
        v0[axisIndex] = swarm.maxVelocity - Math.random() * swarm.maxVelocity * 2;
        v1[axisIndex] = v0[axisIndex];
        //Return true for 10%
        return Math.random() < 0.1;
    }

    @Override
    public Boolean getNewPosition(double currentVelocity, Boolean currentPos, int axisIndex) {
        double sig = 1.0 / (1.0 + Math.exp(-1.0 * currentVelocity));
        if(!useNovelMode)
            return Math.random() <= sig;

        return Math.random() <= sig ? !currentPos : currentPos;

    }

    public void buildPackages(InputStream fis) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(fis));
        String line;
        while ((line = br.readLine()) != null) {

            String[] param = line.split(",");
            double value = Double.parseDouble(param[0]);
            double weight = Double.parseDouble(param[1]);
            packages.add(new Package(value, weight, volumeLimit >= 1));

            if (packages.size() == swarm.dimensions) break;

        }
        Collections.sort(packages);
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

    public class Package implements Comparable<Package> {

        public final double ratio;
        private final double value;
        private final double weight;
        private final double volume;

        public Package(double value, double weight, boolean useVolume) {
            this.value = value;
            this.weight = weight;
            this.volume = useVolume ? 1 + Math.random() * 4 : 0;
            this.ratio = value / (weight + volume);
            System.out.println(this.toString());
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
            return "[Package [Value " + value + "] [Weight " + weight + "] [Volume " + volume + "][Ratio " + ratio + "]";
        }

        @Override
        public int compareTo(Package o) {
            return this.ratio < o.ratio ? 1 : this.ratio == o.ratio ? 0 : -1;
        }
    }
}
