package com.example.lenovo.cardlessatm;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    DatabaseReference mapRef;
    public Double lat;
    public Double longitude;
    public String atmid;

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
        final float zoomLevel = 16.0f;
        mapRef= FirebaseDatabase.getInstance().getReferenceFromUrl("https://cardlessatm-1a9ff.firebaseio.com/").child("ATMLocations");
        mapRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot imageSnapshot: dataSnapshot.getChildren()) {
                    try {
                        System.out.println("lat="+imageSnapshot.child("latitude"));
                        System.out.println("lat="+imageSnapshot.child("longitude"));

                        lat = Double.valueOf(String.valueOf(imageSnapshot.child("latitude").getValue()));
                        longitude = Double.valueOf(String.valueOf(imageSnapshot.child("longitude").getValue()));
                        atmid = String.valueOf(imageSnapshot.child("ATMCode").getValue());

                    }
                    catch(NumberFormatException e){
                        System.out.print(e);
                    }

                    LatLng mindspace = new LatLng(lat, longitude);
                    mMap.addMarker(new MarkerOptions().position(mindspace).title(atmid));
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(mindspace,zoomLevel));

                }



            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }}
        );







        // Add a marker in Sydney, Australia, and move the camera.



    }
}