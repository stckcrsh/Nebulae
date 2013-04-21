package com.zoom.map;


import java.io.IOException;
import java.util.List;


import android.content.Context;

import android.content.Intent;

import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import android.view.View.OnClickListener;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnInfoWindowClickListener;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

	
public class MainActivity extends FragmentActivity 
	{
	//create the map and location manager for screen
	private GoogleMap map = null;
	private Location location;
	private LocationManager locationManager;
	private String provider;	
	private MarkerOptions markerOptions;
	LatLng latLng;
	
	private static final String TAG = "MapsActivity";

	@Override
    protected void onCreate(Bundle savedInstanceState) 
	{	 
        super.onCreate(savedInstanceState);	    
        setContentView(R.layout.activity_main);
        
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Log.d(TAG, "Location Manager get");
        
        Criteria criteria = new Criteria();
        provider = locationManager.getBestProvider(criteria, false);
        location = locationManager.getLastKnownLocation(provider);
        
        if(location != null)
        	{
        		//System.out.println("Provider " + provider + "has been selected.");
        		onLocationChanged(location);
        	}        
        
        //setting up the map
        map = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                .getMap();     
        
        // Getting reference to btn_find of the layout activity_main
        Button btn_find = (Button) findViewById(R.id.btn_find);
 
        // Defining button click event listener for the find button
        OnClickListener findClickListener = new OnClickListener() {
            @Override
            public void onClick(View v) {
                // Getting reference to EditText to get the user input location
                EditText etLocation = (EditText) findViewById(R.id.et_location);
 
                // Getting user input location
                String location = etLocation.getText().toString();
 
                if(location!=null && !location.equals("")){
                    new GeocoderTask().execute(location);
                }
            }
        };
 
        // Setting button click event listener for the find button
        btn_find.setOnClickListener(findClickListener);
 
    
        
        
        
        map.setOnInfoWindowClickListener(new OnInfoWindowClickListener() 
        {

			@Override
			public void onInfoWindowClick(Marker marker)
			{
				Log.d(TAG, "On InfoWindowClick Error!");
					// TODO Auto-generated method stub
					Toast.makeText(getBaseContext(), marker.getTitle(), Toast.LENGTH_LONG).show();
					
					Intent intent = new Intent(MainActivity.this, PhotoViewActivity.class);
				    //intent.putExtra(provider, location);
				    startActivity(intent);
					//startActivity(new Intent(MainActivity.this, PhotoViewActivity.class));
			}        	
        });   
        
        //Animating camera smoothly to fixed position        	
        
        	if(savedInstanceState == null)
        		{        	
        			CameraUpdate center = CameraUpdateFactory.newLatLng(new LatLng(41.896731001085115, -87.62794017791748));        	
        			CameraUpdate zoom = CameraUpdateFactory.zoomTo(5);        	
        			map.moveCamera(center);
        			map.animateCamera(zoom); 
        			Log.d(TAG, "Camera Animate Start!");
        		}
        	
        	
        //testing myLocation features
        map.setMyLocationEnabled(true); 
       map.setOnMyLocationChangeListener(null);        
        
	}
	
	private void addMarker(GoogleMap map, double lat, double lon,
			int title, int snippet, int hue) {
			map.addMarker(new MarkerOptions().position(new LatLng(lat, lon))
			.title(getString(title))
			.snippet(getString(snippet))
			.icon(BitmapDescriptorFactory.defaultMarker(hue)));
			}
	
	/**********************************************
	 * stuff for user location finding and changing
	 *********************************************/
	
	public void onLocationChanged(Location location)
	{
		int lat = (int) (location.getLatitude());
		int lng = (int) (location.getLongitude());
		//lat and lng field set text
		Log.d(TAG, "On Location Changed Error!");
	}
	
	public void onMyLocationChange(Location lastKnownLocation)
	{
		Log.d(getClass().getSimpleName(),
				String.format("%f:%f", lastKnownLocation.getLatitude(),lastKnownLocation.getLongitude()));
	}
	
	/************************
	 * Marker click tests
	 ***********************/
	public boolean onMarkerClick(Marker marker)
	{
		Log.d(TAG, "on Marker click Error!");
		//Toast.makeText(this, marker.getTitle(), Toast.LENGTH_LONG).show();
		
		return(false);
	}	
	
	public boolean onCreateOptionsMenu(Menu menu) 
    {
    	Log.d(TAG, "Options Error!");
        // Inflate the menu;
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
	
	private class GeocoderTask extends AsyncTask<String, Void, List<Address>>
	{
		 
        @Override
        protected List<Address> doInBackground(String... locationName) {
            // Creating an instance of Geocoder class
            Geocoder geocoder = new Geocoder(getBaseContext());
            List<Address> addresses = null;
 
            try {
                // Getting a maximum of 3 Address that matches the input text
                addresses = geocoder.getFromLocationName(locationName[0], 3);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return addresses;
        }
        @Override
        protected void onPostExecute(List<Address> addresses) 
        {
 
            if(addresses==null || addresses.size()==0){
                Toast.makeText(getBaseContext(), "No Location found", Toast.LENGTH_SHORT).show();
            }
 
            // Clears all the existing markers on the map
 
            // Adding Markers on Google Map for each matching address
            for(int i=0;i<addresses.size();i++){
 
                Address address = (Address) addresses.get(i);
 
                // Creating an instance of GeoPoint, to display in Google Map
                latLng = new LatLng(address.getLatitude(), address.getLongitude());
 
                String addressText = String.format("%s, %s",
                address.getMaxAddressLineIndex() > 0 ? address.getAddressLine(0) : "",
                address.getCountryName());
 
                markerOptions = new MarkerOptions();
                markerOptions.position(latLng);
                markerOptions.title(addressText);
                markerOptions.snippet("#hob, #chicago, #rhcp");
                markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_launcher));
                
 
                map.addMarker(markerOptions);
 
                // Locate the first location
                if(i==0)
                    map.animateCamera(CameraUpdateFactory.newLatLng(latLng));
            }
        }  	 
    
	}
	}


 
