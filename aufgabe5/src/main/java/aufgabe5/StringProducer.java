package aufgabe5;

import util.Tools;

import java.util.Optional;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class StringProducer implements Runnable {
    private final int followingConsumerCount;
    private final int stringLength;
    private final BlockingQueue<Optional<String>> queue;
    private int amount;

    public StringProducer(int amount, int stringLength, int followingConsumerCount) {
        this.amount = amount;
        this.followingConsumerCount = followingConsumerCount;
        this.stringLength = stringLength;
        this.queue = new ArrayBlockingQueue<>(10);
    }

    @Override
    public void run() {
        while (0 < amount--) {
            try {
                queue.put(Optional.of(Tools.getRandomString(stringLength)));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        for (int i = 0; i < followingConsumerCount; i++) {
            try {
                queue.put(Optional.empty());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public BlockingQueue<Optional<String>> getOutputQueue() {
        return queue;
    }
}
