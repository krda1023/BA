import java.util.ArrayList;
import java.util.LinkedList;

public class Geschwindigkeitsfaktoren {
	
	double []Faktoren= {1.2,1.3,1.2,1.1,1,0.9,0.8,0.7,0.8,0.85,0.9,0.95,1,0.9,0.8,0.7,0.75,0.8,0.9,0.95,1,1.05,1.1,1.15};
	
	public double getFaktor(int a) {
		return Faktoren[a];
	}
	
}
