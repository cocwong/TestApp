package com.test.testapp;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Shader;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

public class CityView extends LinearLayout {
    private RecyclerView provinceRecycler, cityRecycler, countyRecycler;
    private List<CityDataBean> list;
    private MyAdapter provinceAdapter, cityAdapter, countyAdapter;
    private int ITEM_HEIGHT = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 45, getResources().getDisplayMetrics());
    private Paint linePaint, gradientPaint;
    private float OFFSET;
    private MyTask task;

    public CityView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setWillNotDraw(false);
        linePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        linePaint.setColor(Color.BLUE);
        gradientPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        OFFSET = getMeasuredHeight() / 2f - ITEM_HEIGHT / 2f;
        init();
        setListener();
        getData();
    }

    @Override
    public void onDrawForeground(Canvas canvas) {
        super.onDrawForeground(canvas);
        drawLines(canvas);
        drawForeground(canvas);
    }

    private void drawForeground(Canvas canvas) {
        LinearGradient gradientTop = new LinearGradient(0, 0, 0, (getHeight() - ITEM_HEIGHT) / 2f, new int[]{Color.parseColor("#ffffffff"), Color.parseColor("#00ffffff")}, null, Shader.TileMode.CLAMP);
        gradientPaint.setShader(gradientTop);
        canvas.drawRect(0, 0, getWidth(), (getHeight() - ITEM_HEIGHT) / 2f, gradientPaint);
        LinearGradient gradientBottom = new LinearGradient(0, getHeight(), 0, (getHeight() + ITEM_HEIGHT) / 2f, new int[]{Color.parseColor("#ffffffff"), Color.parseColor("#00ffffff")}, null, Shader.TileMode.CLAMP);
        gradientPaint.setShader(gradientBottom);
        canvas.drawRect(0, (getHeight() + ITEM_HEIGHT) / 2f, getWidth(), getHeight(), gradientPaint);
    }

    private void drawLines(Canvas canvas) {
        int height = getHeight();
        int totalWidth = getWidth();
        float topLine = height / 2f - ITEM_HEIGHT / 2f;
        int width = provinceRecycler.getWidth();
        canvas.drawLine(width / 3f / 2, topLine, width - width / 3f / 2, topLine + 3, linePaint);
        canvas.drawLine(width / 3f / 2, topLine + ITEM_HEIGHT, width - width / 3f / 2, topLine + 3 + ITEM_HEIGHT, linePaint);

        canvas.drawLine(width / 3f / 2 + totalWidth / 3,topLine,width - width / 3f / 2+totalWidth/3f,topLine+3,linePaint);
        canvas.drawLine(width / 3f / 2 + totalWidth / 3, topLine + ITEM_HEIGHT, width - width / 3f / 2 + totalWidth / 3, topLine + 3 + ITEM_HEIGHT, linePaint);

        canvas.drawLine(width / 3f / 2 + totalWidth / 3*2,topLine,width - width / 3f / 2+totalWidth/3f*2,topLine+3,linePaint);
        canvas.drawLine(width / 3f / 2 + totalWidth / 3*2, topLine + ITEM_HEIGHT, width - width / 3f / 2 + totalWidth / 3*2, topLine + 3 + ITEM_HEIGHT, linePaint);
    }

    private void getData() {
        task = new MyTask();
        task.execute();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        task.cancel(true);
    }

    private void setData() {
        provinceAdapter.updateProvince(list);
        cityAdapter.updateCity(list.get(provinceAdapter.getcPosition() - 1).getCity());
        countyAdapter.updateCounty(cityAdapter.getCounty());
    }

    private void init() {
        LinearLayoutManager provinceManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        LinearLayoutManager cityManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        LinearLayoutManager countyManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);

        provinceRecycler = new RecyclerView(getContext());
        provinceRecycler.setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);
        provinceRecycler.setLayoutManager(provinceManager);
        provinceAdapter = new MyAdapter(0);
        provinceRecycler.setAdapter(provinceAdapter);
        LinearSnapHelper provinceHelper = new LinearSnapHelper();
        provinceHelper.attachToRecyclerView(provinceRecycler);
        addProvinceListener(provinceManager, provinceHelper);

        cityRecycler = new RecyclerView(getContext());
        cityRecycler.setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);
        cityRecycler.setLayoutManager(cityManager);
        cityAdapter = new MyAdapter(1);
        cityRecycler.setAdapter(cityAdapter);
        LinearSnapHelper cityHelper = new LinearSnapHelper();
        cityHelper.attachToRecyclerView(cityRecycler);
        addCityListener(cityManager, cityHelper);

        countyRecycler = new RecyclerView(getContext());
        countyRecycler.setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);
        countyRecycler.setLayoutManager(countyManager);
        countyAdapter = new MyAdapter(2);
        countyRecycler.setAdapter(countyAdapter);
        LinearSnapHelper countyHelper = new LinearSnapHelper();
        countyHelper.attachToRecyclerView(countyRecycler);

        LayoutParams params = new LayoutParams(0, LayoutParams.MATCH_PARENT);
        params.weight = 1;
        addView(provinceRecycler, params);
        addView(cityRecycler, params);
        addView(countyRecycler, params);
    }

    private void addProvinceListener(final RecyclerView.LayoutManager manager, final LinearSnapHelper provinceHelper) {
        provinceRecycler.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    View snapView = provinceHelper.findSnapView(manager);
                    if (snapView == null) throw new RuntimeException("获取状态异常");
                    int position = manager.getPosition(snapView);
                    if (provinceAdapter.getcPosition() == position) return;
                    provinceAdapter.setcPosition(position);
                    cityAdapter.updateCity(list.get(position - 1).getCity());
                    cityAdapter.setcPosition(1);
                    cityRecycler.scrollToPosition(0);
                    countyAdapter.updateCounty(cityAdapter.getCounty());
                    countyAdapter.setcPosition(1);
                    countyRecycler.scrollToPosition(0);
                }
            }
        });
    }

    private void addCityListener(final RecyclerView.LayoutManager manager, final LinearSnapHelper helper) {
        cityRecycler.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    View snapView = helper.findSnapView(manager);
                    if (snapView == null) throw new RuntimeException("获取状态异常");
                    int position = manager.getPosition(snapView);
                    if (cityAdapter.getcPosition() == position) return;
                    cityAdapter.setcPosition(position);
                    countyAdapter.updateCounty(cityAdapter.getCounty());
                    countyAdapter.setcPosition(1);
                    countyRecycler.scrollToPosition(0);
                }
            }
        });
    }

    private void setListener() {

    }

    class MyAdapter extends RecyclerView.Adapter {
        private List<CityDataBean> list;
        private List<CityDataBean.CityBean> cityList;
        private List<String> countyList;
        private int mId;//0province/1city/2area
        private int cPosition = 1;

        MyAdapter(int mId) {
            this.mId = mId;
        }

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            if (viewType == 1) {
                TextView textView = new TextView(parent.getContext());
                textView.setTextSize(24);
                textView.setTextColor(Color.DKGRAY);
                textView.setBackground(null);
                textView.setSingleLine();
                textView.setGravity(Gravity.CENTER);
                textView.setEllipsize(TextUtils.TruncateAt.END);
                ViewGroup.LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ITEM_HEIGHT);
                textView.setLayoutParams(params);
                return new ViewHolder(textView);
            } else {
                View view = new View(getContext());
                ViewGroup.LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, (int) OFFSET);
                view.setLayoutParams(params);
                return new OffsetHolder(view);
            }
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
            if (position != 0 && position != getItemCount() - 1) {
                ViewHolder mHolder = (ViewHolder) holder;
                if (mId == 0) {
                    mHolder.tv.setText(list.get(position - 1).getName());
                } else if (mId == 1) {
                    mHolder.tv.setText(cityList.get(position - 1).getName());
                } else {
                    mHolder.tv.setText(countyList.get(position - 1));
                }

            }
        }

        @Override
        public int getItemCount() {
            if (mId == 0)
                return list == null ? 0 : list.size() + 2;
            if (mId == 1) return cityList == null ? 0 : cityList.size() + 2;
            return countyList == null ? 0 : countyList.size() + 2;
        }

        @Override
        public int getItemViewType(int position) {
            if (position == 0 || position == getItemCount() - 1) return 0;
            return 1;
        }

        public List<String> getCounty() {
            return cityList.get(getcPosition() - 1).getArea();
        }

        public int getcPosition() {
            return cPosition;
        }

        public void setcPosition(int cPosition) {
            this.cPosition = cPosition;
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            TextView tv;

            ViewHolder(View itemView) {
                super(itemView);
                tv = (TextView) itemView;
            }
        }

        void updateProvince(List<CityDataBean> list) {
            this.list = list;
            notifyDataSetChanged();
        }

        void updateCity(List<CityDataBean.CityBean> cityList) {
            this.cityList = cityList;
            notifyDataSetChanged();
        }

        void updateCounty(List<String> countyList) {
            this.countyList = countyList;
            notifyDataSetChanged();
        }

        class OffsetHolder extends RecyclerView.ViewHolder {

            OffsetHolder(View itemView) {
                super(itemView);
            }
        }
    }

    class MyTask extends AsyncTask<Void, Void, List<CityDataBean>> {

        @Override
        protected List<CityDataBean> doInBackground(Void... voids) {
            List<CityDataBean> list = null;
            Gson gson = new Gson();
            try {
                list = gson.fromJson(new InputStreamReader(getResources().getAssets().open("city.json")), new TypeToken<List<CityDataBean>>() {
                }.getType());
            } catch (IOException e) {
                e.printStackTrace();
            }
            return list;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(List<CityDataBean> list) {
            CityView.this.list = list;
            setData();
            super.onPostExecute(list);
        }
    }
}
