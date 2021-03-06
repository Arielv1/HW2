package com.example.hw2;

import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

public class ItemRecyclerViewAdapter extends RecyclerView.Adapter<ItemRecyclerViewAdapter.ViewHolder> {

    private final List<GameDetails> mValues;

    public ItemRecyclerViewAdapter(List<GameDetails> items) {
        mValues = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_players_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        // Get a currents' game position from the game list then prints its' data:
        // Place in the leaderboard, image, number of turns it took to win
        GameDetails game = mValues.get(position);
        holder.m_place.setText(""+(position+1));

        holder.m_num_of_turns.setText(""+game.getNum_of_turns());
        if(game.getWinning_player() == MySP.VALUES.PLAYER_ONE) {
            holder.m_player_image.setImageResource(R.drawable.ic_alliance);
        }
        else {
            holder.m_player_image.setImageResource(R.drawable.ic_horde);
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