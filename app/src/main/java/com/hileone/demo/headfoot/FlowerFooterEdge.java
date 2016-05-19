package com.hileone.demo.headfoot;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;

import com.hileone.demo.DisplayUtils;
import com.hileone.demo.R;

/**
 * The creator is Leone && E-mail: butleone@163.com
 *
 * @author Leone
 * @date 5/12/16
 * @description Edit it! Change it! Beat it! Whatever, just do it!
 */
public class FlowerFooterEdge implements RefreshEage {

    private final int mFontOffset;
    private Paint mPaint;
    private int mTime = 0;
    private int mHeight;
    private Paint mBackgroundPaint;
    private Bitmap mBitmap;
    private Matrix mMatrix;
    private int mState = RefreshEage.STATE_REST;
    private int mBitmapPaddingTop = DisplayUtils.dp2px(5);
    private int mTextPaddingTop = DisplayUtils.dp2px(5);
    private int mTextPaddingBottom = DisplayUtils.dp2px(7);
    private Point mOffsetPoint;

    /**
     * FlowerFooterEdge
     * @param context context
     */
    public FlowerFooterEdge(Context context) {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.FILL);
        int fontSize = context.getResources().getDimensionPixelSize(R.dimen.refresh_msg_text_size);
        mPaint.setTextSize(fontSize);
        mPaint.setTextAlign(Paint.Align.CENTER);
        mPaint.setColor(0xff000000);
        mFontOffset = (int) -(mPaint.getFontMetrics().top + mPaint.getFontMetrics().bottom);
        mHeight = context.getResources().getDimensionPixelOffset(R.dimen.refresh_footer_height);

        mBackgroundPaint = new Paint();
        mBackgroundPaint.setAntiAlias(true);
        mBackgroundPaint.setStyle(Paint.Style.FILL);
        mBackgroundPaint.setColor(context.getResources().getColor(R.color.refresh_header_bg));

        mBitmap = BitmapFactory.decodeResource(context.getResources(), R.mipmap.refresh_icon);
        mMatrix = new Matrix();
    }

    public void setTextColor(int color) {
        mPaint.setColor(color);
    }

    @Override
    public boolean draw(Canvas canvas, int left, int top, int right, int bottom) {
        final int width = right - left;
        final int height = mHeight;
        final int viewHeight = bottom - top;
        canvas.save();
        canvas.drawRect(left, top, right, bottom, mBackgroundPaint);

        int center = bottom - top - mBitmap.getHeight() - mFontOffset - mBitmapPaddingTop - mTextPaddingTop - mTextPaddingBottom;
        center = Math.max(center, 0);
        center = center / 2;
        int offset = top + center;
        int rotate = offset * 3;

        switch (mState) {
            case STATE_REST:
            case STATE_PULL:
                mMatrix.reset();
                mMatrix.postRotate(rotate);
                mOffsetPoint = calcRotateOffset((float) rotate);
                mMatrix.postTranslate(width / 2 - mBitmap.getWidth() / 2 + mOffsetPoint.x, offset + mBitmapPaddingTop + mOffsetPoint.y);
                canvas.drawBitmap(mBitmap, mMatrix, null);
                canvas.drawText("向上推，惊喜多多", width / 2, offset + mBitmap.getHeight() + mFontOffset + mBitmapPaddingTop + mTextPaddingTop, mPaint);
                break;
            case STATE_RELEASE:
                mMatrix.reset();
                mMatrix.postRotate(rotate);
                mOffsetPoint = calcRotateOffset((float) rotate);
                mMatrix.postTranslate(width / 2 - mBitmap.getWidth() / 2 + mOffsetPoint.x, offset + mBitmapPaddingTop + mOffsetPoint.y);
                canvas.drawBitmap(mBitmap, mMatrix, null);
                canvas.drawText("累觉不爱，放手吧", width / 2, offset + mBitmap.getHeight() + mFontOffset + mBitmapPaddingTop + mTextPaddingTop, mPaint);
                break;
            case STATE_LOADING:
                mMatrix.reset();
                rotate = rotate - mTime * 15;
                mMatrix.postRotate(rotate);
                mOffsetPoint = calcRotateOffset((float) rotate);
                mMatrix.postTranslate(width / 2 - mBitmap.getWidth() / 2 + mOffsetPoint.x, offset + mBitmapPaddingTop + mOffsetPoint.y);
                canvas.drawBitmap(mBitmap, mMatrix, null);
                canvas.drawText("真的蛮拼的，甭急", width / 2, offset + mBitmap.getHeight() + mFontOffset + mBitmapPaddingTop + mTextPaddingTop, mPaint);
                mTime++;
                break;
            case STATE_SUCCESS:
            case STATE_FAIL:
                mMatrix.reset();
                rotate = rotate * 3 - mTime * 15;
                mMatrix.postRotate(rotate);
                mOffsetPoint = calcRotateOffset((float) rotate);
                mMatrix.postTranslate(width / 2 - mBitmap.getWidth() / 2 + mOffsetPoint.x, offset + mBitmapPaddingTop + mOffsetPoint.y);
                canvas.drawBitmap(mBitmap, mMatrix, null);
                canvas.drawText(mState == STATE_SUCCESS ? "加载成功" : "加载失败", width / 2, offset + mBitmap.getHeight() + mFontOffset + mBitmapPaddingTop + mTextPaddingTop, mPaint);
                mTime++;
                break;
        }
        canvas.restore();
        return true;
    }

    @Override
    public int getHeight() {
        return mHeight;
    }

    @Override
    public void onStateChanged(int state) {
        if (mState != state) {
            mTime = 0;
        }
        mState = state;
    }

    @Override
    public int getState() {
        return mState;
    }

    private Point calcRotateOffset(float degree) {
        return RotateTranslateOffset.offset(mBitmap.getWidth(), mBitmap.getHeight(), degree);
    }

    @Override
    public void setPullStateText(String text) {

    }

    @Override
    public void setReleaseStateText(String text) {

    }

    @Override
    public void setRefreshingText(String text) {

    }

    @Override
    public void setRefreshSuccessText(String text) {

    }

    @Override
    public void setRefreshFailText(String text) {

    }

    @Override
    public void setBackgroundColor(int color) {

    }
}
