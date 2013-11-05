package task1;

public class Particle {

    private Swarm swarm;
    private Velocity velocity;
    private Position position;
    private Position bestPosition;

    private double inertiaWeight;
    private double coolingrate;

    public Particle(Swarm swarm) {
        // Set swarm
        this.swarm = swarm;

        //Set weight
        inertiaWeight = swarm.inertiaWeight;

        // Calculate coolingRate
        coolingrate = (1/(double)swarm.iterations)*3/5;

        // Create random positions
        double[] positions = new double[swarm.dimensions];
        for (int i = 0; i < positions.length; i++) {
            positions[i] = -(swarm.region / 2) + Math.random() * swarm.region;
        }

        // Set position
        position = new Position(positions);

        // Set as bestPosition
        bestPosition = new Position(positions);

        // Create random velocity - Kan settes til c1/c2
        double[] velocities = new double[swarm.dimensions];
        for (int i = 0; i < velocities.length; i++) {
            velocities[i] = -(swarm.region / 2000)  + Math.random() * swarm.region / 1000;
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
        double random = Math.random();

        for (int i = 0; i < swarm.dimensions; i++) {

            double newVelocity = (velocity.getVelocity(i) * inertiaWeight)
                    + (swarm.c1 * random * (bestPosition.getPosition(i) - position
                    .getPosition(i)))
                    + (swarm.c2 * random * (swarm.getBestPosition()
                    .getPosition(i) - position.getPosition(i)));

            velocity.setVelocity(i, newVelocity);
        }
    }

    private void updatePosition() {
        for (int i = 0; i < swarm.dimensions; i++) {
            double newPosition = position.getPosition(i)
                    + velocity.getVelocity(i);
            position.setPosition(i, newPosition);
        }
    }

    private void updateBestPosition() {
        if (position.getFitness() < bestPosition.getFitness()) {

            for (int i = 0; i < swarm.dimensions; i++) {
                bestPosition.setPosition(i, position.getPosition(i));
            }

            System.out.println("LB updated: " + position);
            swarm.updateBestPosition(bestPosition);
        }
    }

    public Position getPosition() {
        return position;
    }

    public String toString() {
        return "[Particle " + position + " " + velocity + "]";
    }
}
