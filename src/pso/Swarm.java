package pso;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.TreeSet;

public class Swarm {

    public final int swarmSize;
    public final int dimensions;
    public final double region;
    public final double inertiaWeightStart;
    public final double inertiaWeightEnd;
    public final double c1, c2;
    public final int neighbourCount;
    public final int iterations;
    public final double acceptanceValue;
    public ArrayList<Particle> particles;
    public Position bestPosition;
    private Neighbour[] neighbourHolder;
    private TreeSet<Neighbour> bestNeighbour;

    public Swarm(int swarmSize, int dimensions, double region,
                 double inertiaWeightStart, double inertiaWeightEnd,
                 double c1, double c2, int neighbourCount, int iterations, double acceptanceValue) {
        this.swarmSize = swarmSize;
        this.dimensions = dimensions;
        this.region = region;
        this.inertiaWeightStart = inertiaWeightStart;
        this.inertiaWeightEnd = inertiaWeightEnd;
        this.c1 = c1;
        this.c2 = c2;
        this.neighbourCount = neighbourCount;
        this.iterations = iterations;
        this.acceptanceValue = acceptanceValue;
        particles = new ArrayList<Particle>();

        this.bestNeighbour = new TreeSet<Neighbour>();
        this.neighbourHolder = new Neighbour[neighbourCount];

        for (int i = 0; i < neighbourCount; i++) {
            this.neighbourHolder[i] = new Neighbour(i, i);
        }

    }

    public void initSwarm() {
        particles.clear();
        // Create swarm
        for (int i = 0; i < swarmSize; i++) {
            // New particle
            Particle particle = new Particle(this, i);

            // Add particle to the swarm
            particles.add(particle);

        }

        // Find best position
        int bestIndex = 0;
        double bestFitness = particles.get(0).getPosition().getFitness();

        for (int i = 1; i < particles.size(); i++) {
            if (particles.get(i).getPosition().getFitness() < bestFitness) {
                bestIndex = i;
                bestFitness = particles.get(i).getPosition().getFitness();
            }
        }

        //Set best position
        double[] positions = new double[dimensions];

        for (int i = 0; i < dimensions; i++) {
            positions[i] = particles.get(bestIndex).getPosition().getPosition(i);
        }
        bestPosition = new Position(positions);

    }

    public void run() {
        System.out.println("GB before run " + bestPosition);
        System.out.println();

        int counter = 0;
        while (bestPosition.getFitness() > acceptanceValue && counter < iterations) {
            updateParticles();
            counter++;

        }

        System.out.println();
        System.out.println("GB after run " + bestPosition);
        System.out.println("Counter " + counter);
        System.out.println("Fitness " + bestPosition.getFitness());
    }

    public void updateParticles() {
        for (int i = 0; i < particles.size(); i++) {
            particles.get(i).updateParticle();
        }
    }

    public void updateBestPosition(Position position) {
        if (position.getFitness() < bestPosition.getFitness()) {
            for (int i = 0; i < dimensions; i++) {
                bestPosition.setPosition(i, position.getPosition(i));
            }
        }
    }

    public Position getBestPosition(Particle particle) {
        //Use global best position if neighbourCount is '0'
        if (neighbourCount == 0) {
            return bestPosition;
        }

        //Build neighbour list
       //Clearer old list, have to clear list so new list gets sorted
        bestNeighbour.clear();
        //Inserts the first particles neighbour list to the list
        for (int i = 0; i < neighbourCount; i++) {
            neighbourHolder[i].distance = getDistance(particle, particles.get(i));
            neighbourHolder[i].index = i;
            bestNeighbour.add(neighbourHolder[i]);
        }
        //Search for the closest neighbours
        for (int i = neighbourCount + 1; i < swarmSize; i++) {

            double distance = getDistance(particle, particles.get(i));
            //If distance is better then the highest distance neighbour add to the list
            //then update neighbour with new distance and index
            if (distance < bestNeighbour.last().distance) {
                //Must poll worst element and add to make the list sort the item
                Neighbour n = bestNeighbour.pollLast();
                n.distance = distance;
                n.index = i;
                bestNeighbour.add(n);
            }
        }

        //Returns the position of the neighbour with the best fitnessValue
        Iterator<Neighbour> it = bestNeighbour.iterator();
        Neighbour n = it.next();
        double bestFitness = particles.get(n.index).getBestPosition().getFitness();
        int bestIndex = n.index;
        while (it.hasNext()) {
            n = it.next();
            double fitness = particles.get(n.index).getBestPosition().getFitness();
            if (fitness < bestFitness) {
                bestIndex = n.index;
                bestFitness = fitness;
            }
        }
        return particles.get(bestIndex).getBestPosition();
    }

    public double getDistance(Particle p1, Particle p2) {
        double distance = 0;
        for (int i = 0; i < dimensions; i++) {
            distance += Math.pow(p1.getPosition().getPosition(i) - p2.getPosition().getPosition(i), 2);
        }
        return distance;
    }

    class Neighbour implements Comparable<Neighbour> {
        public int index;
        public double distance;

        public Neighbour(int index, double distance) {
            this.index = index;
            this.distance = distance;
        }

        public void set(int index, double distance) {
            this.distance = distance;
            this.index = index;
        }

        @Override
        public int compareTo(Neighbour o) {
            return distance < o.distance ? -1 : distance > o.distance ? 1 : 0;
        }
    }
}
