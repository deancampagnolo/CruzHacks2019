package com.deancampagnolo.cruzhacks2019;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class GetCode extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_code);
    }

    public void onButtonClicked(View v){
        switch(v.getId()){
            case R.id.GetCode:
                //TODO Implement
                break;

            case R.id.ContinueToSubmit:
                startActivity(new Intent(this, SubmitLocation.class));
                break;
        }
    }
}
