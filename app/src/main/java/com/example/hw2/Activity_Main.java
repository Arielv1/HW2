package com.example.hw2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class Activity_Main extends AppCompatActivity {

    private Button main_BTN_start_game;
    private Button main_BTN_leaderboard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__main);

        setUpViews();

        main_BTN_start_game.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Activity_Game.class));
            }
        });

        main_BTN_leaderboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Activity_LeaderBoard.class));
            }
        });

    }

    private void setUpViews() {
        main_BTN_start_game = findViewById(R.id.main_BTN_start_game);
        main_BTN_leaderboard = findViewById(R.id.main_BTN_leaderboard);
    }

}
