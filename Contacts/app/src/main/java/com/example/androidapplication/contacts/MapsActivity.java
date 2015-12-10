package com.example.androidapplication.contacts;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

public class MapsActivity extends FragmentActivity implements View.OnClickListener{
    //declare button and edit text
    Button SearchLocation,ZoomIN,ZoomOUT,ChangeMapView;
    EditText LocationEditText;

    private GoogleMap mMap; // Might be null if Google Play services APK is not available.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        SearchLocation =(Button)findViewById(R.id.buttonSeachLocation);
        //initialize the buttons to the appropriate id
        ZoomIN =(Button)findViewById(R.id.buttonZoomIn);
        ZoomOUT =(Button)findViewById(R.id.buttonZoomOut);
        ChangeMapView =(Button)findViewById(R.id.buttonChangeMapView);
        //initialize the EditText to the appropriate id
        LocationEditText=(EditText)findViewById(R.id.editTextSearchLocation);
        setUpMapIfNeeded();
        //set the listener on buttons
        SearchLocation.setOnClickListener(this);
        ZoomIN.setOnClickListener(this);
        ZoomOUT.setOnClickListener(this);
        ChangeMapView.setOnClickListener(this);

        //get the intent from the edit contact activity
        Intent myIntent = getIntent();

        if(myIntent.getStringExtra("address") != ""){//check to make sure the address passed is not empty
            List<Address> addresses = null;//create a list to store the address
            Geocoder geocoder = new Geocoder(this);//create the geocoder object
            try{//pass the intent address
                addresses = geocoder.getFromLocationName(myIntent.getStringExtra("address"), 1);
            }//end try
            catch (Exception e){
                e.printStackTrace();
            }//end catch

            Address address = addresses.get(0);//get the first element
            LatLng latLng1 = new LatLng(address.getLatitude(),address.getLongitude());//get longitude and latitude of the address
            mMap.addMarker(new MarkerOptions().position(latLng1).title("Marker"));//set the marker on the location
            mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng1));
            // Move the camera instantly to location with a zoom of 15.
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng1, 15));//show the map at zoom level 15
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.buttonSeachLocation){//if he search address button is pressed
            String location = LocationEditText.getText().toString();
            List<Address> addresses = null;

            if (location.equals("")) {//check to make sure that the edit text is not empty
                Toast.makeText(this, "Enter Address", Toast.LENGTH_SHORT).show();
            }
            else{//once its not empty than
                Geocoder geocoder = new Geocoder(this);//create geocoder object
                try{
                    addresses=geocoder.getFromLocationName(location,1);//pass the inputed address
                }//end try
                catch (Exception e){
                    e.printStackTrace();
                }//end catch

                Address address = addresses.get(0);//get the first element
                LatLng latLng = new LatLng(address.getLatitude(),address.getLongitude());//get longitude and latitude of the address
                mMap.addMarker(new MarkerOptions().position(latLng).title("Marker"));//set the marker on the location
                mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
                // Move the camera instantly to location with a zoom of 15.
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));//show the map at zoom level 15
                // Zoom in, animating the camera.
                //mMap.animateCamera(CameraUpdateFactory.zoomTo(14), 2000, null);

            }//end else

        }//end if buttonSeachLocation
        else if(v.getId() == R.id.buttonZoomIn){//if the zoom in button is pressed than
            mMap.animateCamera(CameraUpdateFactory.zoomIn());//zoom in
        }
        else if(v.getId() == R.id.buttonZoomOut){// if the zoom out button is pressed than
            mMap.animateCamera(CameraUpdateFactory.zoomOut());//zoom out
        }
        else if(v.getId() == R.id.buttonChangeMapView){//if the change map view button is pressed than
            if(mMap.getMapType()==GoogleMap.MAP_TYPE_NORMAL){//change the map view to satellite view if it is at normal view
                mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
            }
            else{
                mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
            }
        }

    }//end onClick

    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                    .getMap();
            mMap.setMyLocationEnabled(true);
            // Check if we were successful in obtaining the map.
            if (mMap != null) {
                //setUpMap();

            }
        }
    }//end if setUpMapIfNeeded

    /*
    private void setUpMap() {
        mMap.addMarker(new MarkerOptions().position(new LatLng(53.350478, -6.262878)).title("Marker"));
        mMap.setMyLocationEnabled(true);
    }
    */

}
