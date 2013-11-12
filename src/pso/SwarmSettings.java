package pso;

public class SwarmSettings {
    public static SwarmSettings[] tasks = new SwarmSettings[]{
            new SwarmSettings("1a. 1D circle", 0.001, 100, 1, 1000, 0.9, 0.9, 0.8, 1.2, 0, 1000, 1, TypeOfSwarm.SIMPLE_SWARM),
            new SwarmSettings("1c. 2D circle", 0.001, 100, 2, 1000, 0.9, 0.9, 0.8, 1.2, 0, 1000, 1, TypeOfSwarm.SIMPLE_SWARM),
            new SwarmSettings("2a. 1D Nearest Neighbours", 0.001, 100, 1, 1000, 0.9, 0.9, 0.8, 1.2, 3, 1000, 1, TypeOfSwarm.SIMPLE_SWARM),
            new SwarmSettings("2a. 2D Nearest Neighbours", 0.001, 100, 2, 1000, 0.9, 0.9, 0.8, 1.2, 3, 1000, 1, TypeOfSwarm.SIMPLE_SWARM),
            new SwarmSettings("2b. 1D Inertia Change", 0.001, 100, 1, 1000, 1.0, 0.4, 0.8, 1.2, 0, 1000, 1, TypeOfSwarm.SIMPLE_SWARM),
            new SwarmSettings("2b. 2D Inertia Change", 0.001, 100, 2, 1000, 1.0, 0.4, 0.8, 1.2, 0, 1000, 1, TypeOfSwarm.SIMPLE_SWARM),
            new SwarmSettings("2b. 1D Inertia Change Neighbour", 0.001, 100, 1, 1000, 1.0, 0.4, 0.8, 1.2, 3, 1000, 1, TypeOfSwarm.SIMPLE_SWARM),
            new SwarmSettings("2b. 2D Inertia Change Neighbour", 0.001, 100, 2, 1000, 1.0, 0.4, 0.8, 1.2, 3 , 1000, 1, TypeOfSwarm.SIMPLE_SWARM),
            new SwarmSettings("3a. Knapsack", 0.001, 100, 2001, 1, 0.9, 0.9, 0.1, 0.1, 0, 1000, 1, TypeOfSwarm.KNAPSACK),
            new SwarmSettings("3b. Weighted Knapsack", 0.001, 100, 2001, 1000, 0.9, 0.9, 0.2, 1.8, 0, 1000, 1, TypeOfSwarm.KNAPSACK),
            new SwarmSettings("3c. Inertia change and Weighted Knapsack", 0.001, 100, 2001, 1000, 0.9, 0.4, 0.2, 1.8, 0, 1000, 1, TypeOfSwarm.KNAPSACK),
            new SwarmSettings("4a. Knapsack with volume", 0.001, 100, 2001, 1000, 0.9, 0.9, 0.2, 1.0, 0, 1000, 1, TypeOfSwarm.KNAPSACK),
    };
    public String name;
    public double acceptanceValue;
    public int swarmSize;
    public int dimensions;
    public double region;
    public double inertiaWeightStart;
    public double inertiaWeightEnd;
    public double c1, c2;
    public int neighbourCount;
    public int iterations;
    public int simulationCount;
    public double maxVelocity = 6;
    public boolean useVolume = false;
    public boolean useNovelMode = true;
    public TypeOfSwarm type;

    public SwarmSettings(String name, double acceptanceValue, int swarmSize, int dimensions,
                         double region, double inertiaWeightStart, double inertiaWeightEnd,
                         double c1, double c2, int neighbourCount, int iterations, int simulationCount,
                         TypeOfSwarm type) {
        this.name = name;
        this.acceptanceValue = acceptanceValue;
        this.swarmSize = swarmSize;
        this.dimensions = dimensions;
        this.region = region;
        this.inertiaWeightStart = inertiaWeightStart;
        this.inertiaWeightEnd = inertiaWeightEnd;
        this.c1 = c1;
        this.c2 = c2;
        this.neighbourCount = neighbourCount;
        this.iterations = iterations;
        this.simulationCount = simulationCount;
        this.type = type;
    }

    public enum TypeOfSwarm {
        SIMPLE_SWARM, KNAPSACK
    }
}
