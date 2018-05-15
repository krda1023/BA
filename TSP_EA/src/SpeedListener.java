/*
This is source-code for the Article at: 
http://www.codeproject.com/Articles/677591/Defining-Custom-Source-Event-Listener-in-Java

Note:
- Modified: SpeedEvent does not extend java.util.EventObject, and SpeedListener does not extend java.util.EventListener
- Extend if want to use info of Car, but to make this 'pure' custom event, I've decided not to extend EventObject.
*/

public interface SpeedListener { // extends java.util.EventListener 
    public void speedExceeded(SpeedEvent e);
    public void speedGoneBelow(SpeedEvent e);
}
