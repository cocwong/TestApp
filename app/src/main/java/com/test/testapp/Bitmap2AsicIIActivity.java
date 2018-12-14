package com.test.testapp;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

public class Bitmap2AsicIIActivity extends AppCompatActivity {
    private ImageView imgSrc, imgDest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bitmap2_asic_ii);
        imgDest = findViewById(R.id.img_dest);
        imgSrc = findViewById(R.id.img_src);

    }

    @Override
    protected void onResume() {
        super.onResume();
        Bitmap bitmap1 = BitmapFactory.decodeResource(getResources(), R.drawable.cat);
        String asicII = Tools.picToAsicII(this, bitmap1);
        Bitmap bitmap = Tools.strToBitmap(this, asicII);
        imgDest.setImageBitmap(bitmap);
    }
}
