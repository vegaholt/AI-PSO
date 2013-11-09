package pso;

public abstract class SwarmType<T> {
    protected final Swarm<T> swarm;
    public SwarmType(){
        this.swarm = new Swarm<T>(this);
    }

    public Swarm<T> getSwarm(){
        return swarm;
    }
    public abstract double getFitness(Position<T> position);
    public abstract double getDistance(Particle<T> p1, Particle<T> p2);
    public abstract T getRandomPosition(int axisIndex);
    public abstract T getNewPosition(double currentVelocity, T currentPos);
    public abstract double convertType(T value);
    public abstract String getCurrentStats();

    double getNewVelocity(T globalBest, T localBest, T currentPos, double currentVelocity, double randomC1, double randomC2){
        double current = convertType(currentPos);
        return (currentVelocity * swarm.currentInertia)
                + (randomC1 * (convertType(localBest) - current))
                + (randomC2 * (convertType(globalBest) - current));
    }
}
