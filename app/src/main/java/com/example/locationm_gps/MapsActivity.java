package com.example.locationm_gps;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;

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
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
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
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.Date;


public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback,LocationListener, SensorEventListener {

    private GoogleMap mMap;
    private Marker currentMarker = null;
    private LocationManager locationManager;

    private static SensorManager mSensorManager;
    private Sensor mAccelerometer;
    private Sensor mGyrometer;
    private Sensor mPressure;

    private float[] mLastAccelerometer = new float[3];
    private float[] mLastGyrometer = new float[3];

    LatLng currentPosition;
    LatLng prePosition;
    LatLng LastPosition;

    private TextView txtGPSLocation,txtLocationAcc,txtMode,txtAcc,txtGyro,txtPress,txtOnGps; //
    private String TAG = "LocationProvider";

    private int MIN_UPDATE_MILLIS = 1000;
    private int MIN_UPDATE_MITERS = 0;

    private int ZoomLevel = 16;
    private Button btnZin, btnZOut; //줌 버튼 사용

    private Toolbar mToolbar; //툴바사용

    private boolean mode1 = true, mode2 = false, mode3 = false; //모드 변경 확인 변수
    private boolean mSwitch = true; //카메라 이동 확인
    private ImageButton mPositionButton; //위치 고정 버튼 사용

    private ImageButton btnSave; //데이터 저장 버튼
    private boolean save_mode = false;
    final static  String foldername  = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Map_Log";  //폴더이름
    final static  String filename =  new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date()) + "data2.txt"; //파일이름

    private boolean flag = true;

    private Location pre_GPS_Location;
    private Location cur_GPS_Location;
    private  boolean count = true;
    private boolean onGPS = false;

    private float sum_acc = 0;
    private float MIN_sum_acc = 5;
    private float res_acc = 0;
    private double timestamp;
    private double dt;
    private double yaw;
    private double RtoD = 180 / Math.PI;
    private static final float NS2S = 1.0f/1000000000.0f; //나노세컨드 -> 세컨드

    Handler handler;

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
        txtGPSLocation  = (TextView) findViewById(R.id.tv_GpsLocation);
        txtLocationAcc = (TextView) findViewById(R.id.tv_LocationAcc);
        txtMode = (TextView) findViewById(R.id.toolbar_mode); //현재 모드 보여주기
        txtOnGps = (TextView) findViewById(R.id.tv_Ongps);
        txtAcc = (TextView) findViewById(R.id.tv_Acc);
        txtGyro = (TextView) findViewById(R.id.tv_Gyro);
        txtPress = (TextView) findViewById(R.id.tv_Press);
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
        //----------Button--------//

        //----------Sensor--------//
        mSensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mGyrometer = mSensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        mPressure = mSensorManager.getDefaultSensor(Sensor.TYPE_PRESSURE);
        //----------Sensor--------//

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(save_mode == false) {
                    save_mode = true;
                    btnSave.setBackgroundResource(R.drawable.save_click);
                }
                else  {
                    save_mode = false;
                    btnSave.setBackgroundResource(R.drawable.save);
                }

            }
        });

        mPositionButton.setOnClickListener(new View.OnClickListener() { //현재 위치로 돌아오는 버튼
            @Override
            public void onClick(View v) {
                if(currentPosition != null) {
                    mPositionButton.setBackgroundResource(R.drawable.position_click);
                    mSwitch = true;
                    moveCurrentPosition(currentPosition);
                }
                else Toast.makeText(MapsActivity.this, "위치를 찾을 수 없습니다." + "\n" + " 모드를 변경하십시오", Toast.LENGTH_LONG).show();
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
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            return;
        }

        locationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);

        Location lastKnownLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER); //GPS 위치제공자
        if (lastKnownLocation != null) {
            double lng = lastKnownLocation.getLongitude();
            double lat = lastKnownLocation.getLatitude();
            cur_GPS_Location = lastKnownLocation;
            Log.d(TAG, "longtitude=" + lng + ", latitude=" + lat);
            txtGPSLocation.setText("GPS Location : " + Double.toString(lat) + "," + Double.toString(lng));
            LastPosition  = new LatLng(lat,lng);
        }

//        handler = new Handler(){
//            @Override
//            public void handleMessage(Message msg) {
//                handler.sendEmptyMessageDelayed(0,1000);
//                if(cur_GPS_Location != null) Log.d(TAG,"Timer : " + String.valueOf(cur_GPS_Location) + String.valueOf(onGPS));
//                double latitude = cur_GPS_Location.getLatitude();
//                double longitude = cur_GPS_Location.getLongitude();
//
//                if(onGPS) onMapPosition(latitude, longitude, 1);
//                else onMapPosition(latitude, longitude, 2);
//
//                if(save_mode) WriteTextFile(foldername, filename,String.valueOf(cur_GPS_Location.getLatitude()) + "," + String.valueOf(cur_GPS_Location.getLongitude()) + "," +
//                        String.valueOf(System.currentTimeMillis()) + "," + String.valueOf(onGPS) +"\n");
//            }
//        };
//        handler.sendEmptyMessage(0);



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
    public void WriteTextFile(String foldername, String filename, String contents){
        try{
            File dir = new File (foldername);
            //디렉토리 폴더가 없으면 생성함
            if(!dir.exists()){
                dir.mkdir();
            }
            //파일 output stream 생성
            FileOutputStream fos = new FileOutputStream(foldername+"/"+filename, true);
            //파일쓰기
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(fos));
            writer.write(contents);
            writer.flush();

            writer.close();
            fos.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.tool_menu,menu);
        return true;
    }

    @Override
    public  boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.action_settings:
                Toast.makeText(getApplicationContext(),"셋팅아이콘 클릭",Toast.LENGTH_LONG).show();
                break;
            case R.id.mode_1:
                Toast.makeText(getApplicationContext(),"mode1",Toast.LENGTH_LONG).show();
                txtMode.setText("MODE_AUTO");
                txtMode.setTextColor(Color.BLACK);
                mode1 = true;
                mode2 = false;
                mode3 = false;
                break;
            case R.id.mode_2:
                Toast.makeText(getApplicationContext(),"mode2",Toast.LENGTH_LONG).show();
                txtMode.setText("MODE_GPS");
                txtMode.setTextColor(Color.BLUE);
                mode1 = false;
                mode2 = true;
                mode3 = false;
                break;
            case R.id.mode_3:
                Toast.makeText(getApplicationContext(),"mode3",Toast.LENGTH_LONG).show();
                txtMode.setText("MODE_INS");
                txtMode.setTextColor(Color.GREEN);
                mode1 = false;
                mode2 = false;
                mode3 = true;
                break;
        }
        return false;
    }

    @Override //뒤로가기 버튼으로 종료하기
    public void onBackPressed() {
        AlertDialog.Builder  alBuilder = new AlertDialog.Builder(this);
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
                if(save_mode) WriteTextFile(foldername, filename, "Point" + "\n");
            }
        });
    }

    public void setDefaultLocation() { //초기 위치로 이동 , 만약 저장된 위치 데이터가 없다면 디폴트위치로 설정
        Log.d(TAG, "setDefaultLocation()");
        LatLng DEFAULT_LOCATION = null;
        if(LastPosition != null) {
            DEFAULT_LOCATION = LastPosition;
        }
        else {
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
        // Log.d(TAG, "onLocationChanged");
        if(location == null) return;
        double latitude = 0.;
        double longitude = 0.;

        txtLocationAcc.setText("Location Acc : " + location.getAccuracy());
        if(flag) {
            if (location.getProvider().equals(LocationManager.GPS_PROVIDER) && mode1) {
                cur_GPS_Location = location;
                latitude = location.getLatitude();
                longitude = location.getLongitude();
                onGPS = isBetterLocation(cur_GPS_Location);
                if(pre_GPS_Location != null) {
                    txtOnGps.setText("OnGPS : " + String.valueOf(onGPS));
                    if(save_mode) WriteTextFile(foldername, filename,String.valueOf(cur_GPS_Location.getTime()) + "," + String.valueOf(cur_GPS_Location.getAccuracy()) + "," +
                            String.valueOf(pre_GPS_Location.getTime()) + "," + String.valueOf(pre_GPS_Location.getAccuracy())
                            +"," + String.valueOf(sum_acc) +"," + String.valueOf(MIN_sum_acc) +"," + String.valueOf(res_acc) + "," + String.valueOf(onGPS) +"\n");
                }
                pre_GPS_Location = cur_GPS_Location;
                if(onGPS) onMapPosition(latitude, longitude, 1);
                else onMapPosition(latitude, longitude, 2);
                txtGPSLocation.setText("GPS Location : " + Double.toString(latitude) + "," + Double.toString(longitude));
            }

            if (location.getProvider().equals(LocationManager.GPS_PROVIDER) && mode2) {    //GPS 만 사용하기
                cur_GPS_Location = location;
                latitude = location.getLatitude();
                longitude = location.getLongitude();
                onMapPosition(latitude, longitude, 1);
                txtGPSLocation.setText("GPS Location : " + Double.toString(latitude) + "," + Double.toString(longitude));
            }

            flag = false; //한번만 들어오게 하는 flag
        }
        else flag = true;

    }

    public void onMapPosition(double latitude, double longitude,int mode)
    {
        float angle = 0;
        Log.d(TAG, "onMapPosition");
        currentPosition = new LatLng(latitude,longitude);
        angle = CalBearing(currentPosition, prePosition);
        prePosition = currentPosition;
        Log.d(TAG, "onMapPosition : " + "cur : " + String.valueOf(currentPosition) + "pre : " + String.valueOf(prePosition));
        String markerTitle = "MODE_" + String.valueOf(mode);
        String markerSnippet = "위도:" + String.valueOf(latitude) + " 경도:" + String.valueOf(longitude);
        setCurrentLocation(currentPosition,markerTitle,markerSnippet,mode,angle);
    }
    public float CalBearing(LatLng curPos, LatLng prePos){

        float angle = 0;
        if(curPos != null && prePos != null) {
            double f1 = Math.PI * prePos.latitude / 180;
            double f2 = Math.PI * curPos.latitude / 180;
            double dl = Math.PI * (prePos.longitude - curPos.longitude) / 180;
            angle  = (float)Math.atan2(Math.sin(dl) * Math.cos(f2), Math.cos(f1) * Math.sin(f2) - Math.sin(f1) * Math.cos(f2) * Math.cos(dl));
            angle = (float)(-angle * 180 / Math.PI); //마커 회전 (도 단위) 북을 기준 시계방향이 양수, 위경도로 구한 각도 시계방향이 음수
            Log.d(TAG, "CalBearing : " + String.valueOf(angle));
        }
        return  angle;
    }
    public void setCurrentLocation(LatLng currentPosition,String markerTitle,String markerSnippet,int mode,float angle) {
        Log.d(TAG, "setCurrentLocation");
        if (currentMarker != null) currentMarker.remove();

        LatLng currentLatLng = currentPosition;

        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.title(markerTitle);
        markerOptions.snippet(markerSnippet);
        markerOptions.position(currentLatLng);

        switch(mode) {
            case 1:
                BitmapDrawable bitmapdraw = (BitmapDrawable) getResources().getDrawable(R.drawable.marker);
                Bitmap b = bitmapdraw.getBitmap();
                Bitmap smallMarker = Bitmap.createScaledBitmap(b, 80, 80, false);
                markerOptions.icon(BitmapDescriptorFactory.fromBitmap(smallMarker));
                markerOptions.anchor(0.5f, 0.5f);
                markerOptions.rotation(angle);
                break;
            case 2:
                BitmapDrawable bitmapdraw_1 = (BitmapDrawable) getResources().getDrawable(R.drawable.marker_1);
                Bitmap b_1 = bitmapdraw_1.getBitmap();
                Bitmap smallMarker_1 = Bitmap.createScaledBitmap(b_1, 80, 80, false);
                markerOptions.icon(BitmapDescriptorFactory.fromBitmap(smallMarker_1));
                markerOptions.anchor(0.5f, 0.5f);
                markerOptions.rotation(angle);
                break;
            case 3:
                BitmapDrawable bitmapdraw_2 = (BitmapDrawable) getResources().getDrawable(R.drawable.marker_2);
                Bitmap b_2 = bitmapdraw_2.getBitmap();
                Bitmap smallMarker_2 = Bitmap.createScaledBitmap(b_2, 80, 80, false);
                markerOptions.icon(BitmapDescriptorFactory.fromBitmap(smallMarker_2));
                markerOptions.anchor(0.5f, 0.5f);
                markerOptions.rotation(angle);
                break;
        }

        markerOptions.draggable(true);

        currentMarker = mMap.addMarker(markerOptions);

        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLng(currentLatLng);
        if(mSwitch) mMap.moveCamera(cameraUpdate); //위치가 바뀔때 마다 카메라 위치가 따라감
    }

    public void moveCurrentPosition(LatLng currentPosition) {
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(currentPosition, 16);
        mMap.moveCamera(cameraUpdate);
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        Log.d(TAG, "onStatusChanged");
    }

    @Override
    public void onProviderEnabled(String provider) {
        Log.d(TAG, "onProviderEnabled");
        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
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
    protected  void onStart(){
        Log.d(TAG, "onStart()");
        super.onStart();

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            //권한이 없을 경우 최초 권한 요청 또는 사용자에 의한 재요청 확인
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION) &&
                    ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_COARSE_LOCATION)) {
                // 권한 재요청
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 100);
                return;
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 100);
                return;
            }
        }

    }

    @Override
    protected void onPause() {
        super.onPause();
        locationManager.removeUpdates(this);
        mSensorManager.unregisterListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, MIN_UPDATE_MILLIS, MIN_UPDATE_MITERS, this);
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, MIN_UPDATE_MILLIS, MIN_UPDATE_MITERS, this);
        locationManager.requestLocationUpdates(LocationManager.PASSIVE_PROVIDER, MIN_UPDATE_MILLIS, MIN_UPDATE_MITERS, this);

        mSensorManager.registerListener(this,mAccelerometer,SensorManager.SENSOR_DELAY_GAME);
        mSensorManager.registerListener(this,mGyrometer,SensorManager.SENSOR_DELAY_GAME);
        mSensorManager.registerListener(this,mPressure,SensorManager.SENSOR_DELAY_GAME);
    }

//    public boolean isBetterLocation(Location prelocation,Location currentLocation)
    public boolean isBetterLocation(Location currentLocation)
    {
        //현재 수신된 위치 데이터의 제공자가 GPS 인지 아닌지 판단 아니면 false
        //이전 데이터와 현재 데이터의 시간차이를 비교하여 업데이트 시간과 비교
            //데이터의 정확도를 받아와 최소값을 판단하여 비교
        float accuracyData = currentLocation.getAccuracy();
        int threshold = 3;

        if (accuracyData < MIN_sum_acc) MIN_sum_acc = accuracyData;
        res_acc = MIN_sum_acc + threshold ;
        if (res_acc < accuracyData) return false;
        else return true;

//        float accuracyDelta = (currentLocation.getAccuracy() - prelocation.getAccuracy());
//        sum_acc = sum_acc + accuracyDelta;
//        if (sum_acc < MIN_sum_acc) MIN_sum_acc = sum_acc;
//        res_acc = sum_acc - MIN_sum_acc;
//        if (res_acc > 3) return false; //5퍼센트 이내
//        else return true;

    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if(event.sensor == mAccelerometer) {
            System.arraycopy(event.values,0,mLastAccelerometer,0,event.values.length);
            txtAcc.setText( "Ax : " + String.valueOf(event.values[0]) +
                            "Ay : " + String.valueOf(event.values[1]) +
                            "Az : " + String.valueOf(event.values[2]) );

        } else if(event.sensor == mGyrometer) {
            double gyroZ;
            System.arraycopy(event.values,0,mLastGyrometer,0,event.values.length);

            gyroZ = event.values[2];
            dt = (event.timestamp - timestamp) * NS2S;
            timestamp = event.timestamp;

            if(dt - timestamp*NS2S != 0) {

                yaw = yaw + gyroZ*dt;
                txtGyro.setText( " Gz : " + String.valueOf(event.values[2]) + "yaw : " + String.valueOf(yaw*RtoD) );
            }
        } else if(event.sensor == mPressure){
            txtPress.setText("Pressure : " + String.valueOf(event.values[0]));
        }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
