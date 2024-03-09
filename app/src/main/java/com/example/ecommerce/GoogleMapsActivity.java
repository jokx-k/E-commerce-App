package com.example.ecommerce;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;

public class GoogleMapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    LocationManager locManager;
    MyLocationListener locListener;

    Button btn_confirm_location;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        locListener = new MyLocationListener(getApplicationContext());
        locManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        try {
            locManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 6000, 0, locListener);
        } catch (SecurityException e) {
            Toast.makeText(getApplicationContext(), "Not allowed to access this location", Toast.LENGTH_LONG).show();
        }

        setContentView(R.layout.activity_google_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        btn_confirm_location = (Button) findViewById(R.id.btn_confirm_location);

    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(30.04441960,31.235711600),8));
        mMap.addMarker(new MarkerOptions().position(new LatLng(30.04441960,31.235711600)).title("Cairo")).setDraggable(true);

        btn_confirm_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMap.clear();
                Geocoder coder = new Geocoder(getApplicationContext());
                List<Address> addressList;
                Location loc =null;

                try{
                    loc=locManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                }catch (SecurityException x){
                    Toast.makeText(getApplicationContext(),"You Are Not Allowed To Access The Current Location",Toast.LENGTH_LONG).show();
                }
                if(loc!=null){
                    LatLng myPosition =new LatLng(loc.getLatitude(),loc.getLongitude());
                    try{
                        addressList=coder.getFromLocation(myPosition.latitude,myPosition.longitude,1);
                        if(!addressList.isEmpty()){
                            String address="";
                            for (int i=0;i<=addressList.get(0).getMaxAddressLineIndex();i++){
                                address+=addressList.get(0).getAddressLine(i);
                            }
                            mMap.addMarker(new MarkerOptions().position(myPosition).title(address)).setDraggable(true);

                            Cart.Cust_Address = address;

                            new CountDownTimer(25000, 1000) {

                                public void onTick(long millisUntilFinished) {

                                }

                                public void onFinish() {
                                    ((Button)v).setBackgroundColor(getResources().getColor(R.color.green));
                                    Drawable img = getResources().getDrawable( R.drawable.done_icon );
                                    ((Button)v).setCompoundDrawablesWithIntrinsicBounds(img , null , null , null);
                                    ((Button)v).setText("Location Confirmed");
                                    finish();
                                }
                            }.start();

                        }
                    }catch(IOException e){
                        mMap.addMarker(new MarkerOptions().position(myPosition).title("My Location"));
                    }
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(myPosition, 15));
                }else{
                    Toast.makeText(getApplicationContext(),"Please Wait ........",Toast.LENGTH_LONG).show();
                }
            }
        });
        mMap.setOnMarkerDragListener(new GoogleMap.OnMarkerDragListener() {
            @Override
            public void onMarkerDragStart(Marker marker) {
            }

            @Override
            public void onMarkerDrag(Marker marker) {
            }

            @Override
            public void onMarkerDragEnd(Marker marker) {

                Geocoder coder = new Geocoder(getApplicationContext());
                List<Address>addressList;
                try{
                    addressList=coder.getFromLocation(marker.getPosition().latitude,marker.getPosition().longitude,1);
                    if(!addressList.isEmpty()){
                        String address="";
                        for (int i=0;i<=addressList.get(0).getMaxAddressLineIndex();i++){
                            address+=addressList.get(0).getAddressLine(i);
                        }
                        Cart.Cust_Address = address;
                    }else{
                        Toast.makeText(getApplicationContext(),"No Address For This Location",Toast.LENGTH_LONG).show();
                    }
                }catch(IOException e){
                    Toast.makeText(getApplicationContext(),"Can't get the address, Check your Network",Toast.LENGTH_LONG).show();
                }
            }
        });
    }

}