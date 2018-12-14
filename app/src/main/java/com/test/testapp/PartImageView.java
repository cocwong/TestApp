package com.test.testapp;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;

public class PartImageView extends AppCompatImageView {
    private byte[] bytes;

    public PartImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public void setRes(byte[] bytes) {
        this.bytes = bytes;
    }

    public void update(int offset) {
        int totalPixelSize = getWidth() * getHeight();
        int totalBytes = getBytesPerPixel(Bitmap.Config.ARGB_8888) * totalPixelSize;
        if (offset + totalBytes > bytes.length) {
            totalBytes = bytes.length - offset;
        }
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.ARGB_8888;
        Bitmap mBitmap = BitmapFactory.decodeByteArray(bytes, offset, totalBytes, options);
        setImageBitmap(mBitmap);
    }

    int getBytesPerPixel(Bitmap.Config config) {
        if (config == Bitmap.Config.ARGB_8888) {
            return 4;
        } else if (config == Bitmap.Config.RGB_565) {
            return 2;
        } else if (config == Bitmap.Config.ARGB_4444) {
            return 2;
        } else if (config == Bitmap.Config.ALPHA_8) {
            return 1;
        }
        return 1;
    }
}
