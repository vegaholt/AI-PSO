package pso;

public class Particle {

    private Swarm swarm;
    private Velocity velocity;
    private Position position;
    private Position bestPosition;
    private double inertiaWeight;
    private double coolingRate;
    public final int index;

    public Particle(Swarm swarm, int index) {
        // Set swarm
        this.swarm = swarm;
        this.index = index;

        //Set weight
        inertiaWeight = swarm.inertiaWeightStart;


        // Calculate coolingRate
        if (swarm.inertiaWeightStart == swarm.inertiaWeightEnd) {
            coolingRate = 0;
        } else {
            coolingRate = (swarm.inertiaWeightStart - swarm.inertiaWeightEnd) / swarm.iterations;
        }

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
            velocities[i] = -(swarm.region / 2000) + Math.random() * swarm.region / 1000;
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
        double r1 = Math.random();
        double r2 = Math.random();

        // Use global bestPosition if neighbourCount is 0
        for (int i = 0; i < swarm.dimensions; i++) {

            double newVelocity = (velocity.getVelocity(i) * inertiaWeight)
                    + (swarm.c1 * r1 * (bestPosition.getPosition(i) - position
                    .getPosition(i)))
                    + (swarm.c2 * r2 * (swarm.getBestPosition(this)
                    .getPosition(i) - position.getPosition(i)));

            velocity.setVelocity(i, newVelocity);
        }
        inertiaWeight -= coolingRate;
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

            swarm.updateBestPosition(bestPosition);
        }
    }

    public Position getPosition() {
        return position;
    }

    public Position getBestPosition(){
        return bestPosition;
    }

    public String toString() {
        return "[Particle " + position + " " + velocity + "]";
    }
}
