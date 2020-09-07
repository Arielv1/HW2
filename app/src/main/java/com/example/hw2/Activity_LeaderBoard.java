package com.example.hw2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Activity_LeaderBoard extends AppCompatActivity  {

    private Button leaderboard_BTN_reset;
    private Button leaderboard_BTN_back_to_main_menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__leaderboard);

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
        Fragment_Players_List scoresListFragment = Fragment_Players_List.newInstance();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.leaderboard_LAY_scores,scoresListFragment);
        transaction.commit();

        Fragment_Map fragmentMap = Fragment_Map.newInstance();
        FragmentTransaction transaction2 = getSupportFragmentManager().beginTransaction();
        transaction2.replace(R.id.leaderboard_LAY_map, fragmentMap);
        transaction2.commit();
    }


    private void resetLeaderBoardSP() {
        MySP.getInstance().putString(MySP.KEYS.LIST_OF_TOP_GAMES, MySP.VALUES.INITIAL_GAME_LIST);
    }



}