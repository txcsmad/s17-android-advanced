package com.txcsmad.androidadvanced.lesson1;

import android.content.Context;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BaseTransientBottomBar;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.text.InputType;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.txcsmad.androidadvanced.GameHomeActivity;
import com.txcsmad.androidadvanced.R;
import com.txcsmad.androidadvanced.model.GameItem;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnEditorAction;

public class AnagramActivity extends GameHomeActivity {

    public static final GameItem ANAGRAM_ITEM =
            new GameItem(android.R.drawable.ic_input_add,
                    "Anagram",
                    "Check anagrams, woo!",
                    AnagramActivity.class);

    private static final String START_MESSAGE = "Find as many words as possible that can be formed " +
            "by adding one letter to <big>%s</big> (but that do not contain the substring %s).";
    private static final String HEX_COLOR_BAD = "#cc0029";
    private static final String HEX_COLOR_GOOD = "#00aa29";

    private AnagramDictionary dictionary;
    private String currentWord;
    private List<String> anagrams;


    @BindView(R.id.tv_anagram_status) TextView gameStatus;
    @BindView(R.id.fab) FloatingActionButton fab;
    @BindView(R.id.et_anagram) EditText editText;
    @BindView(R.id.tv_result) TextView resultView;

    // region Activity Lifecycle

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anagram);

        loadWords();

        // Set up the EditText box to process the content of the box when the user hits 'enter'
        editText.setRawInputType(InputType.TYPE_CLASS_TEXT);
        editText.setImeOptions(EditorInfo.IME_ACTION_GO);
    }

    // endregion

    // region View Interaction

    @OnClick(R.id.fab)
    public void defaultAction() {
        if (currentWord == null) {
            currentWord = dictionary.pickGoodStarterWord();
            anagrams = dictionary.getAnagrams(currentWord);

            gameStatus.setText(Html.fromHtml(String.format(START_MESSAGE, currentWord.toUpperCase(), currentWord)));

            fab.setImageResource(android.R.drawable.ic_menu_help);

            resultView.setText("");
            editText.setText("");
            editText.setEnabled(true);
            editText.requestFocus();

            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT);
        } else {
            editText.setText(currentWord);
            editText.setEnabled(false);
            fab.setImageResource(android.R.drawable.ic_media_play);
            currentWord = null;
            resultView.append(TextUtils.join("\n", anagrams));
            gameStatus.append(" Hit 'Play' to start again");
        }
    }

    @OnEditorAction(R.id.et_anagram)
    public boolean processEditorAction(int actionId) {
        if (actionId == EditorInfo.IME_ACTION_GO) {
            processWord(editText);
            return true;
        }

        return false;
    }

    // endregion

    // region Helper

    private void processWord(EditText editText) {
        String word = editText.getText().toString().trim().toLowerCase();

        if (word.length() == 0) {
            Snackbar.make(fab, "Word was empty.", BaseTransientBottomBar.LENGTH_SHORT).show();
            return;
        }

        String color;
        if (dictionary.isGoodWord(word, currentWord) && anagrams.contains(word)) {
            anagrams.remove(word);
            color = HEX_COLOR_GOOD;
        } else {
            word = "X " + word;
            color = HEX_COLOR_BAD;
        }

        resultView.append(Html.fromHtml(String.format("<font color=%s>%s</font><BR>", color, word)));
        editText.setText("");

        fab.show();
    }

    private void loadWords() {
        AssetManager assetManager = getAssets();
        try {
            InputStream inputStream = assetManager.open("words.txt");
            dictionary = new AnagramDictionary(inputStream);
        } catch (IOException e) {
            Toast toast = Toast.makeText(this, "Could not load dictionary", Toast.LENGTH_LONG);
            toast.show();
        }
    }

    @NonNull
    @Override
    public GameItem getItem() {
        return ANAGRAM_ITEM;
    }

    // endregion
}
