package task1;

public class Particle {

	private Swarm swarm;
	private Velocity velocity;
	private Position position;
	private Position bestPosition;

	private int dimensions;
	private double c1, c2;

	public Particle(Swarm swarm, int dimensions, double region) {

		// Set swarm
		this.swarm = swarm;

		// Set dimensions
		this.dimensions = dimensions;

		// Calculate c1 and c2
		c1 = Math.random() * 2;
		c2 = Math.random() * 2;

		// Create random positions
		double[] positions = new double[dimensions];
		for (int j = 0; j < positions.length; j++) {
			positions[j] = -(region / 2) + Math.random() * region;
		}

		// Set position
		position = new Position(positions);

		// Create random velocity - Kan settes til c1/c2
		double[] velocities = new double[dimensions];
		for (int j = 0; j < velocities.length; j++) {
			velocities[j] = -(region / 2) + Math.random() * region;
		}

		// Set velocity
		velocity = new Velocity(velocities);

		// Set bestPosition
		bestPosition = position;

		System.out.println(this);
	}

	public void updateParticle() {
		updateVelocity();
		updatePosition();
		updateBestPosition();
	}

	private void updateVelocity() {

		for (int i = 0; i < dimensions; i++) {

			double newVelocity = velocity.getVelocity(i) * 0.9
					+ (c1 * Math.random() * (bestPosition.getPosition(i) - position
							.getPosition(i)))
					+ (c2 * Math.random() * (swarm.bestPosition.getPosition(i) - position
							.getPosition(i)));

			velocity.setVelocity(i, newVelocity);
		}
		
		// @Tip Weight on this.vel
		// @Tip Max-min for velocity
	}

	private void updatePosition() {
		for (int i = 0; i < dimensions; i++) {
			double newPosition = position.getPosition(i)
					+ velocity.getVelocity(i);
			position.setPosition(i, newPosition);
		}

	}

	private void updateBestPosition() {
		if (position.getFitness() < bestPosition.getFitness()) {
			bestPosition = position;
			System.out.println("LB updated: " + position);
			swarm.updateBestPosition(bestPosition);
		}
	}

	public Position getBestPosition() {
		return bestPosition;
	}

	public String toString() {
		return "[Particle " + position + " " + velocity + "]";
	}
}
