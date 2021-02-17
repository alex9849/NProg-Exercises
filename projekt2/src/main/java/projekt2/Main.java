package projekt2;

import util.Tools;

import java.util.Arrays;

public class Main {

  public static void main(String[] args) {
    int[] array1 = Tools.gerRandomIntArray(170, 0, 1000);
    int[] array2 = Tools.gerRandomIntArray(150, 0, 1000);

    int[] controlArray = new int[array1.length + array2.length];
    System.arraycopy(array1, 0, controlArray, 0, array1.length);
    System.arraycopy(array2, 0, controlArray, array1.length, array2.length);

    Arrays.sort(array1);
    Arrays.sort(array2);


    System.out.println("Merge two sorted arrays");
    int[] mergeArray = merge(array1, array2);

    // Teste, ob merge korrekt war
    Arrays.sort(controlArray);
    if (Arrays.compare(controlArray, mergeArray) != 0) {
      System.out.println("Merge error");
      System.out.println(Arrays.toString(controlArray));
      System.out.println(Arrays.toString(mergeArray));
    }

    System.out.println("done");
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
