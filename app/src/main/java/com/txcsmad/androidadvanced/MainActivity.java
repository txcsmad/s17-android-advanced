package com.txcsmad.androidadvanced;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.txcsmad.androidadvanced.lesson1.AnagramActivity;
import com.txcsmad.androidadvanced.lesson2.GameItemAdapter;
import com.txcsmad.androidadvanced.lesson2.GhostActivity;
import com.txcsmad.androidadvanced.model.GameItem;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class MainActivity extends BaseActivity {

    private List<GameItem> gameItems;
    private GameItemAdapter itemAdapter;

    @BindView(R.id.rv_games) RecyclerView gamesRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupGameItems();
    }

    private void setupGameItems() {
        // Create items
        gameItems = new ArrayList<>();
        gameItems.add(AnagramActivity.ANAGRAM_ITEM);
        gameItems.add(GhostActivity.GHOST_ITEM);
        itemAdapter = new GameItemAdapter(this, gameItems);

        // Finish setup
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        gamesRecyclerView.setLayoutManager(linearLayoutManager);
        gamesRecyclerView.setAdapter(itemAdapter);
    }
}
