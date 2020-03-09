package com.example.locationm_gps;

import android.util.Log;

import java.util.Arrays;

public class Matrix {

    static double variance(double mat[], int n) {

        double sum = 0;
        for (int i = 0; i < n; i++) {
            sum += mat[i];
        }

        double mean = sum/(double)n;
        double sqDiff = 0;

        for (int i = 0; i < n; i++) {
            sqDiff += ((mat[i] - mean)*(mat[i] - mean));
        }
        return (double)sqDiff/n;
    }

    static double standardDeviation(double arr[], int n) {
        return Math.sqrt(variance(arr, n));
    }

    public static double[][] inverse(final double[][] squareMatrix)
    {
        final int size = squareMatrix.length;
        final double[][] inverseMatrix = new double[size][size];

        if (squareMatrix[0].length != size || inverseMatrix.length != size
                || inverseMatrix[0].length != size) {
            Log.e("Error","--- invalid length. column should be 2 times larger than row.");
        }
        for (int i = 0; i < size; ++i) {
            Arrays.fill(inverseMatrix[i], 0.0f);
            inverseMatrix[i][i] = 1.0f;
        }
        for (int i = 0; i < size; ++i) {
            findPivotAndSwapRow(i, squareMatrix, inverseMatrix, size);
            sweep(i, squareMatrix, inverseMatrix, size);
        }
        return inverseMatrix;
    }

    private static void findPivotAndSwapRow(final int row,
                                            final double[][] squareMatrix0, final double[][] squareMatrix1,
                                            final int size) {
        int ip = row;
        double pivot = Math.abs(squareMatrix0[row][row]);
        for (int i = row + 1; i < size; ++i) {
            if (pivot < Math.abs(squareMatrix0[i][row])) {
                ip = i;
                pivot = Math.abs(squareMatrix0[i][row]);
            }
        }
        if (ip != row) {
            for (int j = 0; j < size; ++j) {
                final double temp0 = squareMatrix0[ip][j];
                squareMatrix0[ip][j] = squareMatrix0[row][j];
                squareMatrix0[row][j] = temp0;
                final double temp1 = squareMatrix1[ip][j];
                squareMatrix1[ip][j] = squareMatrix1[row][j];
                squareMatrix1[row][j] = temp1;
            }
        }
    }

    private static void sweep(final int row, final double[][] squareMatrix0,
                              final double[][] squareMatrix1, final int size)
    {
        final double pivot = squareMatrix0[row][row];
        if (pivot == 0) {
            Log.e("Error","Inverse failed. Invalid pivot");
        }
        for (int j = 0; j < size; ++j) {
            squareMatrix0[row][j] /= pivot;
            squareMatrix1[row][j] /= pivot;
        }
        for (int i = 0; i < size; i++) {
            final double sweepTargetValue = squareMatrix0[i][row];
            if (i != row) {
                for (int j = row; j < size; ++j) {
                    squareMatrix0[i][j] -= sweepTargetValue
                            * squareMatrix0[row][j];
                }
                for (int j = 0; j < size; ++j) {
                    squareMatrix1[i][j] -= sweepTargetValue
                            * squareMatrix1[row][j];
                }
            }
        }
    }
}
