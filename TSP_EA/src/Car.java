/*
This is source-code for the Article at: 
http://www.codeproject.com/Articles/677591/Defining-Custom-Source-Event-Listener-in-Java

Note:
- Modified: SpeedEvent does not extend java.util.EventObject, and SpeedListener does not extend java.util.EventListener
- Extend if want to use info of Car, but to make this 'pure' custom event, I've decided not to extend EventObject.
*/

import java.util.ArrayList;

public class Car {

    // Inner class
    private static class MySpeedListener implements SpeedListener {

        @Override
        public void speedExceeded(SpeedEvent e) {
            if (e.getCurrentSpeed() > e.getMaxSpeed()) {
                System.out.println("Alert! You have exceeded " + (e.getCurrentSpeed() - e.getMaxSpeed() + " MPH!"));
            }
        }

        @Override
        public void speedGoneBelow(SpeedEvent e) {
            if (e.getCurrentSpeed() < e.getMinSpeed()) {
                System.out.println("Uhm... you are driving " + e.getCurrentSpeed() + " MPH. Speed up!");
            }
        }
    }

    private int maxSpeed;
    private int minSpeed;
    private int currentSpeed;

    private ArrayList<SpeedListener> speedListenerList = new ArrayList<SpeedListener>();

    public static void main(String[] args) {
        Car myCar = new Car(60, 40, 50);

        SpeedListener listener = new MySpeedListener();
        myCar.addSpeedListener(listener);
        // Add more listeners if you want

        myCar.speedUp(50); // fires SpeedEvent
        myCar.speedUp(50); // fires SpeedEvent
        myCar.slowDown(70);
        myCar.slowDown(70); // fires SpeedEvent
    }

    private Car(int max, int min, int cur) {
        this.maxSpeed = max;
        this.minSpeed = min;
        this.currentSpeed = cur;
    }
    
    // Register an event listener
    public synchronized void addSpeedListener(SpeedListener listener) {
        if (!speedListenerList.contains(listener)) {
            speedListenerList.add(listener);
        }
    }

    public void speedUp(int increment) {
        this.currentSpeed += increment;
        if (this.currentSpeed > this.maxSpeed) {
            // fire SpeedEvent
            // processSpeedEvent(new SpeedEvent(this, this.maxSpeed, this.minSpeed, this.currentSpeed));
            // When SpeedEvent does not extend java.util.EventObject
            processSpeedEvent(new SpeedEvent(this.maxSpeed, this.minSpeed, this.currentSpeed));
        }
    }

    public void slowDown(int increment) {
        this.currentSpeed -= increment;
        if (this.currentSpeed < this.minSpeed) {
            // fire SpeedEvent
            // processSpeedEvent(new SpeedEvent(this, this.maxSpeed, this.minSpeed, this.currentSpeed));
            // When SpeedEvent does not extend java.util.EventObject
            processSpeedEvent(new SpeedEvent(this.maxSpeed, this.minSpeed, this.currentSpeed));
        }
    }

    private void processSpeedEvent(SpeedEvent speedEvent) {
        ArrayList<SpeedListener> tempSpeedListenerList;

        synchronized (this) {
            if (speedListenerList.size() == 0) {
                return;
            }
            tempSpeedListenerList = (ArrayList<SpeedListener>) speedListenerList.clone();
        }

        for (SpeedListener listener : tempSpeedListenerList) {
            listener.speedExceeded(speedEvent);
            listener.speedGoneBelow(speedEvent);
        }
    }

}
