package com.kyonggi.eku;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class FreeAdapter extends RecyclerView.Adapter<FreeAdapter.viewHolder>{

    private ArrayList<TodoItem> mFreeItems;

    @NonNull
    @Override
    public FreeAdapter.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {

    }


    @Override
    public int getItemCount() {
        return 0;
    }

    public class viewHolder extends RecyclerView.ViewHolder{

        private TextView tv_title;
        private TextView tv_content;
        private TextView tv_writeDate;

        public viewHolder(@NonNull View itemView) {
            super(itemView);

        }
    }
}
