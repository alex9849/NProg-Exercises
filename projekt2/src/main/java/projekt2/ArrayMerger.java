package projekt2;

import java.util.Arrays;
import java.util.concurrent.RecursiveTask;

public class ArrayMerger extends RecursiveTask<int[]> {
    private final int THRESHOLD = 100;
    private final int[] largerArr;
    private final int[] smallerArr;
    private final int largerArrStart;
    private final int largerArrLength;
    private final int smallerArrStart;
    private final int smallerArrLength;
    private final int[] result;
    private final int resultStartIndex;

    public ArrayMerger(int[] a1, int[] a2) {
        this(a1, 0, a1.length, a2, 0, a2.length, new int[a1.length + a2.length], 0);
    }

    private ArrayMerger(int[] a1, int a1Start, int a1Length, int[] a2, int a2Start, int a2Length,
                        int[] result, int resultStartIndex) {
        if (a1Length > a2Length) {
            this.largerArrStart = a1Start;
            this.largerArr = a1;
            this.largerArrLength = a1Length;
            this.smallerArr = a2;
            this.smallerArrStart = a2Start;
            this.smallerArrLength = a2Length;
        } else {
            this.largerArrStart = a2Start;
            this.largerArr = a2;
            this.largerArrLength = a2Length;
            this.smallerArr = a1;
            this.smallerArrStart = a1Start;
            this.smallerArrLength = a1Length;
        }
        this.result = result;
        this.resultStartIndex = resultStartIndex;
    }

    @Override
    protected int[] compute() {
        if (largerArrLength < THRESHOLD) {
            mergeArr();
            return result;
        }
        //first index in second Array
        final int largeArrSplitIndex = largerArrStart + (largerArrLength / 2);
        int middleValue = largerArr[largeArrSplitIndex];
        //first index in second Array
        final int smallArrSplitIndex = getSplitIndex(middleValue);

        final int largerArrayFirstSplitLength = largeArrSplitIndex - largerArrStart;
        final int smallerArrayFirstSplitLength = smallArrSplitIndex - smallerArrStart;
        ArrayMerger splitTask1 = new ArrayMerger(largerArr, largerArrStart, largerArrayFirstSplitLength,
                smallerArr, smallerArrStart, smallerArrayFirstSplitLength, result, resultStartIndex);
        ArrayMerger splitTask2 = new ArrayMerger(largerArr, largeArrSplitIndex, largerArrLength - largerArrayFirstSplitLength,
                smallerArr, smallArrSplitIndex, smallerArrLength - smallerArrayFirstSplitLength, result,
                resultStartIndex + largerArrayFirstSplitLength + smallerArrayFirstSplitLength);
        invokeAll(splitTask1, splitTask2);
        splitTask1.join();
        splitTask2.join();
        return result;
    }

    private void mergeArr() {
        int sArrIndex = 0;
        int lArrIndex = 0;
        for (int i = 0; i < largerArrLength + smallerArrLength; i++) {
            if (sArrIndex >= smallerArrLength) {
                result[resultStartIndex + i] = largerArr[largerArrStart + lArrIndex];
                lArrIndex++;
                continue;
            }
            if (lArrIndex >= largerArrLength) {
                result[resultStartIndex + i] = smallerArr[smallerArrStart + sArrIndex];
                sArrIndex++;
                continue;
            }
            if (smallerArr[smallerArrStart + sArrIndex] < largerArr[largerArrStart + lArrIndex]) {
                result[resultStartIndex + i] = smallerArr[smallerArrStart + sArrIndex];
                sArrIndex++;
            } else {
                result[resultStartIndex + i] = largerArr[largerArrStart + lArrIndex];
                lArrIndex++;
            }
        }
    }

    /**
     * Returns the index of the first value, that should be in the second array
     *
     * @param searchKey the value to search for
     * @return the index of the first value that should be in the second array
     */
    private int getSplitIndex(int searchKey) {
        int splitIndex = Arrays.binarySearch(smallerArr, smallerArrStart, smallerArrStart + smallerArrLength, searchKey);
        if (splitIndex < 0) {
            splitIndex = -splitIndex - 1;
        }
        return splitIndex;
    }
}
