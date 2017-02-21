package com.txcsmad.androidadvanced.lesson2;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.txcsmad.androidadvanced.R;
import com.txcsmad.androidadvanced.model.GameItem;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Jacob on 2/20/17.
 */

public class GameItemAdapter extends RecyclerView.Adapter<GameItemAdapter.GameItemHolder> {

    private final List<GameItem> gameItems;

    public GameItemAdapter(List<GameItem> gameItems) {
        this.gameItems = gameItems;
    }

    @Override
    public GameItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RelativeLayout gameLayout = (RelativeLayout) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_game, parent, false);

        return new GameItemHolder(gameLayout);
    }

    @Override
    public void onBindViewHolder(GameItemHolder holder, int position) {
        GameItem curItem = gameItems.get(position);

        holder.iconView.setImageResource(curItem.iconRes);
        holder.titleView.setText(curItem.title);
        holder.descView.setText(curItem.desc);

        Class intentClass = gameItems.get(position).intentClass;
        final Intent launchIntent = new Intent(holder.itemView.getContext(), intentClass);

        holder.itemView.setOnClickListener(v -> v.getContext().startActivity(launchIntent));
    }

    @Override
    public int getItemCount() {
        return gameItems.size();
    }

    static class GameItemHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.img_game_icon) ImageView iconView;
        @BindView(R.id.tv_game_title) TextView titleView;
        @BindView(R.id.tv_game_desc) TextView descView;

        public GameItemHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
        }
    }
}
