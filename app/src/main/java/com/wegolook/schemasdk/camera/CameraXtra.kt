package com.wegolook.schemasdk.camera

import android.annotation.SuppressLint
import android.content.Context
import android.content.DialogInterface
import android.hardware.display.DisplayManager
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.camera.core.*
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import java.io.File
import java.lang.Math.abs
import com.wegolook.schemasdk.R
import io.reactivex.Observable
import java.lang.Math.max
import java.util.*
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class CameraXtra(var lookID: String = "") : Fragment(), CameraDelegate,
    VideoCapture.OnVideoSavedCallback {

    private var isVideo: Boolean = false
    private var imageCapture: ImageCapture? = null
    private lateinit var cameraExecutor: ExecutorService
    private lateinit var viewFinder: PreviewView
    private var displayId: Int = -1
    private var cameraProvider: ProcessCameraProvider? = null
    private lateinit var preview: Preview
    private var lensFacing: Int = CameraSelector.LENS_FACING_BACK
    private var camera: Camera? = null
    private lateinit var videoCapture: VideoCapture
    private lateinit var videoSaver: VideoFileSaver
    private var mRotation: Int = 0
    private var isRecording: Boolean = false
    private lateinit var onVideoSavedCallback: VideoCapture.OnVideoSavedCallback


    private val displayManager by lazy {
        requireContext().getSystemService(Context.DISPLAY_SERVICE) as DisplayManager
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?): View? =
            inflater.inflate(R.layout.fragment_x, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewFinder = view.findViewById(R.id.viewFinder);

        cameraExecutor = Executors.newSingleThreadExecutor()

        displayManager.registerDisplayListener(displayListener, null)
        viewFinder.post {

            // Keep track of the display in which this view is attached
            if (viewFinder.display!=null) {
                displayId = viewFinder.display.displayId

                // Set up the camera and its use cases
                setUpCamera(false)
            }


        }


    }

    override fun swapCamera() {
        lensFacing = if (CameraSelector.LENS_FACING_FRONT == lensFacing) {
            CameraSelector.LENS_FACING_BACK
        } else {
            CameraSelector.LENS_FACING_FRONT
        }
        bindCameraUseCases(false)
    }

    override fun getCameraId(): Int {
        TODO("Not yet implemented")
    }

    override fun setFlashMode() {
        TODO("Not yet implemented")
    }

    @SuppressLint("RestrictedApi")
    override fun startRecording(maxTime: Int, videoFile: File,onVideoSavedCallback: VideoCapture.OnVideoSavedCallback): Boolean {
        this.onVideoSavedCallback=onVideoSavedCallback
        if (!isRecording) {
//            videoCapture.setTargetRotation(mRotation)
            var file = VideoCapture.OutputFileOptions.Builder(videoFile).build()
//            videoCapture.startRecording(file, ContextCompat.getMainExecutor(activity), videoSaver)
            return true
        }
        return false
    }

    override fun setVideo(boolean: Boolean) {
//        if (viewFinder.display==null)return
        isVideo = boolean
        viewFinder = (view as ConstraintLayout).findViewById(R.id.viewFinder)
        viewFinder.post {
            // Keep track of the display in which this view is attached
            if (viewFinder.display!=null) {
                displayId = viewFinder.display.displayId
                // Set up the camera and its use cases
                setUpCamera(boolean)
            }

        }
    }

    override fun takePhoto(callback: CameraDelegate.PhotoCallback?) {
        takPhoto(callback)
    }

    override fun getSupportedFlashModes(): Observable<MutableList<String>> {
        TODO("Not yet implemented")
    }

    @SuppressLint("RestrictedApi")
    override fun stopRecording() {
        videoCapture.stopRecording()
    }

    override fun transformImage(width: Int, height: Int) {
    }

//    override fun getSupportedFlashModes(): Observable<MutableList<String>> {
//        TODO("Not yet implemented")
//    }

    override fun hasFrontCamera(): Boolean {
        return cameraProvider?.hasCamera(CameraSelector.DEFAULT_FRONT_CAMERA) ?: false
    }

    private fun takPhoto(callback: CameraDelegate.PhotoCallback?) {
        if (viewFinder.display == null)
            return
        val imageCapture = imageCapture ?: return
        val fileManager = LookFileManager(activity, lookID)

        var photoFile = fileManager.createFile(JPEG)
        // Create output options object which contains file + metadata
        val outputOptions = ImageCapture.OutputFileOptions.Builder(photoFile).build()

        // Set up image capture listener, which is triggered after photo has
        // been taken
        imageCapture.takePicture(
                outputOptions, ContextCompat.getMainExecutor(activity), object : ImageCapture.OnImageSavedCallback {
            override fun onError(exc: ImageCaptureException) {
                Log.e(TAG, "Photo capture failed: ${exc.message}", exc)
            }

            override fun onImageSaved(output: ImageCapture.OutputFileResults) {
//                callback?.onPictureCaptured(photoFile)
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        cameraExecutor.shutdown()
    }

    private val displayListener = object : DisplayManager.DisplayListener {
        override fun onDisplayAdded(displayId: Int) = Unit
        override fun onDisplayRemoved(displayId: Int) = Unit
        @SuppressLint("RestrictedApi")
        override fun onDisplayChanged(displayId: Int) = view?.let { view ->
            if (viewFinder.display == null)
                return
            this@CameraXtra.displayId = viewFinder.display.displayId
            if (displayId == this@CameraXtra.displayId) {
                Log.d(TAG, "Rotation changed: ${view.display.rotation}")
                imageCapture?.targetRotation = view.display.rotation
                if (isVideo&&::videoCapture.isInitialized){
                    videoCapture.setTargetRotation(view.display.rotation)
                }
                mRotation = view.display.rotation
            }
        } ?: Unit
    }

    /** Returns true if the device has an available back camera. False otherwise */
    private fun hasBackCamera(): Boolean {
        return cameraProvider?.hasCamera(CameraSelector.DEFAULT_BACK_CAMERA) ?: false
    }

    private fun setUpCamera(boolean: Boolean) {
        // check if the context is not attached
        if (context==null)return
        val cameraProviderFuture = ProcessCameraProvider.getInstance(requireContext())
        cameraProviderFuture.addListener(Runnable {

            // CameraProvider
            cameraProvider = cameraProviderFuture.get()

            // Select lensFacing depending on the available cameras
            lensFacing = when {
                hasBackCamera() -> CameraSelector.LENS_FACING_BACK
                hasFrontCamera() -> CameraSelector.LENS_FACING_FRONT
                else -> throw IllegalStateException("Back and front camera are unavailable")
            }
            // Build and bind the camera use cases
            bindCameraUseCases(boolean)
        }, ContextCompat.getMainExecutor(requireContext()))
    }

    @SuppressLint("RestrictedApi")
    private fun bindCameraUseCases(boolean: Boolean) {
         if (viewFinder.display==null)return
        val metrics = DisplayMetrics().also { viewFinder.display.getRealMetrics(it) }
        Log.d(TAG, "Screen metrics: ${metrics.widthPixels} x ${metrics.heightPixels}")

        val screenAspectRatio = aspectRatio(metrics.widthPixels, metrics.heightPixels)
        Log.d(TAG, "Preview aspect ratio: $screenAspectRatio")

        val rotation = viewFinder.display.rotation

        // CameraProvider
        val cameraProvider = cameraProvider
                ?: throw IllegalStateException("Camera initialization failed.")

        // CameraSelector
        val cameraSelector = CameraSelector.Builder().requireLensFacing(lensFacing).build()

        // Preview
        preview = Preview.Builder()
                .setTargetAspectRatio(screenAspectRatio)
                .setTargetRotation(rotation)
                .build()

        imageCapture = ImageCapture.Builder()
                .setCaptureMode(ImageCapture.CAPTURE_MODE_MINIMIZE_LATENCY)
                .setTargetAspectRatio(screenAspectRatio)
                .setTargetRotation(rotation)
                .build()
        videoCapture = VideoCapture.Builder()
            .setTargetName("VideoCapture")
            .setTargetRotation(rotation)
            .build()
        videoSaver = VideoFileSaver(this)

        cameraProvider.unbindAll()
        try {
            if (boolean) {
                camera = cameraProvider.bindToLifecycle(
                        this, cameraSelector, preview, videoCapture)

            } else
                camera = cameraProvider.bindToLifecycle(
                        this, cameraSelector, preview, imageCapture)
            preview?.setSurfaceProvider(viewFinder.surfaceProvider)
        } catch (exc: Exception) {
            Log.e(TAG, "Use case binding failed", exc)
        }
    }

    private fun aspectRatio(width: Int, height: Int): Int {
        val previewRatio = max(width, height).toDouble() / Math.min(width, height)
        if (abs(previewRatio - RATIO_4_3_VALUE) <= abs(previewRatio - RATIO_16_9_VALUE)) {
            return AspectRatio.RATIO_4_3
        }
        return AspectRatio.RATIO_16_9
    }

    companion object {
        private const val TAG = "CameraXBasic"
        private const val RATIO_4_3_VALUE = 4.0 / 3.0
        private const val RATIO_16_9_VALUE = 16.0 / 9.0
        private const val JPEG: String = "jpg";

    }

    override fun onVideoSaved(outputFileResults: VideoCapture.OutputFileResults) {
        if (this::onVideoSavedCallback.isInitialized)
            onVideoSavedCallback.onVideoSaved(outputFileResults)
    }

    override fun onError(videoCaptureError: Int, message: String, cause: Throwable?) {
        if (this::onVideoSavedCallback.isInitialized)
        {
            onVideoSavedCallback.onError(videoCaptureError, message, cause)
            if (videoCaptureError==5 && !isRecording){
                AlertDialog.Builder(requireActivity())
                    .setTitle("Camera Error $videoCaptureError")
                    .setMessage("Can,t connect to camera")
                    .setCancelable(false)
                    .setPositiveButton(
                        "Connect"
                    ) { dialog: DialogInterface?, which: Int -> setUpCamera(true) }
                    .show()
            }
        }
    }

}