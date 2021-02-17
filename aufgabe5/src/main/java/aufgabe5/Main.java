package aufgabe5;

import java.util.Optional;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class Main {

    public static void main(String... args) {
        StringProducer producer = new StringProducer(1000, 10, 2);
        BlockingQueue<Optional<String>> analyzerOutputQueue = new ArrayBlockingQueue<>(10);
        StringAnalyzer analyzer1 = new StringAnalyzer(producer.getOutputQueue(), analyzerOutputQueue);
        StringAnalyzer analyzer2 = new StringAnalyzer(producer.getOutputQueue(), analyzerOutputQueue);
        OutputConsumer counter = new OutputConsumer(analyzerOutputQueue);
        new Thread(producer).start();
        new Thread(analyzer1).start();
        new Thread(analyzer2).start();
        new Thread(counter).start();
    }

}
