package com.example.locationm_gps;

import android.location.Location;

public class DataVariable {
    public static boolean START_FLAG = false;

    public static double PRESSURE = 1000;

    public static double[] HeadingX;
    public static double[] SpdPosX;

    public static double Lat_GPS = 0;
    public static double Lon_GPS = 0;
    public static double Alt_GPS = 0;
    public static double Heading_GPS = 0;
    public static double Speed_GPS = 0;

    public static double Lat_INS = 0;
    public static double Lon_INS = 0;
    public static double Heading_INS = 0;
    public static double Speed_INS = 0;

    public static double Slope = 0;
    public static double ScaleFactor_H = 0;
    public static double Bias_H = 0;
    public static double ScaleFactor = 0;
    public static double Bias = 0;

    public static Location preLoc = null;
    public static Location curLoc = null;
}
