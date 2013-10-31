package task4;

public class Package {
	
	double value;
	double weight;
	double volume;
	
	public Package(double value, double weight){
		this.value = value;
		this.weight = weight;
		
		volume = (1 + (int)(Math.random()*5));
	}
	
	public String toString(){
		return "Package: " + value + " : " + weight + " " + volume;
	}
}
