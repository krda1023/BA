import java.util.ArrayList;
import java.util.EventObject;

public class RouteServiceEvent extends EventObject{

	double[] durations;
	Tour best;
	ArrayList<City> Nodes;
	ArrayList<City> Intersection;
	public RouteServiceEvent(GA Opti, ArrayList<City> nodes, ArrayList<City> intersections, double[] durations, Tour best) {
		super(Opti);
		this.Nodes=nodes;
		this.Intersection=intersections;
		this.durations=durations;
		this.best=best;
	}
}
