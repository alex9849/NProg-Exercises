package projekt1;


// Controller für die Fahrstuhltür
// Steuert die Motoren zum Öffnen und Schließen der Fahrstuhltür
public class Door implements Runnable {
  private State state;

  public Door() {
    this.state = State.doorIsClosed;
  }

  public State getState() {
    return this.state;
  }

  @Override
  public void run() {

  }

  private static enum State {
    doorIsOpen, doorIsClosed
  }
}
