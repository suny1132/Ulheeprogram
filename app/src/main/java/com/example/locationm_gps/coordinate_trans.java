package com.example.locationm_gps;

public class coordinate_trans {

    private static double a = 6378137.0000;	// earth semimajor axis in meters
    private static double b = 6356752.3142;	// earth semiminor axis in meters
    private static double e = Math.sqrt(1-Math.pow((b/a),2));

    public static double[] llh2xyz(double latitude, double longitude, double height)
    {
        double[] xyz = {0,0,0};

        double DtoR = Math.PI/180;

        double phi = latitude*DtoR;
        double lambda = longitude*DtoR;
        double h = height;

        double sinphi = Math.sin(phi);
        double cosphi = Math.cos(phi);
        double coslam = Math.cos(lambda);
        double sinlam = Math.sin(lambda);
        double tan2phi = Math.pow(Math.tan(phi),2);
        double tmp = 1 - e*e;
        double tmpden = Math.sqrt(1 + tmp*tan2phi);

        xyz[0] = (a*coslam)/tmpden + h*coslam*cosphi;  //x
        xyz[1] = (a*sinlam)/tmpden + h*sinlam*cosphi;  //y

        double tmp2 = Math.sqrt(1 - e*e*sinphi*sinphi);
        xyz[2] = (a*tmp*sinphi)/tmp2 + h*sinphi;       //z

        return xyz;
    }

    public static double[] xyz2llh(double[] xyz)
    {

        double[] llh = {0,0,0};
        double lat = 0;
        double lon = 0;
        double height = 0;

        double x = xyz[0];
        double y = xyz[1];
        double z = xyz[2];
        double x2 = x*x;
        double y2 = y*y;
        double z2 = z*z;

        double b2 = b*b;
        double e2 = e*e;
        double ep = e*(a/b);
        double r = Math.sqrt(x2 + y2);
        double r2 = r*r;
        double E2 = a*a - b*b;
        double F = 54*b2*z2;
        double G = r2 + (1-e2)*z2 - e2*E2;
        double c = (e2*e2*F*r2)/(G*G*G);
        double s = Math.pow(( 1 + c + Math.sqrt(c*c + 2*c)),(1/3));
        double P = F / (3 * (s+1/s+1)*(s+1/s+1) * G*G);
        double Q = Math.sqrt(1+2*e2*e2*P);
        double ro = -(P*e2*r)/(1+Q) + Math.sqrt((a*a/2)*(1+1/Q) - (P*(1-e2)*z2)/(Q*(1+Q)) - P*r2/2);
        double tmp = Math.pow((r - e2*ro),2);
        double U = Math.sqrt( tmp + z2 );
        double V = Math.sqrt( tmp + (1-e2)*z2 );
        double zo = (b2*z)/(a*V);

        height = U*( 1 - b2/(a*V) );

        lat = Math.atan( (z + ep*ep*zo)/r );

        double temp = Math.atan(y/x);

        if(x >= 0)  lon = temp;
        else if((x < 0) & (y >= 0)) lon = Math.PI + temp;
        else lon = temp - Math.PI;


        llh[0] = lat;  //라디안
        llh[1] = lon;
        llh[2] = height;


        return llh;
    }


    public static double[] xyz2enu(double[] xyz, double[] orgxyz)
    {
        //%ENU2XYZ	Convert from rectangular local-level-tangent
        //            %               ('East'-'North'-Up) coordinates to WGS-84
        //            %               ECEF cartesian coordinates.
        //%
        //%	xyz = ENU2XYZ(enu,orgxyz)
        //            %
        //%	enu(1) = 'East'-coordinate relative to local origin (meters)
        //            %	enu(2) = 'North'-coordinate relative to local origin (meters)
        //            %	enu(3) = Up-coordinate relative to local origin (meters)
        //            %
        //%	orgxyz(1) = ECEF x-coordinate of local origin in meters
        //            %	orgxyz(2) = ECEF y-coordinate of local origin in meters
        //            %	orgxyz(3) = ECEF z-coordinate of local origin in meters
        //            %
        //%	xyz(1,1) = ECEF x-coordinate in meters
        //%	xyz(2,1) = ECEF y-coordinate in meters
        //%	xyz(3,1) = ECEF z-coordinate in meters
        //
        //%	Reference: Alfred Leick, GPS Satellite Surveying, 2nd ed.,
        //%	           Wiley-Interscience, John Wiley & Sons,
        //%	           New York, 1995.
        //            %
        //%	M. & S. Braasch 10-96
        //            %	Copyright (c) 1996 by GPSoft
        //%	All Rights Reserved.
        double[] enu = {0,0,0};

        double[] tmpxyz = xyz;
        double[] tmporg = orgxyz;
        double[] orgllh = xyz2llh(tmpxyz);

        double phi = orgllh[0];
        double lambda = orgllh[1];

        double[] difxyz = {0,0,0};

        double sinphi = Math.sin(phi);
        double cosphi = Math.cos(phi);
        double coslam = Math.cos(lambda);
        double sinlam = Math.sin(lambda);

        difxyz[0] = tmpxyz[0] - tmporg[0];
        difxyz[1] = tmpxyz[1] - tmporg[1];
        difxyz[2] = tmpxyz[2] - tmporg[2];

        enu[0] = -sinlam*difxyz[0] + coslam*difxyz[1];
        enu[1] = -sinphi*coslam*difxyz[0] - sinphi*sinlam*difxyz[1] + cosphi*difxyz[2];
        enu[2] = cosphi*coslam*difxyz[0] + cosphi*sinlam*difxyz[1] + sinphi*difxyz[2];

        return enu;
    }
    public static double[] enu2xyz(double[] enu, double[] orgxyz)
    {
        double[] xyz = {0, 0, 0};

        double[][] R = {{0,0,0},{0,0,0},{0,0,0}};
        double[][] inv_R = {{0,0,0},{0,0,0},{0,0,0}};

        double[] orgllh = xyz2llh(orgxyz);


        double phi = orgllh[0];
        double lambda = orgllh[1];

        double[] difxyz = {0,0,0};

        double sinphi = Math.sin(phi);
        double cosphi = Math.cos(phi);
        double coslam = Math.cos(lambda);
        double sinlam = Math.sin(lambda);

        R[0][0] = -sinlam;          R[0][1] = coslam;            R[0][2] = 0;
        R[1][0] = -sinphi*coslam;   R[1][1] = -sinphi*sinlam;    R[1][2] = cosphi;
        R[2][0] = cosphi*coslam;    R[2][1] = cosphi*sinlam;     R[2][2] = sinphi;

        inv_R = Matrix.inverse(R);

        difxyz[0] = inv_R[0][0]*enu[0] + inv_R[0][1]*enu[1] + inv_R[0][2]*enu[2];
        difxyz[1] = inv_R[1][0]*enu[0] + inv_R[1][1]*enu[1] + inv_R[1][2]*enu[2];
        difxyz[2] = inv_R[2][0]*enu[0] + inv_R[2][1]*enu[1] + inv_R[2][2]*enu[2];

        xyz[0] = orgxyz[0] + difxyz[0];
        xyz[1] = orgxyz[1] + difxyz[1];
        xyz[2] = orgxyz[2] + difxyz[2];

        return xyz;
    }

    private double[] Cal_Matrix(double sinphi, double cosphi, double sinlam, double coslam, double[] tmpenu)
    {
        double[] difxyz = {0,0,0};
        double a,b,c,d,e,f,g,h,i = 0;

        a = -sinlam;
        b = coslam;
        c = 0;

        d = -sinphi*coslam;
        e = -sinphi*sinlam;
        f = cosphi;

        g = cosphi*coslam;
        h = cosphi*sinlam;
        i = sinphi;

        double D = a*e*i + b*f*g + c*d*h - c*e*g - b*d*i - a*f*h;

        difxyz[0] = (1/D) * ((e*i - f*h) * tmpenu[0] - (b*i - c*h) * tmpenu[1] + (b*f - c*e) * tmpenu[2]);
        difxyz[1] = (1/D) * (-(d*i - f*g) * tmpenu[0] + (a*i - c*g) * tmpenu[1] - (a*f - c*d) * tmpenu[2]);
        difxyz[2] = (1/D) * ((d*h - e*g) * tmpenu[0] - (a*h - b*g) * tmpenu[1] + (a*e - b*d) * tmpenu[2]);
        return difxyz;
    }
}
