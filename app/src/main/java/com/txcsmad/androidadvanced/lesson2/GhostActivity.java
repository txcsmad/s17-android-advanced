/* Copyright 2016 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.txcsmad.androidadvanced.lesson2;

import android.content.res.AssetManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.KeyEvent;
import android.widget.TextView;
import android.widget.Toast;

import com.txcsmad.androidadvanced.GameHomeActivity;
import com.txcsmad.androidadvanced.R;
import com.txcsmad.androidadvanced.lesson2.applied.GhostDictionary;
import com.txcsmad.androidadvanced.lesson2.applied.SimpleDictionary;
import com.txcsmad.androidadvanced.model.GameItem;

import java.io.IOException;
import java.util.Random;

import butterknife.BindView;


public class GhostActivity extends GameHomeActivity {

    public static final GameItem GHOST_ITEM =
            new GameItem(android.R.drawable.ic_dialog_alert,
                    "Ghost",
                    "Ghost is pretty neat.",
                    GhostActivity.class);

    private static final String FILE_NAME = "words.txt";
    private static final String COMPUTER_TURN = "Computer's turn";
    private static final String USER_TURN = "Your turn";

    private GhostDictionary dictionary;
    private boolean userTurn = false;
    private Random random = new Random();

    @BindView(R.id.tv_ghost) TextView textView;
    @BindView(R.id.game_status) TextView statusTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ghost);

        loadWords();
    }

    private void loadWords() {
        AssetManager assetManager = getAssets();
        try {
            dictionary = new SimpleDictionary(assetManager.open(FILE_NAME));
        } catch (IOException e) {
            Toast.makeText(this, "Error loading dictionary.", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Handler for the "Reset" button.
     * Randomly determines whether the game starts with a user turn or a computer turn.
     *
     * @return true
     */
    public boolean resetGame() {
        userTurn = random.nextBoolean();
        textView.setText("");
        if (userTurn) {
            statusTextView.setText(USER_TURN);
        } else {
            statusTextView.setText(COMPUTER_TURN);
            computerTurn();
        }
        return true;
    }

    private void computerTurn() {
        // Do computer turn stuff then make it the user's turn again
        userTurn = true;
        statusTextView.setText(USER_TURN);
    }

    /**
     * Handler for user key presses.
     *
     * @param keyCode
     * @param event
     * @return whether the key stroke was handled.
     */
    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        /**
         **
         **  YOUR CODE GOES HERE
         **
         **/
        return super.onKeyUp(keyCode, event);
    }

    @NonNull
    @Override
    public GameItem getItem() {
        return GHOST_ITEM;
    }
}
