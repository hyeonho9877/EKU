package com.kyonggi.eku;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;
import java.util.Locale;


public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback {

    String[] buildingList= {"6강의동","7강의동","8강의동","종합강의동","제2공학관"};
    double[] Latitude = {37.300993099700044,37.30130605532382,37.30081483134132,37.301432678670224,37.30031004517468};
    double[] Longitude = {127.03836020691885,127.03883408985836,127.03931601750516,127.03734812542022,127.03993045991801};
    //6강의동 - 37.300993099700044, 127.03836020691885
    //7강의동 - 37.30130605532382, 127.03883408985836
    //8강의동 - 37.30081483134132, 127.03931601750516
    //종강 - 37.301432678670224, 127.03734812542022
    //제2공 - 37.30031004517468, 127.03993045991801
    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    @Override
    public void onMapReady(final GoogleMap googleMap) {

        mMap = googleMap;
        Intent intent = getIntent();
        String name = intent.getStringExtra("title");


        for (int i=0; i<buildingList.length; i++){
            if(buildingList[i].equals(name)) {
                LatLng location = new LatLng(Latitude[i], Longitude[i]);
                MarkerOptions markerOptions = new MarkerOptions();
                markerOptions.position(location);
                markerOptions.title(name);
                markerOptions.snippet("경기대학교");
                mMap.addMarker(markerOptions);
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 18));
                break;
            }
        }
        googleMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
    }

}
