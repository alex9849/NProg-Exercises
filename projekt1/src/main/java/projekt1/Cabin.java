package projekt1;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Exchanger;

// Controller für die Fahrstuhlkabine
// Steuert die Motoren, die den Fahrstuhl vertikal bewegen
public class Cabin implements Runnable {
  private final Exchanger<Controller.State> exchanger;
  private final CyclicBarrier barrier;
  private State state;

  public Cabin() {
    this.state = State.onGroundFloor;
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
        if (cState == Controller.State.onFirstFloorAndClose) {
          this.state = State.onFirstFloor;
        } else if (cState == Controller.State.onGroundFloorAndClose) {
          this.state = State.onGroundFloor;
        }
        barrier.await();
      } catch (InterruptedException | BrokenBarrierException e) {
        e.printStackTrace();
      }
    }
  }

  public static enum State {
    onGroundFloor, onFirstFloor
  }
}
