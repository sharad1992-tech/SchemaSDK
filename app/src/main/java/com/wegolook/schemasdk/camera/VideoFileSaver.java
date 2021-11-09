/*
 * Copyright (C) 2019 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.wegolook.schemasdk.camera;
import android.util.Log;

import androidx.annotation.GuardedBy;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.camera.core.VideoCapture;
import androidx.camera.core.VideoCapture.OnVideoSavedCallback;
import androidx.camera.core.VideoCapture.VideoCaptureError;

/**
 * Basic functionality required for interfacing the {@link VideoCapture}.
 */
public class VideoFileSaver implements OnVideoSavedCallback {
    private static final String TAG = "VideoFileSaver";
    private final Object mLock = new Object();
    @GuardedBy("mLock")
    private boolean mIsSaving = false;
    private OnVideoSavedCallback  onVideoSavedCallback;
    public VideoFileSaver(OnVideoSavedCallback onVideoSavedCallback){
        this.onVideoSavedCallback=onVideoSavedCallback;
    }
    @Override
    public void onVideoSaved(@NonNull VideoCapture.OutputFileResults outputFileResults) {
        if (outputFileResults.getSavedUri() != null) {
            Log.d(TAG, "Saved file: " + outputFileResults.getSavedUri().getPath());
        }
        synchronized (mLock) {
            mIsSaving = false;
        }
        if (onVideoSavedCallback!=null) {
            onVideoSavedCallback.onVideoSaved(outputFileResults);
        }
    }
    @Override
    public void onError(@VideoCaptureError int videoCaptureError, @NonNull String message,
            @Nullable Throwable cause) {
        Log.e(TAG, "Error: " + videoCaptureError + ", " + message);
        if (cause != null) {
            Log.e(TAG, "Error cause: " + cause.getCause());
        }
        synchronized (mLock) {
            mIsSaving = false;
        }
        if (onVideoSavedCallback!=null) {
            onVideoSavedCallback.onError(videoCaptureError, message, cause);
        }


    }

    boolean isSaving() {
        synchronized (mLock) {
            return mIsSaving;
        }
    }
    /** Sets saving state after video startRecording */
    void setSaving() {
        synchronized (mLock) {
            mIsSaving = true;
        }
    }
}