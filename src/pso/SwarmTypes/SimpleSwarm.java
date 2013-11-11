package pso.SwarmTypes;

import pso.Particle;
import pso.Position;
import pso.SwarmType;

public class SimpleSwarm extends SwarmType<Float> {

    @Override
    public double getFitness(Position<Float> position) {
        double fitness = 0;
        for (int i = 0; i < swarm.dimensions; i++) {
            fitness += Math.pow(position.getAxis(i), 2);
        }
        return fitness;
    }

    @Override
    public double getDistance(Particle<Float> p1, Particle<Float> p2) {
        double distance = 0;
        for (int i = 0; i < swarm.dimensions; i++) {
            distance += Math.pow(p1.getPosition().getAxis(i) - p2.getPosition().getAxis(i), 2);
        }
        return distance;
    }

    @Override
    public Float getRandomPosition(int axisIndex) {
          //To change body of implemented methods use File | Settings | File Templates.
        return  (float)(-(swarm.region / 2) + Math.random() * swarm.region);
    }

    @Override
    public double convertType(Float value) {
        return (double)value;
    }

    @Override
    public String getCurrentStats() {
        return String.format("Best fitness:%f",swarm.bestPosition.getFitness());
    }

    @Override
    public Float getNewPosition(double currentVelocity, Float currentPos, int axisIndex) {
        return (float)(currentPos + currentVelocity);
    }
}
