package com.deancampagnolo.cruzhacks2019;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

public class SubmitLocation extends AppCompatActivity {

    private ImageView takeHider;
    private ImageView goHider;
    private ImageButton submitPic;
    private ImageButton goButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submit_location);
        takeHider=findViewById(R.id.IVhidesTake);
        goHider=findViewById(R.id.IVhidesGo);
        submitPic=findViewById(R.id.SubmitPicture);
        goButton=findViewById(R.id.Go);

    }

    public void onButtonClicked(View v){
        switch(v.getId()){

            case R.id.ScanCode:
                //TODO Implement

                unhideTake();
                break;

            case R.id.SubmitPicture:
                //TODO Implement

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
