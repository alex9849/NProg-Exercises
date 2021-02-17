package aufgabe6;

import util.Tools;

import java.util.concurrent.RecursiveTask;

public class MatrixMultiplier extends RecursiveTask<IntMatrix> {
    private final IntMatrix a;
    private final IntMatrix b;
    private final int aRealRowSize;
    private final int bRealColSize;

    public MatrixMultiplier(IntMatrix a, IntMatrix b, int recursionDepth) {
        int thresholdSize = (int) Math.pow(2, recursionDepth);
        int size = Math.max(Math.max(a.row, b.row), Math.max(a.col, b.col));

        if (!Tools.isPowerOfTwo(size)) {
            if (size % thresholdSize != 0) {
                size = (size / thresholdSize + 1) * thresholdSize;
            }
        }

        this.a = new IntMatrix(a, size);
        this.b = new IntMatrix(b, size);
        this.aRealRowSize = a.row;
        this.bRealColSize = b.col;
    }

    private MatrixMultiplier(IntMatrix a, IntMatrix b) {
        this.a = a;
        this.b = b;
        this.aRealRowSize = a.row;
        this.bRealColSize = b.col;
    }

    @Override
    protected IntMatrix compute() {
        if (a.row % 4 != 0 || a.row < 64) {
            return IntMatrix.mult(a, b);
        }
        IntMatrix a11 = a.getSubMatrix(0, a.row / 2, 0, a.row / 2);
        IntMatrix a12 = a.getSubMatrix(0, a.row / 2, a.row / 2, a.row);
        IntMatrix a21 = a.getSubMatrix(a.row / 2, a.row, 0, a.row / 2);
        IntMatrix a22 = a.getSubMatrix(a.row / 2, a.row, a.row / 2, a.row);

        IntMatrix b11 = b.getSubMatrix(0, b.row / 2, 0, b.row / 2);
        IntMatrix b12 = b.getSubMatrix(0, b.row / 2, b.row / 2, b.row);
        IntMatrix b21 = b.getSubMatrix(b.row / 2, b.row, 0, b.row / 2);
        IntMatrix b22 = b.getSubMatrix(b.row / 2, b.row, b.row / 2, b.row);

        MatrixMultiplier a11_b11 = new MatrixMultiplier(a11, b11);
        MatrixMultiplier a12_b21 = new MatrixMultiplier(a12, b21);
        MatrixMultiplier a11_b12 = new MatrixMultiplier(a11, b12);
        MatrixMultiplier a12_b22 = new MatrixMultiplier(a12, b22);
        MatrixMultiplier a21_b11 = new MatrixMultiplier(a21, b11);
        MatrixMultiplier a22_b21 = new MatrixMultiplier(a22, b21);
        MatrixMultiplier a21_b12 = new MatrixMultiplier(a21, b12);
        MatrixMultiplier a22_b22 = new MatrixMultiplier(a22, b22);

        invokeAll(a11_b11, a12_b21, a11_b12, a12_b22, a21_b11, a22_b21, a21_b12, a22_b22);

        IntMatrix c11 = IntMatrix.add(a11_b11.join(), a12_b21.join());
        IntMatrix c12 = IntMatrix.add(a11_b12.join(), a12_b22.join());
        IntMatrix c21 = IntMatrix.add(a21_b11.join(), a22_b21.join());
        IntMatrix c22 = IntMatrix.add(a21_b12.join(), a22_b22.join());
        IntMatrix result = new IntMatrix(c11, c12, c21, c22);
        if (this.aRealRowSize == result.row && this.bRealColSize == result.col) {
            return result;
        } else {
            return result.getSubMatrix(0, this.aRealRowSize, 0, this.bRealColSize);
        }
    }
}
