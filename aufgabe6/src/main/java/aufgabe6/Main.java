package aufgabe6;


import util.Tools;

import java.util.concurrent.ForkJoinPool;

public class Main {

  public static void main(String[] args) {
    IntMatrix matrix1 = IntMatrix.getRandomMatrix(967, 1011);
    IntMatrix matrix2 = IntMatrix.getRandomMatrix(1011, 987);

    long start;

    System.out.println("Serial");
    start = System.currentTimeMillis();
    IntMatrix matrix = IntMatrix.mult(matrix1, matrix2);
    System.out.println("Dauer " + (System.currentTimeMillis() - start));

    System.out.println("recursive");
    start = System.currentTimeMillis();
    IntMatrix recursive = multRecursive(matrix1, matrix2, 6);
    System.out.println("Dauer " + (System.currentTimeMillis() - start));

    System.out.println("forkJoin");
    start = System.currentTimeMillis();
    IntMatrix forkJoin = forkJoin(matrix1, matrix2);
    System.out.println("Dauer " + (System.currentTimeMillis() - start));

    System.out.println(IntMatrix.equals(matrix, forkJoin));
  }

  static IntMatrix forkJoin(IntMatrix a, IntMatrix b) {
    MatrixMultiplier matrixMultiplier = new MatrixMultiplier(a, b, 6);
    int threads = Runtime.getRuntime().availableProcessors();
    ForkJoinPool executor = new ForkJoinPool(threads);
    executor.execute(matrixMultiplier);
    return matrixMultiplier.join();
  }

  static IntMatrix multRecursive(IntMatrix a, IntMatrix b, int recursionDepth) {
    int thresholdSize = (int) Math.pow(2, recursionDepth);

    //Erzeuge zwei quadratische Matrizen, deren Größen nahe einer Zweierpotenz liegen
    int size = Math.max(Math.max(a.row, b.row), Math.max(a.col, b.col));

    if (Tools.isPowerOfTwo(size) == false) {
      if (size % thresholdSize != 0) {
        size = (size / thresholdSize + 1) * thresholdSize;
      }
    }

    IntMatrix aPadded = new IntMatrix(a, size);
    IntMatrix bPadded = new IntMatrix(b, size);

    IntMatrix squareMatrix = multRecursive(aPadded, bPadded);

    return squareMatrix.getSubMatrix(0, a.row, 0, b.col);
  }

  static private IntMatrix multRecursive(IntMatrix a, IntMatrix b) {
    if (a.row % 4 != 0 || a.row < 64) {
      return IntMatrix.mult(a, b);
    } else {
      IntMatrix a11 = a.getSubMatrix(0, a.row / 2, 0, a.row / 2);
      IntMatrix a12 = a.getSubMatrix(0, a.row / 2, a.row / 2, a.row);
      IntMatrix a21 = a.getSubMatrix(a.row / 2, a.row, 0, a.row / 2);
      IntMatrix a22 = a.getSubMatrix(a.row / 2, a.row, a.row / 2, a.row);

      IntMatrix b11 = b.getSubMatrix(0, b.row / 2, 0, b.row / 2);
      IntMatrix b12 = b.getSubMatrix(0, b.row / 2, b.row / 2, b.row);
      IntMatrix b21 = b.getSubMatrix(b.row / 2, b.row, 0, b.row / 2);
      IntMatrix b22 = b.getSubMatrix(b.row / 2, b.row, b.row / 2, b.row);

      IntMatrix a11_b11 = multRecursive(a11, b11);
      IntMatrix a12_b21 = multRecursive(a12, b21);
      IntMatrix a11_b12 = multRecursive(a11, b12);
      IntMatrix a12_b22 = multRecursive(a12, b22);

      IntMatrix a21_b11 = multRecursive(a21, b11);
      IntMatrix a22_b21 = multRecursive(a22, b21);
      IntMatrix a21_b12 = multRecursive(a21, b12);
      IntMatrix a22_b22 = multRecursive(a22, b22);

      IntMatrix c11 = IntMatrix.add(a11_b11, a12_b21);
      IntMatrix c12 = IntMatrix.add(a11_b12, a12_b22);
      IntMatrix c21 = IntMatrix.add(a21_b11, a22_b21);
      IntMatrix c22 = IntMatrix.add(a21_b12, a22_b22);

      return new IntMatrix(c11, c12, c21, c22);
    }
  }
}
