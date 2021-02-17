package projekt1;

// Controller für die Fahrstuhlkabine
// Steuert die Motoren, die den Fahrstuhl vertikal bewegen
public class Cabin implements Runnable {
  private State state;

  public Cabin() {
    this.state = State.onGroundFloor;
  }

  public State getState() {
    return this.state;
  }

  @Override
  public void run() {
    //TODO
  }

  private static enum State {
    onGroundFloor, onFirstFloor
  }
}
