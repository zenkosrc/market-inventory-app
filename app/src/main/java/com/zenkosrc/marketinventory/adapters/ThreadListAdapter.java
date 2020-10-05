package com.zenkosrc.marketinventory.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zenkosrc.marketinventory.R;
import com.zenkosrc.marketinventory.database.InvThread;
import com.zenkosrc.marketinventory.managers.DataBaseManager;
import com.zenkosrc.marketinventory.util.TimeUtil;

import java.util.List;

public class ThreadListAdapter extends RecyclerView.Adapter<ThreadListAdapter.ViewHolder>{

    private Context context;
    private List<InvThread>                         threadList;
    private ThreadListAdapter.OnThreadlickListener  onThreadlickListener;

    public ThreadListAdapter(Context context, OnThreadlickListener onThreadlickListener) {
        this.context                 = context;
        this.onThreadlickListener    = onThreadlickListener;
        refreshList();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.thread_list_item, viewGroup, false);
        return new ThreadListAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        viewHolder.bind(threadList.get(position));
    }

    @Override
    public int getItemCount() {
        return threadList.size();
    }

    public void refreshList(){
        threadList = DataBaseManager.getInstance(context).getInvThreadsListSortByLast();
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private TextView        dataTextView;
        private TextView        nameTextView;
        private ImageView       deleteImageView;
        private LinearLayout    threadItemLinearLayout;


        public ViewHolder(final View itemView) {
            super(itemView);

            dataTextView           = itemView.findViewById(R.id.dataTextView);
            nameTextView           = itemView.findViewById(R.id.nameTextView);
            deleteImageView        = itemView.findViewById(R.id.deleteImageView);
            threadItemLinearLayout = itemView.findViewById(R.id.threadItemLinearLayout);

            threadItemLinearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    onThreadlickListener.onThreadClick(threadList.get(getLayoutPosition()));
                }
            });


            deleteImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    onThreadlickListener.onDeleteThreadClick(threadList.get(getLayoutPosition()));
                }
            });
        }

        public void bind(InvThread invThread) {
            dataTextView.setText(TimeUtil.getTimeFromMilliseconds(invThread.getTime()));
            nameTextView.setText(invThread.getName());
        }
    }

    public interface OnThreadlickListener {
        void onThreadClick(InvThread invThread);
        void onDeleteThreadClick(InvThread invThread);
    }
}
