package com.zenkosrc.marketinventory;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = MainActivity.class.getSimpleName();

    private RelativeLayout addPropertiesButton;
    private RelativeLayout addProductButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initResources();
    }

    private void initResources() {

        addPropertiesButton = (RelativeLayout) findViewById(R.id.addPropertiesButton);
        addProductButton    = (RelativeLayout) findViewById(R.id.addProductButton);

        addPropertiesButton.setOnClickListener(this);
        addProductButton.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.addPropertiesButton:
                Log.d(TAG, "addPropertiesButton");
                break;

            case R.id.addProductButton:
                Log.d(TAG, "addProductButton");
                break;
        }
    }
}
