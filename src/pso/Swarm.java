package pso;

import java.util.ArrayList;

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
    final double[][] neighbours;
    public ArrayList<Particle> particles;
    public Position bestPosition;

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
        this.neighbours = new double[neighbourCount][2];

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
        //1st column holds neighbour index
        //2nd column holds distance to the particle

        //Initiate with the N first particles
        int particleCounter = 0;

        while (particleCounter < neighbourCount) {
            neighbours[particleCounter][0] = particleCounter;
            neighbours[particleCounter][1] = getDistance(particle, particles.get(particleCounter)); //
            // neighbours[neighbourCounter][1] = getDistance(particle, particles.get(particleCounter));
            particleCounter++;

        }

        //Search for the closest neighbours
        for (int i = particleCounter; i < swarmSize; i++) {

            double distance = getDistance(particle, particles.get(i));
            int bestIndex = -1;
            double worstDistance = -1.0;

            for (int j = 0; j < neighbourCount; j++) {
                if (distance < neighbours[j][1] && neighbours[j][1] > worstDistance) {
                    bestIndex = j;
                    worstDistance = neighbours[j][1];
                    break;
                }
            }

            if (bestIndex > -1) {
                neighbours[bestIndex][1] = distance;
                neighbours[bestIndex][0] = i;
            }
        }

        //Returns the position of the neighbour with the best fitnessValue
        double bestFitness = particles.get((int) neighbours[0][0]).getBestPosition().getFitness();
        int bestIndex = 0;

        for (int i = 1; i < neighbourCount; i++) {
            double fitness = particles.get((int) neighbours[i][0]).getBestPosition().getFitness();
            if (fitness < bestFitness) {
                bestFitness = fitness;
                bestIndex = i;
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
}