package com.example.hw2;

import java.util.ArrayList;

public class LeaderBoard {
    private ArrayList<GameDetails> scores;

    public LeaderBoard() {
    }

    public LeaderBoard(ArrayList<GameDetails> scores) {
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
        return "LeaderBoard{" +
                "scores=" + scores +
                '}';
    }
}