    package com.example.hw2;

    import androidx.appcompat.app.AppCompatActivity;

    import android.content.Intent;
    import android.os.Bundle;
    import android.util.Log;
    import android.view.View;
    import android.widget.Button;
    import android.widget.ImageView;
    import android.widget.TextView;

    import com.google.gson.Gson;

    import java.util.ArrayList;
    import java.util.Collection;
    import java.util.Collections;
    import java.util.HashSet;
    import java.util.Set;

    public class Activity_Game_Over extends AppCompatActivity {

    public static final String WINNER = "WINNER";
    public static final String NUM_OF_TURNS = "NUM_OF_TURNS";

    private TopTenGames topTenGames;

    private TextView game_over_LBL_winner_details;
    private ImageView game_over_IV_winner;

    private Button game_over_BTN_new_game;
    private Button game_over_BTN_top_10;

    private MySP mySP;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__game__over);

        setUpViews();
        mySP = new MySP(this);


        Gson gson = new Gson();
        String json = getIntent().getStringExtra(Activity_Game.GAME_DETAILS);
        GameDetails gameDetails = gson.fromJson(json, GameDetails.class);

        int player = gameDetails.getWinning_player();
        int num_of_turns = gameDetails.getNum_of_turns();
        if(player == Activity_Game.PLAYER_ONE){
            game_over_IV_winner.setImageResource(getResources().getIdentifier("alliance", "drawable", "com.example.hw2"));
        }
        else {
            game_over_IV_winner.setImageResource(getResources().getIdentifier("horde", "drawable", "com.example.hw2"));
        }
        game_over_LBL_winner_details.setText("The Winner Is Player #" + player + " With " + num_of_turns + " Turns");

        ArrayList<GameDetails> top_10 = new ArrayList<GameDetails>();

        json = mySP.getString(MySP.KEYS.LIST_OF_TOP_GAMES,"");

        if(isFirstRecordedGame(json)) {

             top_10.add(gameDetails);
             topTenGames = new TopTenGames(top_10);
             Log.d("First Time", top_10.toString());
         }
         else {

            topTenGames = gson.fromJson(json, TopTenGames.class);
             ArrayList<GameDetails> games = topTenGames.getScores();

            addGameToTopTenGames(gameDetails);
            topTenGames.setScores(games);
         }
         Log.d("TopTenGames", topTenGames.toString());
         mySP.putString(MySP.KEYS.LIST_OF_TOP_GAMES, gson.toJson(topTenGames));

        game_over_BTN_new_game.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Activity_Game.class));
                finish();
            }
        });

        game_over_BTN_top_10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Activity_Top_10.class));
                finish();
            }
        });
    }

    private boolean isFirstRecordedGame(String jsonOfGame) {
        return jsonOfGame.length() == 0;

    }

    private void addGameToTopTenGames(GameDetails gameDetails) {
        ArrayList <GameDetails> gamesList = topTenGames.getScores();
        gamesList.add(gameDetails);
        Collections.sort(gamesList);


        Log.d("CHeck if", "reuslt of if is : " + (gamesList.size() > 3));
        if (gamesList.size() > 3){
            GameDetails bestGame = gamesList.get(0);
            int idx = 0;
            for (int i = 1; i < gamesList.size(); i++) {
                if (gamesList.get(i).getNum_of_turns() > bestGame.getNum_of_turns()) {
                    bestGame = gamesList.get(i);
                    idx = i;
                }
            }
            gamesList.remove(idx);
        }
        Collections.sort(gamesList);
        topTenGames.setScores(gamesList);
    }

    private void setUpViews() {
        game_over_LBL_winner_details = findViewById(R.id.game_over_LBL_winner_details);
        game_over_IV_winner = findViewById(R.id.game_over_IV_winner);
        game_over_BTN_new_game =  findViewById(R.id.game_over_BTN_new_game);
        game_over_BTN_top_10 = findViewById(R.id.game_over_BTN_top_10);
    }

    }