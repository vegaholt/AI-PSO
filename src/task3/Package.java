package task3;

public class Package {
	
	double value;
	double weight;
	
	public Package(double value, double weight){
		this.value = value;
		this.weight = weight;
	}
	
	public String toString(){
		return "Package: " + value + " : " + weight;
	}
}
