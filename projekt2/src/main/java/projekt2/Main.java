package projekt2;

import util.Tools;

import java.util.Arrays;
import java.util.concurrent.ForkJoinPool;

public class Main {

  public static void main(String[] args) {
    //Try 273_000_000
    int[] array1 = Tools.gerRandomIntArray(20_000, 0, 1000);
    //Try 173_000_000 (Maybe Java will run out of heap-space)
    int[] array2 = Tools.gerRandomIntArray(20_000, 0, 1000);

    int[] controlArray = new int[array1.length + array2.length];
    System.arraycopy(array1, 0, controlArray, 0, array1.length);
    System.arraycopy(array2, 0, controlArray, array1.length, array2.length);

    System.out.println("Pre-sorting merge");
    System.out.println("Sorting array1");
    Arrays.sort(array1);
    System.out.println("Sorting array2");
    Arrays.sort(array2);
    System.out.println("Sorting controlArray");
    Arrays.sort(controlArray);

    System.out.println("Sequential merge");
    long startTime = System.currentTimeMillis();
    int[] mergeArray = merge(array1, array2);
    System.out.println("Time needed: " + (System.currentTimeMillis() - startTime) + "ms");

    startTime = System.currentTimeMillis();
    System.out.println("Parallel merge");
    int[] parallelMergeArray = parallelMerge(array1, array2);
    System.out.println("Time needed: " + (System.currentTimeMillis() - startTime) + "ms");
    if (Arrays.compare(controlArray, parallelMergeArray) != 0) {
      System.out.println("Merge error");
      System.out.println(Arrays.toString(controlArray));
      System.out.println(Arrays.toString(parallelMergeArray));
    }

    System.out.println("done");
  }

  public static int[] parallelMerge(int[] array1, int[] array2) {
    int threads = Runtime.getRuntime().availableProcessors();
    ForkJoinPool pool = new ForkJoinPool();
    ArrayMerger arrayMerger = new ArrayMerger(array1, array2);
    pool.execute(arrayMerger);
    return arrayMerger.join();
  }

  public static int[] merge(int[] sortedArray1, int[] sortedArray2) {
    int[] mergeArray = new int[sortedArray1.length + sortedArray2.length];
    int idx1 = 0, idx2 = 0;
    for (int i = 0; i < mergeArray.length; i++) {
      if (idx1 >= sortedArray1.length) {
        mergeArray[i] = sortedArray2[idx2];
        idx2++;
        continue;
      }

      if (idx2 >= sortedArray2.length) {
        mergeArray[i] = sortedArray1[idx1];
        idx1++;
        continue;
      }

      if (sortedArray1[idx1] < sortedArray2[idx2]) {
        mergeArray[i] = sortedArray1[idx1];
        idx1++;
      } else {
        mergeArray[i] = sortedArray2[idx2];
        idx2++;
      }
    }

    return mergeArray;
  }
}
