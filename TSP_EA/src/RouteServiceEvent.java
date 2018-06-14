import java.util.ArrayList;
import java.util.EventObject;

public class RouteServiceEvent extends EventObject{

	double[] durations;
	ArrayList<City> Nodes;
	ArrayList<City> Intersection;
	public RouteServiceEvent(GA Opti, ArrayList<City> nodes, ArrayList<City> intersections, double[] durations) {
		super(Opti);
		this.Nodes=nodes;
		this.Intersection=intersections;
		this.durations=durations;
	}
}
