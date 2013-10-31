package task1;

import java.util.ArrayList;

public class Swarm {

	public ArrayList<Particle> swarm;
	public Position bestPosition;
	public final int swarmSize;
	public final int dimensions;
	public final double region;
	public final int iterations;
	public final double acceptanceValue;


	public Swarm(int swarmSize, int dimensions, double region, int iterations,
			double acceptanceValue) {
		swarm = new ArrayList<Particle>();
		this.swarmSize = swarmSize;
		this.dimensions = dimensions;
		this.region = region;
		this.iterations = iterations;
		this.acceptanceValue = acceptanceValue;
		
	}

	public void initSwarm() {
		for (int i = 0; i < swarmSize; i++) {

			// New particle
			Particle particle = new Particle(this, dimensions, region);

			// Add particle to the swarm
			swarm.add(particle);

		}

		// Set best position
		bestPosition = swarm.get(0).getBestPosition();
		
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
	}

	public void updateParticles() {
		for (int i = 0; i < swarm.size(); i++) {
			swarm.get(i).updateParticle();
		}
	}

	public void updateBestPosition(Position position) {
		if (position.getFitness() < bestPosition.getFitness()) {
			bestPosition = position;
			System.out.println("GB updated: " + position);
		}
	}

}
