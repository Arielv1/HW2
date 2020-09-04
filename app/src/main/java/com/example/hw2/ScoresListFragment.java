package com.example.hw2;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.trendyol.bubblescrollbarlib.BubbleScrollBar;
import com.trendyol.bubblescrollbarlib.BubbleTextProvider;

import java.util.ArrayList;

public class ScoresListFragment extends Fragment {

    private LeaderBoard leaderBoard;
    ArrayList<GameDetails> games;
    BubbleScrollBar bubbleScrollBar;

    public ScoresListFragment() {
    }

    public static ScoresListFragment newInstance() {
        ScoresListFragment fragment = new ScoresListFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LeaderBoard leaderBoard = getAllGamesFromSP();

        try {
            games = leaderBoard.getScores();
        }
        catch (Exception e) {
            games = new ArrayList<GameDetails>();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_scores, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
            MyItemRecyclerViewAdapter myItemRecyclerViewAdapter = new MyItemRecyclerViewAdapter(games);
//            recyclerView.setAdapter(new MyItemRecyclerViewAdapter(games));
            bubbleScrollBar.attachToRecyclerView(recyclerView);
            bubbleScrollBar.setBubbleTextProvider(new BubbleTextProvider() {
                @Override
                public String provideBubbleText(int i) {
                    return new StringBuilder(games.get(i).toString().substring(0,1)).toString();
                }
            });
        }
        return view;
    }

    public static LeaderBoard getAllGamesFromSP() {

        Gson gson = new Gson();
        String json = MySP.getInstance().getString(MySP.KEYS.LIST_OF_TOP_GAMES, MySP.VALUES.INITIAL_GAME_LIST);

        return gson.fromJson(json, LeaderBoard.class);
    }
}