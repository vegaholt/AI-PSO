package pso;

class ParticleNeighbour implements Comparable<ParticleNeighbour> {
    public int index;
    public double distance;

    public ParticleNeighbour(int index, double distance) {
        this.index = index;
        this.distance = distance;
    }

    public void set(int index, double distance) {
        this.distance = distance;
        this.index = index;
    }

   @Override
    public int compareTo(ParticleNeighbour o) {
        return distance < o.distance ? -1 : distance > o.distance ? 1 : 0;
    }
}

