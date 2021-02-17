package aufgabe5;

import java.util.Optional;
import java.util.concurrent.BlockingQueue;

public class StringAnalyzer implements Runnable {
    private final BlockingQueue<Optional<String>> inputQueue;
    private final BlockingQueue<Optional<String>> outputQueue;

    public StringAnalyzer(BlockingQueue<Optional<String>> inputQueue, BlockingQueue<Optional<String>> outputQueue) {
        this.inputQueue = inputQueue;
        this.outputQueue = outputQueue;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Optional<String> oString = inputQueue.take();
                if (oString.isEmpty()) {
                    outputQueue.put(oString);
                    break;
                }
                if (oString.get().contains("a")) {
                    outputQueue.put(oString);
                }

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
