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
    public double averageRatio;
    double[] v0 = new double[2001];
    double[] v1 = new double[2001];
    private int indexWeight;

    public KnapSackSwarm(SwarmSettings settings) throws FileNotFoundException {
        this(settings, new FileInputStream("packages.txt"));
    }

    public KnapSackSwarm(SwarmSettings settings, InputStream stream) {
        super(settings);
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
        return String.format("Selected pacakges:%d  Sum Weight:%.3f  Sum Value:%.3f  WeightLimit:%.0f", count, totalWeight, totalValue, weightLimit);
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

        if (totWeight > weightLimit) {
            int i = swarm.dimensions - 1;
            while (totWeight > weightLimit) {
               //i = (int) (Math.random() * packages.size());
                if (position.getAxis(i)) {
                    position.setAxis(i, false);
                    Package p = packages.get(i);
                    totValue -= p.value;
                    totWeight -= p.weight;
                }
                i--;
            }
        }
//        double value = 1/(totValue)+ (totWeight > weightLimit ? Math.abs(totWeight - weightLimit)*10 + 1  : 1);
        //System.out.printf("Totweight:%.2f, weightLimit:%.2f fitness:%.6f\n", totWeight, weightLimit, value);
        //System.out.println( 10000 - totValue + (totWeight > weightLimit ? (totWeight - weightLimit)*10 : 0));
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
       // System.out.println( Math.abs(currentVelocity) * swarm.currentInertia + randomC1 * convertType(localBest)*10 + randomC2 *convertType(globalBest)*10);
        return Math.abs(currentVelocity) * swarm.currentInertia + randomC1 * convertType(localBest != currentPos) + randomC2 *convertType(globalBest != currentPos);
//        double localD0, localD1, globalD0, globalD1;
//        if (localBest) {
//            localD1 = randomC1;
//            localD0 = -randomC1;
//        } else {
//            localD1 = -randomC1;
//            localD0 = randomC1;
//        }
//
//        if (globalBest) {
//            globalD1 = randomC2;
//            globalD0 = -randomC2;
//        } else {
//            globalD1 = -randomC2;
//            globalD0 = randomC2;
//        }
//
//        v0[axisIndex] = v0[axisIndex] + localD0 + globalD0;
//        v1[axisIndex] = v1[axisIndex] + localD1 + globalD1;
//        return currentPos ? v0[axisIndex] : v1[axisIndex];
        // v0[axisIndex] = currentVelocity*swarm.currentInertia + localD0 + localD1;

        //   return super.getNewVelocity(globalBest, localBest, currentPos, currentVelocity, randomC1, randomC2);    //To change body of overridden methods use File | Settings | File Templates.
    }

    @Override
    public Boolean getRandomPosition(int axisIndex) {
        v0[axisIndex] = Math.random() * swarm.maxVelocity;
        v1[axisIndex] = Math.random() * swarm.maxVelocity;
        return Math.random() < 0.1;
//        return axisIndex < indexWeight;
    }

    @Override
    public Boolean getNewPosition(double currentVelocity, Boolean currentPos, int axisIndex) {

//        System.out.println(currentVelocity);
       // currentVelocity = Math.min(4.25, Math.abs(currentVelocity));
        //System.out.printf("new Vell2%f",currentVelocity);
        double sig = 1.0 / (1.0 + Math.exp(-1.0*currentVelocity));
        //boolean newPos = Math.random() < sig;

  //      if(currentVelocity > 1)
    //        System.out.println(currentVelocity);
        return Math.random() <= sig;// && axisIndex < Math.random() * swarm.dimensions;
        // System.out.printf("Vel:%f  currentPos:%b  newPos:%b\n",currentVelocity, currentPos, newPos);
        //return newPos;


        //return Math.random() > 1 / (1+ Math.abs(currentVelocity)*100000) ? !currentPos : currentPos;
        //  return Math.abs(currentVelocity) > Math.random()*0.1 ? !currentPos : currentPos;
        //Sigmond function
        //return Math.random() <= sig;
        // System.out.printf("Vel:%f  sig:%f\n",currentVelocity, sig);
        // return packages.get(axisIndex).ratio > averageRatio * Math.random(); //Math.random() * 0.99 + 0.01);
        //  if (Math.random()<= sig) {
        //    // return true;
        //}

        // System.out.println(sig);
        // return true;

        // return Math.random() <= sig ? packages.get(axisIndex).value / packages.get(axisIndex).weight > averageRatio : false;

        //System.out.println(currentVelocity);
        //return Ma;
        //return Math.random() > Math.exp(-currentVelocity);
        // return (0.5 + 0.5 * Math.random()) <= (1 / (1 + Math.exp(-currentVelocity)));
    }

    public void buildPackages(InputStream fis) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(fis));
        String line;
        averageRatio = 0;
        while ((line = br.readLine()) != null) {

            String[] param = line.split(",");
            double value = Double.parseDouble(param[0]);
            double weight = Double.parseDouble(param[1]);
            packages.add(new Package(value, weight, volumeLimit >= 1));

            if (packages.size() == swarm.dimensions) break;

        }
        Collections.sort(packages);
        double sumWeight = 0;

        for (Package aPackage : packages) {
            sumWeight += aPackage.weight;
            if (sumWeight > weightLimit) break;
            indexWeight++;
        }
        System.out.println();
        averageRatio = averageRatio / packages.size();
        System.out.println(averageRatio);

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
