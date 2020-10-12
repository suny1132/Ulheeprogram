package com.example.locationm_gps;

import android.location.Location;

public class spFilter {
    public static double RtoD = 180 / Math.PI;
    public static double DtoR = Math.PI/180;

    public static double F_11 = 0; public static double F_12 = 0; public static double F_13 = 0; public static double F_14 = 0; public static double F_15 = 0;
    public static double F_21 = 0; public static double F_22 = 0; public static double F_23 = 0; public static double F_24 = 0; public static double F_25 = 0;
    public static double F_31 = 0; public static double F_32 = 0; public static double F_33 = 0; public static double F_34 = 0; public static double F_35 = 0;
    public static double F_41 = 0; public static double F_42 = 0; public static double F_43 = 0; public static double F_44 = 0; public static double F_45 = 0;
    public static double F_51 = 0; public static double F_52 = 0; public static double F_53 = 0; public static double F_54 = 0; public static double F_55 = 0;

    public static double P_11 = 0.025; public static double P_12 = 0;     public static double P_13 = 0;     public static double P_14 = 0;     public static double P_15 = 0;
    public static double P_21 = 0;     public static double P_22 = 0.025; public static double P_23 = 0;     public static double P_24 = 0;     public static double P_25 = 0;
    public static double P_31 = 0;     public static double P_32 = 0;     public static double P_33 = 0.012; public static double P_34 = 0;     public static double P_35 = 0;
    public static double P_41 = 0;     public static double P_42 = 0;     public static double P_43 = 0;     public static double P_44 = 0.001; public static double P_45 = 0;
    public static double P_51 = 0;     public static double P_52 = 0;     public static double P_53 = 0;     public static double P_54 = 0;     public static double P_55 = 0.001;

    public static double H_11 = 1; public static double H_12 = 0; public static double H_13 = 0; public static double H_14 = 0; public static double H_15 = 0;
    public static double H_21 = 0; public static double H_22 = 1; public static double H_23 = 0; public static double H_24 = 0; public static double H_25 = 0;
    public static double H_31 = 0; public static double H_32 = 0; public static double H_33 = 1; public static double H_34 = 0; public static double H_35 = 0;

    public static double SS_11 = 0; public static double SS_12 = 0; public static double SS_13 = 0; public static double SS_14 = 0; public static double SS_15 = 0;
    public static double SS_21 = 0; public static double SS_22 = 0; public static double SS_23 = 0; public static double SS_24 = 0; public static double SS_25 = 0;
    public static double SS_31 = 0; public static double SS_32 = 0; public static double SS_33 = 0; public static double SS_34 = 0; public static double SS_35 = 0;

    public static double Q_11 = 1e-12; public static double Q_12 = 0;     public static double Q_13 = 0;     public static double Q_14 = 0;         public static double Q_15 = 0;
    public static double Q_21 = 0;     public static double Q_22 = 1e-12; public static double Q_23 = 0;     public static double Q_24 = 0;         public static double Q_25 = 0;
    public static double Q_31 = 0;     public static double Q_32 = 0;     public static double Q_33 = 0.005; public static double Q_34 = 0;         public static double Q_35 = 0;
    public static double Q_41 = 0;     public static double Q_42 = 0;     public static double Q_43 = 0;     public static double Q_44 = 0.01*0.01; public static double Q_45 = 0;
    public static double Q_51 = 0;     public static double Q_52 = 0;     public static double Q_53 = 0;     public static double Q_54 = 0;         public static double Q_55 = 0.01*0.01;

    public static double R_11 = 0.45; public static double R_12 = 0;    public static double R_13 = 0;
    public static double R_21 = 0;    public static double R_22 = 0.45; public static double R_23 = 0;
    public static double R_31 = 0;    public static double R_32 = 0;    public static double R_33 = 0.25;

    public static double S_11 = 0; public static double S_12 = 0; public static double S_13 = 0;
    public static double S_21 = 0; public static double S_22 = 0; public static double S_23 = 0;
    public static double S_31 = 0; public static double S_32 = 0; public static double S_33 = 0;

    public static double KK_11 = 0; public static double KK_12 = 0; public static double KK_13 = 0;
    public static double KK_21 = 0; public static double KK_22 = 0; public static double KK_23 = 0;
    public static double KK_31 = 0; public static double KK_32 = 0; public static double KK_33 = 0;
    public static double KK_41 = 0; public static double KK_42 = 0; public static double KK_43 = 0;
    public static double KK_51 = 0; public static double KK_52 = 0; public static double KK_53 = 0;

    public static double K_11 = 0; public static double K_12 = 0; public static double K_13 = 0;
    public static double K_21 = 0; public static double K_22 = 0; public static double K_23 = 0;
    public static double K_31 = 0; public static double K_32 = 0; public static double K_33 = 0;
    public static double K_41 = 0; public static double K_42 = 0; public static double K_43 = 0;
    public static double K_51 = 0; public static double K_52 = 0; public static double K_53 = 0;

    public static double KR_11 = 0; public static double KR_12 = 0; public static double KR_13 = 0;
    public static double KR_21 = 0; public static double KR_22 = 0; public static double KR_23 = 0;
    public static double KR_31 = 0; public static double KR_32 = 0; public static double KR_33 = 0;
    public static double KR_41 = 0; public static double KR_42 = 0; public static double KR_43 = 0;
    public static double KR_51 = 0; public static double KR_52 = 0; public static double KR_53 = 0;

    public static double P_dot_11 = 0; public static double P_dot_12 = 0; public static double P_dot_13 = 0; public static double P_dot_14 = 0; public static double P_dot_15 = 0;
    public static double P_dot_21 = 0; public static double P_dot_22 = 0; public static double P_dot_23 = 0; public static double P_dot_24 = 0; public static double P_dot_25 = 0;
    public static double P_dot_31 = 0; public static double P_dot_32 = 0; public static double P_dot_33 = 0; public static double P_dot_34 = 0; public static double P_dot_35 = 0;
    public static double P_dot_41 = 0; public static double P_dot_42 = 0; public static double P_dot_43 = 0; public static double P_dot_44 = 0; public static double P_dot_45 = 0;
    public static double P_dot_51 = 0; public static double P_dot_52 = 0; public static double P_dot_53 = 0; public static double P_dot_54 = 0; public static double P_dot_55 = 0;

    public static double P_next_11 = 0; public static double P_next_12 = 0; public static double P_next_13 = 0; public static double P_next_14 = 0; public static double P_next_15 = 0;
    public static double P_next_21 = 0; public static double P_next_22 = 0; public static double P_next_23 = 0; public static double P_next_24 = 0; public static double P_next_25 = 0;
    public static double P_next_31 = 0; public static double P_next_32 = 0; public static double P_next_33 = 0; public static double P_next_34 = 0; public static double P_next_35 = 0;
    public static double P_next_41 = 0; public static double P_next_42 = 0; public static double P_next_43 = 0; public static double P_next_44 = 0; public static double P_next_45 = 0;
    public static double P_next_51 = 0; public static double P_next_52 = 0; public static double P_next_53 = 0; public static double P_next_54 = 0; public static double P_next_55 = 0;

    public static double P_update_11 = 0; public static double P_update_12 = 0; public static double P_update_13 = 0; public static double P_update_14 = 0; public static double P_update_15 = 0;
    public static double P_update_21 = 0; public static double P_update_22 = 0; public static double P_update_23 = 0; public static double P_update_24 = 0; public static double P_update_25 = 0;
    public static double P_update_31 = 0; public static double P_update_32 = 0; public static double P_update_33 = 0; public static double P_update_34 = 0; public static double P_update_35 = 0;
    public static double P_update_41 = 0; public static double P_update_42 = 0; public static double P_update_43 = 0; public static double P_update_44 = 0; public static double P_update_45 = 0;
    public static double P_update_51 = 0; public static double P_update_52 = 0; public static double P_update_53 = 0; public static double P_update_54 = 0; public static double P_update_55 = 0;

    public static double x_tu_11 = 0, x_mu_11 = 0, x_tu_dot_11 = 0;
    public static double x_tu_21 = 0, x_mu_21 = 0, x_tu_dot_21 = 0;
    public static double x_tu_31 = 0, x_mu_31 = 0, x_tu_dot_31 = 0;
    public static double x_tu_41 = 0, x_mu_41 = 0, x_tu_dot_41 = 0;
    public static double x_tu_51 = 0, x_mu_51 = 0, x_tu_dot_51 = 0;

    public static double z_11 = 0, z_21 = 0, z_31 = 0;

    static double[] x_final = {0,0,0,0,0};
    static double GRAVITY = 9.78;
    public static double[][] inverseMatrix;

    public static double[] timeUpdate(double[] x, double fz, double dt, double heading, double sin_theta) {
        F_13 = Math.sin(heading);
        F_23 = Math.cos(heading);
        F_34 = - fz - GRAVITY*sin_theta;
        F_35 = 1;

        //Log.i("Filter F",F_13 + " , " + F_23 + " , " + F_34 + " , " + F_35);

        x_tu_dot_11 = F_11*x[0] + F_12*x[1] + F_13*x[2] + F_14*x[3] + F_15*x[4];
        x_tu_dot_21 = F_21*x[0] + F_22*x[1] + F_23*x[2] + F_24*x[3] + F_25*x[4];
        x_tu_dot_31 = F_31*x[0] + F_32*x[1] + F_33*x[2] + F_34*x[3] + F_35*x[4];
        x_tu_dot_41 = F_41*x[0] + F_42*x[1] + F_43*x[2] + F_44*x[3] + F_45*x[4];
        x_tu_dot_51 = F_51*x[0] + F_52*x[1] + F_53*x[2] + F_54*x[3] + F_55*x[4];

        //Log.i("Filter x_tu_dot",x_tu_dot_11 + " , " + x_tu_dot_21 + " , " + x_tu_dot_31 + " , " + x_tu_dot_41 + " , " + x_tu_dot_51);

        x_tu_11 = x[0] + x_tu_dot_11*dt;
        x_tu_21 = x[1] + x_tu_dot_21*dt;
        x_tu_31 = x[2] + x_tu_dot_31*dt;
        x_tu_41 = x[3] + x_tu_dot_41*dt;
        x_tu_51 = x[4] + x_tu_dot_51*dt;

        //Log.i("Filter x_tu",x_tu_11 + " , " + x_tu_21 + " , " + x_tu_31 + " , " + x_tu_41 + " , " + x_tu_51);

        P_dot_11 = F_11*P_11 + F_12*P_21 + F_13*P_31 + F_14*P_41 + F_15*P_51 + P_11*F_11 + P_12*F_12 + P_13*F_13 + P_14*F_14 + P_15*F_15 + Q_11;
        P_dot_12 = F_11*P_12 + F_12*P_22 + F_13*P_32 + F_14*P_42 + F_15*P_52 + P_11*F_21 + P_12*F_22 + P_13*F_23 + P_14*F_24 + P_15*F_25 + Q_12;
        P_dot_13 = F_11*P_13 + F_12*P_23 + F_13*P_33 + F_14*P_43 + F_15*P_53 + P_11*F_31 + P_12*F_32 + P_13*F_33 + P_14*F_34 + P_15*F_35 + Q_13;
        P_dot_14 = F_11*P_14 + F_12*P_24 + F_13*P_34 + F_14*P_44 + F_15*P_54 + P_11*F_41 + P_12*F_42 + P_13*F_43 + P_14*F_44 + P_15*F_45 + Q_14;
        P_dot_15 = F_11*P_15 + F_12*P_25 + F_13*P_35 + F_14*P_45 + F_15*P_55 + P_11*F_51 + P_12*F_52 + P_13*F_53 + P_14*F_54 + P_15*F_55 + Q_15;

        //Log.i("Filter P_dot1",P_dot_11 + " , " + P_dot_12 + " , " + P_dot_13 + " , " + P_dot_14 + " , " + P_dot_15);

        P_dot_21 = F_21*P_11 + F_22*P_21 + F_23*P_31 + F_24*P_41 + F_25*P_51 + P_21*F_11 + P_22*F_12 + P_23*F_13 + P_24*F_14 + P_25*F_15 + Q_21;
        P_dot_22 = F_21*P_12 + F_22*P_22 + F_23*P_32 + F_24*P_42 + F_25*P_52 + P_21*F_21 + P_22*F_22 + P_23*F_23 + P_24*F_24 + P_25*F_25 + Q_22;
        P_dot_23 = F_21*P_13 + F_22*P_23 + F_23*P_33 + F_24*P_43 + F_25*P_53 + P_21*F_31 + P_22*F_32 + P_23*F_33 + P_24*F_34 + P_25*F_35 + Q_23;
        P_dot_24 = F_21*P_14 + F_22*P_24 + F_23*P_34 + F_24*P_44 + F_25*P_54 + P_21*F_41 + P_22*F_42 + P_23*F_43 + P_24*F_44 + P_25*F_45 + Q_24;
        P_dot_25 = F_21*P_15 + F_22*P_25 + F_23*P_35 + F_24*P_45 + F_25*P_55 + P_21*F_51 + P_22*F_52 + P_23*F_53 + P_24*F_54 + P_25*F_55 + Q_25;

        //Log.i("Filter P_dot2",P_dot_21 + " , " + P_dot_22 + " , " + P_dot_23 + " , " + P_dot_24 + " , " + P_dot_25);

        P_dot_31 = F_31*P_11 + F_32*P_21 + F_33*P_31 + F_34*P_41 + F_35*P_51 + P_31*F_11 + P_32*F_12 + P_33*F_13 + P_34*F_14 + P_35*F_15 + Q_31;
        P_dot_32 = F_31*P_12 + F_32*P_22 + F_33*P_32 + F_34*P_42 + F_35*P_52 + P_31*F_21 + P_32*F_22 + P_33*F_23 + P_34*F_24 + P_35*F_25 + Q_32;
        P_dot_33 = F_31*P_13 + F_32*P_23 + F_33*P_33 + F_34*P_43 + F_35*P_53 + P_31*F_31 + P_32*F_32 + P_33*F_33 + P_34*F_34 + P_35*F_35 + Q_33;
        P_dot_34 = F_31*P_14 + F_32*P_24 + F_33*P_34 + F_34*P_44 + F_35*P_54 + P_31*F_41 + P_32*F_42 + P_33*F_43 + P_34*F_44 + P_35*F_45 + Q_34;
        P_dot_35 = F_31*P_15 + F_32*P_25 + F_33*P_35 + F_34*P_45 + F_35*P_55 + P_31*F_51 + P_32*F_52 + P_33*F_53 + P_34*F_54 + P_35*F_55 + Q_35;

        //Log.i("Filter P_dot3",P_dot_31 + " , " + P_dot_32 + " , " + P_dot_33 + " , " + P_dot_34 + " , " + P_dot_35);

        P_dot_41 = F_41*P_11 + F_42*P_21 + F_43*P_31 + F_44*P_41 + F_45*P_51 + P_41*F_11 + P_42*F_12 + P_43*F_13 + P_44*F_14 + P_45*F_15 + Q_41;
        P_dot_42 = F_41*P_12 + F_42*P_22 + F_43*P_32 + F_44*P_42 + F_45*P_52 + P_41*F_21 + P_42*F_22 + P_43*F_23 + P_44*F_24 + P_45*F_25 + Q_42;
        P_dot_43 = F_41*P_13 + F_42*P_23 + F_43*P_33 + F_44*P_43 + F_45*P_53 + P_41*F_31 + P_42*F_32 + P_43*F_33 + P_44*F_34 + P_45*F_35 + Q_43;
        P_dot_44 = F_41*P_14 + F_42*P_24 + F_43*P_34 + F_44*P_44 + F_45*P_54 + P_41*F_41 + P_42*F_42 + P_43*F_43 + P_44*F_44 + P_45*F_45 + Q_44;
        P_dot_45 = F_41*P_15 + F_42*P_25 + F_43*P_35 + F_44*P_45 + F_45*P_55 + P_41*F_51 + P_42*F_52 + P_43*F_53 + P_44*F_54 + P_45*F_55 + Q_45;

        //Log.i("Filter P_dot4",P_dot_41 + " , " + P_dot_42 + " , " + P_dot_43 + " , " + P_dot_44 + " , " + P_dot_45);

        P_dot_51 = F_51*P_11 + F_52*P_21 + F_53*P_31 + F_54*P_41 + F_55*P_51 + P_51*F_11 + P_52*F_12 + P_53*F_13 + P_54*F_14 + P_55*F_15 + Q_51;
        P_dot_52 = F_51*P_12 + F_52*P_22 + F_53*P_32 + F_54*P_42 + F_55*P_52 + P_51*F_21 + P_52*F_22 + P_53*F_23 + P_54*F_24 + P_55*F_25 + Q_52;
        P_dot_53 = F_51*P_13 + F_52*P_23 + F_53*P_33 + F_54*P_43 + F_55*P_53 + P_51*F_31 + P_52*F_32 + P_53*F_33 + P_54*F_34 + P_55*F_35 + Q_53;
        P_dot_54 = F_51*P_14 + F_52*P_24 + F_53*P_34 + F_54*P_44 + F_55*P_54 + P_51*F_41 + P_52*F_42 + P_53*F_43 + P_54*F_44 + P_55*F_45 + Q_54;
        P_dot_55 = F_51*P_15 + F_52*P_25 + F_53*P_35 + F_54*P_45 + F_55*P_55 + P_51*F_51 + P_52*F_52 + P_53*F_53 + P_54*F_54 + P_55*F_55 + Q_55;

        //Log.i("Filter P_dot5",P_dot_51 + " , " + P_dot_52 + " , " + P_dot_53 + " , " + P_dot_54 + " , " + P_dot_55);

        P_next_11 = P_11 + P_dot_11*dt; P_next_12 = P_12 + P_dot_12*dt; P_next_13 = P_13 + P_dot_13*dt; P_next_14 = P_14 + P_dot_14*dt; P_next_15 = P_15 + P_dot_15*dt;
        P_next_21 = P_21 + P_dot_21*dt; P_next_22 = P_22 + P_dot_22*dt; P_next_23 = P_23 + P_dot_23*dt; P_next_24 = P_24 + P_dot_24*dt; P_next_25 = P_25 + P_dot_25*dt;
        P_next_31 = P_31 + P_dot_31*dt; P_next_32 = P_32 + P_dot_32*dt; P_next_33 = P_33 + P_dot_33*dt; P_next_34 = P_34 + P_dot_34*dt; P_next_35 = P_35 + P_dot_35*dt;
        P_next_41 = P_41 + P_dot_41*dt; P_next_42 = P_42 + P_dot_42*dt; P_next_43 = P_43 + P_dot_43*dt; P_next_44 = P_44 + P_dot_44*dt; P_next_45 = P_45 + P_dot_45*dt;
        P_next_51 = P_51 + P_dot_51*dt; P_next_52 = P_52 + P_dot_52*dt; P_next_53 = P_53 + P_dot_53*dt; P_next_54 = P_54 + P_dot_54*dt; P_next_55 = P_55 + P_dot_55*dt;

        //Log.i("Filter P_next",P_next_11 + " , " + P_next_21 + " , " + P_next_31 + " , " + P_next_41 + " , " + P_next_51);

        P_11 = P_next_11; P_12 = P_next_12; P_13 = P_next_13; P_14 = P_next_14; P_15 = P_next_15;
        P_21 = P_next_21; P_22 = P_next_22; P_23 = P_next_23; P_24 = P_next_24; P_25 = P_next_25;
        P_31 = P_next_31; P_32 = P_next_32; P_33 = P_next_33; P_34 = P_next_34; P_35 = P_next_35;
        P_41 = P_next_41; P_42 = P_next_42; P_43 = P_next_43; P_44 = P_next_44; P_45 = P_next_45;
        P_51 = P_next_51; P_52 = P_next_52; P_53 = P_next_53; P_54 = P_next_54; P_55 = P_next_55;

        x_final[0] = x_tu_11;
        x_final[1] = x_tu_21;
        x_final[2] = x_tu_31;
        x_final[3] = x_tu_41;
        x_final[4] = x_tu_51;

        if ( x_final[2] < 0 ) {
            x_final[2] = 0;
        }

        return x_final;
    }

    public static double[] measUpdate(double[] x, Location preLoc, Location curLoc) {
        SS_11 = H_11*P_11 + H_12*P_21 + H_13*P_31 + H_14*P_41 + H_15*P_51;
        SS_12 = H_11*P_12 + H_12*P_22 + H_13*P_32 + H_14*P_42 + H_15*P_52;
        SS_13 = H_11*P_13 + H_12*P_23 + H_13*P_33 + H_14*P_43 + H_15*P_53;
        SS_14 = H_11*P_14 + H_12*P_24 + H_13*P_34 + H_14*P_44 + H_15*P_54;
        SS_15 = H_11*P_15 + H_12*P_25 + H_13*P_35 + H_14*P_45 + H_15*P_55;

        //Log.i("Filter SS_1",SS_11 + " , " + SS_12 + " , " + SS_13 + " , " + SS_14 + " , " + SS_15);

        SS_21 = H_21*P_11 + H_22*P_21 + H_23*P_31 + H_24*P_41 + H_25*P_51;
        SS_22 = H_21*P_12 + H_22*P_22 + H_23*P_32 + H_24*P_42 + H_25*P_52;
        SS_23 = H_21*P_13 + H_22*P_23 + H_23*P_33 + H_24*P_43 + H_25*P_53;
        SS_24 = H_21*P_14 + H_22*P_24 + H_23*P_34 + H_24*P_44 + H_25*P_54;
        SS_25 = H_21*P_15 + H_22*P_25 + H_23*P_35 + H_24*P_45 + H_25*P_55;

        //Log.i("Filter SS_2",SS_21 + " , " + SS_22 + " , " + SS_23 + " , " + SS_24 + " , " + SS_25);

        SS_31 = H_31*P_11 + H_32*P_21 + H_33*P_31 + H_34*P_41 + H_35*P_51;
        SS_32 = H_31*P_12 + H_32*P_22 + H_33*P_32 + H_34*P_42 + H_35*P_52;
        SS_33 = H_31*P_13 + H_32*P_23 + H_33*P_33 + H_34*P_43 + H_35*P_53;
        SS_34 = H_31*P_14 + H_32*P_24 + H_33*P_34 + H_34*P_44 + H_35*P_54;
        SS_35 = H_31*P_15 + H_32*P_25 + H_33*P_35 + H_34*P_45 + H_35*P_55;

        //Log.i("Filter SS_3",SS_31 + " , " + SS_32 + " , " + SS_33 + " , " + SS_34 + " , " + SS_35);

        S_11 = SS_11*H_11 + SS_12*H_12 + SS_13*H_13  + SS_14*H_14 + SS_15*H_15 + R_11;
        S_12 = SS_11*H_21 + SS_12*H_22 + SS_13*H_23  + SS_14*H_24 + SS_15*H_25 + R_12;
        S_13 = SS_11*H_31 + SS_12*H_32 + SS_13*H_33  + SS_14*H_34 + SS_15*H_35 + R_13;

        //Log.i("Filter S_1",S_11 + " , " + S_12 + " , " + S_13);

        S_21 = SS_21*H_11 + SS_22*H_12 + SS_23*H_13  + SS_24*H_14 + SS_25*H_15 + R_21;
        S_22 = SS_21*H_21 + SS_22*H_22 + SS_23*H_23  + SS_24*H_24 + SS_25*H_25 + R_22;
        S_23 = SS_21*H_31 + SS_22*H_32 + SS_23*H_33  + SS_24*H_34 + SS_25*H_35 + R_23;

        //Log.i("Filter S_2",S_21 + " , " + S_22 + " , " + S_23);

        S_31 = SS_31*H_11 + SS_32*H_12 + SS_33*H_13  + SS_34*H_14 + SS_35*H_15 + R_31;
        S_32 = SS_31*H_21 + SS_32*H_22 + SS_33*H_23  + SS_34*H_24 + SS_35*H_25 + R_32;
        S_33 = SS_31*H_31 + SS_32*H_32 + SS_33*H_33  + SS_34*H_34 + SS_35*H_35 + R_33;

        //Log.i("Filter S_3",S_31 + " , " + S_32 + " , " + S_33);

        double[][] squareMatrix = new double [3][3];
        squareMatrix[0][0] = S_11; squareMatrix[0][1] = S_12; squareMatrix[0][2] = S_13;
        squareMatrix[1][0] = S_21; squareMatrix[1][1] = S_22; squareMatrix[1][2] = S_23;
        squareMatrix[2][0] = S_31; squareMatrix[2][1] = S_32; squareMatrix[2][2] = S_33;
        inverseMatrix = Matrix.inverse(squareMatrix);

        KK_11 = P_11*H_11 + P_12*H_12 + P_13*H_13 + P_14*H_14 + P_15*H_15;
        KK_12 = P_11*H_21 + P_12*H_22 + P_13*H_23 + P_14*H_24 + P_15*H_25;
        KK_13 = P_11*H_31 + P_12*H_32 + P_13*H_33 + P_14*H_34 + P_15*H_35;

        //Log.i("Filter KK_1",KK_11 + " , " + KK_12 + " , " + KK_13);

        KK_21 = P_21*H_11 + P_22*H_12 + P_23*H_13 + P_24*H_14 + P_25*H_15;
        KK_22 = P_21*H_21 + P_22*H_22 + P_23*H_23 + P_24*H_24 + P_25*H_25;
        KK_23 = P_21*H_31 + P_22*H_32 + P_23*H_33 + P_24*H_34 + P_25*H_35;

        //Log.i("Filter KK_2",KK_21 + " , " + KK_22 + " , " + KK_23);

        KK_31 = P_31*H_11 + P_32*H_12 + P_33*H_13 + P_34*H_14 + P_35*H_15;
        KK_32 = P_31*H_21 + P_32*H_22 + P_33*H_23 + P_34*H_24 + P_35*H_25;
        KK_33 = P_31*H_31 + P_32*H_32 + P_33*H_33 + P_34*H_34 + P_35*H_35;

        //Log.i("Filter KK_3",KK_31 + " , " + KK_32 + " , " + KK_33);

        KK_41 = P_41*H_11 + P_42*H_12 + P_43*H_13 + P_44*H_14 + P_45*H_15;
        KK_42 = P_41*H_21 + P_42*H_22 + P_43*H_23 + P_44*H_24 + P_45*H_25;
        KK_43 = P_41*H_31 + P_42*H_32 + P_43*H_33 + P_44*H_34 + P_45*H_35;

        //Log.i("Filter KK_4",KK_41 + " , " + KK_42 + " , " + KK_43);

        KK_51 = P_51*H_11 + P_52*H_12 + P_53*H_13 + P_54*H_14 + P_55*H_15;
        KK_52 = P_51*H_21 + P_52*H_22 + P_53*H_23 + P_54*H_24 + P_55*H_25;
        KK_53 = P_51*H_31 + P_52*H_32 + P_53*H_33 + P_54*H_34 + P_55*H_35;

        //Log.i("Filter KK_5",KK_51 + " , " + KK_52 + " , " + KK_53);

        K_11 = KK_11*inverseMatrix[0][0] + KK_12*inverseMatrix[1][0]+ KK_13*inverseMatrix[2][0];
        K_12 = KK_11*inverseMatrix[0][1] + KK_12*inverseMatrix[1][1]+ KK_13*inverseMatrix[2][1];
        K_13 = KK_11*inverseMatrix[0][2] + KK_12*inverseMatrix[1][2]+ KK_13*inverseMatrix[2][2];

        //Log.i("Filter K_1",K_11 + " , " + K_12 + " , " + K_13);

        K_21 = KK_21*inverseMatrix[0][0] + KK_22*inverseMatrix[1][0]+ KK_23*inverseMatrix[2][0];
        K_22 = KK_21*inverseMatrix[0][1] + KK_22*inverseMatrix[1][1]+ KK_23*inverseMatrix[2][1];
        K_23 = KK_21*inverseMatrix[0][2] + KK_22*inverseMatrix[1][2]+ KK_23*inverseMatrix[2][2];

        //Log.i("Filter K_2",K_21 + " , " + K_22 + " , " + K_23);

        K_31 = KK_31*inverseMatrix[0][0] + KK_32*inverseMatrix[1][0]+ KK_33*inverseMatrix[2][0];
        K_32 = KK_31*inverseMatrix[0][1] + KK_32*inverseMatrix[1][1]+ KK_33*inverseMatrix[2][1];
        K_33 = KK_31*inverseMatrix[0][2] + KK_32*inverseMatrix[1][2]+ KK_33*inverseMatrix[2][2];

        //Log.i("Filter K_3",K_31 + " , " + K_32 + " , " + K_33);

        K_41 = KK_41*inverseMatrix[0][0] + KK_42*inverseMatrix[1][0]+ KK_43*inverseMatrix[2][0];
        K_42 = KK_41*inverseMatrix[0][1] + KK_42*inverseMatrix[1][1]+ KK_43*inverseMatrix[2][1];
        K_43 = KK_41*inverseMatrix[0][2] + KK_42*inverseMatrix[1][2]+ KK_43*inverseMatrix[2][2];

        //Log.i("Filter K_4",K_41 + " , " + K_42 + " , " + K_43);

        K_51 = KK_51*inverseMatrix[0][0] + KK_52*inverseMatrix[1][0]+ KK_53*inverseMatrix[2][0];
        K_52 = KK_51*inverseMatrix[0][1] + KK_52*inverseMatrix[1][1]+ KK_53*inverseMatrix[2][1];
        K_53 = KK_51*inverseMatrix[0][2] + KK_52*inverseMatrix[1][2]+ KK_53*inverseMatrix[2][2];

        //Log.i("Filter K_5",K_51 + " , " + K_52 + " , " + K_53);

        double [] pre_xyz = CoordTrans.llh2xyz(preLoc.getLatitude(), preLoc.getLongitude(), preLoc.getAltitude());
        double [] cur_xyz = CoordTrans.llh2xyz(curLoc.getLatitude(), curLoc.getLongitude(), curLoc.getAltitude());

        double [] enu = CoordTrans.xyz2enu(cur_xyz, pre_xyz);
        double z_speed = curLoc.getSpeed();

        z_11 = enu[0] - H_11*x_tu_11; // pos E
        z_21 = enu[1] - H_22*x_tu_21; // pos N
        z_31 = z_speed - H_33*x_tu_31; // speed

        KR_11 = K_11*R_11 + K_12*R_21 + K_13*R_31;
        KR_12 = K_11*R_12 + K_12*R_22 + K_13*R_32;
        KR_13 = K_11*R_13 + K_12*R_23 + K_13*R_33;

        KR_21 = K_21*R_11 + K_22*R_21 + K_23*R_31;
        KR_22 = K_21*R_12 + K_22*R_22 + K_23*R_32;
        KR_23 = K_21*R_13 + K_22*R_23 + K_23*R_33;

        KR_31 = K_31*R_11 + K_32*R_21 + K_33*R_31;
        KR_32 = K_31*R_12 + K_32*R_22 + K_33*R_32;
        KR_33 = K_31*R_13 + K_32*R_23 + K_33*R_33;

        KR_41 = K_41*R_11 + K_42*R_21 + K_43*R_31;
        KR_42 = K_41*R_12 + K_42*R_22 + K_43*R_32;
        KR_43 = K_41*R_13 + K_42*R_23 + K_43*R_33;

        KR_51 = K_51*R_11 + K_52*R_21 + K_53*R_31;
        KR_52 = K_51*R_12 + K_52*R_22 + K_53*R_32;
        KR_53 = K_51*R_13 + K_52*R_23 + K_53*R_33;

        P_update_11 = P_11 - (KR_11*K_11 + KR_12*K_12 + KR_13*K_13);
        P_update_12 = P_12 - (KR_11*K_21 + KR_12*K_22 + KR_13*K_23);
        P_update_13 = P_13 - (KR_11*K_31 + KR_12*K_32 + KR_13*K_33);
        P_update_14 = P_14 - (KR_11*K_41 + KR_12*K_42 + KR_13*K_43);
        P_update_15 = P_15 - (KR_11*K_51 + KR_12*K_52 + KR_13*K_53);

        P_update_21 = P_21 - (KR_21*K_11 + KR_22*K_12 + KR_23*K_13);
        P_update_22 = P_22 - (KR_21*K_21 + KR_22*K_22 + KR_23*K_23);
        P_update_23 = P_23 - (KR_21*K_31 + KR_22*K_32 + KR_23*K_33);
        P_update_24 = P_24 - (KR_21*K_41 + KR_22*K_42 + KR_23*K_43);
        P_update_25 = P_25 - (KR_21*K_51 + KR_22*K_52 + KR_23*K_53);

        P_update_31 = P_31 - (KR_31*K_11 + KR_32*K_12 + KR_33*K_13);
        P_update_32 = P_32 - (KR_31*K_21 + KR_32*K_22 + KR_33*K_23);
        P_update_33 = P_33 - (KR_31*K_31 + KR_32*K_32 + KR_33*K_33);
        P_update_34 = P_34 - (KR_31*K_41 + KR_32*K_42 + KR_33*K_43);
        P_update_35 = P_35 - (KR_31*K_51 + KR_32*K_52 + KR_33*K_53);

        P_update_41 = P_41 - (KR_41*K_11 + KR_42*K_12 + KR_43*K_13);
        P_update_42 = P_42 - (KR_41*K_21 + KR_42*K_22 + KR_43*K_23);
        P_update_43 = P_43 - (KR_41*K_31 + KR_42*K_32 + KR_43*K_33);
        P_update_44 = P_44 - (KR_41*K_41 + KR_42*K_42 + KR_43*K_43);
        P_update_45 = P_45 - (KR_41*K_51 + KR_42*K_52 + KR_43*K_53);

        P_update_51 = P_51 - (KR_51*K_11 + KR_52*K_12 + KR_53*K_13);
        P_update_52 = P_52 - (KR_51*K_21 + KR_52*K_22 + KR_53*K_23);
        P_update_53 = P_53 - (KR_51*K_31 + KR_52*K_32 + KR_53*K_33);
        P_update_54 = P_54 - (KR_51*K_41 + KR_52*K_42 + KR_53*K_43);
        P_update_55 = P_55 - (KR_51*K_51 + KR_52*K_52 + KR_53*K_53);

        P_11 = P_update_11; P_12 = P_update_12; P_13 = P_update_13;  P_14 = P_update_14;  P_15 = P_update_15;
        P_21 = P_update_21; P_22 = P_update_22; P_23 = P_update_23;  P_24 = P_update_24;  P_25 = P_update_25;
        P_31 = P_update_31; P_32 = P_update_32; P_33 = P_update_33;  P_34 = P_update_34;  P_35 = P_update_35;
        P_41 = P_update_41; P_42 = P_update_42; P_43 = P_update_43;  P_44 = P_update_44;  P_45 = P_update_45;
        P_51 = P_update_51; P_52 = P_update_52; P_53 = P_update_53;  P_54 = P_update_54;  P_55 = P_update_55;

        x_mu_11 = x_tu_11 + (K_11*z_11 + K_12*z_21 + K_13*z_31);
        x_mu_21 = x_tu_21 + (K_21*z_11 + K_22*z_21 + K_23*z_31);
        x_mu_31 = x_tu_31 + (K_31*z_11 + K_32*z_21 + K_33*z_31);
        x_mu_41 = x_tu_41 + (K_41*z_11 + K_42*z_21 + K_43*z_31);
        x_mu_51 = x_tu_51 + (K_51*z_11 + K_52*z_21 + K_53*z_31);

        x_final[0] = x_mu_11;
        x_final[1] = x_mu_21;
        x_final[2] = x_mu_31;
        x_final[3] = x_mu_41;
        x_final[4] = x_mu_51;

        if ( x_final[2] < 0 ) {
            x_final[2] = 0;
        }

        if (Math.abs(1/x_final[3]) > 2.0) {
            x_final[3] = x[3];
            x_final[4] = x[4];
        }

        double sf_check = (1/x_final[3]) - (1/x[3]);
        if(Math.abs(sf_check) > 0.2) {
            x_final[3] = x[3];
            x_final[4] = x[4];
        }
        return x_final;
    }

    public static double[] cal_AccPos(double[] x, double fz, double dt, int mode, double heading, double sin_theta, Location preLoc, Location curLoc, boolean flag_mu) {

        F_13 = Math.sin(heading);
        F_23 = Math.cos(heading);
        F_34 = - fz - GRAVITY*sin_theta;
        F_35 = 1;

        //Log.i("Filter F",F_13 + " , " + F_23 + " , " + F_34 + " , " + F_35);

        x_tu_dot_11 = F_11*x[0] + F_12*x[1] + F_13*x[2] + F_14*x[3] + F_15*x[4];
        x_tu_dot_21 = F_21*x[0] + F_22*x[1] + F_23*x[2] + F_24*x[3] + F_25*x[4];
        x_tu_dot_31 = F_31*x[0] + F_32*x[1] + F_33*x[2] + F_34*x[3] + F_35*x[4];
        x_tu_dot_41 = F_41*x[0] + F_42*x[1] + F_43*x[2] + F_44*x[3] + F_45*x[4];
        x_tu_dot_51 = F_51*x[0] + F_52*x[1] + F_53*x[2] + F_54*x[3] + F_55*x[4];

        //Log.i("Filter x_tu_dot",x_tu_dot_11 + " , " + x_tu_dot_21 + " , " + x_tu_dot_31 + " , " + x_tu_dot_41 + " , " + x_tu_dot_51);

        x_tu_11 = x[0] + x_tu_dot_11*dt;
        x_tu_21 = x[1] + x_tu_dot_21*dt;
        x_tu_31 = x[2] + x_tu_dot_31*dt;
        x_tu_41 = x[3] + x_tu_dot_41*dt;
        x_tu_51 = x[4] + x_tu_dot_51*dt;

        //Log.i("Filter x_tu",x_tu_11 + " , " + x_tu_21 + " , " + x_tu_31 + " , " + x_tu_41 + " , " + x_tu_51);

        P_dot_11 = F_11*P_11 + F_12*P_21 + F_13*P_31 + F_14*P_41 + F_15*P_51 + P_11*F_11 + P_12*F_12 + P_13*F_13 + P_14*F_14 + P_15*F_15 + Q_11;
        P_dot_12 = F_11*P_12 + F_12*P_22 + F_13*P_32 + F_14*P_42 + F_15*P_52 + P_11*F_21 + P_12*F_22 + P_13*F_23 + P_14*F_24 + P_15*F_25 + Q_12;
        P_dot_13 = F_11*P_13 + F_12*P_23 + F_13*P_33 + F_14*P_43 + F_15*P_53 + P_11*F_31 + P_12*F_32 + P_13*F_33 + P_14*F_34 + P_15*F_35 + Q_13;
        P_dot_14 = F_11*P_14 + F_12*P_24 + F_13*P_34 + F_14*P_44 + F_15*P_54 + P_11*F_41 + P_12*F_42 + P_13*F_43 + P_14*F_44 + P_15*F_45 + Q_14;
        P_dot_15 = F_11*P_15 + F_12*P_25 + F_13*P_35 + F_14*P_45 + F_15*P_55 + P_11*F_51 + P_12*F_52 + P_13*F_53 + P_14*F_54 + P_15*F_55 + Q_15;

        //Log.i("Filter P_dot1",P_dot_11 + " , " + P_dot_12 + " , " + P_dot_13 + " , " + P_dot_14 + " , " + P_dot_15);

        P_dot_21 = F_21*P_11 + F_22*P_21 + F_23*P_31 + F_24*P_41 + F_25*P_51 + P_21*F_11 + P_22*F_12 + P_23*F_13 + P_24*F_14 + P_25*F_15 + Q_21;
        P_dot_22 = F_21*P_12 + F_22*P_22 + F_23*P_32 + F_24*P_42 + F_25*P_52 + P_21*F_21 + P_22*F_22 + P_23*F_23 + P_24*F_24 + P_25*F_25 + Q_22;
        P_dot_23 = F_21*P_13 + F_22*P_23 + F_23*P_33 + F_24*P_43 + F_25*P_53 + P_21*F_31 + P_22*F_32 + P_23*F_33 + P_24*F_34 + P_25*F_35 + Q_23;
        P_dot_24 = F_21*P_14 + F_22*P_24 + F_23*P_34 + F_24*P_44 + F_25*P_54 + P_21*F_41 + P_22*F_42 + P_23*F_43 + P_24*F_44 + P_25*F_45 + Q_24;
        P_dot_25 = F_21*P_15 + F_22*P_25 + F_23*P_35 + F_24*P_45 + F_25*P_55 + P_21*F_51 + P_22*F_52 + P_23*F_53 + P_24*F_54 + P_25*F_55 + Q_25;

        //Log.i("Filter P_dot2",P_dot_21 + " , " + P_dot_22 + " , " + P_dot_23 + " , " + P_dot_24 + " , " + P_dot_25);

        P_dot_31 = F_31*P_11 + F_32*P_21 + F_33*P_31 + F_34*P_41 + F_35*P_51 + P_31*F_11 + P_32*F_12 + P_33*F_13 + P_34*F_14 + P_35*F_15 + Q_31;
        P_dot_32 = F_31*P_12 + F_32*P_22 + F_33*P_32 + F_34*P_42 + F_35*P_52 + P_31*F_21 + P_32*F_22 + P_33*F_23 + P_34*F_24 + P_35*F_25 + Q_32;
        P_dot_33 = F_31*P_13 + F_32*P_23 + F_33*P_33 + F_34*P_43 + F_35*P_53 + P_31*F_31 + P_32*F_32 + P_33*F_33 + P_34*F_34 + P_35*F_35 + Q_33;
        P_dot_34 = F_31*P_14 + F_32*P_24 + F_33*P_34 + F_34*P_44 + F_35*P_54 + P_31*F_41 + P_32*F_42 + P_33*F_43 + P_34*F_44 + P_35*F_45 + Q_34;
        P_dot_35 = F_31*P_15 + F_32*P_25 + F_33*P_35 + F_34*P_45 + F_35*P_55 + P_31*F_51 + P_32*F_52 + P_33*F_53 + P_34*F_54 + P_35*F_55 + Q_35;

        //Log.i("Filter P_dot3",P_dot_31 + " , " + P_dot_32 + " , " + P_dot_33 + " , " + P_dot_34 + " , " + P_dot_35);

        P_dot_41 = F_41*P_11 + F_42*P_21 + F_43*P_31 + F_44*P_41 + F_45*P_51 + P_41*F_11 + P_42*F_12 + P_43*F_13 + P_44*F_14 + P_45*F_15 + Q_41;
        P_dot_42 = F_41*P_12 + F_42*P_22 + F_43*P_32 + F_44*P_42 + F_45*P_52 + P_41*F_21 + P_42*F_22 + P_43*F_23 + P_44*F_24 + P_45*F_25 + Q_42;
        P_dot_43 = F_41*P_13 + F_42*P_23 + F_43*P_33 + F_44*P_43 + F_45*P_53 + P_41*F_31 + P_42*F_32 + P_43*F_33 + P_44*F_34 + P_45*F_35 + Q_43;
        P_dot_44 = F_41*P_14 + F_42*P_24 + F_43*P_34 + F_44*P_44 + F_45*P_54 + P_41*F_41 + P_42*F_42 + P_43*F_43 + P_44*F_44 + P_45*F_45 + Q_44;
        P_dot_45 = F_41*P_15 + F_42*P_25 + F_43*P_35 + F_44*P_45 + F_45*P_55 + P_41*F_51 + P_42*F_52 + P_43*F_53 + P_44*F_54 + P_45*F_55 + Q_45;

        //Log.i("Filter P_dot4",P_dot_41 + " , " + P_dot_42 + " , " + P_dot_43 + " , " + P_dot_44 + " , " + P_dot_45);

        P_dot_51 = F_51*P_11 + F_52*P_21 + F_53*P_31 + F_54*P_41 + F_55*P_51 + P_51*F_11 + P_52*F_12 + P_53*F_13 + P_54*F_14 + P_55*F_15 + Q_51;
        P_dot_52 = F_51*P_12 + F_52*P_22 + F_53*P_32 + F_54*P_42 + F_55*P_52 + P_51*F_21 + P_52*F_22 + P_53*F_23 + P_54*F_24 + P_55*F_25 + Q_52;
        P_dot_53 = F_51*P_13 + F_52*P_23 + F_53*P_33 + F_54*P_43 + F_55*P_53 + P_51*F_31 + P_52*F_32 + P_53*F_33 + P_54*F_34 + P_55*F_35 + Q_53;
        P_dot_54 = F_51*P_14 + F_52*P_24 + F_53*P_34 + F_54*P_44 + F_55*P_54 + P_51*F_41 + P_52*F_42 + P_53*F_43 + P_54*F_44 + P_55*F_45 + Q_54;
        P_dot_55 = F_51*P_15 + F_52*P_25 + F_53*P_35 + F_54*P_45 + F_55*P_55 + P_51*F_51 + P_52*F_52 + P_53*F_53 + P_54*F_54 + P_55*F_55 + Q_55;

        //Log.i("Filter P_dot5",P_dot_51 + " , " + P_dot_52 + " , " + P_dot_53 + " , " + P_dot_54 + " , " + P_dot_55);

        P_next_11 = P_11 + P_dot_11*dt; P_next_12 = P_12 + P_dot_12*dt; P_next_13 = P_13 + P_dot_13*dt; P_next_14 = P_14 + P_dot_14*dt; P_next_15 = P_15 + P_dot_15*dt;
        P_next_21 = P_21 + P_dot_21*dt; P_next_22 = P_22 + P_dot_22*dt; P_next_23 = P_23 + P_dot_23*dt; P_next_24 = P_24 + P_dot_24*dt; P_next_25 = P_25 + P_dot_25*dt;
        P_next_31 = P_31 + P_dot_31*dt; P_next_32 = P_32 + P_dot_32*dt; P_next_33 = P_33 + P_dot_33*dt; P_next_34 = P_34 + P_dot_34*dt; P_next_35 = P_35 + P_dot_35*dt;
        P_next_41 = P_41 + P_dot_41*dt; P_next_42 = P_42 + P_dot_42*dt; P_next_43 = P_43 + P_dot_43*dt; P_next_44 = P_44 + P_dot_44*dt; P_next_45 = P_45 + P_dot_45*dt;
        P_next_51 = P_51 + P_dot_51*dt; P_next_52 = P_52 + P_dot_52*dt; P_next_53 = P_53 + P_dot_53*dt; P_next_54 = P_54 + P_dot_54*dt; P_next_55 = P_55 + P_dot_55*dt;

        //Log.i("Filter P_next",P_next_11 + " , " + P_next_21 + " , " + P_next_31 + " , " + P_next_41 + " , " + P_next_51);

        P_11 = P_next_11; P_12 = P_next_12; P_13 = P_next_13; P_14 = P_next_14; P_15 = P_next_15;
        P_21 = P_next_21; P_22 = P_next_22; P_23 = P_next_23; P_24 = P_next_24; P_25 = P_next_25;
        P_31 = P_next_31; P_32 = P_next_32; P_33 = P_next_33; P_34 = P_next_34; P_35 = P_next_35;
        P_41 = P_next_41; P_42 = P_next_42; P_43 = P_next_43; P_44 = P_next_44; P_45 = P_next_45;
        P_51 = P_next_51; P_52 = P_next_52; P_53 = P_next_53; P_54 = P_next_54; P_55 = P_next_55;

        if (flag_mu && mode == 1) {
            SS_11 = H_11*P_11 + H_12*P_21 + H_13*P_31 + H_14*P_41 + H_15*P_51;
            SS_12 = H_11*P_12 + H_12*P_22 + H_13*P_32 + H_14*P_42 + H_15*P_52;
            SS_13 = H_11*P_13 + H_12*P_23 + H_13*P_33 + H_14*P_43 + H_15*P_53;
            SS_14 = H_11*P_14 + H_12*P_24 + H_13*P_34 + H_14*P_44 + H_15*P_54;
            SS_15 = H_11*P_15 + H_12*P_25 + H_13*P_35 + H_14*P_45 + H_15*P_55;

            //Log.i("Filter SS_1",SS_11 + " , " + SS_12 + " , " + SS_13 + " , " + SS_14 + " , " + SS_15);

            SS_21 = H_21*P_11 + H_22*P_21 + H_23*P_31 + H_24*P_41 + H_25*P_51;
            SS_22 = H_21*P_12 + H_22*P_22 + H_23*P_32 + H_24*P_42 + H_25*P_52;
            SS_23 = H_21*P_13 + H_22*P_23 + H_23*P_33 + H_24*P_43 + H_25*P_53;
            SS_24 = H_21*P_14 + H_22*P_24 + H_23*P_34 + H_24*P_44 + H_25*P_54;
            SS_25 = H_21*P_15 + H_22*P_25 + H_23*P_35 + H_24*P_45 + H_25*P_55;

            //Log.i("Filter SS_2",SS_21 + " , " + SS_22 + " , " + SS_23 + " , " + SS_24 + " , " + SS_25);

            SS_31 = H_31*P_11 + H_32*P_21 + H_33*P_31 + H_34*P_41 + H_35*P_51;
            SS_32 = H_31*P_12 + H_32*P_22 + H_33*P_32 + H_34*P_42 + H_35*P_52;
            SS_33 = H_31*P_13 + H_32*P_23 + H_33*P_33 + H_34*P_43 + H_35*P_53;
            SS_34 = H_31*P_14 + H_32*P_24 + H_33*P_34 + H_34*P_44 + H_35*P_54;
            SS_35 = H_31*P_15 + H_32*P_25 + H_33*P_35 + H_34*P_45 + H_35*P_55;

            //Log.i("Filter SS_3",SS_31 + " , " + SS_32 + " , " + SS_33 + " , " + SS_34 + " , " + SS_35);

            S_11 = SS_11*H_11 + SS_12*H_12 + SS_13*H_13  + SS_14*H_14 + SS_15*H_15 + R_11;
            S_12 = SS_11*H_21 + SS_12*H_22 + SS_13*H_23  + SS_14*H_24 + SS_15*H_25 + R_12;
            S_13 = SS_11*H_31 + SS_12*H_32 + SS_13*H_33  + SS_14*H_34 + SS_15*H_35 + R_13;

            //Log.i("Filter S_1",S_11 + " , " + S_12 + " , " + S_13);

            S_21 = SS_21*H_11 + SS_22*H_12 + SS_23*H_13  + SS_24*H_14 + SS_25*H_15 + R_21;
            S_22 = SS_21*H_21 + SS_22*H_22 + SS_23*H_23  + SS_24*H_24 + SS_25*H_25 + R_22;
            S_23 = SS_21*H_31 + SS_22*H_32 + SS_23*H_33  + SS_24*H_34 + SS_25*H_35 + R_23;

            //Log.i("Filter S_2",S_21 + " , " + S_22 + " , " + S_23);

            S_31 = SS_31*H_11 + SS_32*H_12 + SS_33*H_13  + SS_34*H_14 + SS_35*H_15 + R_31;
            S_32 = SS_31*H_21 + SS_32*H_22 + SS_33*H_23  + SS_34*H_24 + SS_35*H_25 + R_32;
            S_33 = SS_31*H_31 + SS_32*H_32 + SS_33*H_33  + SS_34*H_34 + SS_35*H_35 + R_33;

            //Log.i("Filter S_3",S_31 + " , " + S_32 + " , " + S_33);

            double[][] squareMatrix = new double [3][3];
            squareMatrix[0][0] = S_11; squareMatrix[0][1] = S_12; squareMatrix[0][2] = S_13;
            squareMatrix[1][0] = S_21; squareMatrix[1][1] = S_22; squareMatrix[1][2] = S_23;
            squareMatrix[2][0] = S_31; squareMatrix[2][1] = S_32; squareMatrix[2][2] = S_33;
            inverseMatrix = Matrix.inverse(squareMatrix);

            KK_11 = P_11*H_11 + P_12*H_12 + P_13*H_13 + P_14*H_14 + P_15*H_15;
            KK_12 = P_11*H_21 + P_12*H_22 + P_13*H_23 + P_14*H_24 + P_15*H_25;
            KK_13 = P_11*H_31 + P_12*H_32 + P_13*H_33 + P_14*H_34 + P_15*H_35;

            //Log.i("Filter KK_1",KK_11 + " , " + KK_12 + " , " + KK_13);

            KK_21 = P_21*H_11 + P_22*H_12 + P_23*H_13 + P_24*H_14 + P_25*H_15;
            KK_22 = P_21*H_21 + P_22*H_22 + P_23*H_23 + P_24*H_24 + P_25*H_25;
            KK_23 = P_21*H_31 + P_22*H_32 + P_23*H_33 + P_24*H_34 + P_25*H_35;

            //Log.i("Filter KK_2",KK_21 + " , " + KK_22 + " , " + KK_23);

            KK_31 = P_31*H_11 + P_32*H_12 + P_33*H_13 + P_34*H_14 + P_35*H_15;
            KK_32 = P_31*H_21 + P_32*H_22 + P_33*H_23 + P_34*H_24 + P_35*H_25;
            KK_33 = P_31*H_31 + P_32*H_32 + P_33*H_33 + P_34*H_34 + P_35*H_35;

            //Log.i("Filter KK_3",KK_31 + " , " + KK_32 + " , " + KK_33);

            KK_41 = P_41*H_11 + P_42*H_12 + P_43*H_13 + P_44*H_14 + P_45*H_15;
            KK_42 = P_41*H_21 + P_42*H_22 + P_43*H_23 + P_44*H_24 + P_45*H_25;
            KK_43 = P_41*H_31 + P_42*H_32 + P_43*H_33 + P_44*H_34 + P_45*H_35;

            //Log.i("Filter KK_4",KK_41 + " , " + KK_42 + " , " + KK_43);

            KK_51 = P_51*H_11 + P_52*H_12 + P_53*H_13 + P_54*H_14 + P_55*H_15;
            KK_52 = P_51*H_21 + P_52*H_22 + P_53*H_23 + P_54*H_24 + P_55*H_25;
            KK_53 = P_51*H_31 + P_52*H_32 + P_53*H_33 + P_54*H_34 + P_55*H_35;

            //Log.i("Filter KK_5",KK_51 + " , " + KK_52 + " , " + KK_53);

            K_11 = KK_11*inverseMatrix[0][0] + KK_12*inverseMatrix[1][0]+ KK_13*inverseMatrix[2][0];
            K_12 = KK_11*inverseMatrix[0][1] + KK_12*inverseMatrix[1][1]+ KK_13*inverseMatrix[2][1];
            K_13 = KK_11*inverseMatrix[0][2] + KK_12*inverseMatrix[1][2]+ KK_13*inverseMatrix[2][2];

            //Log.i("Filter K_1",K_11 + " , " + K_12 + " , " + K_13);

            K_21 = KK_21*inverseMatrix[0][0] + KK_22*inverseMatrix[1][0]+ KK_23*inverseMatrix[2][0];
            K_22 = KK_21*inverseMatrix[0][1] + KK_22*inverseMatrix[1][1]+ KK_23*inverseMatrix[2][1];
            K_23 = KK_21*inverseMatrix[0][2] + KK_22*inverseMatrix[1][2]+ KK_23*inverseMatrix[2][2];

            //Log.i("Filter K_2",K_21 + " , " + K_22 + " , " + K_23);

            K_31 = KK_31*inverseMatrix[0][0] + KK_32*inverseMatrix[1][0]+ KK_33*inverseMatrix[2][0];
            K_32 = KK_31*inverseMatrix[0][1] + KK_32*inverseMatrix[1][1]+ KK_33*inverseMatrix[2][1];
            K_33 = KK_31*inverseMatrix[0][2] + KK_32*inverseMatrix[1][2]+ KK_33*inverseMatrix[2][2];

            //Log.i("Filter K_3",K_31 + " , " + K_32 + " , " + K_33);

            K_41 = KK_41*inverseMatrix[0][0] + KK_42*inverseMatrix[1][0]+ KK_43*inverseMatrix[2][0];
            K_42 = KK_41*inverseMatrix[0][1] + KK_42*inverseMatrix[1][1]+ KK_43*inverseMatrix[2][1];
            K_43 = KK_41*inverseMatrix[0][2] + KK_42*inverseMatrix[1][2]+ KK_43*inverseMatrix[2][2];

            //Log.i("Filter K_4",K_41 + " , " + K_42 + " , " + K_43);

            K_51 = KK_51*inverseMatrix[0][0] + KK_52*inverseMatrix[1][0]+ KK_53*inverseMatrix[2][0];
            K_52 = KK_51*inverseMatrix[0][1] + KK_52*inverseMatrix[1][1]+ KK_53*inverseMatrix[2][1];
            K_53 = KK_51*inverseMatrix[0][2] + KK_52*inverseMatrix[1][2]+ KK_53*inverseMatrix[2][2];

            //Log.i("Filter K_5",K_51 + " , " + K_52 + " , " + K_53);

            double [] pre_xyz = CoordTrans.llh2xyz(preLoc.getLatitude(), preLoc.getLongitude(), preLoc.getAltitude());
            double [] cur_xyz = CoordTrans.llh2xyz(curLoc.getLatitude(), curLoc.getLongitude(), curLoc.getAltitude());

            double [] enu = CoordTrans.xyz2enu(cur_xyz, pre_xyz);
            double z_speed = curLoc.getSpeed();

            z_11 = enu[0] - H_11*x_tu_11; // pos E
            z_21 = enu[1] - H_22*x_tu_21; // pos N
            z_31 = z_speed - H_33*x_tu_31; // speed

            KR_11 = K_11*R_11 + K_12*R_21 + K_13*R_31;
            KR_12 = K_11*R_12 + K_12*R_22 + K_13*R_32;
            KR_13 = K_11*R_13 + K_12*R_23 + K_13*R_33;

            KR_21 = K_21*R_11 + K_22*R_21 + K_23*R_31;
            KR_22 = K_21*R_12 + K_22*R_22 + K_23*R_32;
            KR_23 = K_21*R_13 + K_22*R_23 + K_23*R_33;

            KR_31 = K_31*R_11 + K_32*R_21 + K_33*R_31;
            KR_32 = K_31*R_12 + K_32*R_22 + K_33*R_32;
            KR_33 = K_31*R_13 + K_32*R_23 + K_33*R_33;

            KR_41 = K_41*R_11 + K_42*R_21 + K_43*R_31;
            KR_42 = K_41*R_12 + K_42*R_22 + K_43*R_32;
            KR_43 = K_41*R_13 + K_42*R_23 + K_43*R_33;

            KR_51 = K_51*R_11 + K_52*R_21 + K_53*R_31;
            KR_52 = K_51*R_12 + K_52*R_22 + K_53*R_32;
            KR_53 = K_51*R_13 + K_52*R_23 + K_53*R_33;

            P_update_11 = P_11 - (KR_11*K_11 + KR_12*K_12 + KR_13*K_13);
            P_update_12 = P_12 - (KR_11*K_21 + KR_12*K_22 + KR_13*K_23);
            P_update_13 = P_13 - (KR_11*K_31 + KR_12*K_32 + KR_13*K_33);
            P_update_14 = P_14 - (KR_11*K_41 + KR_12*K_42 + KR_13*K_43);
            P_update_15 = P_15 - (KR_11*K_51 + KR_12*K_52 + KR_13*K_53);

            P_update_21 = P_21 - (KR_21*K_11 + KR_22*K_12 + KR_23*K_13);
            P_update_22 = P_22 - (KR_21*K_21 + KR_22*K_22 + KR_23*K_23);
            P_update_23 = P_23 - (KR_21*K_31 + KR_22*K_32 + KR_23*K_33);
            P_update_24 = P_24 - (KR_21*K_41 + KR_22*K_42 + KR_23*K_43);
            P_update_25 = P_25 - (KR_21*K_51 + KR_22*K_52 + KR_23*K_53);

            P_update_31 = P_31 - (KR_31*K_11 + KR_32*K_12 + KR_33*K_13);
            P_update_32 = P_32 - (KR_31*K_21 + KR_32*K_22 + KR_33*K_23);
            P_update_33 = P_33 - (KR_31*K_31 + KR_32*K_32 + KR_33*K_33);
            P_update_34 = P_34 - (KR_31*K_41 + KR_32*K_42 + KR_33*K_43);
            P_update_35 = P_35 - (KR_31*K_51 + KR_32*K_52 + KR_33*K_53);

            P_update_41 = P_41 - (KR_41*K_11 + KR_42*K_12 + KR_43*K_13);
            P_update_42 = P_42 - (KR_41*K_21 + KR_42*K_22 + KR_43*K_23);
            P_update_43 = P_43 - (KR_41*K_31 + KR_42*K_32 + KR_43*K_33);
            P_update_44 = P_44 - (KR_41*K_41 + KR_42*K_42 + KR_43*K_43);
            P_update_45 = P_45 - (KR_41*K_51 + KR_42*K_52 + KR_43*K_53);

            P_update_51 = P_51 - (KR_51*K_11 + KR_52*K_12 + KR_53*K_13);
            P_update_52 = P_52 - (KR_51*K_21 + KR_52*K_22 + KR_53*K_23);
            P_update_53 = P_53 - (KR_51*K_31 + KR_52*K_32 + KR_53*K_33);
            P_update_54 = P_54 - (KR_51*K_41 + KR_52*K_42 + KR_53*K_43);
            P_update_55 = P_55 - (KR_51*K_51 + KR_52*K_52 + KR_53*K_53);

            P_11 = P_update_11; P_12 = P_update_12; P_13 = P_update_13;  P_14 = P_update_14;  P_15 = P_update_15;
            P_21 = P_update_21; P_22 = P_update_22; P_23 = P_update_23;  P_24 = P_update_24;  P_25 = P_update_25;
            P_31 = P_update_31; P_32 = P_update_32; P_33 = P_update_33;  P_34 = P_update_34;  P_35 = P_update_35;
            P_41 = P_update_41; P_42 = P_update_42; P_43 = P_update_43;  P_44 = P_update_44;  P_45 = P_update_45;
            P_51 = P_update_51; P_52 = P_update_52; P_53 = P_update_53;  P_54 = P_update_54;  P_55 = P_update_55;

            x_mu_11 = x_tu_11 + (K_11*z_11 + K_12*z_21 + K_13*z_31);
            x_mu_21 = x_tu_21 + (K_21*z_11 + K_22*z_21 + K_23*z_31);
            x_mu_31 = x_tu_31 + (K_31*z_11 + K_32*z_21 + K_33*z_31);
            x_mu_41 = x_tu_41 + (K_41*z_11 + K_42*z_21 + K_43*z_31);
            x_mu_51 = x_tu_51 + (K_51*z_11 + K_52*z_21 + K_53*z_31);

            x_final[0] = x_mu_11;
            x_final[1] = x_mu_21;
            x_final[2] = x_mu_31;
            x_final[3] = x_mu_41;
            x_final[4] = x_mu_51;

            if ( x_final[2] < 0 ) {
                x_final[2] = 0;
            }

            if (Math.abs(1/x_final[3]) > 1.7) {
                x_final[3] = x[3];
                x_final[4] = x[4];
            }

            double sf_check = (1/x_final[3]) - (1/x[3]);
            if(sf_check < 0) {
                x_final[3] = x[3];
                x_final[4] = x[4];
            } else if(sf_check > 0.3) {
                x_final[3] = x[3];
                x_final[4] = x[4];
            }

        } else {
            x_final[0] = x_tu_11;
            x_final[1] = x_tu_21;
            x_final[2] = x_tu_31;
            x_final[3] = x_tu_41;
            x_final[4] = x_tu_51;

            if ( x_final[2] < 0 ) {
                x_final[2] = 0;
            }

        }
        return x_final;
    }
}
