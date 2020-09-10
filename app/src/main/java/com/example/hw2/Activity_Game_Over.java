package com.example.hw2;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.Collections;


public class Activity_Game_Over extends AppCompatActivity {

    private LeaderBoard leaderBoard;

    private TextView game_over_LBL_winner_details;
    private ImageView game_over_IV_winner;

    private Button game_over_BTN_new_game;
    private Button game_over_BTN_leaderboard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__game__over);

        setUpViews();

        // Get the current GameDetails from Activity_Game
        Gson gson = new Gson();
        String json = getIntent().getStringExtra(MySP.KEYS.GAME_DETAILS);

        final GameDetails gameDetails = gson.fromJson(json, GameDetails.class);

        // Display the winners' players' stats - image, num of turns, player numb
        displayWinnerDetails(gameDetails);

        // Get all the games details that are in the leaderboard in string format
        // Will be empty for first time running or after resetting the leaderboard
        json = MySP.getInstance().getString(MySP.KEYS.LIST_OF_TOP_GAMES, MySP.VALUES.INITIAL_GAME_LIST);
        ArrayList<GameDetails> games = new ArrayList<GameDetails>();

        // If 'gameDetails' is the first game - add it to the leaderboard
        // Otherwise need to decide it's place when comparing to all other games
        if (isFirstRecordedGame(json)) {
            games.add(gameDetails);
            leaderBoard = new LeaderBoard(games);
        } else {

            addGameToLeaderBoard(gameDetails);
        }

        // Puts all the games details in SP (string format)
        updateListOfPlayersSP();


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
    }

    private void setUpViews() {
        game_over_LBL_winner_details = findViewById(R.id.game_over_LBL_winner_details);
        game_over_IV_winner = findViewById(R.id.game_over_IV_winner);
        game_over_BTN_new_game = findViewById(R.id.game_over_BTN_new_game);
        game_over_BTN_leaderboard = findViewById(R.id.game_over_BTN_leaderboard);

    }



    private void displayWinnerDetails(GameDetails gameDetails) {
        int player = gameDetails.getWinning_player();
        int num_of_turns = gameDetails.getNum_of_turns();
        if (player == MySP.VALUES.PLAYER_ONE) {
            game_over_IV_winner.setImageResource(R.drawable.ic_alliance);
        } else {
            game_over_IV_winner.setImageResource(R.drawable.ic_horde);
        }
        game_over_LBL_winner_details.setText("The Winner Is Player #" + player + " With " + num_of_turns + " Turns");
    }

    private boolean isFirstRecordedGame(String jsonOfGame) {
        return jsonOfGame.length() == 0;

    }

    private void addGameToLeaderBoard(GameDetails gameDetails) {
        // Get the current leaderboard and all the games from SP
        leaderBoard = Utils.getInstance().getLeaderBoardFromSP();
        ArrayList<GameDetails> games = Utils.getInstance().getAllGamesFromSP();

        // Adds the new game to the games list, sorts the list by number of turns it took to win
        games.add(gameDetails);
        Collections.sort(games);

        // If number of games exceeds the pre-determined size, remove the last game since it'll
        // have the highest number of turns it took to win (result of the sort)
        if (games.size() > MySP.VALUES.SIZE) {
            games.remove(games.get(games.size() - 1));

        }


        leaderBoard.setScores(games);
    }


    private void updateListOfPlayersSP() {
        Gson gson = new Gson();
        MySP.getInstance().putString(MySP.KEYS.LIST_OF_TOP_GAMES, gson.toJson(leaderBoard));
    }

}
