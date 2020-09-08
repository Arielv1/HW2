package com.example.hw2;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
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


public class Fragment_Map extends Fragment implements OnMapReadyCallback  {

    private Location location;
    private FusedLocationProviderClient fusedLocationProviderClient;
    private final int ZOOM_VALUE = 13;

    private LatLng[] latLng ;

    public Fragment_Map() {
    }

    public static Fragment_Map newInstance() {
        Fragment_Map fragment = new Fragment_Map();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_map, null, false);

        SupportMapFragment mapFragment = (SupportMapFragment) this.getChildFragmentManager().findFragmentById(R.id.fragment_map_LAY_map);
        mapFragment.getMapAsync(this);
        setLatLngForAllPlayers();
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getContext());

        return view;
    }

    private void setLatLngForAllPlayers() {
        ArrayList<GameDetails> games =  Utils.getInstance().getAllGamesFromSP();

        if (games.size() != 0) {
            int numPlayers = games.size();

            latLng = new LatLng[numPlayers];

            for (int i = 0 ; i < numPlayers ; i++) {
                GameDetails current = games.get(i);
                latLng[i] = new LatLng(current.getLat(), current.getLon());
            }
        } else {
            MyToaster.getInstance().showToast("No Players To Show On Map");
        }

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        // Get the number of players (markers) to display on the map
        int numPlayers;
        try {
            numPlayers = Utils.getInstance().getAllGamesFromSP().size();

            // Focuses the camera onto the the best player (always the first)
            googleMap.animateCamera(CameraUpdateFactory.newLatLng(latLng[0]));
            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng[0], ZOOM_VALUE));
        }
        catch (Exception e) {
            numPlayers = 0;
        }

        // Display all the players locations as markers on the map
        for (int i = 0 ; i < numPlayers ; i++) {
            MarkerOptions markerOptions = new MarkerOptions()
                    .position(latLng[i]).title("Place #" + (i+1))
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
            googleMap.addMarker(markerOptions);
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        // Check permissions
        switch (requestCode) {
            case Utils.REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    setLatLngForAllPlayers();
                }
                break;
            default:
                break;
        }
    }
}