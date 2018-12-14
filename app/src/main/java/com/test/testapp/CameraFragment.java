package com.test.testapp;

import android.content.Context;
import android.hardware.Camera;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.opengl.GLES20;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.io.IOException;

public class CameraFragment extends Fragment {
    private SurfaceView surfaceView;
    private SurfaceHolder holder;
    private int cameraIdFront, cameraIdBack;
    private int orientationFront, orientationBack;
    private Camera cameraUsing;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        SurfaceView surfaceView = new SurfaceView(getActivity());
        ViewGroup.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        surfaceView.setLayoutParams(params);
        return surfaceView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        surfaceView = (SurfaceView) view;
        init();
    }

    private void init() {
        holder = surfaceView.getHolder();
        holder.addCallback(callback);
    }

    private SurfaceHolder.Callback callback = new SurfaceHolder.Callback() {
        @Override
        public void surfaceCreated(SurfaceHolder holder) {
            int cameras = Camera.getNumberOfCameras();
            if (cameras == 0) {
                Toast.makeText(getContext(), "相机数量0", Toast.LENGTH_SHORT).show();
            } else {
                for (int i = 0; i < cameras; i++) {
                    Camera.CameraInfo info = new Camera.CameraInfo();
                    Camera.getCameraInfo(i, info);
                    getSensor();
                    if (info.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
                        cameraIdFront = i;
                        orientationFront = info.orientation;
                    } else if (info.facing == Camera.CameraInfo.CAMERA_FACING_BACK) {
                        cameraIdBack = i;
                        orientationBack = info.orientation;
                    }
                }
                cameraUsing = Camera.open(cameraIdBack);
                try {
                    Camera.CameraInfo info = new Camera.CameraInfo();
                    Camera.getCameraInfo(cameraIdBack, info);
                    Camera.Parameters parameters = cameraUsing.getParameters();
                    setAutoFocus(parameters);
                    setFlashMode();
                    cameraUsing.setPreviewDisplay(holder);
                    cameraUsing.startPreview();
                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(getContext(), "开启预览失败", Toast.LENGTH_SHORT).show();
                }
            }
        }

        @Override
        public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

        }

        @Override
        public void surfaceDestroyed(SurfaceHolder holder) {
            if (cameraUsing != null) {
                cameraUsing.stopPreview();
                cameraUsing.release();
            }
        }
    };

    /**
     * 设置闪光灯
     */
    private void setFlashMode() {

    }

    /**
     * 自动对焦
     *
     * @param parameters
     */
    private void setAutoFocus(Camera.Parameters parameters) {
        cameraUsing.autoFocus(new Camera.AutoFocusCallback() {
            @Override
            public void onAutoFocus(boolean success, Camera camera) {
                if (success) {
                    Toast.makeText(getContext(), "对焦成功", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void getSensor() {
        SensorManager manager = (SensorManager) getContext().getSystemService(Context.SENSOR_SERVICE);
        manager.registerListener(proximity, manager.getDefaultSensor(Sensor.TYPE_GRAVITY), SensorManager.SENSOR_DELAY_NORMAL);
    }

    private SensorEventListener proximity = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent event) {
            float x = event.values[0];
            float y = event.values[1];
            float z = event.values[2];
            System.out.println("gravity:" + x + "," + y + "," + z);
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
        }
    };
}
