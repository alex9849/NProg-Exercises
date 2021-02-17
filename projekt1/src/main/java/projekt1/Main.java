package projekt1;


public class Main {

  public static void main(String[] args) throws InterruptedException {
    Cabin cabin = new Cabin();
    Door groundFloorDoor = new Door();
    Door firstFloorDoor = new Door();

    Controller controller = new Controller();
    printState(controller, cabin, groundFloorDoor, firstFloorDoor);

    controller.processEvent(InputEvent.up);
    printState(controller, cabin, groundFloorDoor, firstFloorDoor);

    controller.processEvent(InputEvent.open);
    printState(controller, cabin, groundFloorDoor, firstFloorDoor);

    controller.processEvent(InputEvent.close);
    printState(controller, cabin, groundFloorDoor, firstFloorDoor);

    controller.processEvent(InputEvent.down);
    printState(controller, cabin, groundFloorDoor, firstFloorDoor);


    System.out.println("done");
    System.exit(0);
  }

  private static void printState(Controller controller, Cabin cabin, Door groundFloorDoor, Door firstFloorDoor) {
    StringBuilder strBuilder = new StringBuilder();
    strBuilder.append(String.format("Controler : %21s", controller.getState()));
    strBuilder.append(String.format(" | Cabin : %13s", cabin.getState()));
    strBuilder.append(String.format(" | Ground Door : %12s", groundFloorDoor.getState()));
    strBuilder.append(String.format(" | First Door : %12s", firstFloorDoor.getState()));

    System.out.println(strBuilder.toString());
  }
}
