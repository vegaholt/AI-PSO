package pso;

public abstract class SwarmType<T> {
    protected final Swarm<T> swarm;
    public SwarmType(SwarmSettings settings){
        this.swarm = new Swarm<T>(this);
        this.swarm.set(settings.swarmSize, settings.dimensions,
                settings.region, settings.inertiaWeightStart,
                settings.inertiaWeightEnd,
                settings.c1,settings.c2,
                settings.neighbourCount,
                settings.iterations,
                settings.acceptanceValue,
                settings.maxVelocity);
    }

    public Swarm<T> getSwarm(){
        return swarm;
    }
    public abstract double getFitness(Position<T> particle);
    public abstract double getDistance(Particle<T> p1, Particle<T> p2);
    public abstract T getRandomPosition(int axisIndex);
    public abstract T getNewPosition(double currentVelocity, T currentPos, int axisIndex);
    public abstract double convertType(T value);
    public abstract String getCurrentStats();

    public double getNewVelocity(T globalBest, T localBest, T currentPos, double currentVelocity, double randomC1, double randomC2, int axisIndex){
        double current = convertType(currentPos);
        return (currentVelocity * swarm.currentInertia)
                + (randomC1 * (convertType(localBest) - current))
                + (randomC2 * (convertType(globalBest) - current));
    }
}
