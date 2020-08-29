package com.example.hw2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Collections;

public class Activity_Game_Over extends AppCompatActivity  {

    public static final String WINNER = "WINNER";
    public static final String NUM_OF_TURNS = "NUM_OF_TURNS";



    private LeaderBoard topTenGames;

    private TextView game_over_LBL_winner_details;
    private ImageView game_over_IV_winner;

    private Button game_over_BTN_new_game;
    private Button game_over_BTN_leaderboard;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__game__over);

        setUpViews();


        Gson gson = new Gson();
        String json = getIntent().getStringExtra(Activity_Game.GAME_DETAILS);

        final GameDetails gameDetails = gson.fromJson(json, GameDetails.class);

        int player = gameDetails.getWinning_player();
        int num_of_turns = gameDetails.getNum_of_turns();
        if (player == Activity_Game.PLAYER_ONE) {
            game_over_IV_winner.setImageResource(getResources().getIdentifier("alliance", "drawable", "com.example.hw2"));
        } else {
            game_over_IV_winner.setImageResource(getResources().getIdentifier("horde", "drawable", "com.example.hw2"));
        }
        game_over_LBL_winner_details.setText("The Winner Is Player #" + player + " With " + num_of_turns + " Turns");

        ArrayList<GameDetails> games = new ArrayList<GameDetails>();
        json = MySP.getInstance().getString(MySP.KEYS.LIST_OF_TOP_GAMES, MySP.VALUES.INITIAL_GAME_LIST);

        if (isFirstRecordedGame(json)) {

            games.add(gameDetails);
            topTenGames = new LeaderBoard(games);
            Log.d("First Time", games.toString());
        } else {

            topTenGames = gson.fromJson(json, LeaderBoard.class);
            games = topTenGames.getScores();

            addGameToTopTenGames(gameDetails);
            topTenGames.setScores(games);
        }
        Log.d("TopTenGames", topTenGames.toString());
        MySP.getInstance().putString(MySP.KEYS.LIST_OF_TOP_GAMES, gson.toJson(topTenGames));

        game_over_BTN_new_game.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Activity_Game.class));
                finish();
            }
        });

        game_over_BTN_leaderboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Activity_LeaderBoard.class));
                finish();
            }
        });

        game_over_IV_winner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Activity_ShowMap.class);
                intent.putExtra(Activity_ShowMap.LAT, gameDetails.getLat());
                intent.putExtra(Activity_ShowMap.LON, gameDetails.getLon());
                startActivity(intent);
            }
        });
    }

    private boolean isFirstRecordedGame(String jsonOfGame) {
        return jsonOfGame.length() == 0;

    }

    private void addGameToTopTenGames(GameDetails gameDetails) {
        ArrayList<GameDetails> games = topTenGames.getScores();
        games.add(gameDetails);
        Collections.sort(games);


        Log.d("CHeck if", "reuslt of if is : " + (games.size() > Activity_LeaderBoard.SIZE));
        if (games.size() > Activity_LeaderBoard.SIZE) {
            GameDetails bestGame = games.get(0);
            int idx = 0;
            for (int i = 1; i < games.size(); i++) {
                if (games.get(i).getNum_of_turns() > bestGame.getNum_of_turns()) {
                    bestGame = games.get(i);
                    idx = i;
                }
            }
            games.remove(idx);
        }
        Collections.sort(games);
        topTenGames.setScores(games);
    }

    private void setUpViews() {
        game_over_LBL_winner_details = findViewById(R.id.game_over_LBL_winner_details);
        game_over_IV_winner = findViewById(R.id.game_over_IV_winner);
        game_over_BTN_new_game = findViewById(R.id.game_over_BTN_new_game);
        game_over_BTN_leaderboard = findViewById(R.id.game_over_BTN_leaderboard);

    }


}