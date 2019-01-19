package com.deancampagnolo.cruzhacks2019;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.MarkerOptions;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private double latitude;
    private double longitude;
    private double userLatitude;
    private double userLongitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        Intent lastIntent = getIntent();
        latitude = lastIntent.getDoubleExtra("latitude", 0);//goes to 0 if doesn't get value
        longitude = lastIntent.getDoubleExtra("longitude", 0);//goes to 0 if doesn't get value
        userLatitude = lastIntent.getDoubleExtra("userLatitude", 0);
        userLongitude = lastIntent.getDoubleExtra("userLongitude", 0);


    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        try {
            // Customise the styling of the base map using a JSON object defined
            // in a raw resource file.
            boolean success = googleMap.setMapStyle(
                    MapStyleOptions.loadRawResourceStyle(
                            this, R.raw.mapstyle));

            if (!success) {
                Log.e("MapsActivity", "Style parsing failed.");
            }
        } catch (Resources.NotFoundException e) {
            Log.e("MapsActivity", "Can't find style. Error: ", e);
        }

        LatLng location = new LatLng(latitude, longitude);
        LatLng userLocation = new LatLng(userLatitude,userLongitude);
        CircleOptions locationCircleOptions = new CircleOptions();
        locationCircleOptions.center(location);
        locationCircleOptions.radius(30);
        locationCircleOptions.strokeColor(Color.RED);
        locationCircleOptions.fillColor(Color.LTGRAY);
        mMap.addCircle(locationCircleOptions);


        mMap.addMarker(new MarkerOptions().position(userLocation).title("Where User is"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(userLocation));
        //mMap.setMinZoomPreference(14);
    }


}