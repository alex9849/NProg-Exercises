package aufgabe2;


import java.util.concurrent.*;

public class Main {
    private static final int STRING_LENGTH = 10;

    public static void main(String... args) throws InterruptedException, ExecutionException {
        int availableThreads = Runtime.getRuntime().availableProcessors();
        ExecutorService executor = Executors.newFixedThreadPool(availableThreads);
        CompletionService<String> exec = new ExecutorCompletionService<>(executor);
        for(int i = 0; i < availableThreads; i++) {
            exec.submit(new PalindromWorker(STRING_LENGTH));
        }
        String palindrom = exec.take().get();
        System.out.println(palindrom);
        executor.shutdownNow();
    }

}
