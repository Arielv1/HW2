package com.example.hw2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.ArrayList;

public class Activity_LeaderBoard extends AppCompatActivity {

    public static final int SIZE = 10;
    private LinearLayout leaderboard_LL_layout;
    private final int TEXT_SIZE = 24;
    private Button btnResetLeaderBoard;
    private Button btnBackToMainMenu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__leaderboard);

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

        setLeaderBoardView(games);

        setLeaderBoardButtons();



        btnResetLeaderBoard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetLeaderBoardSP();
                finish();
            }
        });

        btnBackToMainMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    private Button createNewWeightedButton(String btnName, int weight) {
        Button btn = new Button(this);
        btn.setText(btnName);
        btn.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, weight));
        return btn;
    }

    private void setLeaderBoardButtons() {
        LinearLayout btnLayout = new LinearLayout(this);

        btnResetLeaderBoard = createNewWeightedButton("Reset LeaderBoard", 1);
        btnBackToMainMenu = createNewWeightedButton("Back To Main Menu", 1);

        btnLayout.addView(btnBackToMainMenu);
        btnLayout.addView(btnResetLeaderBoard);

        leaderboard_LL_layout.addView(btnLayout);
    }

    private void setLeaderBoardView(ArrayList<GameDetails> games) {

        for (int i = 0 ; i < games.size(); i++) {
            TextView tv = new TextView(this);
            tv.setText("#" + (i+1));
            tv.setTextSize(TEXT_SIZE);

            ImageView iv = new ImageView(this);
            if(games.get(i).getWinning_player() == Activity_Game.PLAYER_ONE) {
                iv.setImageResource(getResources().getIdentifier("alliance", "drawable", "com.example.hw2"));
            }
            else {
                iv.setImageResource(getResources().getIdentifier("horde", "drawable", "com.example.hw2"));
            }

            if (i < 9) {
                iv.setLayoutParams(new LinearLayout.LayoutParams(550, LinearLayout.LayoutParams.MATCH_PARENT));
            }
            else {
                iv.setLayoutParams(new LinearLayout.LayoutParams(475, LinearLayout.LayoutParams.MATCH_PARENT));
            }


            TextView tv2 = new TextView(this);
            tv2.setText(games.get(i).getNum_of_turns() + "");
            tv2.setTextSize(TEXT_SIZE);


            LinearLayout newLayout = new LinearLayout(this);
            newLayout.setOrientation(LinearLayout.HORIZONTAL);
            newLayout.addView(tv);
            newLayout.addView(iv);
            newLayout.addView(tv2);
            leaderboard_LL_layout.addView(newLayout);
        }
    }

    private LeaderBoard getAllGamesFromSP() {

        Gson gson = new Gson();
        String json = MySP.getInstance().getString(MySP.KEYS.LIST_OF_TOP_GAMES, MySP.VALUES.INITIAL_GAME_LIST);

        return gson.fromJson(json, LeaderBoard.class);
    }

    private void resetLeaderBoardSP() {
        MySP.getInstance().putString(MySP.KEYS.LIST_OF_TOP_GAMES, MySP.VALUES.INITIAL_GAME_LIST);
    }

    private void setUpViews() {
        leaderboard_LL_layout = findViewById(R.id.leaderboard_LL_layout);
    }
}