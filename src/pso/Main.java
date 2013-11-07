package pso;

import pso.SwarmTypes.KnapSackSwam;
import pso.SwarmTypes.SimpleSwarm;

public class Main<T> {
    T[] test;

    public Main() {
        test = (T[]) new Object[100];
    }

    public static void main(String[] args) {
//
        new Main<Double>();
        //swarm (swarmSize, dimensions, region, inertiaWeightStart, inertiaWeightEnd, c1, c2, iterations, acceptanceValue)
        // One dimension
        //Swarm swarm = new Swarm(20, 1, 1000, 1, 0.4, 0.01, 0.01, 3, 1000, 0.001);
        //swarm.initSwarm();
        //swarm.run();i

      //  SimpleSwarm s = new SimpleSwarm();
      //  s.swarm.initSwarm();
      //  s.swarm.run();
        KnapSackSwam knapSackSwam = new KnapSackSwam(1000,0);
        Swarm<Boolean> swarm = new Swarm<Boolean>(knapSackSwam, 2000, 2000, 1000, 1, 0.4, 0.01, 0.01, 0, 1000, 0.001);
        swarm.initSwarm();
        swarm.run();
        knapSackSwam.printStats();
        // Two dimensions
        //swarm = new Swarm(100, 2, 50, 0.9, 0.5, 1.5, 10000, 0.001);
        //swarm.initSwarm();
        //swarm.run();

    }
}
