package com.deancampagnolo.cruzhacks2019;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.google.android.gms.maps.model.LatLng;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class LocationPicker extends AppCompatActivity {

    private LatLng[] theFourClosestLatLng = new LatLng[4];
    private double userLatitude;
    private double userLongitude;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_picker);


        ArrayList<LatLng> arrayOfAllLatLng = new ArrayList<>();//FIXME Probably Placeholder value

        ArrayList<String> arrayOfAllQrCodes = new ArrayList<>();

        Intent lastIntent = getIntent();
        userLatitude = lastIntent.getDoubleExtra("userLatitude", 0);
        userLongitude = lastIntent.getDoubleExtra("userLongitude", 0);

        LatLng firstSpot = new LatLng(36.995891199999996,-122.05752319999999);

        LatLng secondSpot = new LatLng(36.99589119,-122.0575231999);
        LatLng thirdSpot = new LatLng(36, -122);
        LatLng fourthSpot = new LatLng(30, -122);
        LatLng fifthSpot = new LatLng(36,-120);
        LatLng sixthSpot = new LatLng(37,-120);



        arrayOfAllLatLng.add(firstSpot);
        arrayOfAllLatLng.add(secondSpot);
        arrayOfAllLatLng.add(thirdSpot);
        arrayOfAllLatLng.add(fourthSpot);
        arrayOfAllLatLng.add(fifthSpot);
        arrayOfAllLatLng.add(sixthSpot);



        theFourClosestLatLng = findFourClosest(arrayOfAllLatLng);



    }

    private double calculateDistance(LatLng latLng){
        return  Math.sqrt(Math.pow((userLatitude-latLng.latitude),2) + Math.pow((userLongitude-latLng.longitude),2));
    }

    private LatLng[] findFourClosest(ArrayList<LatLng> arrayOfAllLatLng){
        double[] distanceOfFour= new double[4];
        LatLng[] fourClosest = new LatLng[4];

        for(int i = 0; i<4;i++){
            fourClosest[i] = arrayOfAllLatLng.get(i);
            distanceOfFour[i] = calculateDistance(arrayOfAllLatLng.get(i));
        }

        sort(distanceOfFour,fourClosest);

        for(int i = 4; i<arrayOfAllLatLng.size(); i++){
            double tempDistance = calculateDistance(arrayOfAllLatLng.get(i));
            if(tempDistance<distanceOfFour[3]){
                fourClosest[3] = arrayOfAllLatLng.get(i);
                distanceOfFour[3] = tempDistance;
                sort(distanceOfFour,fourClosest);
            }
        }

        return fourClosest;


    }

    private void sort( double[] distance, LatLng[] latLng)//change to insertion later
    {
        int n = distance.length;

        // One by one move boundary of unsorted subarray
        for (int i = 0; i < n-1; i++)
        {
            // Find the minimum element in unsorted array
            int min_idx = i;
            for (int j = i+1; j < n; j++)
                if (distance[j] < distance[min_idx])
                    min_idx = j;

            // Swap the found minimum element with the first
            // element
            double temp = distance[min_idx];
            distance[min_idx] = distance[i];
            distance[i] = temp;

            LatLng temp2 = latLng[min_idx];
            latLng[min_idx] = latLng[i];
            latLng[i] = temp2;
        }
    }

    public void onButtonClicked(View v){
        switch(v.getId()){
            case R.id.location1:
                //FIXME These are placeholder values
                LatLng cruzHacks = theFourClosestLatLng[0];

                //Friendly reminder that this is Location Picker and will go to Find Location

                Intent i = new Intent(this, FindLocation.class);
                i.putExtra("latitude", cruzHacks.latitude );
                i.putExtra("longitude", cruzHacks.longitude);
                startActivity(i);
                break;

            case R.id.location2:
                //FIXME These are placeholder values
                LatLng cruzHacks2 = theFourClosestLatLng[1];

                //Friendly reminder that this is Location Picker and will go to Find Location

                Intent i2 = new Intent(this, FindLocation.class);
                i2.putExtra("latitude", cruzHacks2.latitude );
                i2.putExtra("longitude", cruzHacks2.longitude);
                startActivity(i2);
                break;

            case R.id.location3:
                //FIXME These are placeholder values
                LatLng cruzHacks3 = theFourClosestLatLng[2];

                //Friendly reminder that this is Location Picker and will go to Find Location

                Intent i3 = new Intent(this, FindLocation.class);
                i3.putExtra("latitude", cruzHacks3.latitude );
                i3.putExtra("longitude", cruzHacks3.longitude);
                startActivity(i3);
                break;

            case R.id.location4:
                //FIXME These are placeholder values
                LatLng cruzHacks4 = theFourClosestLatLng[3];

                //Friendly reminder that this is Location Picker and will go to Find Location

                Intent i4 = new Intent(this, FindLocation.class);
                i4.putExtra("latitude", cruzHacks4.latitude );
                i4.putExtra("longitude", cruzHacks4.longitude);
                startActivity(i4);
                break;
        }
    }
}
