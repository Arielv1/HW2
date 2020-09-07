package com.example.hw2;

import android.content.Context;
import android.content.SharedPreferences;
import android.icu.util.LocaleData;
import android.util.Log;

import com.google.gson.Gson;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

public class Utils {

    private static Utils instance;
    private Context context;


    public static Utils initHelper(Context context) {
        if (instance == null)
            instance = new Utils(context);
        return instance;
    }

    public static Utils getInstance() {

        return instance;
    }

    public Utils(Context context) {
       this.context = context;
    }



    public LeaderBoard getLeaderBoardFromSP() {

        Gson gson = new Gson();
        String json = MySP.getInstance().getString(MySP.KEYS.LIST_OF_TOP_GAMES, MySP.VALUES.INITIAL_GAME_LIST);

        return gson.fromJson(json, LeaderBoard.class);
    }

    public ArrayList<GameDetails> getAllGamesFromSP(){
        ArrayList<GameDetails> games;

        try {
            games = getLeaderBoardFromSP().getScores();

        }
        catch (Exception e) {
            games = new ArrayList<GameDetails>();
        }

        return games;
    }
}
