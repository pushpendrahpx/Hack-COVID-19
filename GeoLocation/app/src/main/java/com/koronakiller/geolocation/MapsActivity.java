package com.koronakiller.geolocation;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationAvailability;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {
    public static final int LOCATION_FREQ = 5000; //ms
    private static final String TAG = "MapsActivity";
    private static final int LOCATION_PERMISSION = 100;
    private GoogleMap mMap;
    private FusedLocationProviderClient locationProviderClient;
    private Location currentLocation;
    private LocationRequest request;
    private LocationCallback callback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.

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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        locationProviderClient.removeLocationUpdates(callback);
    }

    private void startGettingLocation() {
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this); //call back set
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
                    LatLng CurrnetLatLng = new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude());
                    mMap.addMarker(new MarkerOptions().position(CurrnetLatLng).title("Marker in Sydney"));
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(CurrnetLatLng, 15.0f));
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

        if (currentLocation != null) {
            // Add a marker in Sydney and move the camera

        }
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
        mMap = googleMap; //this is my MAP

    }
}