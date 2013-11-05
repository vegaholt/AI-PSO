package task1;

public class Main {
	public static void main(String[] args) {

        //swarm (swarmSize, dimensions, region, inertiaWeight, c1, c2, iterations, acceptanceValue)
        // One dimension
        Swarm swarm = new Swarm(10, 1, 500, 0.99, 0.01, 0.01, 1000, 0.001);
		swarm.initSwarm();
		swarm.run();

		// Two dimensions
        //swarm = new Swarm(100, 2, 50, 0.9, 0.5, 1.5, 10000, 0.001);
        //swarm.initSwarm();
        //swarm.run();

	}
}
