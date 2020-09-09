package com.zenkosrc.marketinventory;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;

public class CountingActivity extends AppCompatActivity {


    Dialog myDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_counting);


        showPopup();

    }


    public void showPopup() {

        myDialog = new Dialog(this);


        ((ViewGroup)myDialog.getWindow().getDecorView())
                .getChildAt(0).startAnimation(AnimationUtils.loadAnimation(
                this, R.anim.slide_in_up));

        myDialog.setContentView(R.layout.test);
        myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        myDialog.show();
    }

}
