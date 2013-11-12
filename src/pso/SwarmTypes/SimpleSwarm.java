package pso.SwarmTypes;

import pso.Particle;
import pso.Position;
import pso.SwarmSettings;
import pso.SwarmType;

public class SimpleSwarm extends SwarmType<Float> {


   public SimpleSwarm(SwarmSettings settings){
      super(settings);
   }
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
    public double getNewVelocity(Float globalBest, Float localBest, Float currentPos, double currentVelocity, double randomC1, double randomC2, int axisIndex) {
        return (currentVelocity * swarm.currentInertia)
                + (randomC1 * (localBest - currentPos))
                + (randomC2 * (globalBest - currentPos));
    }

    @Override
    public Float getRandomPosition(int axisIndex) {
          //To change body of implemented methods use File | Settings | File Templates.
        return  (float)(-(swarm.region / 2) + Math.random() * swarm.region);
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
