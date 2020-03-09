package com.example.locationm_gps;

public class slopeCalculation {

    static double GRAVITY = 9.78;

    public static double[] cal_Slope(double sin_theta_old, double[] stack_acc, double[] stack_alt, double[] stack_spd, double[] stack_tic, double accZ, double altitude, double speed, int C_WINDOW, int M_WINDOW, double dt) {
        boolean isConvergence = false;
        double loop_count = 1;
        double[] output = {0, 0};
        double sin_theta = sin_theta_old;
        int len = stack_acc.length;
        double[] dist = new double[len];
        double[] dist_new = new double[len];
        double[] acc_new = new double[len];
        double[] spd = new double[len];
        double[] spd_new = new double[len];

        if (C_WINDOW == M_WINDOW) {

            double dh = stack_alt[M_WINDOW-1] - stack_alt[0];

            for (int i = 0 ; i < len ; ++i) {
                double tic = stack_tic[i];
                if (i == 0) {
                    spd[i] = stack_spd[0] - stack_acc[0]*tic;
                } else {
                    spd[i] = spd[i-1] - stack_acc[i]*tic;
                }

                if(spd[i] < 0) {
                    spd[i] = 0;
                }

                if (i == 0) {
                    dist[0] = spd[0]*tic;
                } else {
                    dist[i] = dist[i-1] + spd[i]*tic;
                }
            }

            double dist_end = dist[len-1];

            while(!isConvergence) {

                if (dist_end == 0) {
                    sin_theta = sin_theta_old;
                    C_WINDOW = 1;

                    break;
                }

                if(loop_count > 20) {
                    sin_theta = sin_theta_old;

                    loop_count = 1;
                    C_WINDOW = 1;

                    break;
                }

                sin_theta = dh/dist[len-1];

                for (int i = 0 ; i < len ; ++i) {
                    if (dh < 0) {
                        acc_new[i] = stack_acc[i] + GRAVITY*sin_theta;
                    } else {
                        acc_new[i] = stack_acc[i] - GRAVITY*sin_theta;
                    }

                    double tic = stack_tic[i];

                    if (i == 0) {
                        spd_new[0] = stack_spd[0] - acc_new[0]*tic;
                    } else {
                        spd_new[i] = spd_new[i-1] - acc_new[i]*tic;
                    }

                    if ( spd_new[i] < 0 ) {
                        spd_new[i] = 0;
                    }

                    if (i == 0) {
                        dist_new[0] = spd_new[0]*tic;
                    } else {
                        dist_new[i] = dist_new[i-1] + spd_new[i]*tic;
                    }

                    if (Math.abs(dist_end - dist_new[len-1]) < 1e-3) {
                        isConvergence = true;

                        sin_theta = dh/dist_new[len-1];
                        C_WINDOW = 1;
                    } else {
                        System.arraycopy(dist_new,0,dist,0,len);

                        loop_count++;
                    }
                }
            }

        } else {
            sin_theta = sin_theta_old;
            C_WINDOW++;
        }

        output[0] = sin_theta;
        output[1] = C_WINDOW;

        return output;
    }
}
