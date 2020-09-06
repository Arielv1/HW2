package com.example.hw2;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.SupportMapFragment;
import com.google.gson.Gson;
import com.trendyol.bubblescrollbarlib.BubbleScrollBar;
import com.trendyol.bubblescrollbarlib.BubbleTextProvider;

import java.util.ArrayList;

public class ScoresListFragment extends Fragment {

    private ArrayList<GameDetails> games;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;


    public ScoresListFragment() {
    }

    public static ScoresListFragment newInstance() {
        ScoresListFragment fragment = new ScoresListFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_scores, container, false);
        games = Utils.getInstance().getAllGamesFromSP();
        Context context = view.getContext();
        mRecyclerView = view.findViewById(R.id.fragment_scores_LAY_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(context);
        mAdapter = new MyItemRecyclerViewAdapter(games);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        mRecyclerView.setAdapter(new MyItemRecyclerViewAdapter(games));
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
        return view;
    }
}
