package com.example.zbarlib;

import android.content.Context;
import android.graphics.Point;
import android.hardware.Camera;
import android.hardware.Camera.AutoFocusCallback;
import android.hardware.Camera.PreviewCallback;
import android.util.Log;
import android.view.Display;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.WindowManager;

import java.io.IOException;

/**
 * Created by reding on 26/04/2017.
 */

/** A basic Camera preview class */
public class CameraPreview extends SurfaceView implements SurfaceHolder.Callback {
    private SurfaceHolder mHolder;
    private Camera mCamera;
    private PreviewCallback previewCallback;
    private AutoFocusCallback autoFocusCallback;

    public CameraPreview(Context context, Camera camera,
                         PreviewCallback previewCb,
                         AutoFocusCallback autoFocusCb) {
        super(context);
        mCamera = camera;
        previewCallback = previewCb;
        autoFocusCallback = autoFocusCb;
        mHolder = getHolder();
        mHolder.addCallback(this);

        mHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);

        Camera.Parameters parameters = mCamera.getParameters();
        CameraConfigurationUtils.setBestPreviewFPS(parameters);
        CameraConfigurationUtils.setBarcodeSceneMode(parameters);
        CameraConfigurationUtils.setBestExposure(parameters, false);

        WindowManager manager = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        Display display = manager.getDefaultDisplay();
        Point theScreenResolution = new Point();
        display.getSize(theScreenResolution);
        CameraConfigurationUtils.findBestPreviewSizeValue(parameters, theScreenResolution);
        mCamera.setParameters(parameters);

    }

    public void surfaceCreated(SurfaceHolder holder) {
        try {
            mCamera.setPreviewDisplay(holder);
        } catch (IOException e) {
            Log.d("DBG", "Error setting camera preview: " + e.getMessage());
        }
    }

    public void surfaceDestroyed(SurfaceHolder holder) {
        // Camera preview released in activity
    }

    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        /*
         * If your preview can change or rotate, take care of those events here.
         * Make sure to stop the preview before resizing or reformatting it.
         */
        if (mHolder.getSurface() == null){
            // preview surface does not exist
            return;
        }

        Log.d("GJP", "surfaceChanged");

        // stop preview before making changes
        try {
            mCamera.stopPreview();
        } catch (Exception e){
            // ignore: tried to stop a non-existent preview
        }

        try {
            // Hard code camera surface rotation 90 degs to match Activity view in portrait
            mCamera.setDisplayOrientation(90);

            mCamera.setPreviewDisplay(mHolder);
            mCamera.setPreviewCallback(previewCallback);
            mCamera.startPreview();
            mCamera.autoFocus(autoFocusCallback);
        } catch (Exception e){
            Log.d("DBG", "Error starting camera preview: " + e.getMessage());
        }
    }
}