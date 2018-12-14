package com.test.testapp.glidemodule;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.bumptech.glide.load.Options;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.ModelCache;
import com.bumptech.glide.load.model.ModelLoader;
import com.bumptech.glide.load.model.ModelLoaderFactory;
import com.bumptech.glide.load.model.MultiModelLoaderFactory;

import java.io.InputStream;

import okhttp3.OkHttpClient;

public class OkHttpUrlLoader implements ModelLoader<GlideUrl, InputStream> {
    private OkHttpClient client;
    private ModelCache<GlideUrl, GlideUrl> cache;

    public OkHttpUrlLoader(ModelCache<GlideUrl, GlideUrl> cache, OkHttpClient client) {
        this.cache = cache;
        this.client = client;
    }

    @Nullable
    @Override
    public LoadData<InputStream> buildLoadData(@NonNull GlideUrl glideUrl, int width, int height, @NonNull Options options) {
        GlideUrl url = glideUrl;
        if (cache != null) {
            url = cache.get(url, 0, 0);
            if (url == null) {
                cache.put(glideUrl, 0, 0, glideUrl);
                url = glideUrl;
            }
        }
        return new LoadData<>(url, new OkHttpFetcher(client, url.toStringUrl()));
    }

    @Override
    public boolean handles(@NonNull GlideUrl glideUrl) {
        return true;
    }

    public static class Factory implements ModelLoaderFactory<GlideUrl, InputStream> {
        private OkHttpClient client;
        ModelCache<GlideUrl, GlideUrl> cache = new ModelCache<>(500);

        public OkHttpClient getClient() {
            if (client == null) {
                client = new OkHttpClient();
            }
            return client;
        }

        @NonNull
        @Override
        public ModelLoader<GlideUrl, InputStream> build(@NonNull MultiModelLoaderFactory multiFactory) {
            return new OkHttpUrlLoader(cache, getClient());
        }

        @Override
        public void teardown() {

        }
    }
}
