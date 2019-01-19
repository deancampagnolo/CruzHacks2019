package com.deancampagnolo.cruzhacks2019;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class SubmitLocation extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submit_location);
    }

    public void onButtonClicked(View v){
        switch(v.getId()){

            case R.id.ScanCode:
                //TODO Implement
                break;

            case R.id.SubmitPicture:
                //TODO Implement
                break;

            case R.id.Go:
                //TODO Implement
                break;
        }
    }
}
