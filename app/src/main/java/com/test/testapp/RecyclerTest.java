package com.test.testapp;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class RecyclerTest extends AppCompatActivity {
    private RecyclerView recycler;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aty_recycler_test);
        recycler = findViewById(R.id.recycler);
        MyAdapter adapter = new MyAdapter();
        recycler.setLayoutManager(new MyLayoutManager());
        recycler.setAdapter(adapter);
    }

    class MyAdapter extends RecyclerView.Adapter<MyViewHolder> {

        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycler_test, parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
            holder.tv.setText(String.valueOf(position));
        }

        @Override
        public int getItemCount() {
            return 3000;
        }
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tv;

        public MyViewHolder(View itemView) {
            super(itemView);
            tv = itemView.findViewById(R.id.tv);
        }
    }

    class MyLayoutManager extends RecyclerView.LayoutManager {


        @Override
        public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
//            super.onLayoutChildren(recycler, state);
            detachAndScrapAttachedViews(recycler);
            for (int i = 0; i < 2; i++) {
                if(i>=getItemCount()){
                    return;
                }
                View child = recycler.getViewForPosition(i);
                addView(child,0);

                measureChildWithMargins(child, 0, 0);
                int width = getDecoratedMeasuredWidth(child);
                int height = getDecoratedMeasuredHeight(child);
                layoutDecorated(child, 0, 0, width, height);

            }
        }

        @Override
        public RecyclerView.LayoutParams generateDefaultLayoutParams() {
            return new RecyclerView.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        }

    }
}
