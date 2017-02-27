package com.txcsmad.androidadvanced.lesson2;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
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

    private static final String TRANSITION_NAME_ICON = "img_game_icon";
    private static final String TRANSITION_NAME_TITLE = "tv_game_title";
    private static final String TRANSITION_NAME_DESC = "tv_game_desc";

    private Activity context;
    private final List<GameItem> gameItems;

    public GameItemAdapter(Activity context, List<GameItem> gameItems) {
        this.context = context;
        this.gameItems = gameItems;
    }

    @Override
    public GameItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RelativeLayout gameLayout = (RelativeLayout) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_game, parent, false);

        return new GameItemHolder(gameLayout);
    }

    @SuppressWarnings("unchecked")
    @Override
    public void onBindViewHolder(GameItemHolder holder, int position) {
        GameItem curItem = gameItems.get(position);

        holder.iconView.setImageResource(curItem.iconRes);
        holder.titleView.setText(curItem.title);
        holder.descView.setText(curItem.desc);

        holder.itemView.setOnClickListener(v -> {
            Pair<View, String> pair1 = Pair.create(holder.iconView, TRANSITION_NAME_ICON);
            Pair<View, String> pair2 = Pair.create(holder.titleView, TRANSITION_NAME_TITLE);
            Pair<View, String> pair3 = Pair.create(holder.descView, TRANSITION_NAME_DESC);

            Intent intent = new Intent(context, curItem.intentClass);
            ActivityOptionsCompat options =
                    ActivityOptionsCompat.makeSceneTransitionAnimation(context, pair1, pair2, pair3);

            context.startActivity(intent, options.toBundle());
        });
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
