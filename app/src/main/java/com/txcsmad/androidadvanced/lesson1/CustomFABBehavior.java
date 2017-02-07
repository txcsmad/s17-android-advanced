package com.txcsmad.androidadvanced.lesson1;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.AttributeSet;
import android.view.View;

public class CustomFABBehavior extends CoordinatorLayout.Behavior<FloatingActionButton> {

    public CustomFABBehavior() {
        super();
    }

    public CustomFABBehavior(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, FloatingActionButton child, View dependency) {
        return dependency instanceof Snackbar.SnackbarLayout;
    }

    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, FloatingActionButton child, View dependency) {
        float translation = Math.min(0, dependency.getTranslationY() - dependency.getHeight());

        child.setTranslationX(translation);
        child.setTranslationY(translation);

        return true;
    }
}