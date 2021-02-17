package aufgabe8;

import java.util.concurrent.CompletableFuture;

public class Main {

  public static void main(String[] args) {
    CompletableFuture<Integer> s1 = Sensors.getValueFromSensor1();
    CompletableFuture<Integer> s2 = Sensors.getValueFromSensor2();
    CompletableFuture<Integer> s3 = Sensors.getValueFromSensor3();
    CompletableFuture<Integer> s1_s2 = s1.thenCombineAsync(s2, (v1, v2) -> v1 * v2);
    CompletableFuture<Integer> s2_s3 = s2.thenCombineAsync(s3, (v1, v2) -> v1 * v2);
    CompletableFuture<Integer> s1_s3 = s1.thenCombineAsync(s3, (v1, v2) -> v1 * v2);
    CompletableFuture<Integer> s1_s2AndS2_s3 = s1_s2.thenCombineAsync(s2_s3, Integer::sum);
    System.out.println(s1_s2AndS2_s3.thenCombineAsync(s1_s3, Integer::sum).join());
  }

}
