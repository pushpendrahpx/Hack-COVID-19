//package com.koronakiller.geolocation;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.view.View;
//
//import androidx.appcompat.app.AppCompatActivity;
//
//public class HereMapActivity extends AppCompatActivity {
//    private static final String TAG = "GetUserLocation";
////
////    private static final int LOCATION_PERMISSION = 100;
////    public static final int LOCATION_FREQ = 5000; //ms
////    private FusedLocationProviderClient locationProviderClient;
////    private Location currentLocation;
////    private LocationRequest request;
////    private LocationCallback callback;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_get_user_location);
//
////        locationProviderClient = LocationServices.getFusedLocationProviderClient(getApplicationContext());//this will provide location
////        request = LocationRequest.create(); //set perameters to our client
////        request.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
////        request.setInterval(LOCATION_FREQ);
////        callback = new LocationCallback() {
////            @Override
////            public void onLocationResult(LocationResult locationResult) {
////                super.onLocationResult(locationResult);
////                Log.i(TAG, "onLocationResult: got location");
////            }
////
////            @Override
////            public void onLocationAvailability(LocationAvailability locationAvailability) {
////                super.onLocationAvailability(locationAvailability);
////                Log.i(TAG, "onLocationAvailability: Location is available");
////            }
////        };//set methods when we able to get location
//        //all API are SET
//
//        findViewById(R.id.bGetLocation).setOnClickListener(new View.OnClickListener() {
//                                                               @Override
//                                                               public void onClick(View v) {
////                startGettingLocation();
//                                                                   Intent i = new Intent(GetUserLocation.this, HereMapActivity.class);
//                                                                   startActivity(i);
//                                                               }
//                                                           }
//        );
//
//
//    }
//
//    /* private void startGettingLocation() {
//         if ((ActivityCompat.checkSelfPermission(GetUserLocation.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)
//                 && (ActivityCompat.checkSelfPermission(GetUserLocation.this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED)) {
//             //get Location
//             locationProviderClient.requestLocationUpdates(request, callback, GetUserLocation.this.getMainLooper());
//             locationProviderClient.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
//                 @Override
//                 public void onSuccess(Location location) {
//                     currentLocation = location;
//                     Log.i(TAG, "onSuccess: latitude" + location.getLatitude());
//                     Log.i(TAG, "onSuccess: longitude" + location.getLongitude());
//                 }
//             }).addOnFailureListener(new OnFailureListener() {
//                 @Override
//                 public void onFailure(@NonNull Exception e) {
//                     Toast.makeText(GetUserLocation.this, "some ERROR occured", Toast.LENGTH_SHORT).show();
//                 }
//             });
//
//
//         } else {
//             if (ActivityCompat.shouldShowRequestPermissionRationale(GetUserLocation.this, Manifest.permission.ACCESS_FINE_LOCATION))
//                 Toast.makeText(GetUserLocation.this, "permission needed ", Toast.LENGTH_SHORT).show();
//             else if (ActivityCompat.shouldShowRequestPermissionRationale(GetUserLocation.this, Manifest.permission.ACCESS_COARSE_LOCATION))
//                 Toast.makeText(GetUserLocation.this, "permission needed ", Toast.LENGTH_SHORT).show();
//             else {
//                 ActivityCompat.requestPermissions(GetUserLocation.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, LOCATION_PERMISSION);
//             }
//         }
//     }
// */
////    @Override
//   /* public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//            startGettingLocation();
//        }
//    }
//*/
//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
////        locationProviderClient.removeLocationUpdates(callback);
//    }
//}
