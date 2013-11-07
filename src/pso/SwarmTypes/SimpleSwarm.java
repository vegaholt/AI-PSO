package pso.SwarmTypes;

import pso.Particle;
import pso.Position;
import pso.Swarm;
import pso.SwarmType;

public class SimpleSwarm implements SwarmType<Float> {
    public Swarm<Float> swarm;

    @Override
    public void setSwarm(Swarm<Float> swarm) {
        this.swarm = swarm;
    }

    @Override
    public double getFitness(Position<Float> position) {
        double fitness = 0;
        for (int i = 0; i < swarm.dimensions; i++) {
            fitness += Math.pow(position.getPosition(i), 2);
        }
        return fitness;
    }

    @Override
    public double getDistance(Particle<Float> p1, Particle<Float> p2) {
        double distance = 0;
        for (int i = 0; i < swarm.dimensions; i++) {
            distance += Math.pow(p1.getPosition().getPosition(i) - p2.getPosition().getPosition(i), 2);
        }
        return distance;
    }

    @Override
    public Float getRandomPosition(int axisIndex) {
          //To change body of implemented methods use File | Settings | File Templates.
        return  (float)(-(swarm.region / 2) + Math.random() * swarm.region);
    }

    @Override
    public double getNewVelocity(Float globalBest, Float localBest, Float currentPos, double currentVelocity) {
        double r1 = Math.random();
        double r2 = Math.random();
        return (currentVelocity * swarm.currentInertia)
                + (swarm.c1 * r1 * (localBest - currentPos))
                + (swarm.c2 * r2 * (globalBest - currentPos));
    }

    @Override
    public Float getNewPosition(double currentVelocity, Float currentPos) {
        return (float)(currentPos + currentVelocity);  //To change body of implemented methods use File | Settings | File Templates.
    }
}
