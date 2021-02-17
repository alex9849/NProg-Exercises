package projekt1;


import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Exchanger;

// Controller für die Fahrstuhltür
// Steuert die Motoren zum Öffnen und Schließen der Fahrstuhltür
public class Door implements Runnable {
  private final Exchanger<Controller.State> exchanger;
  private final CyclicBarrier barrier;
  private final boolean isGroundDoor;
  private State state;

  public Door(boolean isGroundDoor) {
    this.state = State.doorIsClosed;
    this.isGroundDoor = isGroundDoor;
    this.exchanger = new Exchanger<>();
    this.barrier = new CyclicBarrier(2);
  }

  Exchanger<Controller.State> getExchanger() {
    return exchanger;
  }

  CyclicBarrier getBarrier() {
    return barrier;
  }

  public State getState() {
    return this.state;
  }

  @Override
  public void run() {
    while (true) {
      try {
        Controller.State cState = exchanger.exchange(null);
        if (isGroundDoor) {
          if (cState == Controller.State.onGroundFloorAndClose) {
            this.state = State.doorIsClosed;
          } else if (cState == Controller.State.onGroundFloorAndOpen) {
            this.state = State.doorIsOpen;
          }
        } else {
          if (cState == Controller.State.onFirstFloorAndClose) {
            this.state = State.doorIsClosed;
          } else if (cState == Controller.State.onFirstFloorAndOpen) {
            this.state = State.doorIsOpen;
          }
        }
        barrier.await();
      } catch (InterruptedException | BrokenBarrierException e) {
        e.printStackTrace();
      }
    }
  }

  public static enum State {
    doorIsOpen, doorIsClosed
  }
}
