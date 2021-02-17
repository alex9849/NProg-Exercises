package aufgabe5;

import java.util.Optional;
import java.util.concurrent.BlockingQueue;

public class OutputConsumer implements Runnable {
    private final BlockingQueue<Optional<String>> inputQueue;
    private int counter;

    public OutputConsumer(BlockingQueue<Optional<String>> inputQueue) {
        this.inputQueue = inputQueue;
        this.counter = 0;
    }


    @Override
    public void run() {
        while (true) {
            try {
                Optional<String> oString = inputQueue.take();
                if (oString.isEmpty()) {
                    System.out.println("Counted elements: " + counter);
                    break;
                }
                counter++;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
