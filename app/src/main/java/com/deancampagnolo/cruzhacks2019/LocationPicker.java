package com.deancampagnolo.cruzhacks2019;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class LocationPicker extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_picker);
    }

    public void onButtonClicked(View v){
        switch(v.getId()){
            case R.id.location1:
                startActivity(new Intent(this, FindLocation.class));
                break;
        }
    }
}
