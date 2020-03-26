package com.koronakiller.geolocation;

import android.Manifest;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingClient;
import com.google.android.gms.location.GeofencingRequest;
import com.google.android.gms.location.LocationAvailability;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.koronakiller.geolocation.utils.AppNotificationManager;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback {
    public static final int LOCATION_FREQ = 5000; //ms
    private static final String TAG = "MapsActivity";
    private static final int LOCATION_PERMISSION = 100;
    private static final String GEO_FENCE_ID = "USER_HOME";
    private static final float RADIUS_IN_METER = 20f;
    private static final float ZOOM_LEVEL = 19f;
    private static final int USER_DWELL = 1;
    private static final int BACKGROUND_REQUEST_CODE = 100;
    private GoogleMap mMap;
    private MapView mapView;
    private FusedLocationProviderClient locationProviderClient;
    private Location currentLocation;
    private LocationRequest request;
    private LocationCallback callback;
    private GeofencingClient client;
    private Geofence geofence;
    private PendingIntent geofencePendingIntent;
    private TextView tv;
    private AppNotificationManager notificationManager;

//    private GoogleApiClient client;

    //TODO ask permission  ACCESS_BACKGROUND_LOCATION
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        mapView = findViewById(R.id.map_view);
        tv = findViewById(R.id.tv);
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

    private GeofencingRequest getGeofencingRequest() {
        GeofencingRequest.Builder builder = new GeofencingRequest.Builder();
        builder.addGeofence(geofence);
        builder.setInitialTrigger(GeofencingRequest.INITIAL_TRIGGER_ENTER);
//        builder.setInitialTrigger(GeofencingRequest.INITIAL_TRIGGER_EXIT);
        return builder.build();
    }

    private PendingIntent getGeofencePendingIntent() {
        if (geofencePendingIntent != null) {
            return geofencePendingIntent;
        }
        Intent i = new Intent(MapsActivity.this, MyIntentService.class);
        geofencePendingIntent = PendingIntent.getService(getApplicationContext(), USER_DWELL, i, PendingIntent.FLAG_UPDATE_CURRENT);
        return geofencePendingIntent;
    }

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            startGettingLocation();
        }
    }

    public void refreshLocation(View view) {
        startGettingLocation();
        mMap.addMarker(new MarkerOptions().position(new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude())).title("Marker in Sydney"));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude()), ZOOM_LEVEL));
        tv.append("Lat : " + currentLocation.getLatitude() + "\n");
        tv.append("Long : " + currentLocation.getLongitude() + "\n");
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
        client.removeGeofences(getGeofencePendingIntent());
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
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(CurrnetLatLng, ZOOM_LEVEL));
    }

    public void createGeofence(View view) {
        setUpGeoFence();
//        notificationManager = AppNotificationManager.getInstance(getApplicationContext());
//        notificationManager.registerNotificationChannel();
//        notificationManager.triggerNotification();
    }


    private void setUpGeoFence() {
        if ((Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) && ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_BACKGROUND_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(MapsActivity.this, Manifest.permission.ACCESS_BACKGROUND_LOCATION)) {
                ActivityCompat.requestPermissions(MapsActivity.this, new String[]{Manifest.permission.ACCESS_BACKGROUND_LOCATION}, BACKGROUND_REQUEST_CODE);
            } else {
                // Show an explanation to the user as to why your app needs the
                // permission. Display the explanation *asynchronously* -- don't block
                // this thread waiting for the user's response!
            }
        } else {
            // Background location runtime permission already granted.
            // You can now call geofencingClient.addGeofences().
            client = LocationServices.getGeofencingClient(getApplicationContext());
            geofence = new Geofence.Builder().setRequestId(GEO_FENCE_ID).
                    setCircularRegion(currentLocation.getLatitude(), currentLocation.getLongitude(), RADIUS_IN_METER).
                    setExpirationDuration(Geofence.NEVER_EXPIRE).
                    setTransitionTypes(Geofence.GEOFENCE_TRANSITION_ENTER).build();

            client.addGeofences(getGeofencingRequest(), getGeofencePendingIntent()).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.d(TAG, "onSuccess: Fence added success fully ready to request to the server" + currentLocation.getLatitude() + " " + currentLocation.getLatitude());
                    mMap.addCircle(new CircleOptions().center(new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude()))
                            .radius(RADIUS_IN_METER)
                            .strokeWidth(5.0f)
                            .strokeColor(Color.BLUE));
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.d(TAG, "onFailure: error occurred " + e.toString());
                }
            });

        }

    }
}


