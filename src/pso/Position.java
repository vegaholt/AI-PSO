package pso;

public class Position {

    private double[] positions;
    private double fitness;
    private boolean fitnessChanged;

    public Position(double[] positions) {
        this.positions = new double[positions.length];
        System.arraycopy(positions, 0, this.positions, 0, positions.length);

        // Calculate fitness
        calculateFitness();
        fitnessChanged = false;

    }

    public void setPosition(int index, double position) {
        positions[index] = position;
        fitnessChanged = true;
    }

    public double getPosition(int index) {
        return positions[index];
    }

    public double getFitness() {
        if (fitnessChanged) {
            calculateFitness();
            fitnessChanged = false;
        }
        return fitness;
    }

    private void calculateFitness() {
        fitness = 0;
        for (int i = 0; i < positions.length; i++) {
            fitness += Math.pow(positions[i], 2);
        }
    }

    public String toString() {
        String string = "[Position ";

        for (int i = 0; i < positions.length; i++) {
            string += positions[i] + " ";
        }
        string += "]";

        return string;
    }
}
