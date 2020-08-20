package com.example.hw2;

import java.util.ArrayList;

public class TopTenGames {
    private ArrayList<GameDetails> scores;

    public TopTenGames() {
    }

    public TopTenGames(ArrayList<GameDetails> scores) {
        this.scores = scores;
    }

    public ArrayList<GameDetails> getScores() {
        return scores;
    }

    public void setScores(ArrayList<GameDetails> scores) {
        this.scores = scores;
    }

    @Override
    public String toString() {
        return "TopTenGames{" +
                "scores=" + scores +
                '}';
    }
}