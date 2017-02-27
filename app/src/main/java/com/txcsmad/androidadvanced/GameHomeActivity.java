package com.txcsmad.androidadvanced;

import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.widget.ImageView;
import android.widget.TextView;

import com.txcsmad.androidadvanced.model.GameItem;

import butterknife.BindView;

/**
 * Created by Jacob on 2/27/17.
 */

public abstract class GameHomeActivity extends BaseActivity {

    @BindView(R.id.tv_game_title) TextView titleTextView;
    @BindView(R.id.tv_game_desc) TextView descTextView;
    @BindView(R.id.img_game_icon) ImageView iconImageView;

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
        bindHeader(getItem());
    }

    private void bindHeader(GameItem item) {
        titleTextView.setText(item.title);
        descTextView.setText(item.desc);
        iconImageView.setImageResource(item.iconRes);
    }

    @NonNull
    public abstract GameItem getItem();
}
