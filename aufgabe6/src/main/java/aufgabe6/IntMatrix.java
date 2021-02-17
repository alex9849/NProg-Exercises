package aufgabe6;

import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.IntStream;

/**
 * Die Klasse repäsentiert eine Integer-Matrix
 * <p>
 * Es werden mehrere Konstruktoren bereit gestellt
 */
public class IntMatrix {
  final int row;
  final int[][] data;
  int col;

  /**
   * Erzeugt eine Interger-Matrix
   * <p>
   * Alle Element erhalten Null als Default-Wert
   *
   * @param row - Anzahl der Zeilen
   * @param col - Anzahl der Spalten
   */
  IntMatrix(int row, int col) {
    this.row = row;
    this.col = col;

    this.data = new int[row][col];
  }

  /**
   * Erzuegt eine neue, größere quadratische Matrix mit der angegeben Größe
   *
   * @param matrix      - Matrix, die "vergrößert" wird
   * @param paddingSize - Zeilen- und Spaltenanzahl
   */
  IntMatrix(IntMatrix matrix, int paddingSize) {
    this(paddingSize, paddingSize);

    assert matrix.col < paddingSize;
    assert matrix.row < paddingSize;

    IntStream.range(0, matrix.row).forEach(i -> System.arraycopy(matrix.data[i], 0, data[i], 0, matrix.col));
  }

  /**
   * Erzeugt eine neue Matrix durch das Zusammensetzen von den vier Matrizen.
   * <p>
   * | A B |
   * | c D |
   * <p>
   * Die Dimensionen der Matrizen müssen passen!
   *
   * @param A
   * @param B
   * @param C
   * @param D
   */
  IntMatrix(IntMatrix A, IntMatrix B, IntMatrix C, IntMatrix D) {
    assert A.row == B.row;
    assert C.row == D.row;
    assert A.col == C.col;
    assert B.col == D.col;

    this.row = A.row + C.row;
    this.col = A.col + B.col;

    this.data = new int[row][col];

    IntStream.range(0, A.row).forEach(i -> System.arraycopy(A.data[i], 0, data[i], 0, A.col));
    IntStream.range(0, C.row).forEach(i -> System.arraycopy(C.data[i], 0, data[A.row + i], 0, C.col));

    IntStream.range(0, B.row).forEach(i -> System.arraycopy(B.data[i], 0, data[i], A.col, B.col));
    IntStream.range(0, D.row).forEach(i -> System.arraycopy(D.data[i], 0, data[A.row + i], C.col, D.col));
  }

  static boolean equals(IntMatrix a, IntMatrix b) {
    if ((a.row != b.row) || (a.col != b.col))
      return false;

    for (int i = 0; i < a.row; i++) {
      if (Arrays.equals(a.data[i], b.data[i]) == false) {
        System.out.println("Error in row " + i);
        return false;
      }
    }

    return true;
  }

  static IntMatrix add(IntMatrix a, IntMatrix b) {
    assert a.row == b.row;
    assert a.col == b.col;

    int row = a.row;
    int col = a.col;

    IntMatrix matrix = new IntMatrix(row, col);

    for (int i = 0; i < row; i++)
      for (int j = 0; j < col; j++)
        matrix.data[i][j] = a.data[i][j] + b.data[i][j];

    return matrix;
  }

  static IntMatrix mult(IntMatrix a, IntMatrix b) {
    assert a.col == b.row;

    int row = a.row;
    int col = b.col;

    int tmp = a.col;

    IntMatrix matrix = new IntMatrix(row, col);

    // jik-Version
    for (int j = 0; j < col; j++)
      for (int i = 0; i < row; i++) {
        int scratch = 0;
        for (int k = 0; k < tmp; k++)
          scratch += a.data[i][k] * b.data[k][j];

        matrix.data[i][j] = scratch;
      }

    return matrix;
  }

  //---------------------------------------------------------------------------
  // Einige statische Hilfsmethoden
  //---------------------------------------------------------------------------

  static IntMatrix getRandomMatrix(int row, int col) {
    IntMatrix matrix = new IntMatrix(row, col);

    IntStream.range(0, row).forEach(i -> {
      IntStream.range(0, col).forEach(j -> {
        matrix.data[i][j] = -100 + ThreadLocalRandom.current().nextInt(200);
      });
    });

    return matrix;
  }

  /**
   * Ereugt eine neue Matrix, die mit den Werten aus dem angegebenen
   * Matrixbereich initialisiert wird
   *
   * @param rowStart
   * @param rowEnd
   * @param colStart
   * @param colEnd
   * @return
   */
  IntMatrix getSubMatrix(int rowStart, int rowEnd, int colStart, int colEnd) {
    int row = rowEnd - rowStart;
    int col = colEnd - colStart;

    IntMatrix matrix = new IntMatrix(row, col);

    IntStream.range(0, row).forEach(i -> System.arraycopy(this.data[rowStart + i], colStart, matrix.data[i], 0, col));

    return matrix;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + col;
    result = prime * result + Arrays.deepHashCode(data);
    result = prime * result + row;
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    IntMatrix other = (IntMatrix) obj;
    if (col != other.col)
      return false;
    if (!Arrays.deepEquals(data, other.data))
      return false;
    if (row != other.row)
      return false;
    return true;
  }
}
