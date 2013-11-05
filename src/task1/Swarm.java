package task1;

import java.util.ArrayList;

public class Swarm {

    public final int swarmSize;
    public final int dimensions;
    public final double region;
    public final double inertiaWeightStart;
    public final double inertiaWeightEnd;
    public final double c1, c2;
    public final int iterations;
    public final double acceptanceValue;
    public ArrayList<Particle> particles;
    public Position bestPosition;

    public Swarm(int swarmSize, int dimensions, double region,
                 double inertiaWeightStart, double inertiaWeightEnd,
                 double c1, double c2, int iterations, double acceptanceValue) {
        this.swarmSize = swarmSize;
        this.dimensions = dimensions;
        this.region = region;
        this.inertiaWeightStart = inertiaWeightStart;
        this.inertiaWeightEnd = inertiaWeightEnd;
        this.c1 = c1;
        this.c2 = c2;
        this.iterations = iterations;
        this.acceptanceValue = acceptanceValue;
        particles = new ArrayList<Particle>();

    }

    public void initSwarm() {
        // Create swarm
        for (int i = 0; i < swarmSize; i++) {
            // New particle
            Particle particle = new Particle(this);

            // Add particle to the swarm
            particles.add(particle);

            System.out.println(particle);
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
            System.out.println("GB updated: " + position);
        }
    }

    public Position getBestPosition() {
        return bestPosition;
    }
}