package com.example.hw2;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.google.gson.Gson;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import java.util.Random;

public class Activity_Game extends AppCompatActivity {

    private ProgressBar game_PB_p1_hp;
    private ProgressBar game_PB_p2_hp;

    private Button game_BTN_roll;
    private TextView game_LBL_timer;

    private Button game_BTN_p1_attack_1;
    private Button game_BTN_p1_attack_2;
    private Button game_BTN_p1_attack_3;

    private Button game_BTN_p2_attack_1;
    private Button game_BTN_p2_attack_2;
    private Button game_BTN_p2_attack_3;

    public static final int PLAYER_ONE = 1;
    public static final int PLAYER_TWO = 2;
    public static final String GAME_DETAILS = "GAME_DETAILS";

    private final int HIGH_DAMAGE = 50;
    private final int MEDIUM_DAMAGE = 25;
    private final int LOW_DAMAGE = 10;

    private final Random RANDOM = new Random();

    private ImageView game_IV_dice_1;
    private ImageView game_IV_dice_2;

    private int player_turn;
    private int num_of_turns = 0;


    private int timer_value = 1;

    private Runnable automaticGame;
    private Handler handler = new Handler();
    private final int DELAY = 1000;

    private final int LOW = 1;
    private final int MEDIUM = 2;
    private final int HIGH = 3;

    private final int DICE_UPPER_BOUND = 6;
    private final int DICE_LOWER_BOUND = 1;

    private final double LAT_BOUND = 32.1149152;
    private final double LON_BOUND = 34.8159152;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        setUpViews();

        game_BTN_roll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                game_IV_dice_1.setVisibility(View.VISIBLE);
                game_IV_dice_2.setVisibility(View.VISIBLE);

                if (chooseStartingPlayer()) {
                    hideRollButton();
                    automaticGame = new Runnable(){
                        public void run(){
                            AutoTurn();
                            game_LBL_timer.setText(timer_value + "");
                            timer_value++;
                            handler.postDelayed(this, DELAY);

                        }
                    };
                    automaticGame.run();
                }
            }
        });

        game_BTN_p1_attack_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attack(HIGH_DAMAGE);
            }
        });
        game_BTN_p1_attack_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attack(MEDIUM_DAMAGE);
            }
        });
        game_BTN_p1_attack_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attack(LOW_DAMAGE);
            }
        });

        game_BTN_p2_attack_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attack(HIGH_DAMAGE);
            }
        });
        game_BTN_p2_attack_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attack(MEDIUM_DAMAGE);
            }
        });
        game_BTN_p2_attack_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attack(LOW_DAMAGE);

            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        handler.postDelayed(automaticGame, DELAY);
    }

    @Override
    protected void onStop() {
        super.onStop();
        handler.removeCallbacks(automaticGame);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void AutoTurn() {
        int attackButton = RANDOM.nextInt(HIGH) + LOW;
        if (attackButton == LOW)
        {
            attack(LOW_DAMAGE);

        }
        else if(attackButton == MEDIUM)
        {
            attack(MEDIUM_DAMAGE);
        }
        else
        {
            attack(HIGH_DAMAGE);
        }
    }


    private void setUpViews() {

        game_PB_p1_hp = findViewById(R.id.game_PB_p1_hp);
        game_PB_p2_hp = findViewById(R.id.game_PB_p2_hp);

        game_BTN_roll = findViewById(R.id.game_BTN_roll);
        game_LBL_timer = findViewById(R.id.game_LBL_timer);

        game_BTN_p1_attack_1 = findViewById(R.id.game_BTN_p1_attack_1);
        game_BTN_p1_attack_2 = findViewById(R.id.game_BTN_p1_attack_2);
        game_BTN_p1_attack_3 = findViewById(R.id.game_BTN_p1_attack_3);

        game_BTN_p2_attack_1 = findViewById(R.id.game_BTN_p2_attack_1);
        game_BTN_p2_attack_2 = findViewById(R.id.game_BTN_p2_attack_2);
        game_BTN_p2_attack_3 = findViewById(R.id.game_BTN_p2_attack_3);

        game_IV_dice_1 = findViewById(R.id.game_IV_dice_1);
        game_IV_dice_2 = findViewById(R.id.game_IV_dice_2);
    }

    private void hideRollButton() {
        game_BTN_roll.setVisibility(View.INVISIBLE);
    }

    private int rollDice() {
        return RANDOM.nextInt(DICE_UPPER_BOUND) + DICE_LOWER_BOUND;
    }

    private double randomCoordinate (double bound){
        double offset = RANDOM.nextDouble() / 100;
        if (rollDice() >= DICE_UPPER_BOUND / 2)
            offset *= -1;
        return bound + offset;
    }

    private boolean chooseStartingPlayer(){
        int dice1 = rollDice();
        int dice2 = rollDice();

        game_IV_dice_1.setImageResource(getResources().getIdentifier("ic_dice_" + dice1, "drawable", "com.example.hw2"));
        game_IV_dice_2.setImageResource(getResources().getIdentifier("ic_dice_" + dice2, "drawable", "com.example.hw2"));

        if (dice1 > dice2) {
            enableButtons(game_BTN_p1_attack_1, game_BTN_p1_attack_2, game_BTN_p1_attack_3);
            player_turn = PLAYER_ONE;
            return true;
        }
        else if (dice1 < dice2) {
            enableButtons(game_BTN_p2_attack_1, game_BTN_p2_attack_2, game_BTN_p2_attack_3);
            player_turn = PLAYER_TWO;
            return true;
        }
        return false;
    }



    private void lowHp(ProgressBar pb) {
        if (pb.getProgress() < pb.getMax() / 3){
            pb.setProgressTintList(ColorStateList.valueOf(Color.RED));
        }

    }

    private void enableButtons(Button... buttons) {
        for (Button btn : buttons) {
            btn.setEnabled(true);
            btn.setBackgroundColor(getColor(R.color.activeButton));
        }
    }

    private void disableButtons(Button... buttons) {
        for (Button btn : buttons) {
            btn.setEnabled(false);
            btn.setBackgroundColor(getColor(R.color.inactiveButton));

        }
    }

    private void switchPlayers() {
        if(player_turn == PLAYER_ONE) {
            disableButtons(game_BTN_p1_attack_1, game_BTN_p1_attack_2, game_BTN_p1_attack_3);
            enableButtons(game_BTN_p2_attack_1, game_BTN_p2_attack_2, game_BTN_p2_attack_3);
        }
        else {
            disableButtons(game_BTN_p2_attack_1, game_BTN_p2_attack_2, game_BTN_p2_attack_3);
            enableButtons(game_BTN_p1_attack_1, game_BTN_p1_attack_2, game_BTN_p1_attack_3);
        }
        player_turn = (player_turn % 2) + 1;
    }


    private boolean checkIsGameOver () {
        if (game_PB_p1_hp.getProgress() == 0 || game_PB_p2_hp.getProgress() == 0) {
            num_of_turns = (num_of_turns/2) + (num_of_turns%2);
            return true;
        }
        return false;

    }

    private void gameOver() {

        handler.removeCallbacks(automaticGame);
        Intent intent = new Intent(getApplicationContext(), Activity_Game_Over.class);

        double lat = randomCoordinate(LAT_BOUND);
        double lon = randomCoordinate(LON_BOUND);

        GameDetails gameDetails = new GameDetails(player_turn, num_of_turns, lat, lon);
        Gson gson = new Gson();
        String json = gson.toJson(gameDetails);

        intent.putExtra(GAME_DETAILS,json);
        startActivity(intent);
        finish();

    }

    private void attack(int damage) {
        if (player_turn == PLAYER_ONE) {
            game_PB_p2_hp.setProgress(game_PB_p2_hp.getProgress() - damage);
            lowHp(game_PB_p2_hp);
        }
        else {
            game_PB_p1_hp.setProgress(game_PB_p1_hp.getProgress() - damage);
            lowHp(game_PB_p1_hp);

        }
        num_of_turns++;
        if (checkIsGameOver()){
            gameOver();
        }
        else {
            switchPlayers();
        }

    }
}