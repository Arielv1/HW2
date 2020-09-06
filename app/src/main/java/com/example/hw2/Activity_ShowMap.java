package com.example.hw2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import java.util.ArrayList;

public class Activity_ShowMap extends AppCompatActivity implements OnMapReadyCallback  {


    private Location location;
    private FusedLocationProviderClient fusedLocationProviderClient;
    private static final int REQUEST_CODE = 1;


    private LatLng[] latLng ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__show_map);

        setLatLngForAllPlayers();
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        showMapAndFocusOnLocation();
    }

    private void setLatLngForAllPlayers() {
        try {
            ArrayList<GameDetails> games =  Activity_LeaderBoard.getAllGamesFromSP().getScores();
            int numPlayers = games.size();

            latLng = new LatLng[numPlayers];

            for (int i = 0 ; i < numPlayers ; i++) {
                GameDetails current = games.get(i);
                latLng[i] = new LatLng(current.getLat(), current.getLon());
            }
        }
        catch (Exception e) {
            latLng = new LatLng[0];
            MyToaster.getInstance().showToast("No Players To Show On Map");
        }


    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        int numPlayers;
        try {
            numPlayers = Activity_LeaderBoard.getAllGamesFromSP().getScores().size();
            googleMap.animateCamera(CameraUpdateFactory.newLatLng(latLng[0]));
            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng[0], 15));
        }
        catch (Exception e) {
            numPlayers = 0;
        }
        for (int i = 0 ; i < numPlayers ; i++) {
            MarkerOptions markerOptions = new MarkerOptions()
                    .position(latLng[i]).title("Place #" + (i+1))
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
            googleMap.addMarker(markerOptions);
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    showMapAndFocusOnLocation();
                }
                break;
            default:
                break;
        }
    }

    private void showMapAndFocusOnLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE);
            return;
        }
        Task<Location> task = fusedLocationProviderClient.getLastLocation();
        task.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location currentLocation) {
                if (currentLocation != null) {
                    location = currentLocation;
                    //SupportMapFragment supportMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.show_map_FR_google_map);
                  //  supportMapFragment.getMapAsync(Activity_ShowMap.this);
                }
            }
        });
    }
}