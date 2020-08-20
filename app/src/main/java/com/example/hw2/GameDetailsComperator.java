package com.example.hw2;

import java.util.Comparator;

public class GameDetailsComperator implements Comparator<GameDetails> {

    @Override
    public int compare(GameDetails gameDetails, GameDetails t1) {
        return gameDetails.getNum_of_turns() - t1.getNum_of_turns();
    }
}
