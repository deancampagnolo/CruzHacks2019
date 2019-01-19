package com.deancampagnolo.cruzhacks2019;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.google.android.gms.maps.model.LatLng;

public class FindLocation extends AppCompatActivity {

    private double latitude;
    private double longitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_location);

        Intent lastIntent = getIntent();
        latitude = lastIntent.getDoubleExtra("latitude", 0);//goes to 0 if doesn't get value
        longitude = lastIntent.getDoubleExtra("longitude", 0);//goes to 0 if doesn't get value


    }

    public void onButtonClicked(View v){
        switch (v.getId()){
            case R.id.FindLocationScan:
                //TODO Implement Scan QR
                startActivity(new Intent(this, ShareAchievements.class));
                break;

            case R.id.MapButton:
                //Friendly reminder that this is FindLocation and this will go to Map
                Intent i = new Intent(this, MapsActivity.class);
                i.putExtra("latitude", latitude );
                i.putExtra("longitude", longitude);
                startActivity(i);

                break;
        }
    }
}
