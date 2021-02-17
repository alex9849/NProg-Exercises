package aufgabe4;

import util.Tools;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {
    private final static int THREAD_COUNT = 10;
    private final static AtomicInteger iteration = new AtomicInteger();

    public static void main(String... args) {
        CyclicBarrier barrier = new CyclicBarrier(THREAD_COUNT, () -> {
            System.out.println("Iteration: " + iteration.incrementAndGet());
        });
        AtomicBoolean patternFound = new AtomicBoolean();
        List<Worker> workers = new ArrayList<>();
        for (int i = 0; i < THREAD_COUNT; i++) {
            Thread searcher = new Thread(new Worker(barrier, "abcde", patternFound));
            searcher.start();
        }

    }
}

class Worker implements Runnable {
    private final static int STRING_LENGTH = 10;
    private final CyclicBarrier barrier;
    private final String searchPattern;
    private final AtomicBoolean patternFoundGlobal;
    private boolean containsPattern = false;
    private String generatedString;

    Worker(CyclicBarrier barrier, String searchPattern, AtomicBoolean patternFoundGlobal) {
        this.barrier = barrier;
        this.searchPattern = searchPattern;
        this.patternFoundGlobal = patternFoundGlobal;
    }

    @Override
    public void run() {
        do {
            this.generatedString = Tools.getRandomString(STRING_LENGTH);
            this.containsPattern = this.generatedString.contains(this.searchPattern);
            if (this.containsPattern) {
                this.patternFoundGlobal.set(true);
            }
            try {
                barrier.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }
        } while (!this.patternFoundGlobal.get());
        if (this.containsPattern) {
            System.out.println(this.generatedString);
        }
    }
}