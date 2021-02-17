package projekt1;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Exchanger;

// Main-Controller, der alle Eingaben entgegen nimmt
// und die anderen Controller koordiniert
public class Controller {
  private volatile State state;
  private final Exchanger<Cabin.State> cabinExchanger;
  private final Exchanger<Door.State> groundDoorExchanger;
  private final Exchanger<Door.State> firstFloorDorExchanger;
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
        cabinExchanger.exchange(Cabin.State.onFirstFloor);
        this.state = State.onFirstFloorAndClose;
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
        cabinExchanger.exchange(Cabin.State.onGroundFloor);
        this.state = State.onGroundFloorAndClose;
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
        groundDoorExchanger.exchange(Door.State.doorIsOpen);
        this.state = State.onGroundFloorAndOpen;
        try {
          groundDoorBarrier.await();
        } catch (BrokenBarrierException e) {
          e.printStackTrace();
        }
        break;
      case onGroundFloorAndOpen:
        break;
      case onFirstFloorAndClose:
        firstFloorDorExchanger.exchange(Door.State.doorIsOpen);
        this.state = State.onFirstFloorAndOpen;
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
        groundDoorExchanger.exchange(Door.State.doorIsClosed);
        this.state = State.onGroundFloorAndClose;
        try {
          groundDoorBarrier.await();
        } catch (BrokenBarrierException e) {
          e.printStackTrace();
        }
        break;
      case onFirstFloorAndClose:
        break;
      case onFirstFloorAndOpen:
        firstFloorDorExchanger.exchange(Door.State.doorIsClosed);
        this.state = State.onFirstFloorAndClose;
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

  private enum State {
    onGroundFloorAndClose, onGroundFloorAndOpen, onFirstFloorAndClose, onFirstFloorAndOpen
  }
}
