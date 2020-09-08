package com.example.hw2;

public class GameDetails implements Comparable<GameDetails> {

    private int winning_player;
    private int num_of_turns;
    private double lat = 0.0;
    private double lon = 0.0;

    public GameDetails() {
    }

    public GameDetails(int winning_player, int num_of_turns, double lat, double lon) {
        this.winning_player = winning_player;
        this.num_of_turns = num_of_turns;
        this.lat = lat;
        this.lon = lon;
    }

    public int getWinning_player() {
        return winning_player;
    }

    public void setWinning_player(int winning_player) {
        this.winning_player = winning_player;
    }

    public int getNum_of_turns() {
        return num_of_turns;
    }

    public void setNum_of_turns(int num_of_turns) {
        this.num_of_turns = num_of_turns;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    @Override
    public int compareTo(GameDetails gameDetails) {
        return Integer.compare(this.getNum_of_turns(), gameDetails.getNum_of_turns());

    }

    @Override
    public String toString() {
        return "GameDetails{" +
                "winning_player=" + winning_player +
                ", num_of_turns=" + num_of_turns +
                ", lat=" + lat +
                ", lon=" + lon +
                '}';
    }
}