package com.example.hw2;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Set;

import static android.content.Context.MODE_PRIVATE;

public class MySP {


    public interface KEYS {
        public static final String NUM_OF_TURNS = "NUM_OF_TURNS";
        public static final String WINNER = "WINNER";
        public static final String LIST_OF_TOP_GAMES = "LIST_OF_TOP_GAMES";
        public static final String GAME_DETAILS = "GAME_DETAILS";
    }



    private SharedPreferences prefs;

    public MySP(Context context) {
        prefs = context.getSharedPreferences("MY_SP", MODE_PRIVATE);
    }

    public void putInt(String key, int value) {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(key, value);
        editor.apply();
    }

    public int getInt(String key, int def) {
        return prefs.getInt(key, def);
    }

    public void putString(String key, String value) {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public String getString(String key, String def) {
        return prefs.getString(key, def);
    }

    public void putSetString(String key, Set<String> value){
        SharedPreferences.Editor editor = prefs.edit();
        editor.putStringSet(key, value);
        editor.apply();
    }
    public Set<String> getSetString(String key, Set<String> def){
        return prefs.getStringSet(key, def);
    }
}