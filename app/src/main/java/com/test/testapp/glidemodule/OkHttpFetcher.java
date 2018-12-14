package com.test.testapp.glidemodule;

import android.support.annotation.NonNull;

import com.bumptech.glide.Priority;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.data.DataFetcher;

import java.io.IOException;
import java.io.InputStream;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class OkHttpFetcher implements DataFetcher<InputStream> {
    private boolean isCancel;
    private String url;
    private OkHttpClient client;
    private InputStream stream;
    private ResponseBody responseBody;

    public OkHttpFetcher(OkHttpClient client, String url) {
        this.url = url;
        this.client = client;
    }

    @Override
    public void loadData(@NonNull Priority priority, @NonNull DataCallback<? super InputStream> callback) {
        Request.Builder builder = new Request.Builder();
        Request request = builder.url(url).build();
        try {
            if (isCancel) return;
            Response response = client.newCall(request).execute();
            responseBody = response.body();
            if (response.isSuccessful() && responseBody != null) {
                stream = response.body().byteStream();
                callback.onDataReady(stream);
            }
        } catch (IOException e) {
            e.printStackTrace();
            callback.onLoadFailed(e);
        }
    }

    @Override
    public void cleanup() {
        if (stream != null) {
            try {
                stream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (responseBody != null) {
            responseBody.close();
        }
    }

    @Override
    public void cancel() {
        isCancel = true;
    }

    @NonNull
    @Override
    public Class<InputStream> getDataClass() {
        return InputStream.class;
    }

    @NonNull
    @Override
    public DataSource getDataSource() {
        return DataSource.REMOTE;
    }
}
