package com.example.locationm_gps;

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
}
