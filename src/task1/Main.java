package task1;

public class Main {
	public static void main(String[] args) {

		// One dimension
		Swarm swarm = new Swarm(100, 2, 800, 1000, 0.001);
		swarm.initSwarm();
		swarm.run();

		// Two dimensions
		/*swarm = new Swarm(100, 2, 50, 1000, 0.001);
		swarm.initSwarm();
		swarm.run();*/
		
	}
}
