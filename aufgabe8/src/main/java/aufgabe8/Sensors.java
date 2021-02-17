package aufgabe8;

import util.Tools;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

// Klasse stellt drei asynchrone Methoden zur Verfügung
public class Sensors {
    public static CompletableFuture<Integer> getValueFromSensor1() {
        return CompletableFuture.supplyAsync(
                () -> {
                    Tools.randomSleep(300, 2000, TimeUnit.MILLISECONDS);
                    return 42;
                }
        );
    }

    public static CompletableFuture<Integer> getValueFromSensor2() {
        return CompletableFuture.supplyAsync(
                () -> {
                    Tools.randomSleep(300, 2000, TimeUnit.MILLISECONDS);
                    return 11;
                }
        );
    }

    public static CompletableFuture<Integer> getValueFromSensor3() {
        return CompletableFuture.supplyAsync(
                () -> {
                    Tools.randomSleep(300, 2000, TimeUnit.MILLISECONDS);
                    return 13;
                }
        );
    }
}
