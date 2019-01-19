package com.deancampagnolo.cruzhacks2019;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class FindLocation extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_location);
    }

    public void onButtonClicked(View v){
        switch (v.getId()){
            case R.id.FindLocationScan:
                //TODO Implement Scan QR
                startActivity(new Intent(this, ShareAchievements.class));
                break;

            case R.id.MapButton:
                //TODO Implement
                break;
        }
    }
}
