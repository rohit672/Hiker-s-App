package com.example.hikersapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    LocationManager locationManager ;
    LocationListener locationListener ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE) ;

        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(@NonNull Location location) {

                updateLocationInfo(location);
            }
        };

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,new String []{Manifest.permission.ACCESS_FINE_LOCATION},1);
        }
        else {

            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER , 0,0,locationListener);
            Location lastLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER) ;

            if (lastLocation != null) {

                  updateLocationInfo(lastLocation);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
            }
        }
    }

    public void updateLocationInfo(Location location) {

        TextView Lati = findViewById(R.id.Latti);
        TextView Logi = findViewById(R.id.Longi);
        //TextView Alti = findViewById(R.id.Altti);
        TextView newAdd = findViewById(R.id.locadd);

        Lati.setText("LATITUDE :" + Double.toString(location.getLatitude()));
        Logi.setText("LONGITUDE :" + Double.toString(location.getLongitude()));
        //Alti.setText("ALTITUDE :" + Double.toString(location.getAltitude()));

        String address = "could nt find add. :(" ;

        Geocoder geocoder = new Geocoder(this , Locale.getDefault()) ;

        try {
            List<Address> listAdd = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
            
           // Log.i("xyz" , listAdd.get(0).getAddressLine(0)) ;

            if (listAdd != null && listAdd.size() > 0 ) {

                address = "Address\n" ;

                address += listAdd.get(0).getAddressLine(0);

//
//                if (listAdd.get(0).getFeatureName() != null) {
//                    address += listAdd.get(0).getThoroughfare()  + "\n";
//                }
//
//
//                if (listAdd.get(0).getThoroughfare() != null) {
//                      address += listAdd.get(0).getThoroughfare()  + "\n";
//                }
//
//                if (listAdd.get(0).getSubAdminArea() != null ) {
//                    address += listAdd.get(0).getThoroughfare()  + "\n";
//                }
//                if (listAdd.get(0).getLocality() != null) {
//                    address += listAdd.get(0).getLocality()  + "\n";
//                }
//
//                if (listAdd.get(0).getAdminArea() != null) {
//                    address += listAdd.get(0).getAdminArea()   + "\n";
//                }
//


            }


        } catch (Exception e){
            e.printStackTrace();
        }

        newAdd.setText(address);

    }
}