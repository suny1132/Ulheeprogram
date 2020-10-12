package com.example.locationm_gps;

public class SlopeCorrection {
    static double GRAVITY = 9.78;
    static double THRESHOLD_DIST = 1e-3;

    public static double cal_Slope(double sin_theta_old, double[] stack_acc, double[] stack_alt, double[] stack_spd_gps, double[] stack_spd_ins, double tic) {

        double LOOP_COUNT = 0;

        int len = stack_alt.length;
        double[] spd = new double[stack_acc.length];
        double[] dist = new double[stack_acc.length];

        double[] acc_final = new double[stack_acc.length];
        double[] spd_final = new double[stack_acc.length];
        double[] dist_final = new double[stack_acc.length];

        double v0 = stack_spd_ins[0];
        double dh = stack_alt[len-1] - stack_alt[0];
        double sin_theta0 = 0;
        double sin_theta = 0;

        spd[0] = v0 - stack_acc[0]*tic;
        if(spd[0] < 0) {
            spd[0] = 0;
        }
        dist[0] = spd[0]*tic;
        for (int i = 1; i < len; i++) {
            spd[i] = spd[i-1] - stack_acc[i]*tic;
            if(spd[i] < 0) {
                spd[i] = 0;
            }
            dist[i] = dist[i-1] + spd[i]*tic;
        }
        double dist0 = dist[len-1];
        if (dist0 == 0) {
            sin_theta0 = 0;
        } else {
            sin_theta0 = dh/dist0;
        }

        if (Math.abs(sin_theta0) > 1) {
            sin_theta0 = sin_theta_old;
        }
        sin_theta = sin_theta0;

        while(true) {
            if (LOOP_COUNT > 100) {
                sin_theta = sin_theta0;

                break;
            }

            for (int i = 0; i < len; i++) {
                acc_final[i] = stack_acc[i] - GRAVITY*sin_theta;
                if ( i == 0 ) {
                    spd_final[0] = v0 - acc_final[0]*tic;
                    dist_final[0] = spd_final[0]*tic;
                } else {
                    spd_final[i] = spd_final[i-1] - acc_final[i]*tic;
                    dist_final[i] = dist_final[i-1] + spd_final[i]*tic;
                }
            }

            double dist_check = Math.abs(dist[len-1] - dist_final[len-1]);
            if (dist_check < THRESHOLD_DIST) {
                // Fix the Slope
                break;
            } else {
                dist = dist_final;

                if (Math.pow(dist[len-1],2) < 0) {
                    sin_theta = sin_theta0;
                } else {
                    sin_theta = dh/dist[len-1];

                    if (Math.abs(sin_theta) > 0.05) {
                        sin_theta = sin_theta_old;
                    }
                }

                LOOP_COUNT = LOOP_COUNT + 1;
            }
        }

        return sin_theta;
    }
}
