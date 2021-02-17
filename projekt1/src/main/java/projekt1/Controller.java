package projekt1;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Exchanger;

// Main-Controller, der alle Eingaben entgegen nimmt
// und die anderen Controller koordiniert
public class Controller {
  private volatile State state;
  private final Exchanger<State> cabinExchanger;
  private final Exchanger<State> groundDoorExchanger;
  private final Exchanger<State> firstFloorDorExchanger;
  private final CyclicBarrier cabinBarrier;
  private final CyclicBarrier groundDoorBarrier;
  private final CyclicBarrier firstFloorDoorBarrier;

  public Controller(Cabin cabin, Door groundDoor, Door firstDoor) {
    // Init state
    this.state = State.onGroundFloorAndClose;
    this.cabinExchanger = cabin.getExchanger();
    this.cabinBarrier = cabin.getBarrier();
    this.groundDoorExchanger = groundDoor.getExchanger();
    this.groundDoorBarrier = groundDoor.getBarrier();
    this.firstFloorDorExchanger = firstDoor.getExchanger();
    this.firstFloorDoorBarrier = firstDoor.getBarrier();
  }

  public State getState() {
    return this.state;
  }

  public void processEvent(InputEvent event) throws InterruptedException {
    System.out.println("Controller process " + event);

    switch (event) {
      case up:
        processUpEvent(event);
        break;
      case down:
        processDownEvent(event);
        break;
      case open:
        processOpenEvent(event);
        break;
      case close:
        processCloseEvent(event);
        break;
      default:
        System.err.println("Wrong input event");
    }
  }

  private void processUpEvent(InputEvent event) throws InterruptedException {
    switch (this.state) {
      case onGroundFloorAndClose:
        this.state = State.onFirstFloorAndClose;
        cabinExchanger.exchange(this.state);
        try {
          cabinBarrier.await();
        } catch (BrokenBarrierException e) {
          e.printStackTrace();
        }
        break;
      case onGroundFloorAndOpen:
      case onFirstFloorAndClose:
      case onFirstFloorAndOpen:
        break;
      default:
        System.err.println("Error processing event !");
    }
  }

  private void processDownEvent(InputEvent event) throws InterruptedException {
    switch (this.state) {
      case onGroundFloorAndClose:
      case onGroundFloorAndOpen:
        break;
      case onFirstFloorAndClose:
        this.state = State.onGroundFloorAndClose;
        cabinExchanger.exchange(this.state);
        try {
          cabinBarrier.await();
        } catch (BrokenBarrierException e) {
          e.printStackTrace();
        }
        break;
      case onFirstFloorAndOpen:
        break;
      default:
        System.err.println("Error processing event !");
    }
  }

  private void processOpenEvent(InputEvent event) throws InterruptedException {
    switch (this.state) {
      case onGroundFloorAndClose:
        this.state = State.onGroundFloorAndOpen;
        groundDoorExchanger.exchange(this.state);
        try {
          groundDoorBarrier.await();
        } catch (BrokenBarrierException e) {
          e.printStackTrace();
        }
        break;
      case onGroundFloorAndOpen:
        break;
      case onFirstFloorAndClose:
        this.state = State.onFirstFloorAndOpen;
        firstFloorDorExchanger.exchange(this.state);
        try {
          firstFloorDoorBarrier.await();
        } catch (BrokenBarrierException e) {
          e.printStackTrace();
        }
      case onFirstFloorAndOpen:
        break;
      default:
        System.err.println("Error processing event !");
    }
  }

  private void processCloseEvent(InputEvent event) throws InterruptedException {
    switch (this.state) {
      case onGroundFloorAndClose:
        break;
      case onGroundFloorAndOpen:
        this.state = State.onGroundFloorAndClose;
        groundDoorExchanger.exchange(this.state);
        try {
          groundDoorBarrier.await();
        } catch (BrokenBarrierException e) {
          e.printStackTrace();
        }
        break;
      case onFirstFloorAndClose:
        break;
      case onFirstFloorAndOpen:
        this.state = State.onFirstFloorAndClose;
        firstFloorDorExchanger.exchange(this.state);
        try {
          firstFloorDoorBarrier.await();
        } catch (BrokenBarrierException e) {
          e.printStackTrace();
        }
        break;
      default:
        System.err.println("Error processing event !");
    }
  }

  public enum State {
    onGroundFloorAndClose, onGroundFloorAndOpen, onFirstFloorAndClose, onFirstFloorAndOpen
  }
}
