package com.example.locationm_gps;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.GnssStatus;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.telephony.mbms.FileInfo;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.sql.Ref;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;


public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback, LocationListener, SensorEventListener {

    private GoogleMap mMap;
    private Geocoder geocoder;
    private Marker currentMarker = null;
    private Marker SearchMarker = null;
    private Marker gpsMarker = null;

    private LocationManager locationManager;
    private GnssStatus.Callback mGnssStatuscallback;

    private static SensorManager mSensorManager;
    private Sensor mAccelerometer;
    private Sensor mGyroscope;
    private Sensor mPressure;
    private float[] mLastAccelerometer = new float[3];
    private float[] mLastGyroscope = new float[3];
    private float mLastPressure;

    LatLng currentPosition;
    LatLng prePosition;
    LatLng LastPosition;

    private TextView txtGPSLocation, txtLocationAcc, txtMode, txtSpeed, txtSf, txtHeading, txtGPSINSLocation, txtSlope, txtSearch, txtHeading_GPS; //
    private String TAG = "LocationProvider";
    private static final String FILE_PREFIX = "Map_Log";
    private final Object mFileLock = new Object();

    private int MIN_UPDATE_MILLIS = 1000;
    private int MIN_UPDATE_MITERS = 0;
    private double MIN_accuracy;
    private double Accuracy_Threshold = 0;
    private double INITIAL_HEIGHT = 50;
    private double INITIAL_PRESSURE = 1020;
    private double PI = Math.PI;

    private int ZoomLevel = 16;
    private Button btnZin, btnZOut; //줌 버튼 사용

    private Toolbar mToolbar; //툴바사용

    private boolean modeAuto = true;
    private int mode = 2;

    private long cur_location_time = 0, pre_location_time = 0;


    private boolean mSwitch = true; //카메라 이동 확인
    private ImageButton mPositionButton; //위치 고정 버튼 사용
    private ImageButton btnSave; //데이터 저장 버튼
    private ImageButton btnSearch;//검색 버튼

    BufferedWriter currentFileWriter;
    String currentFilePath;

    private boolean save_mode = false;
    public boolean headingFilter_flag = false;
    public boolean accposFilter_flag = false;

    final static String foldername = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Map_Log";  //폴더이름
    final static String fileName = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date()) + "data.txt";//파일이름
    //final static String filename = "data.txt"; //파일이름

    private boolean flag = true;

    private Location cur_GPS_Location;
    private Location Ref_GPS_Location;
    private Location lastKnownLocation;

    private boolean onGPS = false;

    private float Map_angle = 0;
    private float Last_Map_angle = 0;

    private int temp_epoch = 0;
    private float final_angle = 0;

    private float MIN_sum_acc = 5;
    private float res_acc = 0;
    private double timestamp_gyro;
    private double timestamp_acc;
    private double dt_gyro;
    private double dt_acc;
    private double dt_all_gyro = 0;
    private double dt_all_acc = 0;
    private double pitch;

    private double cur_gyro_y = 0;
    private double pre_gyro_y = 0;
    private double cur_acc_z = 0;
    private double cur_acc_z_V;
    private double cur_acc_z_D;

    private double pre_acc_z = 0;
    private double pre_acc_z_V = 0;
    private double ave_acc_z = 0;
    private double ave_acc_z_V = 0;
    private int acc_count = 0;
    private int gyro_count = 0;
    private int sec_count = 0;

    private double pre_heading = 0, cur_heading = 0;
    private double pre_sf_h = 0.99975, cur_sf_h = 0.99975;
    private double pre_bias_h = 0.00001, cur_bias_h = 0.00001;

    private double pre_pos_e = 0, cur_pos_e = 0;
    private double pre_pos_n = 0, cur_pos_n = 0;
    private double pre_speed = 0, cur_speed = 0;
    private double pre_sf = 1.2, cur_sf = 1.2;
    private double pre_bias = 0.0001, cur_bias = 0.0001;

    private int THREAD_TIME = 100; //ms
    private double dt_sensor = THREAD_TIME * 1e-3;
    private double RtoD = 180 / Math.PI;
    private double DtoR = Math.PI / 180;
    private static final float NS2S = 1.0f / 1000000000.0f; //나노세컨드 -> 세컨드
    private double ALPHA_PRESSURE = 1 / 5.255;
    private double BETA_PRESSURE = 44330;
    private double WINDOW_PRESSURE = 300;
    private int C_WINDOW = 1;
    private int WINDOW_SLOPE = 600;
    private int WINDOW_VARIANCE = 20;
    private int WINDOW_HEADING = 2;
    private int WINDOW_SF_BIAS = 10;
    private double THRESHOLD_VARIANCE = 0.02;

    private int dataIndex_pressure = 1;
    private int dataIndex_variance = 1;
    private int dataIndex_heading = 1;
    private int dataIndex_slope = 1;
    private int dataIndex_sf_bias = 1;

    private double altitude = 50;
    private double sin_theta = 0;
    private double sin_theta_old = 0;

    double final_dGPS_h, final_GPS_h;

    private double acc_norm[] = new double[20];
    private double acc_var[] = new double[20];
    private double stack_alt[] = new double[WINDOW_SLOPE];
    private double stack_acc[] = new double[WINDOW_SLOPE];
    private double stack_spd[] = new double[WINDOW_SLOPE];
    private double stack_tic[] = new double[WINDOW_SLOPE];
    private double averagePressure, averageHeading, pre_heading_meas;
    private double averageSf_h = 0, averageBias_h = 0, averageSf = 0, averageBias = 0;

    private double[] Ref_xyz = {0, 0, 0};
    private double[] enu = {0, 0, 0};
    private double[] Cur_xyz = {0, 0, 0};
    private double[] Cur_llh = {0, 0, 0};

    private double[] pre_x_h = {0, (1 / pre_sf_h), (pre_bias_h / pre_sf_h)};
    private double[] cur_x_h = {0, (1 / pre_sf_h), (pre_bias_h / pre_sf_h)};

    private double[] pre_x = {0, 0, 0, (1 / pre_sf), (pre_bias / pre_sf)};
    private double[] cur_x = {0, 0, 0, (1 / pre_sf), (pre_bias / pre_sf)};

    double[] slopeOut = {0, 0};
    double[] ins_cur_llh = {37.5, 127.5, 50};
    double[] save_llh = {0, 0, 0};
    double save_spd = 0;
    double save_hding = 0;


    double ins_cur_heading = 0;
    double heading_stack = 0;
    double map_heading = 0;
    double heading_meas = 0;

    public double GPS_lat = 0, GPS_lon = 0, GPS_alt = 0, GPS_speed = 0, GPS_heading = 0;
    public String Strdata;
    Location preLoc = null;
    Location curLoc = null;

    coordinate_trans Coor = new coordinate_trans();

    headingFilter hFilter = new headingFilter();
    accposFilter apFilter = new accposFilter();

    private float accuracyData = 0;

    private double cur_time = 0;
    private double pre_time = 0;
    private double d_time = 0;

    private List listsatellite = new ArrayList();
    private double G_in = 0, G_out = 0, T_in = 0, T_out = 0, S_in = 0, S_out = 0, D_in = 0, D_out = 0;
    private double Time = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate()");
        //----------Map--------//
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        //----------Map--------//

        //----------TextView--------//
//        txtGPSLocation = (TextView) findViewById(R.id.tv_GpsLocation);
//        txtLocationAcc = (TextView) findViewById(R.id.tv_LocationAcc);
        txtMode = (TextView) findViewById(R.id.toolbar_mode); //현재 모드 보여주기
/*        txtGPSINSLocation = (TextView) findViewById(R.id.tv_GpsInsLocation);
        txtHeading = (TextView) findViewById(R.id.tv_Heading);
        txtHeading_GPS = (TextView) findViewById(R.id.tv_HeadingGPS);
        txtSpeed = (TextView) findViewById(R.id.tv_Speed);
        txtSf = (TextView) findViewById(R.id.tv_Sf);
        txtSlope = (TextView) findViewById(R.id.tv_Slope);
        txtSearch = (TextView) findViewById(R.id.tv_search);*/
        //----------TextView--------//

        //----------Toolbar--------//
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false); //기존 툴바 타이틀 보이는지 여부
        //----------Toolbar--------//

        //----------Button--------//
        mPositionButton = (ImageButton) findViewById(R.id.bt_position); //현재 위치로 돌아오는 버튼
        btnZin = (Button) findViewById(R.id.btn_zoomIn); //줌 인 버튼
        btnZOut = (Button) findViewById(R.id.btn_zoomOut); //줌 아웃 버튼
        btnSave = (ImageButton) findViewById(R.id.btn_save); //데이터 저장 버튼 테스트
//        btnSearch = (ImageButton) findViewById(R.id.btn_search); // 검색 버튼
        //----------Button--------//

        //----------Sensor--------//
        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mGyroscope = mSensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        mPressure = mSensorManager.getDefaultSensor(Sensor.TYPE_PRESSURE);
        //----------Sensor--------//


        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (save_mode == false) {
                    save_mode = true;
                    synchronized (mFileLock) {
                        File baseDirectory;
                        baseDirectory = new File(Environment.getExternalStorageDirectory(), FILE_PREFIX);
                        baseDirectory.mkdirs();
                        Date date = new Date();
                        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");
                        dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
                        String dateStr = dateFormat.format(date);

                        String fileName = "LOCATION_" + dateStr + ".txt";
                        File currentFile = new File(baseDirectory, fileName);
                        currentFilePath = currentFile.getAbsolutePath();
                        //BufferedWriter currentFileWriter;
                        try {
                            currentFileWriter = new BufferedWriter(new FileWriter(currentFile));
                        } catch (IOException e) {
                            logException("Could not open the file : " + currentFilePath, e);
                            return;
                        }
                    }

                    btnSave.setBackgroundResource(R.drawable.save_click);
                } else {
                    save_mode = false;

                    try {
                        currentFileWriter.close();
                    } catch (IOException e) {
                        logException("Could not close the file : " + currentFilePath, e);
                        return;
                    }

                    btnSave.setBackgroundResource(R.drawable.save);
                }

            }
        });

        mGnssStatuscallback = new GnssStatus.Callback() {
            @Override
            public void onStarted() {
                Log.d("Gnss.Callback()", " : onStarted()");
            }

            @Override
            public void onSatelliteStatusChanged(GnssStatus status) {
                int satellitecount = status.getSatelliteCount();
                int used_satellitecount = 0;
                int cno25_count = 0;
                float cno25_sum = 0;
                float totalSnr = 0;

                double alpa = 0.45, beta = 0.3, gamma = 0.25;
                int theta_out = 30, theta_in = 25;
                int cn0_25_out = 15, cn0_25_in = 5;

                listsatellite.clear();

                for (int i = 0; i < satellitecount; i++) {
                    if (status.usedInFix(i)) {
                        listsatellite.add(used_satellitecount, status.getCn0DbHz(i));
                        used_satellitecount++;
                        totalSnr += status.getCn0DbHz(i);
                        if (status.getCn0DbHz(i) >= 25) {
                            cno25_count++;
                        }

                    }
                }

                //CNR 데이터 전체 보여주기
           /*     for (int i = 0; i < used_satellitecount; i++) {
                    if (i == used_satellitecount - 1)
                        txtSatCnr.append(String.valueOf(listsatellite.get(i)));
                    else
                        txtSatCnr.append(String.valueOf(listsatellite.get(i)) + ",");
                }
            */
                float avgcnr = (used_satellitecount > 0) ? totalSnr / used_satellitecount : 0.0f;

                cur_time = Time;
                d_time = cur_time - pre_time;

                /*
                가시 위성들의 평균 CNR
                 */
                if (avgcnr > theta_out) {
                    G_in = 0;
                    G_out = 1;
                } else if (avgcnr < theta_in) {
                    G_in = 0.85;
                    G_out = 0.15;
                } else {
                    G_out = (avgcnr - theta_in) / (theta_out - theta_in);
                    G_in = 1 - G_out;
                }

                /*
                 Location 현재 시간 - 이전 시간
                 */
                if (d_time == 1000) {
                    T_in = 0.05;
                    T_out = 0.95;
                } else if (d_time == 0) {
                    T_in = 1;
                    T_out = 0;
                } else {
                    T_in = 0.95;
                    T_out = 0.05;
                }

                pre_time = cur_time;

                /*l
                cnr >= 25 인 위성 개수
                 */
                if (cno25_count > cn0_25_out) {
                    S_in = 0;
                    S_out = 1;
                } else if (cno25_count < cn0_25_in) {
                    S_in = 1;
                    S_out = 0;
                } else {
                    S_out = (cno25_count - cn0_25_in) / (cn0_25_out - cn0_25_in);
                    S_in = 1 - S_out;
                }

                D_in = alpa * G_in + beta * T_in + gamma * S_in;
                D_out = alpa * G_out + beta * T_out + gamma * S_out;

                if (modeAuto) {
                    Log.d("isBetterLocation()",": update...");
                    if (D_in > D_out) {
                        mode = 2; //INS Mode
                        headingFilter_flag = false; //추가
                        accposFilter_flag = false;
                    } else {   //실외일때
                        mode = 1; //GPS Mode
                    }
                }
            }
        };

       /* btnSearch.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (SearchMarker != null) SearchMarker.remove();

                String str_search = txtSearch.getText().toString();
                if (!str_search.equals("")) {
                    List<Address> addressList = null;
                    try {
                        // editText에 입력한 텍스트(주소, 지역, 장소 등)을 지오 코딩을 이용해 변환
                        addressList = geocoder.getFromLocationName(str_search, 10);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    String[] splitStr = addressList.get(0).toString().split(",");
                    String address = splitStr[0].substring(splitStr[0].indexOf("\"") + 1, splitStr[0].length() - 2); // 주소
                    String latitude = splitStr[10].substring(splitStr[10].indexOf("=") + 1); // 위도
                    String longitude = splitStr[12].substring(splitStr[12].indexOf("=") + 1); // 경도

                    LatLng SearchPoint = new LatLng(Double.parseDouble(latitude), Double.parseDouble(longitude));
                    MarkerOptions SearchMarkerOptions = new MarkerOptions();
                    SearchMarkerOptions.title("search result");
                    SearchMarkerOptions.snippet(address);
                    SearchMarkerOptions.position(SearchPoint);

                    SearchMarker = mMap.addMarker(SearchMarkerOptions);
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(SearchPoint, ZoomLevel));
                } else
                    Toast.makeText(getApplicationContext(), "검색 위치를 입력해주세요", Toast.LENGTH_LONG).show();

            }
        });*/

        mPositionButton.setOnClickListener(new View.OnClickListener() { //현재 위치로 돌아오는 버튼
            @Override
            public void onClick(View v) {
                if (currentPosition != null) {
                    mPositionButton.setBackgroundResource(R.drawable.position_click);
                    mSwitch = true;
                    moveCurrentPosition(currentPosition);
                } else
                    Toast.makeText(MapsActivity.this, "위치를 찾을 수 없습니다." + "\n" + " 모드를 변경하십시오", Toast.LENGTH_LONG).show();
            }
        });

        btnZin.setOnClickListener(new View.OnClickListener() { //줌 인 버튼
            @Override
            public void onClick(View v) {
                ZoomLevel += 1;
                mMap.animateCamera(CameraUpdateFactory.zoomTo(ZoomLevel));
            }
        });

        btnZOut.setOnClickListener(new View.OnClickListener() {//줌 아웃 버튼
            @Override
            public void onClick(View v) {
                ZoomLevel -= 1;
                mMap.animateCamera(CameraUpdateFactory.zoomTo(ZoomLevel));

            }
        });


        //권한 체크
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            Log.d(TAG, "Permission is granted");
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        }


        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        lastKnownLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER); //GPS 위치제공자
        if (lastKnownLocation != null) {
            double lng = lastKnownLocation.getLongitude();
            double lat = lastKnownLocation.getLatitude();
            double spd = lastKnownLocation.getSpeed();
            double heading = lastKnownLocation.getBearing();
            MIN_accuracy = lastKnownLocation.getAccuracy();

            Ref_GPS_Location = lastKnownLocation;
            Log.d(TAG, "lastKnownLocation : " + ", latitude=" + lat + "longtitude=" + lng);
           /* txtGPSLocation.setText("GPS Location : " + Double.toString(lat) + "," + Double.toString(lng));
            txtGPSINSLocation.setText("GPS/INS Location : " + Double.toString(lat) + "," + Double.toString(lng));*/
            LastPosition = new LatLng(lat, lng);

            pre_x_h[0] = heading * DtoR;
            preLoc = lastKnownLocation;
            curLoc = lastKnownLocation;
        } else {
            MIN_accuracy = 10;
        }

        BackRunnable runnable = new BackRunnable();
        Thread thread = new Thread(runnable);
        thread.setDaemon(true);
        thread.start();
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */

    //데이터 저장
    public void WriteTextFile(String foldername, String filename, String contents) {
        File dir = new File(foldername);
        //디렉토리 폴더가 없으면 생성함
        if (!dir.exists()) {
            dir.mkdir();
        } else {
            Log.d("WriteTextFile", "dir.exists()");
        }
        try {
            File file = new File(foldername + "/" + filename);
            FileOutputStream fos = new FileOutputStream(file, true);
            //파일쓰기
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(fos));
            writer.write(contents);
            writer.flush();
            writer.close();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void ReadTextFile(String foldername, String filename) {
        String line = null;
        File dir = new File(foldername);
        if (!dir.exists()) {
            dir.mkdir();
        } else {
            Log.d("WriteTextFile", "dir.exists()");
        }
        try {
            BufferedReader buf = new BufferedReader(new FileReader(foldername + "/" + filename));
            while ((line = buf.readLine()) != null) {
                txtSlope.append(line);
                txtSlope.append("\n");
            }
            buf.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.tool_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                Toast.makeText(getApplicationContext(), "셋팅아이콘 클릭", Toast.LENGTH_LONG).show();
                break;
            case R.id.mode_1:
                Toast.makeText(getApplicationContext(), "Mode Auto", Toast.LENGTH_LONG).show();
                txtMode.setText("MODE_AUTO");
                txtMode.setTextColor(Color.BLACK);

                headingFilter_flag = true;
                accposFilter_flag = true;
                MIN_accuracy = 10;
                modeAuto = true;
                mode = 1;
                break;
            case R.id.mode_2:
                Toast.makeText(getApplicationContext(), "Mode GPS", Toast.LENGTH_LONG).show();
                txtMode.setText("MODE_GPS");
                txtMode.setTextColor(Color.BLUE);
                modeAuto = false;
                mode = 1;
                break;
            case R.id.mode_3:
                Toast.makeText(getApplicationContext(), "Mode INS", Toast.LENGTH_LONG).show();
                txtMode.setText("MODE_INS");
                txtMode.setTextColor(Color.GREEN);

                headingFilter_flag = false;
                accposFilter_flag = false;

                modeAuto = false;
                mode = 2;
                break;
        }
        return false;

    }

    @Override //뒤로가기 버튼으로 종료하기
    public void onBackPressed() {
        AlertDialog.Builder alBuilder = new AlertDialog.Builder(this);
        alBuilder.setMessage("종료하시겠습니까?");

        alBuilder.setPositiveButton("예", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish(); // 현재 액티비티를 종료한다. (MainActivity에서 작동하기 때문에 애플리케이션을 종료한다.)
            }
        });
        // "아니오" 버튼을 누르면 실행되는 리스너
        alBuilder.setNegativeButton("아니오", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                return; // 아무런 작업도 하지 않고 돌아간다
            }
        });
        alBuilder.setTitle("프로그램 종료");
        alBuilder.show(); // AlertDialog.Bulider로 만든 AlertDialog를 보여준다.
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        geocoder = new Geocoder(this);
        Log.d(TAG, "onMapReady()");
        // Add a marker in Sydney and move the camera

        setDefaultLocation();

        mMap.setOnCameraMoveListener(new GoogleMap.OnCameraMoveListener() {
            @Override
            public void onCameraMove() {
                mPositionButton.setBackgroundResource(R.drawable.position);
                mSwitch = false;
            }
        });

        mMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng latLng) {
                Toast.makeText(MapsActivity.this, "Point 저장", Toast.LENGTH_LONG).show();
                //if (save_mode) WriteTextFile(foldername, filename, "Point" + "\n");
            }
        });
    }

    public void setDefaultLocation() { //초기 위치로 이동 , 만약 저장된 위치 데이터가 없다면 디폴트위치로 설정
        Log.d(TAG, "setDefaultLocation()");
        LatLng DEFAULT_LOCATION;
        if (LastPosition != null) {
            DEFAULT_LOCATION = LastPosition;
        } else {
            DEFAULT_LOCATION = new LatLng(37.56, 126.97);
        }//디폴트 위치, Seoul

        if (currentMarker != null) currentMarker.remove();

        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(DEFAULT_LOCATION);
        markerOptions.draggable(true);
        markerOptions.visible(true);

        //currentMarker = mMap.addMarker(markerOptions);

        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(DEFAULT_LOCATION, 16);
        mMap.moveCamera(cameraUpdate);

    }

    @Override
    public void onLocationChanged(Location location) {
        double accZ_tr = 0;
        double gyroY_tr = 0;
        double press_tr = 0;

        //double[] cur_llh = {location.getLatitude()*DtoR, location.getLongitude()*DtoR, location.getAltitude()};

        accZ_tr = mLastAccelerometer[2];
        gyroY_tr = mLastGyroscope[1];
        //press_tr = mLastPressure;
        press_tr = averagePressure;

        int update_mode = mode;
        if (location == null) return;

        if (location.getProvider().equals(LocationManager.GPS_PROVIDER)) {
            //txtLocationAcc.setText("Location Acc : " + location.getAccuracy());
            //Log.d(TAG, "onLocationChanged_GPS");

            GPS_lat = location.getLatitude();
            GPS_lon = location.getLongitude();
            GPS_alt = location.getAccuracy();
            GPS_speed = location.getSpeed();
            GPS_heading = location.getBearing();
            Time = location.getTime();
            //isBetterLocation(location);
            //heading_meas = GPS_heading * DtoR;

           /* double diff_heading = heading_meas - heading_stack;

            while (Math.abs(diff_heading) > PI) {
                if (diff_heading > 0) {
                    heading_meas = heading_meas - (2 * PI);
                } else {
                    heading_meas = heading_meas + (2 * PI);
                }
                diff_heading = heading_meas - heading_stack;
            }

            double dH = heading_meas - pre_averageHeading;
            double alpa = 0.25;
            if ( mode == 1)
            {
               final_dH = dH * alpa + cur_gyro_y * (1-alpa);
                final_H = pre_averageHeading + final_dH;
            }

            if (save_mode) {
                // File Write
                String seperator = ",";
                String dataStr = heading_meas* RtoD + seperator + pre_averageHeading * RtoD + seperator + final_H * RtoD + seperator + cur_gyro_y * RtoD + seperator + mode;
                wrtieToFile(dataStr);
            }

            heading_stack = heading_meas;
            pre_averageHeading = heading_meas;
*/

            GPS_setCurrentLocation(GPS_lat, GPS_lon, GPS_heading);//초록색마커로 gps 위치 찍음
            /*if (save_mode) {
                String seperator = ",";
                String str = "GPS" + seperator + System.currentTimeMillis() + seperator + GPS_lat + seperator + GPS_lon + seperator + GPS_heading + seperator + GPS_speed + seperator + mode;
                wrtieToFile(str);
            }*/

            if (modeAuto) {
                headingFilter_flag = true;
                accposFilter_flag = true;

                double[] cur_llh = {0, 0, 0};
                cur_GPS_Location = location;
                curLoc = cur_GPS_Location;


                if (accposFilter_flag) {
                    //Log.i("Flag","Filter flag true");
                    if (acc_var[WINDOW_VARIANCE - 1] < THRESHOLD_VARIANCE) {
                        cur_x_h[0] = pre_x_h[0];
                        cur_x_h[1] = pre_x_h[1];
                        cur_x_h[2] = pre_x_h[2];

                        cur_x[0] = pre_x[0];
                        cur_x[1] = pre_x[1];
                        //cur_x[2] = pre_x[2];
                        cur_x[2] = 0;
                        cur_x[3] = pre_x[3];
                        cur_x[4] = pre_x[4];

                    } //else {
                    if (preLoc != null) {
                        //double heading_meas = CalBearing(curLoc, preLoc);
                        double heading_meas = curLoc.getBearing() * DtoR;
                        double diff_heading = heading_meas - heading_stack;  //의문점   heading_meas - heading_stack

                        while (Math.abs(diff_heading) > PI) {
                            if (diff_heading > 0) {
                                heading_meas = heading_meas - (2 * PI);
                            } else {
                                heading_meas = heading_meas + (2 * PI);
                            }
                            diff_heading = heading_meas - heading_stack;
                        }
                        heading_stack = heading_meas;

                        //double heading_meas = CalBearing(curLoc, preLoc);
                        average_Heading_Process(heading_stack);

                        //cur_x_h = hFilter.cal_Heading(pre_x_h, gyroY_tr, dt_sensor, update_mode, averageHeading, headingFilter_flag);
                        cur_x_h = hFilter.cal_Heading(pre_x_h, gyroY_tr, dt_sensor, update_mode, heading_stack, headingFilter_flag);
                        heading_stack = cur_x_h[0];

                        cur_x = apFilter.cal_AccPos(pre_x, accZ_tr, dt_sensor, update_mode, cur_x_h[0], sin_theta, preLoc, curLoc, accposFilter_flag);

                        double[] cur_enu = {cur_x[0], cur_x[1], curLoc.getAltitude()};
                        double[] pre_xyz = coordinate_trans.llh2xyz(preLoc.getLatitude(), preLoc.getLongitude(), preLoc.getAltitude());
                        double[] cur_xyz = coordinate_trans.enu2xyz(cur_enu, pre_xyz);
                        cur_llh = coordinate_trans.xyz2llh(cur_xyz);

                        curLoc.setLatitude(cur_llh[0] * RtoD);
                        curLoc.setLongitude(cur_llh[1] * RtoD);
                        curLoc.setSpeed((float) cur_x[2]);
                        //curLoc.setBearing((float) (averageHeading*RtoD));

                        average_sf_bias_Process((1 / cur_x_h[1]), (cur_x_h[2] / cur_x_h[1]), (1 / cur_x[3]), (cur_x[4] / cur_x[3]));
                        cur_x_h[1] = 1 / averageSf_h;
                        cur_x_h[2] = averageBias_h / averageSf_h;

                        cur_x[3] = 1 / averageSf;
                        cur_x[4] = averageBias / averageSf;

                        //Log.i("ENU",cur_x[0] + " , " + cur_x[1] + " , " + cur_x[2] + " , " + cur_x[3] + " , " + cur_x[4]);
                    }
                    //}

                    map_heading = averageHeading;
                    if (averageHeading > (2 * PI)) {
                        map_heading = averageHeading % (2 * PI);
                    } else if (averageHeading < (-2 * PI)) {
                        map_heading = averageHeading % (-2 * PI);
                    }

            /*        if (save_mode) {
                        String seperator = ",";
                        String str = "GPSINS" + seperator + System.currentTimeMillis() + seperator + curLoc.getLatitude() + seperator + curLoc.getLongitude() + seperator
                                + map_heading * RtoD + seperator + curLoc.getSpeed() + seperator + mode;
                        wrtieToFile(str);
                    }*/

                    /*map_heading = averageHeading;
                    if (averageHeading > (2 * PI)) {
                        map_heading = averageHeading % (2 * PI);
                    } else if (averageHeading < (-2 * PI)) {
                        map_heading = averageHeading % (-2 * PI);
                    }*/


                    System.arraycopy(cur_x_h, 0, pre_x_h, 0, 3);
                    System.arraycopy(cur_x, 0, pre_x, 0, 5);

                    pre_x[0] = 0;
                    pre_x[1] = 0;
                    //cur_x[0] = 0;
                    //cur_x[1] = 0;

                    headingFilter_flag = false;
                    accposFilter_flag = false;

                }
                preLoc = curLoc;

//                isBetterLocation(cur_GPS_Location); //모드 체인지

                if (mode == 1) {
                    //txtGPSINSLocation.setText("GPS/INS Location : " + Double.toString(lat) + "," + Double.toString(lng));
                    //txtGPSINSLocation.setText("GPS/INS Location : " + cur_llh[0] * RtoD + "," + cur_llh[1] * RtoD);
                    //onMapPosition(cur_GPS_Location, mode);
                    //onMapPosition(GPS_lat, GPS_lon, GPS_heading, mode);
                    onMapPosition(cur_llh[0] * RtoD, cur_llh[1] * RtoD, (map_heading * RtoD), mode);
                    Ref_GPS_Location = cur_GPS_Location;  //마지막 위치 저장
                    Last_Map_angle = Map_angle; //마지막 각도 저장
                }
            } else {
                if (mode == 1) {
                    onMapPosition(GPS_lat, GPS_lon, GPS_heading, mode);
                    //onMapPosition(cur_GPS_Location, mode);
                    Ref_GPS_Location = cur_GPS_Location;  //마지막 위치,각도 저장
                    Last_Map_angle = Map_angle;
                    //txtGPSLocation.setText("GPS Location : " + curLoc.getLatitude() + "," + curLoc.getLongitude());
                }
            }

            //Log.i("Filter heading", pre_x_h[0] + " , " + pre_x_h[1] + " , " + pre_x_h[2]);
            //Log.i("Filter heading", cur_x_h[0] + " , " + cur_x_h[1] + " , " + cur_x_h[2]);

            //Log.i("Filter acc", pre_x[0] + " , " + pre_x[1] + " , " + pre_x[2] + " , " + (1 / pre_x[3]) + " , " + (pre_x[4] / pre_x[3]));
            //Log.i("Filter acc", cur_x[0] + " , " + cur_x[1] + " , " + cur_x[2] + " , " + (1 / cur_x[3]) + " , " + (cur_x[4] / cur_x[3]));

        }
    }

    public void onMapPosition(double lat, double lon, double heading, int mode) {
        Log.d(TAG, "onMapPosition");
        currentPosition = new LatLng(lat, lon);
        Map_angle = (float) heading;  //마커 회전을 위한 각도 (도 단위) 북을 기준 반시계가 양수
        Log.d(TAG, "onMapPosition : " + "cur : " + currentPosition);

        String markerTitle = "MODE_" + mode;
        String markerSnippet = "위도:" + lat + " 경도:" + lon;
        setCurrentLocation(currentPosition, markerTitle, markerSnippet, mode, Map_angle);
    }

    /*public void onMapPosition(Location location,int mode)
    {
        Log.d(TAG, "onMapPosition");
        currentPosition = new LatLng(location.getLatitude(),location.getLongitude());
        Map_angle = location.getBearing();  //마커 회전을 위한 각도 (도 단위) 북을 기준 반시계가 양수
        Log.d(TAG, "onMapPosition : " + "cur : " + String.valueOf(currentPosition) + "pre : " + String.valueOf(prePosition));

        String markerTitle = "MODE_" + String.valueOf(mode);
        String markerSnippet = "위도:" + String.valueOf(location.getLatitude()) + " 경도:" + String.valueOf(location.getLongitude());
        setCurrentLocation(currentPosition,markerTitle,markerSnippet,mode,Map_angle);
    }*/

    public double CalBearing(Location curPos, Location prePos) {
        double angle = 0;
        if (curPos != null && prePos != null) {
            //double f1 = prePos.getLatitude();
            //double f2 = curPos.getLatitude();
            //double dl = (prePos.getLongitude() - curPos.getLongitude()) * DtoR;
            //angle  = (float)Math.atan2(Math.sin(dl) * Math.cos(f2), Math.cos(f1) * Math.sin(f2) - Math.sin(f1) * Math.cos(f2) * Math.cos(dl));
            //angle = (float)(-angle * 180 / Math.PI); //마커 회전 (도 단위) 북을 기준 시계방향이 양수, 위경도로 구한 각도 시계방향이 음수
            double d_lat = curPos.getLatitude() - prePos.getLatitude();
            double d_lon = curPos.getLongitude() - prePos.getLongitude();
            angle = Math.atan2(d_lat, d_lon);
        }
        return angle;
    }

    public void GPS_setCurrentLocation(double lat, double lon, double heading) {

        LatLng GPS_Position = new LatLng(lat, lon);
        if (gpsMarker != null) gpsMarker.remove();
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(GPS_Position);

        BitmapDrawable bitmapdraw_gps = (BitmapDrawable) getResources().getDrawable(R.drawable.m_gps); //초록색 마커
        Bitmap b_gps = bitmapdraw_gps.getBitmap();
        Bitmap smallMarker_1 = Bitmap.createScaledBitmap(b_gps, 60, 60, false);
        markerOptions.icon(BitmapDescriptorFactory.fromBitmap(smallMarker_1));

        markerOptions.anchor(0.5f, 0.5f);
        markerOptions.rotation((float) heading);
        markerOptions.draggable(true);
        gpsMarker = mMap.addMarker(markerOptions);
        //CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(currentPosition, ZoomLevel);
        //if (mSwitch) mMap.moveCamera(cameraUpdate); //위치가 바뀔때 마다 카메라 위치가 따라감
    }


    public void setCurrentLocation(LatLng currentPosition, String markerTitle, String markerSnippet, int mode, float angle) {
        Log.d(TAG, "setCurrentLocation");
        if (currentMarker != null) currentMarker.remove();
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.title(markerTitle);
        markerOptions.snippet(markerSnippet);
        markerOptions.position(currentPosition);
        switch (mode) {
            case 1:
                BitmapDrawable bitmapdraw = (BitmapDrawable) getResources().getDrawable(R.drawable.m_gpsins); //검은색 마커
                Bitmap b = bitmapdraw.getBitmap();
                Bitmap smallMarker = Bitmap.createScaledBitmap(b, 40, 40, false);
                markerOptions.icon(BitmapDescriptorFactory.fromBitmap(smallMarker));
                break;
            case 2:
                BitmapDrawable bitmapdraw_1 = (BitmapDrawable) getResources().getDrawable(R.drawable.m_ins); //파란색 마커
                Bitmap b_1 = bitmapdraw_1.getBitmap();
                Bitmap smallMarker_1 = Bitmap.createScaledBitmap(b_1, 40, 40, false);
                markerOptions.icon(BitmapDescriptorFactory.fromBitmap(smallMarker_1));
                break;
        }
        markerOptions.anchor(0.5f, 0.5f);
        markerOptions.rotation(angle);
        markerOptions.draggable(true);
        currentMarker = mMap.addMarker(markerOptions);
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(currentPosition, ZoomLevel);
        //if (mSwitch)
        mMap.moveCamera(cameraUpdate); //위치가 바뀔때 마다 카메라 위치가 따라감
    }

    public void moveCurrentPosition(LatLng currentPosition) {
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(currentPosition, ZoomLevel);
        mMap.moveCamera(cameraUpdate);
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        Log.d(TAG, "onStatusChanged");
    }

    @Override
    public void onProviderEnabled(String provider) {
        Log.d(TAG, "onProviderEnabled");
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, MIN_UPDATE_MILLIS, MIN_UPDATE_MITERS, this);
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, MIN_UPDATE_MILLIS, MIN_UPDATE_MITERS, this);
        locationManager.requestLocationUpdates(LocationManager.PASSIVE_PROVIDER, MIN_UPDATE_MILLIS, MIN_UPDATE_MITERS, this);
    }

    @Override
    public void onProviderDisabled(String provider) {
        Log.d(TAG, "onProviderDisabled");
    }

    @Override
    protected void onStart() {
        Log.d(TAG, "onStart()");
        super.onStart();

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            //권한이 없을 경우 최초 권한 요청 또는 사용자에 의한 재요청 확인
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION) &&
                    ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_COARSE_LOCATION)) {
                // 권한 재요청
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 100);
                locationManager.registerGnssStatusCallback(mGnssStatuscallback);
                return;
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 100);
                locationManager.registerGnssStatusCallback(mGnssStatuscallback);
                return;
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        locationManager.removeUpdates(this);
        mSensorManager.unregisterListener(this);
        locationManager.unregisterGnssStatusCallback(mGnssStatuscallback);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, MIN_UPDATE_MILLIS, MIN_UPDATE_MITERS, this);
//        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, MIN_UPDATE_MILLIS, MIN_UPDATE_MITERS, this);
//        locationManager.requestLocationUpdates(LocationManager.PASSIVE_PROVIDER, MIN_UPDATE_MILLIS, MIN_UPDATE_MITERS, this);

        mSensorManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_FASTEST);
        mSensorManager.registerListener(this, mGyroscope, SensorManager.SENSOR_DELAY_FASTEST);
        mSensorManager.registerListener(this, mPressure, SensorManager.SENSOR_DELAY_FASTEST);
        locationManager.registerGnssStatusCallback(mGnssStatuscallback);

    }

    //    public boolean isBetterLocation(Location prelocation,Location currentLocation)
    public void isBetterLocation(Location currentLocation) {
        if (currentLocation != null) {
            accuracyData = currentLocation.getAccuracy(); //현재 위치의 정확도 데이터

            if (accuracyData < MIN_accuracy) MIN_accuracy = accuracyData;
            double threshold = MIN_accuracy * 0.8;
            Accuracy_Threshold = MIN_accuracy + threshold; //정확도 min 값을 이용하여 Threshold 지정
            if (Accuracy_Threshold < accuracyData) {  //실내일때
                //Init_SensorData();
                mode = 2; //INS Mode
                headingFilter_flag = false; //추가
                accposFilter_flag = false;
            } else {   //실외일때
                mode = 1; //GPS Mode
            }
            cur_location_time = currentLocation.getTime();
            if (cur_location_time - pre_location_time >= 2000 || cur_location_time - pre_location_time <= 0) {
                mode = 2;
                headingFilter_flag = false;  //추가
                accposFilter_flag = false;
            }

            //if (accuracyData <= 0) mode = 2; //INS Mode
            //Log.d(TAG, "isBetterLocation() mode : " + mode + "accuracy : " + accuracyData + "time : " + currentLocation.getTime());
            pre_location_time = cur_location_time;
        }
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor == mAccelerometer) {
            System.arraycopy(event.values, 0, mLastAccelerometer, 0, event.values.length);
            variance_Acceleration_Process(mLastAccelerometer);
            //Log.i("VARIANCE",String.valueOf(acc_var[19]));
        } else if (event.sensor == mGyroscope) {
            System.arraycopy(event.values, 0, mLastGyroscope, 0, event.values.length);
        } else if (event.sensor == mPressure) {
            mLastPressure = event.values[0];
            average_Pressure_Process(mLastPressure);
            //(double sin_theta_old, double[] stack_acc, double[] stack_alt, double[] stack_spd, double[] stack_tic, double accZ, double altitude, double spd, int C_WINDOW, int M_WINDOW, double tic)
            //slopeCalculation.cal_Slope();
            //txtPress.setText("Pressure : " + String.valueOf(event.values[0]));
        }
    }

    private void average_Pressure_Process(double sensorPressure) {
        if (dataIndex_pressure == 1) {
            averagePressure = sensorPressure;
        } else if (dataIndex_pressure < WINDOW_PRESSURE) {
            averagePressure = (averagePressure * (dataIndex_pressure - 1) / dataIndex_pressure) + (sensorPressure / dataIndex_pressure);
        } else {
            averagePressure = (averagePressure * (WINDOW_PRESSURE - 1) / WINDOW_PRESSURE) + (sensorPressure / WINDOW_PRESSURE);
        }
        dataIndex_pressure++;
    }

    private void average_Heading_Process(double heading) {
        if (dataIndex_heading == 1) {
            averageHeading = heading;
        } else if (dataIndex_heading < WINDOW_HEADING) {
            averageHeading = (averageHeading * (dataIndex_heading - 1) / dataIndex_heading) + (heading / dataIndex_heading);
        } else {
            averageHeading = (averageHeading * (WINDOW_HEADING - 1) / WINDOW_HEADING) + (heading / WINDOW_HEADING);
        }
        dataIndex_heading++;
    }

    private void average_sf_bias_Process(double Sf_h, double Bias_h, double Sf, double Bias) {
        if (dataIndex_sf_bias == 1) {
            averageSf_h = pre_sf_h;
            averageBias_h = pre_bias_h;
            averageSf = pre_sf;
            averageBias = pre_bias;
        } else if (dataIndex_sf_bias < WINDOW_SF_BIAS) {
            averageSf_h = (averageSf_h * (dataIndex_sf_bias - 1) / dataIndex_sf_bias) + (Sf_h / dataIndex_sf_bias);
            averageBias_h = (averageBias_h * (dataIndex_sf_bias - 1) / dataIndex_sf_bias) + (Bias_h / dataIndex_sf_bias);
            averageSf = (averageSf * (dataIndex_sf_bias - 1) / dataIndex_sf_bias) + (Sf / dataIndex_sf_bias);
            averageBias = (averageBias * (dataIndex_sf_bias - 1) / dataIndex_sf_bias) + (Bias / dataIndex_sf_bias);
        } else {
            averageSf_h = (averageSf_h * (WINDOW_SF_BIAS - 1) / WINDOW_SF_BIAS) + (Sf_h / WINDOW_SF_BIAS);
            averageBias_h = (averageBias_h * (WINDOW_SF_BIAS - 1) / WINDOW_SF_BIAS) + (Bias_h / WINDOW_SF_BIAS);
            averageSf = (averageSf * (WINDOW_SF_BIAS - 1) / WINDOW_SF_BIAS) + (Sf / WINDOW_SF_BIAS);
            averageBias = (averageBias * (WINDOW_SF_BIAS - 1) / WINDOW_SF_BIAS) + (Bias / WINDOW_SF_BIAS);
        }
        dataIndex_sf_bias++;
    }

    private void variance_Acceleration_Process(float[] sensorAcc) { //센서값이 바뀔 때 마다
        double acc_xyz = sensorAcc[0] * sensorAcc[0] + sensorAcc[1] * sensorAcc[1] + sensorAcc[2] * sensorAcc[2];
        double acc_xyz_norm = Math.sqrt(acc_xyz);
        if (dataIndex_variance == 1) {
            acc_norm[0] = acc_xyz_norm;
            acc_var[0] = 0;
        } else if (dataIndex_variance <= WINDOW_VARIANCE) {
            acc_norm[dataIndex_variance - 1] = acc_xyz_norm;
            double acc_mat[] = new double[dataIndex_variance];
            System.arraycopy(acc_norm, 0, acc_mat, 0, dataIndex_variance);
            acc_var[dataIndex_variance - 1] = Matrix.variance(acc_mat, dataIndex_variance);
        } else {
            System.arraycopy(acc_norm, 1, acc_norm, 0, WINDOW_VARIANCE - 1);
            acc_norm[WINDOW_VARIANCE - 1] = acc_xyz_norm;
            acc_var[WINDOW_VARIANCE - 1] = Matrix.variance(acc_norm, WINDOW_VARIANCE);
        }
        //Log.i("VARIANCE","VAR : " + acc_var[WINDOW_VARIANCE - 1]);
        dataIndex_variance++;
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }


class BackRunnable implements Runnable {

    double accX, accY, accZ  = 0;
    double gyroX, gyroY, gyroZ = 0;
    double lat, lon, alt, spd, hding = 0;
    double save_var, save_sin_theta, save_sf, save_bias = 0;

    double accZ_tr = 0;
    double gyroY_tr = 0;
    double press_tr = 0;

    @Override
    public void run() {
        while (true) {
            accX = mLastAccelerometer[0];
            accY = mLastAccelerometer[1];
            accZ = mLastAccelerometer[2];

            gyroX = mLastGyroscope[0];
            gyroY = mLastGyroscope[1];
            gyroZ = mLastGyroscope[2];

            press_tr = mLastPressure;

            save_var = acc_var[WINDOW_VARIANCE - 1];
            save_sin_theta = sin_theta;
            save_sf = averageSf;
            save_bias = averageBias;

            accZ_tr = mLastAccelerometer[2];
            gyroY_tr = mLastGyroscope[1];
            //press_tr = mLastPressure;
            //press_tr = averagePressure;
            int update_mode = mode;

            // SLOPE CALCULATION
            double temp_alt = 1 - Math.pow((press_tr / INITIAL_PRESSURE), ALPHA_PRESSURE);
            altitude = INITIAL_HEIGHT + (BETA_PRESSURE * temp_alt);

            double slope_spd = pre_x[2];
            if (acc_var[WINDOW_VARIANCE - 1] < THRESHOLD_VARIANCE) {
                slope_spd = 0;
            }

            // ZUPT
            if (dataIndex_slope <= WINDOW_SLOPE) {
                stack_alt[dataIndex_slope - 1] = altitude;
                stack_acc[dataIndex_slope - 1] = accZ_tr;
                stack_spd[dataIndex_slope - 1] = slope_spd;
                stack_tic[dataIndex_slope - 1] = dt_sensor;
            } else {
                System.arraycopy(stack_alt, 1, stack_alt, 0, WINDOW_SLOPE - 1);
                stack_alt[WINDOW_SLOPE - 1] = altitude;

                System.arraycopy(stack_acc, 1, stack_acc, 0, WINDOW_SLOPE - 1);
                stack_acc[WINDOW_SLOPE - 1] = accZ_tr;

                System.arraycopy(stack_spd, 1, stack_spd, 0, WINDOW_SLOPE - 1);
                stack_spd[WINDOW_SLOPE - 1] = slope_spd;

                System.arraycopy(stack_tic, 1, stack_tic, 0, WINDOW_SLOPE - 1);
                stack_tic[WINDOW_SLOPE - 1] = dt_sensor;
            }
            dataIndex_slope++;
            //Log.i("ALT",String.valueOf(C_WINDOW));
            //Log.i("ALT",String.valueOf(altitude));
            //Log.i("ALT",String.valueOf(sin_theta));

            //(double sin_theta_old, double[] stack_acc, double[] stack_alt, double[] stack_spd, double[] stack_tic, double accZ, double altitude, double spd, int C_WINDOW, int M_WINDOW, double tic)
            slopeOut = slopeCalculation.cal_Slope(sin_theta_old, stack_acc, stack_alt, stack_spd, stack_tic, accZ_tr, altitude, slope_spd, C_WINDOW, WINDOW_SLOPE, dt_sensor);
            sin_theta = slopeOut[0];
            C_WINDOW = (int) slopeOut[1];

            if (Math.abs(sin_theta) > 0.4) {
                sin_theta = sin_theta_old;
            }
            sin_theta_old = sin_theta;
            //txtPress.setText("Pressure : " + String.valueOf(event.values[0]));


            if (preLoc != null) {
                //Log.e("Flag","Filter flag false");
                //double heading_meas = preLoc.getBearing();

                if (!accposFilter_flag) {
                    //cur_x_h = hFilter.cal_Heading(pre_x_h, gyroY_tr, dt_sensor, update_mode, averageHeading, headingFilter_flag);
                    cur_x_h = hFilter.cal_Heading(pre_x_h, gyroY_tr, dt_sensor, update_mode, heading_stack, headingFilter_flag);
                    cur_x = apFilter.cal_AccPos(pre_x, accZ_tr, dt_sensor, update_mode, cur_x_h[0], sin_theta, preLoc, curLoc, accposFilter_flag);
                }

                if ( acc_var[WINDOW_VARIANCE-1] < THRESHOLD_VARIANCE ) {
                    cur_x_h[0] = pre_x_h[0];
                    cur_x_h[1] = pre_x_h[1];
                    cur_x_h[2] = pre_x_h[2];

                    cur_x[0] = pre_x[0];
                    cur_x[1] = pre_x[1];
                    //cur_x[2] = pre_x[2];
                    cur_x[2] = 0;
                    cur_x[3] = pre_x[3];
                    cur_x[4] = pre_x[4];
                }

                double[] cur_enu = {cur_x[0], cur_x[1], curLoc.getAltitude()};
                double[] pre_xyz = coordinate_trans.llh2xyz(preLoc.getLatitude(), preLoc.getLongitude(), preLoc.getAltitude());
                double[] cur_xyz = coordinate_trans.enu2xyz(cur_enu, pre_xyz);

                System.arraycopy(cur_x_h, 0, pre_x_h, 0, 3);
                System.arraycopy(cur_x, 0, pre_x, 0, 5);

                ins_cur_heading = cur_x_h[0];
                save_llh[0] = ins_cur_llh[0]; save_llh[1] = ins_cur_llh[1]; save_llh[2] = ins_cur_llh[2];
                save_spd = cur_x[2];
                save_hding = ins_cur_heading;


                map_heading = ins_cur_heading;
                if (ins_cur_heading > (2 * PI)) {
                    map_heading = ins_cur_heading % (2 * PI);
                } else if (ins_cur_heading < (-2 * PI)) {
                    map_heading = ins_cur_heading % (-2 * PI);
                }

                ins_cur_llh = coordinate_trans.xyz2llh(cur_xyz);
            }

            if (modeAuto || mode == 2) {
                handler.sendEmptyMessage(0);
            }

            // OR 문 사용해서 AUTO & INS 모드일때 맵에 찍어주기

            //Log.i("ANGLE", String.valueOf(cur_x_h[0]*RtoD));

            handler.post(new Runnable() {
                @Override
                public void run() {
                    // TextView
              /*          txtGPSINSLocation.setText("GPS/INS Location : " + ins_cur_llh[0] * RtoD + "," + ins_cur_llh[1] * RtoD);
                        txtHeading.setText("Heading : " + map_heading * RtoD);
                        txtSpeed.setText("Speed : " + cur_x[2]);
                        txtSf.setText("Scale-factor : " + averageSf_h + " , " + averageSf);
                        txtSlope.setText("Sin(theta) : " + sin_theta);*/
                }
            });

            try {
                Thread.sleep(THREAD_TIME);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if (save_mode) {
                // File Write
                String seperator = ",";
                Date date = new Date();
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
                dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
                String dateStr = dateFormat.format(date);

                String dataStr = "DATA" + seperator + dateStr + seperator + System.currentTimeMillis() + seperator + accX + seperator + accY + seperator + accZ + seperator
                        + gyroX + seperator + gyroY + seperator + gyroZ + seperator + press_tr + seperator + lat + seperator + lon + seperator + alt + seperator
                        + spd + seperator + hding + seperator + save_llh[0] + seperator + save_llh[1] + seperator + save_llh[2] + seperator + save_spd + seperator + save_hding + seperator
                        + save_var + seperator + save_sin_theta + seperator + save_sf + seperator + save_bias + seperator + mode;

                //String dataStr = cur_x[0] + seperator + cur_x[1] + seperator + cur_x_h[0] + seperator + heading_stack + seperator + averageHeading + seperator + map_heading + seperator +
                //        altitude + seperator + averageSf_h + seperator + averageBias_h + seperator + averageSf + seperator + averageBias + seperator + sin_theta;
                wrtieToFile(dataStr);

            }
        } //end while
    } // end run()
} // end class Runnable

    Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            if (msg.what == 0) {
                if (preLoc != null) {
                    onMapPosition(ins_cur_llh[0] * RtoD, ins_cur_llh[1] * RtoD, map_heading * RtoD, mode);
                }
            }
        }
    };


    public void wrtieToFile(String dataStr) {
        synchronized (mFileLock) {
            try {
                currentFileWriter.write(dataStr);
                currentFileWriter.write("\n");

            } catch (IOException e) {
                logException("Could not read the line in the file : " + currentFilePath, e);
                return;
            }
        }
    }


    private void logException(String errorMessage, Exception e) {
        Log.e("Error", errorMessage, e);
    }
}
