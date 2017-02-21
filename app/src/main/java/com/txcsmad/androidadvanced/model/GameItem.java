package com.txcsmad.androidadvanced.model;

import android.support.annotation.DrawableRes;
import android.support.v7.app.AppCompatActivity;

import com.txcsmad.androidadvanced.gamelogic.Game;
import com.txcsmad.androidadvanced.lesson2.GhostActivity;

/**
 * Created by Jacob on 2/20/17.
 */

public class GameItem {

    @DrawableRes public int iconRes;

    public String title;

    public String desc;

    public Class intentClass;

    public GameItem() {
    }

    public GameItem(@DrawableRes int iconRes, String title, String desc, Class intentClass) {
        this.iconRes = iconRes;
        this.title = title;
        this.desc = desc;
        this.intentClass = intentClass;
    }
}
