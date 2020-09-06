package com.example.hw2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentTransaction;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

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
import com.google.gson.Gson;

import java.util.ArrayList;

public class Activity_LeaderBoard extends AppCompatActivity  {

    private Button leaderboard_BTN_reset;
    private Button leaderboard_BTN_back_to_main_menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__leaderboard_with_fragments);

        setUpViews();
        setUpFragments();

        leaderboard_BTN_reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetLeaderBoardSP();
                finish();
            }
        });

        leaderboard_BTN_back_to_main_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    private void setUpViews() {
        leaderboard_BTN_reset = findViewById(R.id.leaderboard_BTN_reset);
        leaderboard_BTN_back_to_main_menu = findViewById(R.id.leaderboard_BTN_back_to_main_menu);
    }

    private void setUpFragments() {
        ScoresListFragment scoresListFragment = ScoresListFragment.newInstance();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.leaderboard_LAY_scores,scoresListFragment);
        transaction.commit();

        MapFragment mapFragment = MapFragment.newInstance();
        FragmentTransaction transaction2 = getSupportFragmentManager().beginTransaction();
        transaction2.replace(R.id.leaderboard_LAY_map,mapFragment);
        transaction2.commit();
    }


    private void resetLeaderBoardSP() {
        MySP.getInstance().putString(MySP.KEYS.LIST_OF_TOP_GAMES, MySP.VALUES.INITIAL_GAME_LIST);
    }



}