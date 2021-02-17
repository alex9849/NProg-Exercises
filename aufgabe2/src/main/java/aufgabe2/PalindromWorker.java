package aufgabe2;

import util.Tools;

import java.util.concurrent.Callable;

public class PalindromWorker implements Callable<String> {
    private final int stringLength;

    public PalindromWorker(int stringLength) {
        this.stringLength = stringLength;
    }


    @Override
    public String call() throws Exception {
        String generated;
        do {
            generated = Tools.getRandomString(stringLength);
        } while (!Tools.isPalindrom(generated));

        return generated;
    }
}
