package com.txcsmad.androidadvanced.lesson1;

import android.content.Context;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.text.InputType;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.txcsmad.androidadvanced.R;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnEditorAction;

public class AnagramActivity extends AppCompatActivity {

    private static final String START_MESSAGE = "Find as many words as possible that can be formed by adding one letter to <big>%s</big> (but that do not contain the substring %s).";
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

        ButterKnife.bind(this);

        loadWords();

        // Set up the EditText box to process the content of the box when the user hits 'enter'
        editText.setRawInputType(InputType.TYPE_CLASS_TEXT);
        editText.setImeOptions(EditorInfo.IME_ACTION_GO);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_anagrams, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return item.getItemId() == R.id.action_settings || super.onOptionsItemSelected(item);
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
            fab.hide();

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

    private void processWord(EditText editText) {
        String word = editText.getText().toString().trim().toLowerCase();

        if (word.length() == 0) {
            return; // FIXME
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


    // region Helper

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

    // endregion
}
