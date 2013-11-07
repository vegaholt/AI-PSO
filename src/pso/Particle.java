package pso;

public class Particle<T> {
    public int index;
    private Swarm<T> swarm;
    private Position<T> position;
    private Position<T> bestPosition;
    //private Position<T> bestPosition;
    private Velocity velocity;

    public Particle(Swarm<T> swarm, int index) {
        // Set swarm
        this.index = index;
        this.swarm = swarm;

        // Create random positions
        T[] positions = (T[]) new Object[swarm.dimensions];
        for (int i = 0; i < swarm.dimensions; i++) {
            positions[i] = swarm.type.getRandomPosition(i);
        }

        // Set position
        position = new Position<T>(positions, swarm.type);

        // Set as bestPosition
        bestPosition = new Position<T>(positions, swarm.type);

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
        // Use global bestPosition if neighbourCount is 0
        Position<T> globalBest = swarm.getBestPosition(this);
        for (int i = 0; i < swarm.dimensions; i++) {
            velocity.setVelocity(i,swarm.type.getNewVelocity(globalBest.getPosition(i),
                    this.bestPosition.getPosition(i),
                    this.position.getPosition(i),
                    this.velocity.getVelocity(i)));
        }
    }

    /*
    *     private void updateVelocity() {
        double r1 = Math.random();
        double r2 = Math.random();

        double totalVelocity = 0;
        double maxVelocity = 7;
        double scale;

        // Calculate Total new velocity
        for (int i = 0; i < swarm.dimensions; i++) {

            double newVelocity = (velocity.getVelocity(i) * inertiaWeight)
                    + (swarm.c1 * r1 * (bestPosition.getPosition(i) - position
                    .getPosition(i)))
                    + (swarm.c2 * r2 * (swarm.getBestPosition(this)
                    .getPosition(i) - position.getPosition(i)));

            totalVelocity += Math.pow(newVelocity, 2);
        }
        totalVelocity = Math.sqrt(totalVelocity);

        // Calculate scale for new Velocity
        scale = (totalVelocity > maxVelocity) ? maxVelocity/totalVelocity : 1;

        // Set new Velocity
        for (int i = 0; i < swarm.dimensions; i++) {

            double newVelocity = (velocity.getVelocity(i) * inertiaWeight)
                    + (swarm.c1 * r1 * (bestPosition.getPosition(i) - position
                    .getPosition(i)))
                    + (swarm.c2 * r2 * (swarm.getBestPosition(this)
                    .getPosition(i) - position.getPosition(i)));

            newVelocity*=scale;

            velocity.setVelocity(i, newVelocity);
        }

        inertiaWeight -= coolingRate;
    }*/

    private void updatePosition() {
        for (int i = 0; i < swarm.dimensions; i++) {
            position.setPosition(i,swarm.type.getNewPosition(velocity.getVelocity(i),
                    position.getPosition(i)));
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

    public Position<T> getPosition() {
        return position;
    }

    public Position<T> getBestPosition() {
        return bestPosition;
    }

    public Velocity getVelocity() {
        return velocity;
    }

    public String toString() {
        return "[Particle " + position + " " + velocity + "]";
    }
}
