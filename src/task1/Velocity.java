package task1;

public class Velocity {

	private double velocities[];

	public Velocity(double[] velocities) {
		this.velocities = velocities;
	}

	public void setVelocity(int index, double velocity) {
		velocities[index] = velocity;
	}

	public double getVelocity(int index) {
		return velocities[index];
	}

	public String toString() {
		String string = "[Velocity: ";

		for (int i = 0; i < velocities.length; i++) {
			string += velocities[i] + " ";
		}
		string += "]";
		
		return string;
	}
	
}
