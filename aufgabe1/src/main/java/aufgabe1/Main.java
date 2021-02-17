package aufgabe1;

import java.util.concurrent.TimeUnit;
import util.Tools;

public class Main {

    public static void main(String... args) {
        while (!Thread.currentThread().isInterrupted()) {
            System.out.println("Hello world");
            Tools.sleep(3, TimeUnit.SECONDS);
        }
    }

}
