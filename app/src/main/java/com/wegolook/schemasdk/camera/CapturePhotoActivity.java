//package com.wegolook.schemasdk.camera;
//
//import android.content.Intent;
//import android.net.Uri;
//import android.os.AsyncTask;
//import android.os.Bundle;
//import android.os.Handler;
//import android.os.Looper;
//import android.view.MotionEvent;
//import android.view.View;
//import android.widget.ImageView;
//
//import androidx.annotation.Nullable;
//import androidx.appcompat.app.AppCompatActivity;
//
//import com.bumptech.glide.Glide;
//import com.bumptech.glide.load.engine.DiskCacheStrategy;
//import com.github.florent37.viewanimator.ViewAnimator;
//import com.wegolook.app.R;
//import com.wegolook.app.utils.Intents;
//import com.wegolook.app.utils.Views;
//
//import org.parceler.Parcel;
//import org.parceler.ParcelConstructor;
//import org.parceler.Parcels;
//
//import java.io.File;
//
//import butterknife.BindView;
//import butterknife.ButterKnife;
//import butterknife.OnClick;
//
///**
// *   on 7/28/16.
// */
//public class CapturePhotoActivity extends AppCompatActivity {
//
//    private CameraDelegate cameraDelegate;
//    private Uri imageUri;
//    private Config config;
//
//    @BindView(R.id.loading_view) View loadingView;
//    @BindView(R.id.confirm_view) View confirmView;
//    @BindView(R.id.badge_confirm_image) ImageView confirmImage;
//    @BindView(R.id.shutter) View shutter;
//    @BindView(R.id.swap) View swap;
//
//    @Override
//    protected void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.take_badge_photo_activity);
//
//        this.config = Parcels.unwrap(getIntent().getParcelableExtra(Intents.KEY_CAMERA_CONFIG));
//
//        if(config == null) {
//            throw new IllegalArgumentException("Capture photo activity must have a config.");
//        }
//
//        ButterKnife.bind(this);
//
//        if(config.isFrontModeEnabled()) {
//            Views.setVisible(swap, true);
//        }
//        else {
//            Views.setVisible(swap, false);
//        }
//
//        //Camera2Fragment fragment = Camera2Fragment.newInstance(!config.isDefaultToFrontMode());
//        CameraXtra fragment=new CameraXtra();
//        cameraDelegate = fragment;
//        getSupportFragmentManager()
//                .beginTransaction()
//                .add(R.id.camera_preview_container, fragment)
//                .commit();
//
//
//        setTouchAnimations(shutter);
//        setTouchAnimations(swap);
//    }
//
//    @OnClick(R.id.shutter)
//    void takePhoto() {
//        Views.setVisible(loadingView, true);
//        cameraDelegate
//                .takePhoto(new CameraDelegate.PhotoCallback() {
//                    @Override
//                    public void onPictureCaptured(File photo) {
//                        AsyncTask.execute(new Runnable() {
//                            @Override
//                            public void run() {
//                               /* Uri configUri = Uri.parse(config.getFileUri());
//
//                                File file = new File(configUri.getPath());
//                                BufferedOutputStream outputStream = null;
//                                try {
//                                    outputStream = new BufferedOutputStream(new FileOutputStream(file));
//                                    outputStream.write(photo);
//                                    outputStream.close();
//                                } catch (IOException e) {
//                                    e.printStackTrace();
//                                    NewRelicUtil.logException(e);
//
//                                    new Handler(Looper.getMainLooper()).post(() -> {
//                                        Views.setVisible(loadingView, false);
//                                        Views.setVisible(confirmView, false);
//                                        Toast.makeText(CapturePhotoActivity.this, "Error taking photo.", Toast.LENGTH_SHORT).show();
//                                    });
//
//                                } finally {
//                                    IO.closeQuietly(outputStream);
//                                }*/
//                                imageUri = Uri.fromFile(photo);
//
//                                new Handler(Looper.getMainLooper()).post(() -> {
//                                    //ImageRotationHelper.correctImageOrientation(CapturePhotoActivity.this,file,cameraDelegate.getCameraId());
//                                    Glide.with(CapturePhotoActivity.this)
//                                            .load(imageUri)
//                                            .skipMemoryCache(true)
//                                            .diskCacheStrategy(DiskCacheStrategy.NONE)
//                                            .fitCenter()
//                                            .into(confirmImage);
//                                    Views.setVisible(loadingView, false);
//                                    Views.setVisible(confirmView, true);
//                                });
//
//                            }
//                        });
//                    }
//                });
//    }
//
//    @OnClick(R.id.swap)
//    void swapCamera() {
//        cameraDelegate.swapCamera();
//    }
//
//    @OnClick(R.id.retake_btn)
//    void retake() {
//        Views.setVisible(confirmView, false);
//    }
//
//    @OnClick(R.id.use_photo_btn)
//    void confirmPhoto() {
//        Intent resultIntent = new Intent();
//        resultIntent.setData(this.imageUri);
//        File file = new File(this.imageUri.getPath());
//        setResult(RESULT_OK, resultIntent);
//        finish();
//    }
//
//    private void setTouchAnimations(View v) {
//        v.setOnTouchListener((view, motionEvent) -> {
//            if(motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
//                ViewAnimator.animate(v)
//                        .scale(1, .9f)
//                        .decelerate()
//                        .duration(150)
//                        .start();
//            }
//            else if(motionEvent.getAction() == MotionEvent.ACTION_UP) {
//                ViewAnimator.animate(v)
//                        .scale(.9f, 1.25f, 1)
//                        .decelerate()
//                        .duration(200)
//                        .start();
//            }
//            return false;
//        });
//    }
//
//    @Parcel
//    public static class Config {
//        boolean frontModeEnabled;
//        boolean defaultToFrontMode;
//        String fileUri;
//
//        @ParcelConstructor
//        public Config(boolean frontModeEnabled, boolean defaultToFrontMode, String fileUri) {
//            this.frontModeEnabled = frontModeEnabled;
//            this.defaultToFrontMode = defaultToFrontMode;
//            this.fileUri = fileUri;
//        }
//
//        public boolean isFrontModeEnabled() {
//            return frontModeEnabled;
//        }
//
//        public boolean isDefaultToFrontMode() {
//            return defaultToFrontMode;
//        }
//
//        public String getFileUri() {
//            return fileUri;
//        }
//    }
//
//    public static class ConfigBuilder {
//        boolean frontModeEnabled = false;
//        boolean defaultToFrontMode = false;
//        String fileUri;
//
//        public ConfigBuilder(String fileUri) {
//            this.fileUri = fileUri;
//        }
//
//        public ConfigBuilder frontModeIsEnabled(boolean enabled) {
//            this.frontModeEnabled = enabled;
//            return this;
//        }
//
//        public ConfigBuilder frontCameraIsDefault(boolean shouldDefault) {
//            this.defaultToFrontMode = shouldDefault;
//            return this;
//        }
//
//        public Config build() {
//            return new Config(this.frontModeEnabled, this.frontModeEnabled, this.fileUri);
//        }
//    }
//}
