package com.test.testapp.life_cycle;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleOwner;
import android.graphics.ColorMatrix;
import android.graphics.Matrix;
import android.widget.SectionIndexer;

public class BasePresenter implements IPresenter {
    Matrix matrix = new Matrix();
    ColorMatrix colorMatrix = new ColorMatrix();
//    SectionIndexer
    @Override
    public void onCreate(LifecycleOwner owner) {

    }

    @Override
    public void onDestroy(LifecycleOwner owner) {
    }

    @Override
    public void onPause() {

    }

    @Override
    public void onStop() {

    }

    @Override
    public void onLifeCycleChanged(LifecycleOwner owner, Lifecycle.Event event) {

    }
}
