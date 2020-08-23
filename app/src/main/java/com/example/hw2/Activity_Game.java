package com.example.hw2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import com.google.gson.Gson;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

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

   // private MySP mySP;

    private int player_turn;
    private int num_of_turns = 0;
    private boolean timer_start = false;

    private int timer_value = 1;

    Runnable secondlyRun;





    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        Log.d("pttt", "onCreate");
        setUpViews();
      //  mySP = new MySP(this);
      //mySP.putString(MySP.KEYS.LIST_OF_TOP_GAMES, "");

        game_BTN_roll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                game_IV_dice_1.setVisibility(View.VISIBLE);
                game_IV_dice_2.setVisibility(View.VISIBLE);
                Log.d("pttt", "game_BTN_roll Clicked");

                if (chooseStartingPlayer()) {
                    Log.d("pttt", "chooseStartingPlayer == true");
                    hideRollButton();
                    timer_start = true;
                    secondlyRun = new Runnable(){
                        public void run(){
                            AutoTurn();
                            timer_value++;
                            if (timer_value <= 100) {
                                handler.postDelayed(this, DELAY);
                            }
                        }
                    };
                    secondlyRun.run();
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
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        Log.d("pttt", "onSaveInstanceState");
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        Log.d("pttt", "onRestoreInstanceState");
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    protected void onPause() {
        Log.d("pttt", "onPause");
        super.onPause();
    }

    @Override
    protected void onResume() {
        Log.d("pttt", "onResume");
        super.onResume();
    }

    private Handler handler = new Handler();
    private final int DELAY = 5000;

    @Override
    protected void onStart() {
        Log.d("pttt", "onStart");
        super.onStart();

        handler.postDelayed(secondlyRun, DELAY);
    }

    @Override
    protected void onStop() {
        Log.d("pttt", "onStop");
        super.onStop();

        handler.removeCallbacks(secondlyRun);
    }

    @Override
    protected void onDestroy() {
        Log.d("pttt", "onDestroy");
        super.onDestroy();
    }

//    Runnable secondlyRun = new Runnable(){
//        public void run(){
//
////            game_LBL_timer.setText("" + timer_value);
////            MyToaster.getInstance().showToast("" + timer_value);
//            AutoTurn();
//
//            timer_value++;
//            if (timer_value <= 100) {
//                handler.postDelayed(this, DELAY);
//            }
//        }
//    };

    private void AutoTurn() {

        Log.d("pttt", "AutoTurn called");
        int attackButton = RANDOM.nextInt(3) + 1;
        if (attackButton == 1)
        {
            attack(LOW_DAMAGE);

        }
        else if(attackButton == 2)
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
        return RANDOM.nextInt(6) + 1;
    }

    private boolean chooseStartingPlayer(){
        int dice1 = rollDice();
        int dice2 = rollDice();

        game_IV_dice_1.setImageResource(getResources().getIdentifier("dice_" + dice1, "drawable", "com.example.hw2"));
        game_IV_dice_2.setImageResource(getResources().getIdentifier("dice_" + dice2, "drawable", "com.example.hw2"));

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

    private void gameOver() {
        /*
         TODO - ADD GPS LOCATION AND SEND IT TO THE INTENT
         */
        handler.removeCallbacks(secondlyRun);
        Intent intent = new Intent(getApplicationContext(), Activity_Game_Over.class);

        GameDetails gameDetails = new GameDetails(player_turn, num_of_turns, 0, 0);
        Gson gson = new Gson();
        String json = gson.toJson(gameDetails);

        intent.putExtra(GAME_DETAILS,json);
        startActivity(intent);
        finish();

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



    private void lowHp(ProgressBar pb) {
        if (pb.getProgress() < pb.getMax() / 3){
            pb.setProgressTintList(ColorStateList.valueOf(Color.RED));
        }

    }

    private boolean checkIsGameOver () {
        if (game_PB_p1_hp.getProgress() == 0 || game_PB_p2_hp.getProgress() == 0) {
            num_of_turns = (num_of_turns/2) + (num_of_turns%2);
            Toast.makeText(getBaseContext(), "Game Over - With " + num_of_turns + " Turns", Toast.LENGTH_SHORT).show();
            return true;
        }
        return false;

    }
}