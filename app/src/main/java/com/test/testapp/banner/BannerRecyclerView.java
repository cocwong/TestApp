package com.test.testapp.banner;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

public class BannerRecyclerView extends RecyclerView {
    private BannerAdapter adapter;
    private boolean touchable = true;

    public BannerRecyclerView(Context context) {
        super(context);
        init(context);
    }

    private void init(Context context) {
        setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        adapter = new BannerAdapter(context);
        setAdapter(adapter);
    }

    class BannerAdapter extends BannerRecyclerView.Adapter<BannerViewHolder> {
        private List<String> dataList;
        private Context context;

        BannerAdapter(Context context) {
            this.context = context;
            this.dataList = new ArrayList<>();
        }

        @NonNull
        @Override
        public BannerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new BannerViewHolder(createItemView(context));
        }

        @Override
        public void onBindViewHolder(@NonNull BannerViewHolder holder, int position) {

        }

        @Override
        public int getItemCount() {
            return dataList.size();
        }

        void setDataList(List<String> dataList) {
            this.dataList = dataList;
            notifyDataSetChanged();
        }
    }

    class BannerViewHolder extends BannerRecyclerView.ViewHolder {

        BannerViewHolder(View itemView) {
            super(itemView);
        }
    }

    /**
     * 创建ImageView
     *
     * @param context context
     */
    private View createItemView(Context context) {
        ImageView img = new ImageView(context);
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        img.setLayoutParams(params);
        img.setScaleType(ImageView.ScaleType.CENTER_CROP);
        return img;
    }

    //------------------//
    void setList(List<String> list) {
        adapter.setDataList(list);
    }
}
