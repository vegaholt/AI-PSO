package pso;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.TreeSet;

public class Swarm<T> {

    public final int swarmSize;
    public final int dimensions;
    public final double region;
    public final double inertiaWeightStart;
    public final double inertiaWeightEnd;
    public double currentInertia;
    public double coolingRate;
    public final double c1, c2;
    public final int neighbourCount;
    public final int iterations;
    public final double acceptanceValue;
    public final SwarmType<T> type;
    public ArrayList<Particle<T>> particles;
    public Position<T> bestPosition;
    private ParticleNeighbour[] neighbourHolder;
    private TreeSet<ParticleNeighbour> bestNeighbour;

    public Swarm(SwarmType<T> swarmType, int swarmSize, int dimensions, double region,
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
        this.type = swarmType;
        this.bestNeighbour = new TreeSet<ParticleNeighbour>();
        this.neighbourHolder = new ParticleNeighbour[neighbourCount];
        this.particles = new ArrayList<Particle<T>>();
        for (int i = 0; i < neighbourCount; i++) {
            this.neighbourHolder[i] = new ParticleNeighbour(i, i);
        }

        coolingRate = (inertiaWeightStart - inertiaWeightEnd) /iterations;
        swarmType.setSwarm(this);
    }

    public void initSwarm() {
        this.currentInertia = inertiaWeightStart;
        particles.clear();
        // Create swarm
        for (int i = 0; i < swarmSize; i++) {
            // Add particle to the swarm
            particles.add(new Particle<T>(this, i));
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
        T[] positions = (T[])new Object[dimensions];

        for (int i = 0; i < dimensions; i++) {
            positions[i] = particles.get(bestIndex).getPosition().getPosition(i);
        }
        bestPosition = new Position<T>(positions, type);

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

        currentInertia -= coolingRate;
    }

    public void updateBestPosition(Position<T> position) {
        if (position.getFitness() < bestPosition.getFitness()) {
            for (int i = 0; i < dimensions; i++) {
                bestPosition.setPosition(i, position.getPosition(i));
            }
        }
    }

    public Position<T> getBestPosition(Particle<T> particle) {
        //Use global best position if neighbourCount is '0'
        if (neighbourCount == 0) {
            return bestPosition;
        }

        //Build Particle Neighbour list
        //Clearer old list, have to clear list so new list gets sorted
        bestNeighbour.clear();
        //Inserts the first particles Particle Neighbour list to the list
        int i = -1, size = 0;
        while (size < neighbourCount) {
            i++;
            if (particle.index == i) continue;
            neighbourHolder[size].distance = type.getDistance(particle, particles.get(i));
            neighbourHolder[size].index = i;
            bestNeighbour.add(neighbourHolder[size]);
            size++;
        }
        //Search for the closest neighbours
        for (i = neighbourCount + 1; i < swarmSize; i++) {
            if (particle.index == i) continue;
            double distance = type.getDistance(particle, particles.get(i));
            //If distance is better then the highest distance Particle Neighbour add to the list
            //then update Particle Neighbour with new distance and index
            if (distance < bestNeighbour.last().distance) {
                //Must poll worst element and add to make the list sort the item
                ParticleNeighbour n = bestNeighbour.pollLast();
                n.distance = distance;
                n.index = i;
                bestNeighbour.add(n);
            }
        }

        //Returns the position of the Particle Neighbour with the best fitnessValue
        Iterator<ParticleNeighbour> it = bestNeighbour.iterator();
        ParticleNeighbour n = it.next();
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

}
