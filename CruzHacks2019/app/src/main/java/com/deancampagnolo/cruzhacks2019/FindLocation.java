package com.deancampagnolo.cruzhacks2019;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.ml.vision.FirebaseVision;
import com.google.firebase.ml.vision.barcode.FirebaseVisionBarcode;
import com.google.firebase.ml.vision.barcode.FirebaseVisionBarcodeDetector;
import com.google.firebase.ml.vision.barcode.FirebaseVisionBarcodeDetectorOptions;
import com.google.firebase.ml.vision.common.FirebaseVisionImage;

import java.util.List;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;

public class FindLocation extends AppCompatActivity {

    FirebaseVisionBarcodeDetectorOptions options =
            new FirebaseVisionBarcodeDetectorOptions.Builder()
                    .setBarcodeFormats(FirebaseVisionBarcode.FORMAT_QR_CODE ).build();

    private LocationManager locationManager;
    private LocationListener locationListener;
    private TextView weAreGettingCloserText;


    private int counter;

    private double latitude;
    private double longitude;

    private double userLatitude;
    private double userLongitude;

    private double lastDistance;
    private String qrCode;


    private int gettingCloserColor;

    private Bitmap bitmap;
    private String barcodeData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_location);

        //FirebaseApp.initializeApp(this);

        gettingCloserColor = Color.GRAY;

        Intent lastIntent = getIntent();
        latitude = lastIntent.getDoubleExtra("latitude", 0);//goes to 0 if doesn't get value
        longitude = lastIntent.getDoubleExtra("longitude", 0);//goes to 0 if doesn't get value
        qrCode = lastIntent.getStringExtra("qr");

        //initializing these values
        userLatitude = 0;
        userLongitude = 0;
        counter = 0;
        lastDistance = 0;
        weAreGettingCloserText = findViewById(R.id.GettingCloserText);

        weAreGettingCloserText.setTextColor(gettingCloserColor);


        locationManager = (LocationManager) this.getSystemService(LOCATION_SERVICE);

        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                //Log.d("Location", Double.toString(location.getLatitude()));
                //Log.d("Location", "HHHHHHHHHHHHH");


                userLatitude = location.getLatitude();
                userLongitude = location.getLongitude();
                if(lastDistance==0){
                    lastDistance = calculateDistance(userLatitude,userLongitude);
                }

                counter++;
                //weAreGettingCloserText.setText(Integer.toString(counter));
                //weAreGettingCloserText.setText("Lat: "+userLatitude+"\n" + "Lng: "+userLongitude);

                if(counter>5){//counter is at "6"
                    if(weAreGettingCloser(userLatitude,userLongitude)){
                        gettingCloserColor = Color.GREEN;
                        weAreGettingCloserText.setText("You are walking towards the landmark!");
                    }else{
                        gettingCloserColor = Color.RED;
                        weAreGettingCloserText.setText("You are walking away from the landmark!");
                    }
                    counter = 0;
                    weAreGettingCloserText.setTextColor(gettingCloserColor);
                }
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        };



        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation


            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},1);


        }else{
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);






    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
            if(ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED){
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
            }
        }
    }

    public void onButtonClicked(View v){
        switch (v.getId()){
            case R.id.FindLocationScan:
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent,0);

                //startActivity(new Intent(this, ShareAchievements.class));

                break;

            case R.id.MapButton:

                //Friendly reminder that this is FindLocation and this will go to Map
                if(userLatitude ==0 || userLongitude==0){
                    Toast.makeText(this, "Please Wait One Moment", Toast.LENGTH_SHORT).show();
                    break;
                }

                Intent i = new Intent(this, MapsActivity.class);
                i.putExtra("latitude", latitude );
                i.putExtra("longitude", longitude);
                i.putExtra("userLatitude", userLatitude);
                i.putExtra("userLongitude",userLongitude);
                startActivity(i);

                break;
        }
    }

    private boolean weAreGettingCloser(double currentLatitude, double currentLongitude){
        double currentDistance = calculateDistance(currentLatitude,currentLongitude);
        if(currentDistance<lastDistance){
            lastDistance = currentDistance;
            return true;
        }else{
            lastDistance = currentDistance;
            return false;
        }
    }

    private double calculateDistance(double currentLatitude, double currentLongitude){
        return  Math.sqrt(Math.pow((latitude-currentLatitude),2) + Math.pow((longitude-currentLongitude),2));
    }

    public void onActivityResult(int requestCode,int resultCode, Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        bitmap= (Bitmap)data.getExtras().get("data");
        //txt=findViewById(R.id.textView);
        checkBarcode();

    }

    public void checkBarcode(){
        FirebaseVisionImage image= FirebaseVisionImage.fromBitmap(bitmap);

        FirebaseVisionBarcodeDetector detector = FirebaseVision.getInstance().getVisionBarcodeDetector();


        Task<List<FirebaseVisionBarcode>> result = detector.detectInImage(image)
                .addOnSuccessListener(new OnSuccessListener<List<FirebaseVisionBarcode>>() {
                    @Override
                    public void onSuccess(List<FirebaseVisionBarcode> barcodes) {
                        // Task completed successfully
                        // ...
                        for (FirebaseVisionBarcode barcode: barcodes){
                            barcodeData=barcode.getDisplayValue();
                            Toast.makeText(FindLocation.this,barcodeData,Toast.LENGTH_LONG).show();
                            if(barcodeData.equals(qrCode)){
                                startActivity(new Intent(FindLocation.this, ShareAchievements.class));
                            }else {
                                Toast.makeText(FindLocation.this,"Please try again, barcodeData: "+barcodeData +" != " + qrCode,Toast.LENGTH_LONG).show();
                            }

                        }

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Task failed with an exception
                        // ...
                    }
                });


    }

}
