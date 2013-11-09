package pso;

public class Position<T>{
    private T[] positions;
    private double fitness;
    private boolean fitnessChanged = true;
    private final SwarmType<T> calculator;
    public final int length;
    public Position(T[] positions, SwarmType<T> calculator) {
        this.positions = (T[]) new Object[positions.length];
        this.length = positions.length;
//        this.positions = new Class<T>[positions.length];
//        Array.newInstance(T,positions.length)
        this.calculator = calculator;
        System.arraycopy(positions, 0, this.positions, 0, positions.length);

        // Calculate fitness
        //calculateFitness();
        getFitness();
        fitnessChanged = false;

    }

    public void setAxis(int index, T position) {
        positions[index] = position;
        fitnessChanged = true;
    }

    public T getAxis(int index) {
        return positions[index];
    }

    public double getFitness() {
        if (fitnessChanged) {
            fitness = calculator.getFitness(this);
            fitnessChanged = false;
        }
        return fitness;
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
