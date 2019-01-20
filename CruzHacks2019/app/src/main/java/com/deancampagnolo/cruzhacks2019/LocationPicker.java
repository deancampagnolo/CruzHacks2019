package com.deancampagnolo.cruzhacks2019;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.google.android.gms.maps.model.LatLng;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class LocationPicker extends AppCompatActivity {

    private LatLng[] theFourClosestLatLng = new LatLng[4];
    private String[] theFourClosestQr = new String[4];
    private Integer[] theFourClosestPic = new Integer[4];
    private double userLatitude;
    private double userLongitude;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_picker);


        ArrayList<LatLng> arrayOfAllLatLng = new ArrayList<>();//FIXME Probably Placeholder value

        ArrayList<String> arrayOfAllQrCodes = new ArrayList<>();

        ArrayList<Integer> arrayOfAllPictures = new ArrayList<>();



        Intent lastIntent = getIntent();
        userLatitude = lastIntent.getDoubleExtra("userLatitude", 0);
        userLongitude = lastIntent.getDoubleExtra("userLongitude", 0);

        LatLng firstSpot = new LatLng(36.996842,-122.05206063);

        LatLng secondSpot = new LatLng(36.99703627,-122.05346599);
        LatLng thirdSpot = new LatLng(36.99720275, -122.05334009);
        LatLng fourthSpot = new LatLng(36.99759336, -122.05329262);
        LatLng fifthSpot = new LatLng(36.99684219,-122.05183109);
        LatLng sixthSpot = new LatLng(37,-120);


        arrayOfAllQrCodes.add("apple");
        arrayOfAllQrCodes.add("banana");
        arrayOfAllQrCodes.add("cantelope");
        arrayOfAllQrCodes.add("doritos");
        arrayOfAllQrCodes.add("eggplant");
        arrayOfAllQrCodes.add("fig");

        arrayOfAllLatLng.add(firstSpot);
        arrayOfAllLatLng.add(secondSpot);
        arrayOfAllLatLng.add(thirdSpot);
        arrayOfAllLatLng.add(fourthSpot);
        arrayOfAllLatLng.add(fifthSpot);
        arrayOfAllLatLng.add(sixthSpot);

        arrayOfAllPictures.add(R.drawable.a);
        arrayOfAllPictures.add(R.drawable.b);
        arrayOfAllPictures.add(R.drawable.c);
        arrayOfAllPictures.add(R.drawable.d);
        arrayOfAllPictures.add(R.drawable.e);
        arrayOfAllPictures.add(R.drawable.f);



        theFourClosestLatLng = findFourClosest(arrayOfAllLatLng, arrayOfAllQrCodes, arrayOfAllPictures);


        ImageButton imageButton1 = findViewById(R.id.location1);



        ImageButton imageButton2 = findViewById(R.id.location2);

        ImageButton imageButton3 = findViewById(R.id.location3);
        ImageButton imageButton4 = findViewById(R.id.location4);


        imageButton1.setImageResource(theFourClosestPic[0]);
        imageButton2.setImageResource(theFourClosestPic[1]);
        imageButton3.setImageResource(theFourClosestPic[2]);
        imageButton4.setImageResource(theFourClosestPic[3]);


    }

    private double calculateDistance(LatLng latLng){
        return  Math.sqrt(Math.pow((userLatitude-latLng.latitude),2) + Math.pow((userLongitude-latLng.longitude),2));
    }

    private LatLng[] findFourClosest(ArrayList<LatLng> arrayOfAllLatLng, ArrayList<String> arrayOfAllQrCodes, ArrayList<Integer> arrayOfAllPictures){
        double[] distanceOfFour= new double[4];
        LatLng[] fourClosest = new LatLng[4];
        String[] fourQr = new String[4];
        Integer[] fourPic = new Integer[4];

        for(int i = 0; i<4;i++){
            fourClosest[i] = arrayOfAllLatLng.get(i);
            distanceOfFour[i] = calculateDistance(arrayOfAllLatLng.get(i));
            fourQr[i] = arrayOfAllQrCodes.get(i);
            fourPic[i] = arrayOfAllPictures.get(i);
        }

        sort(distanceOfFour,fourClosest, fourQr, fourPic);

        for(int i = 4; i<arrayOfAllLatLng.size(); i++){
            double tempDistance = calculateDistance(arrayOfAllLatLng.get(i));
            if(tempDistance<distanceOfFour[3]){
                fourClosest[3] = arrayOfAllLatLng.get(i);
                distanceOfFour[3] = tempDistance;
                fourPic[3] = arrayOfAllPictures.get(i);
                sort(distanceOfFour,fourClosest, fourQr, fourPic);
            }
        }

        theFourClosestQr = fourQr;
        theFourClosestPic = fourPic;

        return fourClosest;


    }

    private void sort( double[] distance, LatLng[] latLng, String[] qr, Integer[] pic)//change to insertion later
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

            String temp3 = qr[min_idx];
            qr[min_idx] = qr[i];
            qr[i] = temp3;

            Integer temp4 = pic[min_idx];
            pic[min_idx] = pic[i];
            pic[i] = temp4;
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
                i.putExtra("qr",theFourClosestQr[0]);
                startActivity(i);
                break;

            case R.id.location2:
                //FIXME These are placeholder values
                LatLng cruzHacks2 = theFourClosestLatLng[1];

                //Friendly reminder that this is Location Picker and will go to Find Location

                Intent i2 = new Intent(this, FindLocation.class);
                i2.putExtra("latitude", cruzHacks2.latitude );
                i2.putExtra("longitude", cruzHacks2.longitude);
                i2.putExtra("qr",theFourClosestQr[1]);
                startActivity(i2);
                break;

            case R.id.location3:
                //FIXME These are placeholder values
                LatLng cruzHacks3 = theFourClosestLatLng[2];

                //Friendly reminder that this is Location Picker and will go to Find Location

                Intent i3 = new Intent(this, FindLocation.class);
                i3.putExtra("latitude", cruzHacks3.latitude );
                i3.putExtra("longitude", cruzHacks3.longitude);
                i3.putExtra("qr",theFourClosestQr[2]);
                startActivity(i3);
                break;

            case R.id.location4:
                //FIXME These are placeholder values
                LatLng cruzHacks4 = theFourClosestLatLng[3];

                //Friendly reminder that this is Location Picker and will go to Find Location

                Intent i4 = new Intent(this, FindLocation.class);
                i4.putExtra("latitude", cruzHacks4.latitude );
                i4.putExtra("longitude", cruzHacks4.longitude);
                i4.putExtra("qr",theFourClosestQr[3]);
                startActivity(i4);
                break;
        }
    }
}
