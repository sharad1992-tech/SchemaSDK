package com.wegolook.schemasdk.camera;

import androidx.camera.core.VideoCapture;

import java.io.File;
import java.util.List;

import io.reactivex.Observable;


/**
 *   on 7/14/16.
 */
public interface CameraDelegate {
    void takePhoto(PhotoCallback callback);
    Observable<List<String>> getSupportedFlashModes();
    boolean startRecording(int maxTime, File videoFile, VideoCapture.OnVideoSavedCallback onVideoSavedCallback);
    void  setVideo(boolean isVideo);
    boolean hasFrontCamera();
    void transformImage(int width, int height);

    void swapCamera();
    //void setFlashMode(String flashMode);
    void setFlashMode();

    void stopRecording();
    int getCameraId();

    interface PhotoCallback {
        void onPictureCaptured(File file);
    }
}
