package com.txcsmad.androidadvanced.lesson2;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.util.Pair;
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

        transitionActivity(holder, launchIntent);
    }

    @Override
    public int getItemCount() {
        return gameItems.size();
    }

    void transitionActivity(GameItemHolder holder, Intent intent) {
        ActivityOptions options;
        Context vContext = holder.itemView.getContext();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP && vContext instanceof Activity) {
            options = ActivityOptions.makeSceneTransitionAnimation(
                    (Activity) holder.itemView.getContext(),
                    Pair.create(holder.iconView, "img_game_icon"),
                    Pair.create(holder.titleView, "tv_game_title"),
                    Pair.create(holder.descView, "tv_game_desc")
            );

            holder.itemView.setOnClickListener(v -> vContext.startActivity(intent, options.toBundle()));
        } else {
            holder.itemView.setOnClickListener(v -> vContext.startActivity(intent));
        }
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
