package com.example.locationm_gps;

public class headingFilter {

    public static double RtoD = 180 / Math.PI;
    public static double DtoR = Math.PI/180;

    public static double F_h_12 = 0;
    public static double F_h_13 = 1;

    public static double P_h_11 = 0.02;
    public static double P_h_12 = 0;
    public static double P_h_13 = 0;
    public static double P_h_21 = 0;
    public static double P_h_22 = 0.001;
    public static double P_h_23 = 0;
    public static double P_h_31 = 0;
    public static double P_h_32 = 0;
    public static double P_h_33 = 0.001;

    public static double H_h_11 = 1;
    public static double H_h_21 = 0;
    public static double H_h_31 = 0;

    public static double Q_h_11 = 0.0025*0.0025;
    public static double Q_h_22 = 1e-8;
    public static double Q_h_33 = 1e-8;

    public static double R_h = 0.2;
    public static double S_h = 0;

    public static double K_h_11 = 0;
    public static double K_h_21 = 0;
    public static double K_h_31 = 0;

    public static double P_h_dot_11, P_h_next_11;
    public static double P_h_dot_12, P_h_next_12;
    public static double P_h_dot_13, P_h_next_13;
    public static double P_h_dot_21, P_h_next_21;
    public static double P_h_dot_22, P_h_next_22;
    public static double P_h_dot_23 = 0, P_h_next_23;
    public static double P_h_dot_31, P_h_next_31;
    public static double P_h_dot_32 = 0, P_h_next_32;
    public static double P_h_dot_33, P_h_next_33;

    public static double P_h_update_11;
    public static double P_h_update_12;
    public static double P_h_update_13;
    public static double P_h_update_21;
    public static double P_h_update_22;
    public static double P_h_update_23;
    public static double P_h_update_31;
    public static double P_h_update_32;
    public static double P_h_update_33;

    public static double x_h_tu_11, x_h_mu_11;
    public static double x_h_tu_21, x_h_mu_21;
    public static double x_h_tu_31, x_h_mu_31;

    public static double z_h = 0;

    static double x_h_tu_dot;
    static double[] x_final = {0,0,0};

    public static double[] cal_Heading(double[] x_h, double gy, double dt, int mode, double GPS_heading, boolean flag_mu) {

        //GPS_heading = GPS_heading*DtoR;

        // Time Update
        F_h_12 = -gy;
        F_h_13 = 1;
        x_h_tu_dot = F_h_12*x_h[1] + F_h_13*x_h[2];
        x_h_tu_11 = x_h[0] + x_h_tu_dot*dt;
        x_h_tu_21 = x_h[1];
        x_h_tu_31 = x_h[2];

        P_h_dot_11 = Q_h_11;
        P_h_dot_12 = F_h_12*P_h_22;
        P_h_dot_13 = F_h_13*P_h_33;
        P_h_dot_21 = F_h_12*P_h_22;
        P_h_dot_22 = Q_h_22;
        P_h_dot_31 = F_h_13*P_h_33;
        P_h_dot_33 = Q_h_33;

        P_h_next_11 = P_h_11 + P_h_dot_11*dt;
        P_h_next_12 = P_h_12 + P_h_dot_12*dt;
        P_h_next_13 = P_h_13 + P_h_dot_13*dt;
        P_h_next_21 = P_h_21 + P_h_dot_21*dt;
        P_h_next_22 = P_h_22 + P_h_dot_22*dt;
        P_h_next_23 = P_h_23 + P_h_dot_23*dt;
        P_h_next_31 = P_h_31 + P_h_dot_31*dt;
        P_h_next_32 = P_h_32 + P_h_dot_32*dt;
        P_h_next_33 = P_h_33 + P_h_dot_33*dt;

        P_h_11 = P_h_next_11;
        P_h_12 = P_h_next_12;
        P_h_13 = P_h_next_13;
        P_h_21 = P_h_next_21;
        P_h_22 = P_h_next_22;
        P_h_23 = P_h_next_23;
        P_h_31 = P_h_next_31;
        P_h_32 = P_h_next_32;
        P_h_33 = P_h_next_33;

        // Measurement Update
        if (flag_mu && mode == 1) {
            double diff_yaw = GPS_heading - x_h_tu_11;
            if (diff_yaw < -90*DtoR) {
                x_h_tu_11 = x_h_tu_11 + diff_yaw;
            }
            S_h = P_h_next_11 + R_h;
            double inv_S_h = (1/S_h);

            K_h_11 = P_h_next_11*H_h_11*inv_S_h;
            K_h_21 = P_h_next_21*H_h_21*inv_S_h;
            K_h_31 = P_h_next_31*H_h_31*inv_S_h;

            z_h =  GPS_heading - H_h_11*x_h_tu_11;

            x_h_mu_11 = x_h_tu_11 + K_h_11*z_h;
            x_h_mu_21 = x_h_tu_21 + K_h_21*z_h;
            x_h_mu_31 = x_h_tu_31 + K_h_31*z_h;

            P_h_update_11 = P_h_next_11 - K_h_11*R_h*K_h_11;
            P_h_update_12 = P_h_next_12 - K_h_11*R_h*K_h_21;
            P_h_update_13 = P_h_next_13 - K_h_11*R_h*K_h_31;
            P_h_update_21 = P_h_next_21 - K_h_21*R_h*K_h_11;
            P_h_update_22 = P_h_next_22 - K_h_21*R_h*K_h_21;
            P_h_update_23 = P_h_next_23 - K_h_21*R_h*K_h_31;
            P_h_update_31 = P_h_next_31 - K_h_31*R_h*K_h_11;
            P_h_update_32 = P_h_next_32 - K_h_31*R_h*K_h_21;
            P_h_update_33 = P_h_next_33 - K_h_31*R_h*K_h_31;

            P_h_11 = P_h_update_11;
            P_h_12 = P_h_update_12;
            P_h_13 = P_h_update_13;
            P_h_21 = P_h_update_21;
            P_h_22 = P_h_update_22;
            P_h_23 = P_h_update_23;
            P_h_31 = P_h_update_31;
            P_h_32 = P_h_update_32;
            P_h_33 = P_h_update_33;

            x_final[0] = x_h_mu_11;
            x_final[1] = x_h_mu_21;
            x_final[2] = x_h_mu_31;
        } else {
            x_final[0] = x_h_tu_11;
            x_final[1] = x_h_tu_21;
            x_final[2] = x_h_tu_31;
        }

        //Log.i("GAIN", + K_h_11 + " , " + K_h_21 + " , " + K_h_31);
        return x_final;
    }
}
