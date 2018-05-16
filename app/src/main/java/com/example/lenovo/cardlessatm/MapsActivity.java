package com.example.lenovo.cardlessatm;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ArrayList<Marker> mMarkerArray = new ArrayList<Marker>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        float zoomLevel = 16.0f;





        // Add a marker in Sydney, Australia, and move the camera.
                LatLng mindspace = new LatLng(17.43803746, 78.38469116);

        mMap.addMarker(new MarkerOptions().position(mindspace).title("Marker in Mindspace"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(mindspace,zoomLevel));
        LatLng mindspace1 = new LatLng( 17.443704, 78.367929);

        mMap.addMarker(new MarkerOptions().position(mindspace1).title("Marker in Mindspace"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(mindspace1,zoomLevel));

        LatLng mindspace2 = new LatLng( 17.441493, 78.380632);

        mMap.addMarker(new MarkerOptions().position(mindspace2).title("Marker in Mindspace"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(mindspace2,zoomLevel));

        LatLng mindspace3 = new LatLng( 17.444646, 78.381662);

        mMap.addMarker(new MarkerOptions().position(mindspace3).title("Marker in Mindspace"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(mindspace3,zoomLevel));

        LatLng mindspace4= new LatLng( 17.444564, 78.381190);

        mMap.addMarker(new MarkerOptions().position(mindspace4).title("Marker in Mindspace"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(mindspace4,zoomLevel));

        LatLng mindspace5 = new LatLng( 17.435433, 78.385653);

        mMap.addMarker(new MarkerOptions().position(mindspace5).title("Marker in Mindspace"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(mindspace5,zoomLevel));
        LatLng mindspace6 = new LatLng( 17.432649, 78.387026);

        mMap.addMarker(new MarkerOptions().position(mindspace6).title("Marker in Mindspace"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(mindspace6,zoomLevel));



    }
}