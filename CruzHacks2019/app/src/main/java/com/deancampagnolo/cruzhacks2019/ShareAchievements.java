package com.deancampagnolo.cruzhacks2019;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class ShareAchievements extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share_achievements);
    }

    public void onButtonClicked(View v){
        switch (v.getId()){
            case R.id.TakeAPic:
                //TODO Implement
                break;

            case R.id.Share:
                //TODO Implement
                break;
        }
    }
}
