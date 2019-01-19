package com.deancampagnolo.cruzhacks2019;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

public class SubmitLocation extends AppCompatActivity {

    private ImageView takeHider;
    private ImageView goHider;
    private ImageButton submitPic;
    private ImageButton goButton;
    private LocationManager locationManager;
    private LocationListener locationListener;
    private double userLatitude;
    private double userLongitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submit_location);
        takeHider=findViewById(R.id.IVhidesTake);
        goHider=findViewById(R.id.IVhidesGo);
        submitPic=findViewById(R.id.SubmitPicture);
        goButton=findViewById(R.id.Go);

        userLatitude = 0;
        userLongitude = 0;

        locationManager = (LocationManager) this.getSystemService(LOCATION_SERVICE);

        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {

                userLatitude = location.getLatitude();
                userLongitude = location.getLongitude();
                Log.d("Locationz", Double.toString(location.getLatitude()));


            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        };



        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation


            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},1);


        }else{
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
            if(ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED){
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
            }
        }
    }

    public void onButtonClicked(View v){
        switch(v.getId()){

            case R.id.ScanCode:
                //TODO Implement
                Log.v("Locationz", "ScanCode()");

                unhideTake();
                break;

            case R.id.SubmitPicture:
                //TODO Implement

                if(userLatitude != 0 && userLongitude != 0){

                }

                unhideGo();



                break;

            case R.id.Go:
                //TODO Implement
                break;

        }
    }
    public void unhideTake(){

        takeHider.setVisibility(View.INVISIBLE);

    }
    public void unhideGo(){

    goHider.setVisibility(View.INVISIBLE);
    }
}
