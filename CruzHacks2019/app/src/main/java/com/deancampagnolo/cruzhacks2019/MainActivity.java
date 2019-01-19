package com.deancampagnolo.cruzhacks2019;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }

    public void onButtonClicked(View v){
        switch(v.getId()){
            case R.id.MainSubmitButton:
                startActivity(new Intent(this, GetCode.class));
                break;

            case R.id.MainLocateButton:
                startActivity(new Intent(this, LocationPicker.class));
                break;


        }

    }

}
