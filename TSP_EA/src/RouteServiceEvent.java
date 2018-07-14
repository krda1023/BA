import java.util.ArrayList;
import java.util.EventObject;
// Event class for activating the Simulation class "Salesman" after a RouteRequest
public class RouteServiceEvent extends EventObject{

//VARIABLES:
	private static final long serialVersionUID = 1L;
	double[] durations;
	Tour best;
	ArrayList<City> Nodes;
	ArrayList<City> Intersection;

//CONSTRUCTOR:
	public RouteServiceEvent(GA Opti, ArrayList<City> nodes, ArrayList<City> intersections, double[] durations, Tour best) {
		super(Opti);
		this.Nodes=nodes;
		this.Intersection=intersections;
		this.durations=durations;
		this.best=best;
	}
	
}
