package com.zenkosrc.marketinventory;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;

import com.zenkosrc.marketinventory.adapters.ThreadListAdapter;
import com.zenkosrc.marketinventory.database.InvThread;
import com.zenkosrc.marketinventory.managers.DataBaseManager;
import com.zenkosrc.marketinventory.util.Dialogs;

import static com.zenkosrc.marketinventory.CountingActivity.THREAD_ID;

public class CountingListActivity extends AppCompatActivity implements ThreadListAdapter.OnThreadlickListener {

    private RecyclerView        threadListRecyclerView;
    private LinearLayout        addButtonLinearLayout;
    private ThreadListAdapter   threadListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_counting_list);

        initResources();
        initRecyclerView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        getThreadListAdapter().refreshList();
    }

    private void initResources() {

        addButtonLinearLayout  = (LinearLayout) findViewById(R.id.addButtonLinearLayout);
        addButtonLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openIntThread(DataBaseManager.getInstance(CountingListActivity.this).createNewThreadInDataBase());
            }
        });
    }

    private void initRecyclerView() {

        threadListRecyclerView = (RecyclerView) findViewById(R.id.threadListRecyclerView);
        threadListRecyclerView.setLayoutManager(new LinearLayoutManager(CountingListActivity.this, LinearLayoutManager.VERTICAL, false));
        threadListAdapter = new ThreadListAdapter(CountingListActivity.this, this);
        threadListRecyclerView.setAdapter(threadListAdapter);
    }

    private ThreadListAdapter getThreadListAdapter() {
        return threadListAdapter;
    }

    private void openIntThread(long threadId) {

        Intent countingActivity = new Intent(CountingListActivity.this, CountingActivity.class);
        countingActivity.putExtra(THREAD_ID, threadId);
        startActivity(countingActivity);
    }

    public void showPopup() {

        Dialog myDialog = new Dialog(this);

        ((ViewGroup)myDialog.getWindow().getDecorView())
                .getChildAt(0).startAnimation(AnimationUtils.loadAnimation(
                this, R.anim.slide_in_up));

        myDialog.setContentView(R.layout.test);
        myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        myDialog.show();
    }

    @Override
    public void onThreadClick(InvThread invThread) {
        openIntThread(invThread.getId());
    }

    @Override
    public void onDeleteThreadClick(final InvThread invThread) {

        Dialogs.showDialog(this,
                getResources().getString(R.string.dialog_inv_thread_delete),
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //Ok
                        DataBaseManager.getInstance(CountingListActivity.this).deleteInvThread(invThread);
                        getThreadListAdapter().refreshList();
                    }
                }, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //No
                        dialog.dismiss();
                    }});
    }
}
