package com.txcsmad.androidadvanced;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
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
import butterknife.ButterKnife;
import butterknife.OnItemClick;

public class MainActivity extends AppCompatActivity {

    private List<GameItem> gameItems;
    private GameItemAdapter itemAdapter;

    @BindView(R.id.rv_games) RecyclerView gamesRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        setupGameItems();
    }

    private void setupGameItems() {
        // Create items

        gameItems = new ArrayList<>();
        gameItems.add(
                new GameItem(android.R.drawable.ic_input_add,
                        "Anagram",
                        "Check anagrams, woo!",
                        AnagramActivity.class)
        );
        gameItems.add(
                new GameItem(android.R.drawable.ic_dialog_alert,
                        "Ghost",
                        "Ghost is pretty neat.",
                        GhostActivity.class)
        );

        itemAdapter = new GameItemAdapter(gameItems);


        // Finish setup

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);

        gamesRecyclerView.setLayoutManager(linearLayoutManager);
        gamesRecyclerView.setAdapter(itemAdapter);
    }
}
