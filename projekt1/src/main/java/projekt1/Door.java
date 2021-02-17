package projekt1;


import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Exchanger;

// Controller für die Fahrstuhltür
// Steuert die Motoren zum Öffnen und Schließen der Fahrstuhltür
public class Door implements Runnable {
  private final Exchanger<State> exchanger;
  private final CyclicBarrier barrier;
  private State state;

  public Door() {
    this.state = State.doorIsClosed;
    this.exchanger = new Exchanger<>();
    this.barrier = new CyclicBarrier(2);
  }

  Exchanger<State> getExchanger() {
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
        this.state = exchanger.exchange(null);
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
