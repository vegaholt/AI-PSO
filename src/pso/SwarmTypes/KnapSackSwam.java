package pso.SwarmTypes;

import pso.Particle;
import pso.Position;
import pso.Swarm;
import pso.SwarmType;

import java.io.*;
import java.nio.charset.Charset;
import java.util.ArrayList;

public class KnapSackSwam implements SwarmType<Boolean> {
    public final double weightLimit;
    public final double volumeLimit;
    public ArrayList<Package> packages;
    public Swarm<Boolean> swarm;

    public KnapSackSwam(double weightLimit, double volumeLimit) {
        packages = new ArrayList<Package>();
        this.weightLimit = weightLimit;
        this.volumeLimit = volumeLimit;

        try {
            buildPackages();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setSwarm(Swarm<Boolean> swarm) {
        this.swarm = swarm;
    }

    @Override
    public double getFitness(Position<Boolean> position) {
        double totWeight = 0;
        double totValue = 0;
        for (int i = 0; i < swarm.dimensions; i++) {
             if(position.getPosition(i)){
                 Package p = packages.get(i);
                 totValue+= p.getValue();
                 totWeight += p.getWeight();
             }
        }
        return (1.0/totValue) * (totWeight > weightLimit ? 100000 : 1);
    }

    @Override
    public double getDistance(Particle p1, Particle p2) {
        int distance = 0;
        for (int i = 0; i < swarm.dimensions; i++) {
            if(p1.getPosition() != p2.getPosition().getPosition(i))
                distance++;
        }
        return distance;
    }

    @Override
    public Boolean getRandomPosition(int axisIndex) {
        return Math.random() < 0.5;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public double getNewVelocity(Boolean globalBest, Boolean localBest, Boolean currentPos, double currentVelocity) {
        double r1 = Math.random();
        double r2 = Math.random();
        int current = currentPos ? 1 : 0;
        return (currentVelocity * swarm.currentInertia)
                + (swarm.c1 * r1 * ((localBest ? 1 : 1) - current))
                + (swarm.c2 * r2 * ((globalBest ? 1 : 0) - current));
    }

    @Override
    public Boolean getNewPosition(double currentVelocity, Boolean currentPos) {
        return currentPos == (((int)Math.round(currentVelocity))%2 == 0);
    }

    public void buildPackages() throws IOException {
        InputStream fis = new FileInputStream("packages.txt");
        BufferedReader br = new BufferedReader(new InputStreamReader(fis,
                Charset.forName("UTF-8")));
        String line;

        while ((line = br.readLine()) != null) {
            String[] param = line.split(",");
            packages.add(new Package(Double.parseDouble(param[0]), Double
                    .parseDouble(param[1]), false));
        }

        br.close();
        br = null;
        fis = null;
    }

    public void printStats(){
        double totalValue = 0;
        double totalWeight = 0;
        int count = 0;
        for(int i = 0; i < swarm.dimensions; i++){
            if(swarm.bestPosition.getPosition(i)){
                totalValue +=packages.get(i).getValue();
                totalWeight += packages.get(i).getWeight();
                count++;
            }
        }
        System.out.printf("Number:%d  Weight:%.3f  Value:%.3f", count ,totalWeight, totalValue);
    }

    class Package {

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
