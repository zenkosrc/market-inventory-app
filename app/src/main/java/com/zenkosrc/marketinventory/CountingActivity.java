package com.zenkosrc.marketinventory;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class CountingActivity extends AppCompatActivity {

    private static final String TAG = CountingActivity.class.getSimpleName();

    public static String THREAD_ID = "ThreadId";

    private long threadId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_counting);


        initThread();
        Log.d(TAG, "Thread ID " + threadId);
    }

    private void initThread() {
        if (getIntent() !=null){
            threadId = getIntent().getLongExtra(THREAD_ID, 0);
        }
    }
}
