package com.test.testapp.webres;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ImageFormat;
import android.graphics.YuvImage;
import android.graphics.drawable.BitmapDrawable;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;

import com.test.testapp.R;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class WebAty extends AppCompatActivity {
    private WebView webView;
    private ImageView img;
    private String testUrl = "http://m.baidu.com/static/index/u.png";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aty_web_res);
        webView = findViewById(R.id.web_res);
        img = findViewById(R.id.img);
        webView.setWebViewClient(new WebViewClient() {
            @RequiresApi(21)
            @Nullable
            @Override
            public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request) {
                String url = request.getUrl().toString();
                if (TextUtils.equals(url, testUrl)) {
                    try {
                        InputStream inputStream = getAssets().open("aTestHtml.html");
                        return new WebResourceResponse("text/html", "utf-8", inputStream);
                    } catch (IOException e) {
                        e.printStackTrace();
                        return super.shouldInterceptRequest(view, request);
                    }
                }
                return super.shouldInterceptRequest(view, request);
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                return true;
            }
        });
//        webView.loadUrl("https://www.baidu.com");
        loadImage();
    }

    private void loadImage() {
        new Thread() {
            @Override
            public void run() {
                super.run();
                try {
                    URL url = new URL("http://res.cngoldres.com/upload/usercenter/2018/0815/8491eefa4ec74270f51da3fde0032dca.jpg");
                    URLConnection connection = url.openConnection();
                    connection.connect();
                    InputStream stream = connection.getInputStream();
                    byte[] bytes = new byte[1024 * 2];
                    int len;
                    while ((len = stream.read(bytes)) != -1) {
                        String str = new String(bytes, 0, len);
                        YuvImage image = new YuvImage(bytes, ImageFormat.NV21, 1080, 1860, null);
                        byte[] strBytes = str.getBytes("utf-8");
                        BitmapDrawable drawable = (BitmapDrawable) img.getDrawable();
                        if (drawable != null) {
                            Bitmap bitmap = drawable.getBitmap();
                            if (bitmap != null && !bitmap.isRecycled()) {
                                bitmap.recycle();
                            }
                        }
                        final Bitmap bitmap = BitmapFactory.decodeByteArray(strBytes, 0, strBytes.length);
                        System.out.println("bitmapNull:" + (bitmap == null));
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                img.setImageBitmap(bitmap);
                                if (bitmap != null && !bitmap.isRecycled()) {
                                    bitmap.recycle();
                                }
                            }
                        });
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }
}
