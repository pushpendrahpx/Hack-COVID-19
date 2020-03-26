package com.koronakiller.stayqurantine;

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
import com.koronakiller.stayqurantine.models.User;
import com.koronakiller.stayqurantine.utils.HttpHelper;
import com.koronakiller.stayqurantine.utils.RequestPackage;

public class HomeActivity extends AppCompatActivity implements OnMapReadyCallback {
    private static final String TAG = HomeActivity.class.getSimpleName();
    private static final long LOCATION_FREQ = 5000;
    private static final float ZOOM_LEVEL = 15f;
    private static final int LOCATION_PERMISSION = 100;
    private static final String GEO_FENCE_ID = "USER_HOME";
    private static final float RADIUS_IN_METER = 20f;
    private static final int USER_ENTRY = 1;
    private static final int BACKGROUND_REQUEST_CODE = 100;
    private static final String KEY_MAP = "KEY_MAP";
    private static final String LOCATION_ENDPOINT ="";

    private TextView tvName, tvStatus, tvScore;
    private MapView mMap;
    private GoogleMap gMap;
    private LocationRequest request;
    private FusedLocationProviderClient locationProviderClient;
    private LocationCallback callback;
    private Location currentLocation;
    private GeofencingClient client;
    private PendingIntent geofencePendingIntent;
    private Geofence geofence;
    private TextView tvPhone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Bundle mapViewBundle = null;
        mMap = findViewById(R.id.mapView);
        if (savedInstanceState != null)
            mapViewBundle = savedInstanceState.getBundle(getResources().getString(R.string.google_maps_key));
        mMap.onCreate(mapViewBundle);
        User user = getIntent().getParcelableExtra(LogIn.KEY_USER);//FIXME
        tvPhone = findViewById(R.id.tvUserName);
        tvPhone.setText(user.getPhoneNo());
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
        client = LocationServices.getGeofencingClient(getApplicationContext());
//        if (mapViewBundle != null) {
//            gMap = mapViewBundle.getParcelable(KEY_MAP);
//        } else
        startGettingLocation();

    }

    private void startGettingLocation() {
        if ((ActivityCompat.checkSelfPermission(HomeActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)
                && (ActivityCompat.checkSelfPermission(HomeActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED)) {
            locationProviderClient.requestLocationUpdates(request, callback, HomeActivity.this.getMainLooper());
            locationProviderClient.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    currentLocation = location;
                    mMap.getMapAsync(HomeActivity.this);
                    Toast.makeText(HomeActivity.this, "Stay home !!! Stay corona Free", Toast.LENGTH_SHORT).show();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(HomeActivity.this, "some ERROR occured", Toast.LENGTH_SHORT).show();
                }
            });


        } else {
            if (ActivityCompat.shouldShowRequestPermissionRationale(HomeActivity.this, Manifest.permission.ACCESS_FINE_LOCATION))
                Toast.makeText(HomeActivity.this, "permission needed ", Toast.LENGTH_SHORT).show();
            else if (ActivityCompat.shouldShowRequestPermissionRationale(HomeActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION))
                Toast.makeText(HomeActivity.this, "permission needed ", Toast.LENGTH_SHORT).show();
            else {
                ActivityCompat.requestPermissions(HomeActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 3);
            }
        }
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        gMap = googleMap; //this is my MAP
        LatLng CurrnetLatLng = new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude());
        gMap.addMarker(new MarkerOptions().position(CurrnetLatLng).title("Home"));
        gMap.animateCamera(CameraUpdateFactory.newLatLngZoom(CurrnetLatLng, ZOOM_LEVEL));

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
        Intent i = new Intent(HomeActivity.this, MyIntentService.class);
        geofencePendingIntent = PendingIntent.getService(getApplicationContext(), USER_ENTRY, i, PendingIntent.FLAG_UPDATE_CURRENT); //FIXME
        return geofencePendingIntent;
    }

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            startGettingLocation();
        }
    }

    public void refreshLocation(View view) {

        gMap.addMarker(new MarkerOptions().position(new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude())).title("Marker in Sydney"));
        gMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude()), ZOOM_LEVEL));

    }

    public void createGeofence(View view) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                setUpGeoFence();
            }
        }).start();
        Toast.makeText(this, "Please wait ....", Toast.LENGTH_SHORT).show();
    }


    private void setUpGeoFence() {

        if ((Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) && ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_BACKGROUND_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(HomeActivity.this, new String[]{Manifest.permission.ACCESS_BACKGROUND_LOCATION}, BACKGROUND_REQUEST_CODE);
            //            if (ActivityCompat.shouldShowRequestPermissionRationale(HomeActivity.this, Manifest.permission.ACCESS_BACKGROUND_LOCATION)) {
//                ActivityCompat.requestPermissions(HomeActivity.this, new String[]{Manifest.permission.ACCESS_BACKGROUND_LOCATION}, BACKGROUND_REQUEST_CODE);
//            } else {
//                ActivityCompat.requestPermissions(HomeActivity.this, new String[]{Manifest.permission.ACCESS_BACKGROUND_LOCATION}, LOCATION_PERMISSION);
            // Show an explanation to the user as to why your app needs the
            // permission. Display the explanation *asynchronously* -- don't block
            // this thread waiting for the user's response!

        } else {
            // Background location runtime permission already granted.
            // You can now call geofencingClient.addGeofences().
            Log.d(TAG, "Fencing ");
            client = LocationServices.getGeofencingClient(getApplicationContext());
            geofence = new Geofence.Builder().setRequestId(GEO_FENCE_ID).
                    setCircularRegion(currentLocation.getLatitude(), currentLocation.getLongitude(), RADIUS_IN_METER).
                    setExpirationDuration(Geofence.NEVER_EXPIRE).
                    setTransitionTypes(Geofence.GEOFENCE_TRANSITION_ENTER | Geofence.GEOFENCE_TRANSITION_EXIT).build();

            client.addGeofences(getGeofencingRequest(), getGeofencePendingIntent()).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.d(TAG, "onSuccess: Fence added success fully ready to request to the server" + currentLocation.getLatitude() + " " + currentLocation.getLatitude());
                    gMap.addCircle(new CircleOptions().center(new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude()))
                            .radius(RADIUS_IN_METER)
                            .strokeWidth(5.0f)
                            .strokeColor(Color.BLUE));
                    RequestPackage requestPackage = new RequestPackage();
                    requestPackage.setEndPoint(LOCATION_ENDPOINT);
                    requestPackage.setMethod("POST");
                    requestPackage.setParams(,);

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.d(TAG, "onFailure: error occurred " + e.toString());
                }
            });

        }

    }

    @Override
    protected void onStart() {
        super.onStart();
        mMap.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mMap.onResume();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mMap.onStop();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mMap.onPause();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mMap.onDestroy();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        Bundle mapViewBundle = outState.getBundle(getResources().getString(R.string.google_maps_key));
        if (mapViewBundle == null) {
            mapViewBundle = new Bundle();
//            mapViewBundle.putParcelable(KEY_MAP, gMap);
            outState.putBundle(getResources().getString(R.string.google_maps_key), mapViewBundle);
        }
        mMap.onSaveInstanceState(mapViewBundle);
    }


    public void logOut(View view) {
        new User(HomeActivity.this).removeUser();
        Intent intent = new Intent(HomeActivity.this, LogIn.class);
        startActivity(intent);
        finish();
    }

}
