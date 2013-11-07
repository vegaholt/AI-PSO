package pso;

public class Package {

    private final double value;
    private final double weight;
    private final double volume;

    public Package(double value, double weight, boolean volume) {
        this.value = value;
        this.weight = weight;
        this.volume = (volume) ? 1 + Math.random() * 4 : 0;
    }

    public double getValue() {
        return value;
    }

    public double getWeight() {
        return weight;
    }

    public double getVolume() {
        return volume;
    }

    public String toString(){
        return "[Package [Value " + value + "] [Weight " + weight + "] [Volume " + volume + "]]";
    }
}
