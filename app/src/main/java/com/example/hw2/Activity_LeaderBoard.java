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

    public static final int SIZE = 10;
    private final int TEXT_SIZE = 24;

    private LinearLayout leaderboard_LL_best_players;

    private Button leaderboard_BTN_reset;
    private Button leaderboard_BTN_back_to_main_menu;
    private Button leaderboard_BTN_show_map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity__leaderboard);
        setContentView(R.layout.activity__leaderboard_with_fragments);

        setUpViews();

        getAllGamesFromSP();

        LeaderBoard leaderBoard = getAllGamesFromSP();

        ArrayList<GameDetails> games;
        try {
            games = leaderBoard.getScores();
        }
        catch (Exception e) {
            games = new ArrayList<GameDetails>();
        }

//        showTopPlayers(games);

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

        leaderboard_BTN_show_map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), Activity_ShowMap.class));
            }
        });
    }


    private TextView createTextView(String text, int textSize) {
        TextView textView = new TextView(this);
        textView.setText(text);
        textView.setTextSize(textSize);
        return textView;

    }

    private LinearLayout addViewsToLinearLayout (LinearLayout layout, View... views) {
        for (View view : views) {
            layout.addView(view);
        }
        return layout;
    }

    private void showTopPlayers(ArrayList<GameDetails> games) {

        for (int i = 0 ; i < games.size(); i++) {

            TextView playerPlace = createTextView("#" + (i+1), TEXT_SIZE);

            ImageView playerImage = new ImageView(this);
            if(games.get(i).getWinning_player() == Activity_Game.PLAYER_ONE) {
                playerImage.setImageResource(getResources().getIdentifier("ic_alliance", "drawable", "com.example.hw2"));
            }
            else {
                playerImage.setImageResource(getResources().getIdentifier("ic_horde", "drawable", "com.example.hw2"));
            }

            if (i < 9) {
                playerImage.setLayoutParams(new LinearLayout.LayoutParams(550, 100));
            }
            else {
                playerImage.setLayoutParams(new LinearLayout.LayoutParams(510, 100));

            }

            TextView numOfTurns = createTextView(games.get(i).getNum_of_turns() + "", TEXT_SIZE);

            numOfTurns.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            LinearLayout newPlayerRecordLayout = new LinearLayout(this);

            newPlayerRecordLayout.setOrientation(LinearLayout.HORIZONTAL);

            newPlayerRecordLayout = addViewsToLinearLayout(newPlayerRecordLayout, playerPlace, playerImage, numOfTurns);

            leaderboard_LL_best_players.addView(newPlayerRecordLayout);
        }
    }

    public static LeaderBoard getAllGamesFromSP() {

        Gson gson = new Gson();
        String json = MySP.getInstance().getString(MySP.KEYS.LIST_OF_TOP_GAMES, MySP.VALUES.INITIAL_GAME_LIST);

        return gson.fromJson(json, LeaderBoard.class);
    }

    private void resetLeaderBoardSP() {
        MySP.getInstance().putString(MySP.KEYS.LIST_OF_TOP_GAMES, MySP.VALUES.INITIAL_GAME_LIST);
    }

    private void setUpViews() {

        leaderboard_BTN_reset = findViewById(R.id.leaderboard_BTN_reset);
        leaderboard_BTN_show_map = findViewById(R.id.leaderboard_BTN_show_map);
        leaderboard_BTN_back_to_main_menu = findViewById(R.id.leaderboard_BTN_back_to_main_menu);
        leaderboard_LL_best_players = findViewById(R.id.leaderboard_LL_best_players);

        ScoresListFragment scoresListFragment = ScoresListFragment.newInstance();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.leaderboard_LYT_scores,scoresListFragment);
        transaction.commit();

        MapFragment mapFragment = MapFragment.newInstance();
        FragmentTransaction transaction2 = getSupportFragmentManager().beginTransaction();
        transaction2.replace(R.id.leaderboard_LYT_map,mapFragment);
        transaction2.commit();

    }

}