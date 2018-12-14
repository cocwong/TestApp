package com.test;

import android.annotation.SuppressLint;
import android.content.Context;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CameraManager;
import android.hardware.camera2.CaptureRequest;
import android.hardware.camera2.params.StreamConfigurationMap;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.FrameLayout;

import java.util.Arrays;

@RequiresApi(21)
public class CameraView extends FrameLayout {
    private String cameraId;
    private SurfaceHolder mHolder;

    public CameraView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        SurfaceView surfaceView = new SurfaceView(context);
        mHolder = surfaceView.getHolder();
        mHolder.addCallback(surfaceCallBack);
        addView(surfaceView, LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        init();
    }
    private void init() {
        CameraManager manager = (CameraManager) getContext().getSystemService(Context.CAMERA_SERVICE);
        if (manager == null) return;
        try {
            String[] list = manager.getCameraIdList();
            for (String str : list) {
                CameraCharacteristics characteristics = manager.getCameraCharacteristics(str);
                Integer facing = characteristics.get(CameraCharacteristics.LENS_FACING);
                if (facing != null && facing == CameraCharacteristics.LENS_FACING_FRONT) {
                    continue;
                }
                StreamConfigurationMap map = characteristics.get(CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP);
                if (map == null) {
                    continue;
                }
                cameraId = str;
            }
        } catch (CameraAccessException e) {
            e.printStackTrace();
            System.out.println("camera:error");
        }
        openCamera(manager);
    }

    @SuppressLint("MissingPermission")
    private void openCamera(CameraManager manager) {
        try {
            manager.openCamera(cameraId, stateCallback, handler);
        } catch (CameraAccessException e) {
            e.printStackTrace();
            System.out.println("camera:error");
        }
    }

    private CameraDevice.StateCallback stateCallback = new CameraDevice.StateCallback() {
        @Override
        public void onOpened(@NonNull CameraDevice camera) {
            createSession(camera);
        }

        @Override
        public void onDisconnected(@NonNull CameraDevice camera) {
            camera.close();
            System.out.println("camera:error2");
        }

        @Override
        public void onError(@NonNull CameraDevice camera, int error) {
            camera.close();
            System.out.println("camera:error1");
        }
    };

    private void createSession(final CameraDevice camera) {
        try {
            final CaptureRequest.Builder builder = camera.createCaptureRequest(CameraDevice.TEMPLATE_PREVIEW);
            builder.addTarget(mHolder.getSurface());
            camera.createCaptureSession(Arrays.asList(mHolder.getSurface()),
                    new CameraCaptureSession.StateCallback() {
                        @Override
                        public void onConfigured(@NonNull CameraCaptureSession session) {
                            if (camera == null) {
                                return;
                            }
                            builder.set(CaptureRequest.CONTROL_AF_MODE,CaptureRequest.CONTROL_AF_MODE_CONTINUOUS_PICTURE);
                            CaptureRequest request = builder.build();
                            try {
                                session.setRepeatingRequest(request,null,handler);
                            } catch (CameraAccessException e) {
                                e.printStackTrace();
                                System.out.println("camera:error");
                            }
                        }

                        @Override
                        public void onConfigureFailed(@NonNull CameraCaptureSession session) {

                        }
                    }, null);
        } catch (CameraAccessException e) {
            e.printStackTrace();
            System.out.println("camera:error");
        }
    }

    private Handler handler = new Handler();
    private SurfaceHolder.Callback surfaceCallBack = new SurfaceHolder.Callback() {
        @Override
        public void surfaceCreated(SurfaceHolder holder) {

        }

        @Override
        public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

        }

        @Override
        public void surfaceDestroyed(SurfaceHolder holder) {

        }
    };
}
