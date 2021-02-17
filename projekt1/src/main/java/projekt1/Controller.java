package projekt1;

// Main-Controller, der alle Eingaben entgegen nimmt
// und die anderen Controller koordiniert
public class Controller {
  private volatile State state;

  public Controller() {
    // Init state
    this.state = State.onGroundFloorAndClose;
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
        break;
      case onGroundFloorAndOpen:
        break;
      case onFirstFloorAndClose:
        this.state = State.onFirstFloorAndOpen;
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
        break;
      case onFirstFloorAndClose:
        break;
      case onFirstFloorAndOpen:
        this.state = State.onFirstFloorAndClose;
        break;
      default:
        System.err.println("Error processing event !");
    }
  }

  private enum State {
    onGroundFloorAndClose, onGroundFloorAndOpen, onFirstFloorAndClose, onFirstFloorAndOpen
  }
}
