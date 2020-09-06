package com.example.hw2;

import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

public class MyItemRecyclerViewAdapter extends RecyclerView.Adapter<MyItemRecyclerViewAdapter.ViewHolder> {

    private final List<GameDetails> mValues;

    public MyItemRecyclerViewAdapter(List<GameDetails> items) {
        mValues = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_list_scores_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
//        holder.mItem = mValues.get(position);
        GameDetails game = mValues.get(position);
        holder.m_place.setText(""+(position+1));

        holder.m_num_of_turns.setText(""+game.getNum_of_turns());
        if(game.getWinning_player() == Activity_Game.PLAYER_ONE)
        {
            holder.m_player_image.setImageResource(R.drawable.alliance);
        }
        else {
            holder.m_player_image.setImageResource(R.drawable.horde);
        }
        if (position < 9) {
            holder.m_player_image.setLayoutParams(new LinearLayout.LayoutParams(550, 100));
        }
        else {
            holder.m_player_image.setLayoutParams(new LinearLayout.LayoutParams(510, 100));
        }
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public  View mView;
        public  TextView m_num_of_turns;
        public  TextView m_place;
        public  ImageView m_player_image;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            m_num_of_turns = view.findViewById(R.id.scores_LAY_num_turns_to_win);
            m_place = view.findViewById(R.id.scores_LAY_place);
            m_player_image = view.findViewById(R.id.scores_LAY_image);
        }

    }
}