package com.kyonggi.eku;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapFragment extends Fragment implements OnMapReadyCallback {

    View rootView;
    MapView mapView;
    String[] buildingList= {"6강의동","7강의동","8강의동","종합강의동","제2공학관"};
    double[] Latitude = {37.300993099700044,37.30130605532382,37.30081483134132,37.301432678670224,37.30031004517468};
    double[] Longitude = {127.03836020691885,127.03883408985836,127.03931601750516,127.03734812542022,127.03993045991801};
    //6강의동 - 37.300993099700044, 127.03836020691885
    //7강의동 - 37.30130605532382, 127.03883408985836
    //8강의동 - 37.30081483134132, 127.03931601750516
    //종강 - 37.301432678670224, 127.03734812542022
    //제2공 - 37.30031004517468, 127.03993045991801

    public MapFragment() {

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.activity_maps, container, false);
        mapView = (MapView) rootView.findViewById(R.id.map);
        mapView.onCreate(savedInstanceState);

        mapView.getMapAsync(this);

        return rootView;
    }

    @Override
    public void onResume() {
        mapView.onResume();
        super.onResume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        MapsInitializer.initialize(this.getActivity());
        String name = MainBoard.BuildingButton.getText().toString();

        for (int i=0; i<buildingList.length; i++){
            if(buildingList[i].equals(name)) {
                LatLng location = new LatLng(Latitude[i], Longitude[i]);
                MarkerOptions markerOptions = new MarkerOptions();
                markerOptions.position(location);
                markerOptions.title(name);
                markerOptions.snippet("경기대학교");
                googleMap.addMarker(markerOptions);
                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 18));
                break;
            }
        }
        //googleMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);

    }

}

