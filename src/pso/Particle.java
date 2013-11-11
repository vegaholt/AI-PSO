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

        Position<T> globalBest = swarm.getBestPosition(this);

        double randomC1 = Math.random()*swarm.c1;
        double randomC2 = Math.random() * swarm.c2;

        double totalVelocity = 0;
        // Calculate Total new velocity
        for (int i = 0; i < swarm.dimensions; i++) {

            double newVelocity = swarm.type.getNewVelocity(
                    globalBest.getAxis(i),
                    this.bestPosition.getAxis(i),
                    this.position.getAxis(i),
                    this.velocity.getAxis(i),randomC1,randomC2);

            totalVelocity += newVelocity * newVelocity;
            velocity.setAxis(i, newVelocity);
        }
        totalVelocity = Math.sqrt(totalVelocity);
        //If toatl velocity is less than max allowed no point in continue
        if(totalVelocity < swarm.maxVelocity) return;
        // Calculate scale for new Velocity
        double scale = swarm.maxVelocity/totalVelocity;

        // Scale the Velocity down
        for (int i = 0; i < swarm.dimensions; i++) {
            velocity.setAxis(i, velocity.getAxis(i) * scale);
        }
    }

    private void updatePosition() {
        for (int i = 0; i < swarm.dimensions; i++) {
            position.setAxis(i, swarm.type.getNewPosition(velocity.getAxis(i),
                    position.getAxis(i),i));
        }
    }

    private void updateBestPosition() {
        if (position.getFitness() < bestPosition.getFitness()) {

            for (int i = 0; i < swarm.dimensions; i++) {
                bestPosition.setAxis(i, position.getAxis(i));
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
