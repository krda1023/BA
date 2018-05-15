/*
/*
This is source-code for the Article at: 
http://www.codeproject.com/Articles/677591/Defining-Custom-Source-Event-Listener-in-Java

Note:
- Modified: SpeedEvent does not extend java.util.EventObject, and SpeedListener does not extend java.util.EventListener
- Extend if want to use info of Car, but to make this 'pure' custom event, I've decided not to extend EventObject.
*/

public class SpeedEvent { // extends java.util.EventObject

    private int maxSpeed;
    private int minSpeed;
    private int currentSpeed;

    public SpeedEvent(int maxSpeed, int minSpeed, int currentSpeed) {
        // public SpeedEvent(Object source, int maxSpeed, int minSpeed, int currentSpeed) {
        // super(source);
        this.maxSpeed = maxSpeed;
        this.minSpeed = minSpeed;
        this.currentSpeed = currentSpeed;
    }

    public int getMaxSpeed() {
        return maxSpeed;
    }

    public int getMinSpeed() {
        return minSpeed;
    }

    public int getCurrentSpeed() {
        return currentSpeed;
    }

    
}
