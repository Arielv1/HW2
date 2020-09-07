package com.example.hw2;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

public class Fragment_Players_List extends Fragment {

    private ArrayList<GameDetails> games;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;


    public Fragment_Players_List() {
    }

    public static Fragment_Players_List newInstance() {
        Fragment_Players_List fragment = new Fragment_Players_List();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_players_list, container, false);

        games = Utils.getInstance().getAllGamesFromSP();

        Context context = view.getContext();

        mRecyclerView = view.findViewById(R.id.fragment_scores_LAY_recycler_view);
        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(context);

        mAdapter = new ItemRecyclerViewAdapter(games);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        mRecyclerView.setAdapter(new ItemRecyclerViewAdapter(games));
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        return view;
    }
}
