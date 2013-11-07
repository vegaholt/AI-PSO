package pso;

public interface SwarmType<T> {
    void setSwarm(Swarm<T> swarm);
    double getFitness(Position<T> position);
    double getDistance(Particle<T> p1, Particle<T> p2);
    T getRandomPosition(int axisIndex);
    double getNewVelocity(T globalBest, T localBest, T currentPos, double currentVelocity);
    T getNewPosition(double currentVelocity, T currentPos);
}
