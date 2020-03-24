package com.koronakiller.geolocation;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationAvailability;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback {
    public static final int LOCATION_FREQ = 5000; //ms
    private static final String TAG = "MapsActivity";
    private static final int LOCATION_PERMISSION = 100;
    private GoogleMap mMap;
    private MapView mapView;
    private FusedLocationProviderClient locationProviderClient;
    private Location currentLocation;
    private LocationRequest request;
    private LocationCallback callback;
    private LocationManager locationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        mapView = findViewById(R.id.map_view);
        Bundle mapViewBundle = null;
        if (savedInstanceState != null)
            mapViewBundle = savedInstanceState.getBundle(getResources().getString(R.string.google_maps_key));
        mapView.onCreate(mapViewBundle);
        locationProviderClient = LocationServices.getFusedLocationProviderClient(getApplicationContext());//this will provide location
        request = LocationRequest.create(); //set perameters to our client
        request.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        request.setInterval(LOCATION_FREQ);
        callback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                super.onLocationResult(locationResult);
                Log.i(TAG, "onLocationResult: got location");
            }

            @Override
            public void onLocationAvailability(LocationAvailability locationAvailability) {
                super.onLocationAvailability(locationAvailability);
                Log.i(TAG, "onLocationAvailability: Location is available");
            }
        };//set methods when we able to get location
        /* all API are SET */

        startGettingLocation();
    }

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            startGettingLocation();
        }
    }

    public void refreshLocation(View view) {
        startGettingLocation();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mapView.onStop();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();

    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        Bundle mapViewBundle = outState.getBundle(getResources().getString(R.string.google_maps_key));
        if (mapViewBundle == null) {
            mapViewBundle = new Bundle();
            outState.putBundle(getResources().getString(R.string.google_maps_key), mapViewBundle);
        }
        mapView.onSaveInstanceState(mapViewBundle);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
        locationProviderClient.removeLocationUpdates(callback);
    }

    private void startGettingLocation() {
//        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
//                .findFragmentById(R.id.map);
        //call back set
        if ((ActivityCompat.checkSelfPermission(MapsActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)
                && (ActivityCompat.checkSelfPermission(MapsActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED)) {
            //get Location
            Log.i(TAG, "startGettingLocation: ");
            locationProviderClient.requestLocationUpdates(request, callback, MapsActivity.this.getMainLooper());
            locationProviderClient.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    currentLocation = location;

                    Log.i(TAG, "onSuccess: latitude" + location.getLatitude());
                    Log.i(TAG, "onSuccess: longitude" + location.getLongitude());
                    mapView.getMapAsync(MapsActivity.this);
                    Toast.makeText(MapsActivity.this, "Stay home !!! Stay corona Free", Toast.LENGTH_SHORT).show();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(MapsActivity.this, "some ERROR occured", Toast.LENGTH_SHORT).show();
                }
            });


        } else {
            if (ActivityCompat.shouldShowRequestPermissionRationale(MapsActivity.this, Manifest.permission.ACCESS_FINE_LOCATION))
                Toast.makeText(MapsActivity.this, "permission needed ", Toast.LENGTH_SHORT).show();
            else if (ActivityCompat.shouldShowRequestPermissionRationale(MapsActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION))
                Toast.makeText(MapsActivity.this, "permission needed ", Toast.LENGTH_SHORT).show();
            else {
                ActivityCompat.requestPermissions(MapsActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, LOCATION_PERMISSION);
            }
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap; //this is my MAP
        LatLng CurrnetLatLng = new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude());
        mMap.addMarker(new MarkerOptions().position(CurrnetLatLng).title("Marker in Sydney"));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(CurrnetLatLng, 15.0f));

    }


}