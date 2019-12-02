package com.example.locationm_gps;

import java.util.jar.Manifest;

public class coordinate_trans {

    private double a = 6378137.0000;	// earth semimajor axis in meters
    private double b = 6356752.3142;	// earth semiminor axis in meters
    private double  e = Math.sqrt(1-Math.pow((b/a),2));

    public double[] llh2xyz(double latitude, double longitude, double height)
    {
        //        LLH2XYZ  Convert from latitude, longitude and height
        //         to ECEF cartesian coordinates.  WGS-84
        //
        //	xyz = LLH2XYZ(llh)
        //
        //	llh(1) = latitude in radians
        //	llh(2) = longitude in radians
        //	llh(3) = height above ellipsoid in meters
        //
        //	xyz(1) = ECEF x-coordinate in meters
        //	xyz(2) = ECEF y-coordinate in meters
        //	xyz(3) = ECEF z-coordinate in meters
        //
        //	Reference: Understanding GPS: Principles and Applications,
        //	           Elliott D. Kaplan, Editor, Artech House Publishers,
        //	           Boston, 1996.
        //
        //	M. & S. Braasch 10-96
        //            	Copyright (c) 1996 by GPSoft
        //	All Rights Reserved.

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

    public double[] xyz2llh(double[] xyz)
    {
        //        %XYZ2LLH	Convert from ECEF cartesian coordinates to
        //            %               latitude, longitude and height.  WGS-84
        //            %
        //%	llh = XYZ2LLH(xyz)
        //            %
        //%    INPUTS
        //            %	xyz(1) = ECEF x-coordinate in meters
        //%	xyz(2) = ECEF y-coordinate in meters
        //%	xyz(3) = ECEF z-coordinate in meters
        //%
        //%    OUTPUTS
        //            %	llh(1) = latitude in radians
        //%	llh(2) = longitude in radians
        //%	llh(3) = height above ellipsoid in meters
        //
        //%	Reference: Understanding GPS: Principles and Applications,
        //%	           Elliott D. Kaplan, Editor, Artech House Publishers,
        //%	           Boston, 1996.
        //            %
        //%	M. & S. Braasch 10-96
        //            %	Copyright (c) 1996 by GPSoft
        //%	All Rights Reserved.
        //            %

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
        double s =Math.pow(( 1 + c + Math.sqrt(c*c + 2*c)),(1/3));
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


    public double[] enu2xyz(double[] enu, double[] xyz)
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
        double[] tmpenu = enu;
        double[] tmpxyz = xyz;
        double[] orgllh = xyz2llh(tmpxyz);

        double phi = orgllh[0];
        double lambda = orgllh[1];

        double sinphi = Math.sin(phi);
        double cosphi = Math.cos(phi);
        double coslam = Math.cos(lambda);
        double sinlam = Math.sin(lambda);



        return xyz;
    }

    public double[] Cal_Matrix(double sinphi, double cosphi, double sinlam, double coslam, double[] tmpenu)
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
