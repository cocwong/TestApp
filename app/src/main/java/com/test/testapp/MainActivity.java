package com.test.testapp;

import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Looper;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.content.FileProvider;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class MainActivity extends BaseActivity implements View.OnClickListener {
    private ImageView img2;
    @FindView(id = R.id.img1)
    private ImageView img1;
    private RefreshView refreshView;
    private String name = "hello";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        refreshView = findViewById(R.id.refresh);
        img1 = findViewById(R.id.img1);
        img2 = findViewById(R.id.img2);
//        img1.setImageDrawable(new BitmapDrawable(getResources(),BitmapFactory.decodeResource(getResources(),R.mipmap.ic_launcher)));
//        img1.setImageBitmap(BitmapFactory.decodeResource(getResources(),R.mipmap.ic_launcher));
        img1.setImageResource(R.drawable.cat);
        img1.setOnClickListener(this);
        img2.setOnClickListener(this);
        BitmapDrawable drawable = (BitmapDrawable) img1.getDrawable();
//        img1.setDrawingCacheEnabled(true);
//        Bitmap bitmap = Bitmap.createBitmap(img1.getDrawingCache());
//        img1.setDrawingCacheEnabled(false);
        Bitmap bitmap = drawable.getBitmap();
        System.out.println(bitmap.getScaledWidth(getResources().getDisplayMetrics())+","+bitmap.getScaledHeight(getResources().getDisplayMetrics()));
        System.out.println(getResources().getDisplayMetrics().widthPixels+","+getResources().getDisplayMetrics().heightPixels);
        Glide.with(this).load("https://gss2.bdstatic.com/9fo3dSag_xI4khGkpoWK1HF6hhy/baike/w%3D268%3Bg%3D0/sign=c25ec7c6023b5bb5bed727f80ee8b204/b7fd5266d016092441726476d50735fae6cd340c.jpg")
                .into(img2);
        if (savedInstanceState != null)
            System.out.println(savedInstanceState.getString("name"));
//        compress();
//        refresh();
//        setImage();
//        showDialog();
//        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 100);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        name = "world-->save";
        outState.putString("name", name);
        System.out.println("onSaveInstanceState:" + name);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        System.out.println("onRestoreInstanceState:" + savedInstanceState.getString("name"));
    }

    @Override
    protected int setViewId() {
        return R.layout.activity_main;
    }

    File file = new File(Environment.getExternalStorageDirectory(), "/pic/" + System.currentTimeMillis() + ".jpg");

    private void camera() {
//        File file = new File(Environment.getExternalStorageDirectory(), "/pic/" + System.currentTimeMillis() + ".jpg");

        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        Uri imageUri = FileProvider.getUriForFile(this, getApplicationContext().getPackageName() + ".fileprovider", file);
        Intent intent = new Intent();
        intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);//设置Action为拍照
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);//将拍取的照片保存到指定URI
        startActivityForResult(intent, 1);

    }

    private void crop() {
        String cachePath = getApplicationContext().getExternalCacheDir().getPath();
        File picFile = new File(cachePath, "test.jpg");
        Uri picUri = Uri.fromFile(picFile);
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, picUri);
        startActivityForResult(intent, 100);
    }

    private void setImage() {
        String paths = Environment.getExternalStorageDirectory() + File.separator + "DCIM" + File.separator + "Camera";
        File file = new File(paths);
        File[] files = file.listFiles();
        Bitmap bitmap = BitmapFactory.decodeFile(Uri.fromFile(files[0]).getPath());
//        Bitmap bitmap = BitmapFactory.decodeFile(files[0].getAbsolutePath());
        img1.setImageBitmap(bitmap);
    }

    private void compress() {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.cat);
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        int sample = 100;
        while (baos.toByteArray().length / 1024 > 30) {
            baos.reset();
            Log.i("ff", "compress: " + sample);
            bitmap.compress(Bitmap.CompressFormat.JPEG, sample, baos);
            sample -= 10;
        }
        Bitmap bitmap1 = BitmapFactory.decodeByteArray(baos.toByteArray(), 0, baos.toByteArray().length);
        img2.setImageBitmap(bitmap1);
    }

    private void refresh() {
        refreshView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        MyAdapter adapter = new MyAdapter();
        refreshView.getRecyclerView().setAdapter(adapter);
    }

    public void save() {
        showDialog();
        TextView textView = findViewById(R.id.tv);
//        textView.setDrawingCacheEnabled(true);
//        textView.buildDrawingCache();
//        Bitmap cache = textView.getDrawingCache();
        ////////
        Bitmap bitmap = Bitmap.createBitmap(textView.getWidth(), textView.getHeight(), Bitmap.Config.ARGB_4444);
        Canvas canvas = new Canvas(bitmap);
        textView.draw(canvas);
        if (bitmap != null) {
            FileOutputStream fos = null;
            try {
                fos = new FileOutputStream(new File(Environment.getExternalStorageDirectory() + "/fff.jpg"));
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
                fos.flush();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (fos != null) {
                    try {
                        fos.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        } else {
            Toast.makeText(this, "null", Toast.LENGTH_SHORT).show();
        }
        Glide.with(this).pauseAllRequests();
    }

    private void showDialog() {
        MyDialog dialog = new MyDialog();
        dialog.setCancelable(true);
        dialog.setStyle(DialogFragment.STYLE_NO_TITLE, R.style.dialog);
        dialog.show(getSupportFragmentManager(), "fram");
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 100) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "允许", Toast.LENGTH_SHORT).show();
//                save();
//                new Timer().schedule(new TimerTask() {
//                    @Override
//                    public void run() {
//                        save();
//                    }
//                }, 3000);
//                setImage();
//                crop();
                camera();
            } else {
                Toast.makeText(this, "拒绝", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
            img1.setImageBitmap(bitmap);
        }
    }

    MyPop pop;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img1:
                if (pop == null)
                    pop = new MyPop(this, R.layout.dialog);
                pop.showAtLocation(img1, Gravity.CENTER, 0, 0);
                break;
            case R.id.img2:
                MySQLite sqLite = new MySQLite(this);
                SQLiteDatabase database = sqLite.getReadableDatabase();
                ContentValues values = new ContentValues();
                values.put("name", "scott");
                database.insert("per", null, values);
                database.close();
                sqLite.close();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Looper.prepare();
                        Toast.makeText(MainActivity.this, "toast", Toast.LENGTH_SHORT).show();
                        Looper.loop();
                    }
                }).start();
                break;
        }
    }


    @Override
    public void onStateNotSaved() {
        super.onStateNotSaved();
    }
}
