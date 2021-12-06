package com.example.loacationsaver.model.locations;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import com.example.loacationsaver.R;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import java.util.List;
import java.util.Locale;

public class LocationModel {
    Context context;
    SupportMapFragment supportMapFragment;
    GoogleApiAvailability api;
    FusedLocationProviderClient client;
    LocationRequest request;
    Marker marker;

    public LocationModel(Context context) {
        this.context = context;
        supportMapFragment = (SupportMapFragment) ((FragmentActivity) context).getSupportFragmentManager().findFragmentById(R.id.map_fragment);
        client = LocationServices.getFusedLocationProviderClient(context);
    }

    private LocationRequest getConfig() {
        request = LocationRequest.create();
        request.setInterval(10000);
        request.setFastestInterval(3000);
        request.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        return request;
    }

    public boolean PlayServiceAvailable() {
        api = GoogleApiAvailability.getInstance();
        int res = api.isGooglePlayServicesAvailable(context);
        return res == ConnectionResult.SUCCESS;

    }

    public void CheckPrerequisite() {
        if (PlayServiceAvailable()) {
            StartLocationService();
        } else {
            Toast.makeText(context, "Google Play Service Not Available", Toast.LENGTH_SHORT).show();
        }
    }


    private void StartLocationService() {
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 100);
        }

        Task<Location> current = client.getLastLocation();
        current.addOnSuccessListener(location -> supportMapFragment.getMapAsync(googleMap -> {
            LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
            AddMarkerToPos(latLng, googleMap);
        }));
        KeepUpdatingLocation();
    }


    public void getUpdates() {
        StartLocationService();
    }

    public void AddMarkerToPos(LatLng latLng, GoogleMap googleMap) {
        if (marker != null) {
            marker.remove();
        }
        MarkerOptions options = new MarkerOptions().position(latLng)
                .title("You are here");
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 18));
        marker = googleMap.addMarker(options);
    }

    public void KeepUpdatingLocation() {
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Log.d("Error here","Returning form permission");
            return;
        }
        Log.d("Error","Near Callback");
        client.requestLocationUpdates(getConfig(), new LocationCallback() {
            @Override
            public void onLocationResult(@NonNull LocationResult locationResult) {
                super.onLocationResult(locationResult);
                supportMapFragment.getMapAsync(googleMap -> {
                    for(Location locationUpdate:locationResult.getLocations()){
                        LatLng latLng = new LatLng(locationUpdate.getLatitude(), locationUpdate.getLongitude());
                        AddMarkerToPos(latLng, googleMap);
                    }
                });
            }
        }, null);
    }

    public LatLng GetLatLng() {
        return marker.getPosition();
    }

    public String GetAddress(LatLng latLng){
        String strAdd = "";
        Geocoder geocoder = new Geocoder(context, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1);
            if (addresses != null) {
                Address returnedAddress = addresses.get(0);
                StringBuilder strReturnedAddress = new StringBuilder();
                for (int i = 0; i <= returnedAddress.getMaxAddressLineIndex(); i++) {
                    strReturnedAddress.append(returnedAddress.getAddressLine(i)).append("\n");
                }
                strReturnedAddress.append(returnedAddress.getLocality()).append("\n");
                strReturnedAddress.append(returnedAddress.getPostalCode()).append("\n");
                strReturnedAddress.append(returnedAddress.getCountryName());
                strAdd = strReturnedAddress.toString();
            } else {
                return "No Address Found";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "Something went wrong";
        }
        return strAdd;
    }
}
