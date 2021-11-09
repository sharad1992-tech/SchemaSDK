package com.wegolook.schemasdk.camera;

import android.app.Activity;
import android.content.Context;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Size;
import android.view.Surface;
import android.view.TextureView;

/**
 * A {@link TextureView} that can be adjusted to a specified aspect ratio.
 */
public class AutoFitTextureView extends TextureView {

    private int mRatioWidth = 0;
    private int mRatioHeight = 0;
    private int maxwidth = 0;
    private int maxheight = 0;
    private Size previewSize;
    public DisplayMetrics mMetrics = new DisplayMetrics();



    public AutoFitTextureView(Context context) {
        this(context, null);
    }

    public AutoFitTextureView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AutoFitTextureView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    /**
     * Sets the aspect ratio for this view. The size of the view will be measured based on the ratio
     * calculated from the parameters. Note that the actual sizes of parameters don't matter, that
     * is, calling setAspectRatio(2, 3) and setAspectRatio(4, 6) make the same result.
     *
     * @param width  Relative horizontal size
     * @param height Relative vertical size
     */
    public void setAspectRatio(int width, int height) {
        if (width < 0 || height < 0) {
            throw new IllegalArgumentException("Size cannot be negative.");
        }
        mRatioWidth = width;
        mRatioHeight = height;
        requestLayout();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        if (0 == mRatioWidth || 0 == mRatioHeight) {
            setMeasuredDimension(width, height);
        } else {
           /* WindowManager windowManager = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
            windowManager.getDefaultDisplay().getMetrics(mMetrics);
            double ratio = (double)mRatioWidth / (double)mRatioHeight;
            double invertedRatio = (double)mRatioHeight / (double)mRatioWidth;
            double portraitHeight = width * invertedRatio;
            double portraitWidth = width * (mMetrics.heightPixels / portraitHeight);
            double landscapeWidth = height * ratio;
            double landscapeHeight = (mMetrics.widthPixels / landscapeWidth) * height;
            if (width < height * mRatioWidth / mRatioHeight) {
                setMeasuredDimension((int)portraitWidth, mMetrics.heightPixels);
            } else {
                setMeasuredDimension(mMetrics.widthPixels, (int)landscapeHeight);
            }*/
            if (width < height * mRatioWidth / mRatioHeight) {
                setMeasuredDimension(width, width * mRatioHeight / mRatioWidth);
            } else {
                setMeasuredDimension(height * mRatioWidth / mRatioHeight, height);
            }
        }
    }

    private void transformMatrix(int previewWidth,
                                 int previewHeight,
                                 int rotation) {
        Matrix txform = new Matrix();
        int viewWidth = getWidth();
        int viewHeight = getHeight();
        RectF rectView = new RectF(0, 0, viewWidth, viewHeight);
        float viewCenterX = rectView.centerX();
        float viewCenterY = rectView.centerY();
        RectF rectPreview = new RectF(0, 0, previewHeight, previewWidth);
        float previewCenterX = rectPreview.centerX();
        float previewCenterY = rectPreview.centerY();

        if (Surface.ROTATION_90 == rotation ||
                Surface.ROTATION_270 == rotation) {
            rectPreview.offset(viewCenterX - previewCenterX,
                    viewCenterY - previewCenterY);

            txform.setRectToRect(rectView, rectPreview,
                    Matrix.ScaleToFit.FILL);

            float scale = Math.max((float) viewHeight / previewHeight,
                    (float) viewWidth / previewWidth);

            txform.postScale(scale, scale, viewCenterX, viewCenterY);
            txform.postRotate(90 * (rotation - 2), viewCenterX,
                    viewCenterY);
        } else {
            if (Surface.ROTATION_180 == rotation) {
                txform.postRotate(180, viewCenterX, viewCenterY);
            }
        }

        setTransform(txform);
    }

    private void enterTheMatrix() {
        if (previewSize == null)
            return;
        transformMatrix(mRatioWidth,
                mRatioHeight,
                ((Activity) getContext()).getWindowManager().getDefaultDisplay().getRotation());
    }

}
