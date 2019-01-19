package com.deancampagnolo.cruzhacks2019;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnSuccessListener;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;

public class FindLocation extends AppCompatActivity {

    private LocationManager locationManager;
    private LocationListener locationListener;
    private TextView weAreGettingCloserText;


    private int counter;

    private double latitude;
    private double longitude;

    private double userLatitude;
    private double userLongitude;

    private double lastDistance;


    private int gettingCloserColor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_location);

        gettingCloserColor = Color.GRAY;

        Intent lastIntent = getIntent();
        latitude = lastIntent.getDoubleExtra("latitude", 0);//goes to 0 if doesn't get value
        longitude = lastIntent.getDoubleExtra("longitude", 0);//goes to 0 if doesn't get value

        //initializing these values
        userLatitude = 0;
        userLongitude = 0;
        counter = 0;
        lastDistance = 0;
        weAreGettingCloserText = findViewById(R.id.GettingCloserText);

        weAreGettingCloserText.setTextColor(gettingCloserColor);


        locationManager = (LocationManager) this.getSystemService(LOCATION_SERVICE);

        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                //Log.d("Location", Double.toString(location.getLatitude()));
                //Log.d("Location", "HHHHHHHHHHHHH");


                userLatitude = location.getLatitude();
                userLongitude = location.getLongitude();
                if(lastDistance==0){
                    lastDistance = calculateDistance(userLatitude,userLongitude);
                }

                counter++;
                weAreGettingCloserText.setText(Integer.toString(counter));

                if(counter>10){
                    if(weAreGettingCloser(userLatitude,userLongitude)){
                        gettingCloserColor = Color.GREEN;
                    }else{
                        gettingCloserColor = Color.RED;
                    }
                    counter = 0;
                    weAreGettingCloserText.setTextColor(gettingCloserColor);
                }
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
        switch (v.getId()){
            case R.id.FindLocationScan:
                //TODO Implement Scan QR
                startActivity(new Intent(this, ShareAchievements.class));
                break;

            case R.id.MapButton:

                //Friendly reminder that this is FindLocation and this will go to Map
                if(userLatitude ==0 || userLongitude==0){
                    Toast.makeText(this, "Please Wait One Moment", Toast.LENGTH_SHORT).show();
                    break;
                }

                Intent i = new Intent(this, MapsActivity.class);
                i.putExtra("latitude", latitude );
                i.putExtra("longitude", longitude);
                i.putExtra("userLatitude", userLatitude);
                i.putExtra("userLongitude",userLongitude);
                startActivity(i);

                break;
        }
    }

    private boolean weAreGettingCloser(double currentLatitude, double currentLongitude){
        double currentDistance = calculateDistance(currentLatitude,currentLongitude);
        if(currentDistance<lastDistance){
            lastDistance = currentDistance;
            return true;
        }else{
            lastDistance = currentDistance;
            return false;
        }
    }

    private double calculateDistance(double currentLatitude, double currentLongitude){
        return  Math.sqrt(Math.pow((latitude-currentLatitude),2) + Math.pow((longitude-currentLongitude),2));
    }

}
