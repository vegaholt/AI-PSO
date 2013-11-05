package task1;

public class Particle {
	
	private Swarm swarm;
	private Velocity velocity;
	private Position position;
	private Position bestPosition;
	private int dimensions;
	private double region;
	private double c1, c2;

	public Particle(Swarm swarm, int dimensions, double region) {
		// Set swarm
		this.swarm = swarm;

		// Set dimensions
		this.dimensions = dimensions;
		
		// Set region
		this.region = region;

		// Calculate c1 and c2
		c1 = Math.random() * 2;
		c2 = Math.random() * 2;

		// Create random positions
		double[] positions = new double[dimensions];
		for (int i = 0; i < positions.length; i++) {
			positions[i] = -(region / 2) + Math.random() * region;
		}

		// Set position
		position = new Position(positions);
		
		// Set as bestPosition
		bestPosition = new Position(positions);
		
		// Create random velocity - Kan settes til c1/c2
		double[] velocities = new double[dimensions];
		for (int i = 0; i < velocities.length; i++) {
			velocities[i] = -(region / 2) + Math.random() * region;
		}

		// Set velocity
		velocity = new Velocity(velocities);
		
	}

	public void updateParticle() {
		updateVelocity();
		updatePosition();
		updateBestPosition();
	}

	private void updateVelocity() {
		for (int i = 0; i < dimensions; i++) {
			//Weighted inertia
			double newVelocity = (velocity.getVelocity(i) * 0.9)
					+ (c1 * Math.random() * (bestPosition.getPosition(i) - position
							.getPosition(i)))
					+ (c2 * Math.random() * (swarm.getBestPosition()
							.getPosition(i) - position.getPosition(i)));
			
			//Max min for velocity. value?
			if(newVelocity > region/2) newVelocity = region/2;
			if(newVelocity < -region/2) newVelocity = -region/2;
			
			velocity.setVelocity(i, newVelocity);
		}
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
			
			for (int i = 0; i < dimensions; i++) {
				bestPosition.setPosition(i, position.getPosition(i));
			}
			
			System.out.println("LB updated: " + position);
			swarm.updateBestPosition(bestPosition);
		}
	}
	
	public Position getPosition(){
		return position;
	}

	public String toString() {
		return "[Particle " + position + " " + velocity + "]";
	}
}
