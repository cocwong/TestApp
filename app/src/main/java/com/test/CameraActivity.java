package com.test;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.test.testapp.R;
import com.test.testapp.glidemodule.GlideApp;
import com.test.testapp.glidemodule.MyTransformer;

public class CameraActivity extends AppCompatActivity {
    private ImageView img;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
        img = findViewById(R.id.img);
        GlideApp.with(this)
                .setDefaultRequestOptions(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.NONE))
                .load("https://www.baidu.com/img/bd_logo1.png")
//                .circleCrop()
                .transform(new MyTransformer())
                .into(img);
    }
}
