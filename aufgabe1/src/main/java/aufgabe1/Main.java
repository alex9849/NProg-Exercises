package aufgabe1;

import util.Tools;

import java.util.concurrent.TimeUnit;

public class Main {

    private static volatile boolean running = true;

    public static void main(String... args) {

        new Thread(() -> {
            Tools.sleep(3, TimeUnit.SECONDS);
            running = false;
        }).start();

        while (running) {
            System.out.println("Hello world");
            Tools.sleep(500, TimeUnit.MILLISECONDS);
        }
    }

}
