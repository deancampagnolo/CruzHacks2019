package com.deancampagnolo.cruzhacks2019;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.google.android.gms.maps.model.LatLng;

public class LocationPicker extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_picker);
    }

    public void onButtonClicked(View v){
        switch(v.getId()){
            case R.id.location1:
                //FIXME These are placeholder values
                LatLng cruzHacks = new LatLng(36.995891199999996,-122.05752319999999);

                //Friendly reminder that this is Location Picker and will go to Find Location

                Intent i = new Intent(this, FindLocation.class);
                i.putExtra("latitude", cruzHacks.latitude );
                i.putExtra("longitude", cruzHacks.longitude);
                startActivity(i);
                break;
        }
    }
}
