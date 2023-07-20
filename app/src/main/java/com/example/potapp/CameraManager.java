package com.example.potapp;

import android.content.Context;
import android.content.pm.PackageManager;
import android.widget.Toast;

public class CameraManager {
    Context context;

    public CameraManager(Context context) {
        this.context = context;
    }

    /** Check if this device has a camera */
    public static void checkCameraHardware(Context context) {
        if (context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)){
            // this device has a camera
        } else {
            Toast.makeText(context, "您的設備沒有相機", Toast.LENGTH_SHORT).show();
        }
    }

//    /** A safe way to get an instance of the Camera object. */
//    public static Camera getCameraInstance(){
//        Camera c = null;
//        try {
//            c = Camera.open(); // attempt to get a Camera instance
//        }
//        catch (Exception e){
//            // Camera is not available (in use or does not exist)
//        }
//        return c; // returns null if camera is unavailable
//    }


}
