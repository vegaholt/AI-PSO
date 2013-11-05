package task1;

import java.util.ArrayList;

public class Swarm {

    public final int swarmSize;
    public final int dimensions;
    public final double region;
    public final double inertiaWeight;
    public final double c1, c2;
    public final int iterations;
    public final double acceptanceValue;
    public ArrayList<Particle> swarm;
    public Position bestPosition;

    public Swarm(int swarmSize, int dimensions, double region, double inertiaWeight, double c1,
                 double c2, int iterations, double acceptanceValue) {
        this.swarmSize = swarmSize;
        this.dimensions = dimensions;
        this.region = region;
        this.inertiaWeight = inertiaWeight;
        this.c1 = c1;
        this.c2 = c2;
        this.iterations = iterations;
        this.acceptanceValue = acceptanceValue;
        swarm = new ArrayList<Particle>();

    }

    public void initSwarm() {
        // Create swarm
        for (int i = 0; i < swarmSize; i++) {
            // New particle
            Particle particle = new Particle(this);

            // Add particle to the swarm
            swarm.add(particle);

            System.out.println(particle);
        }

        // Find best position
        int bestIndex = 0;
        double bestFitness = swarm.get(0).getPosition().getFitness();

        for (int i = 1; i < swarm.size(); i++) {
            if (swarm.get(i).getPosition().getFitness() < bestFitness) {
                bestIndex = i;
                bestFitness = swarm.get(i).getPosition().getFitness();
            }
        }

        //Set best position
        double[] positions = new double[dimensions];

        for (int i = 0; i < dimensions; i++) {
            positions[i] = swarm.get(bestIndex).getPosition().getPosition(i);
        }
        bestPosition = new Position(positions);

    }

    public void run() {
        System.out.println("GB before run " + bestPosition);
        int counter = 0;
        while (bestPosition.getFitness() > acceptanceValue
                && counter < iterations) {
            updateParticles();
            counter++;
        }
        System.out.println("GB after run " + bestPosition);
        System.out.println("Counter " + counter);
    }

    public void updateParticles() {
        for (int i = 0; i < swarm.size(); i++) {
            swarm.get(i).updateParticle();
        }
    }

    public void updateBestPosition(Position position) {
        if (position.getFitness() < bestPosition.getFitness()) {

            for (int i = 0; i < dimensions; i++) {
                bestPosition.setPosition(i, position.getPosition(i));
            }

            System.out.println("GB updated: " + position);
        }
    }

    public Position getBestPosition() {
        return bestPosition;
    }
}