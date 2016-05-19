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
public class FlowerHeaderEdge implements RefreshEage {

    private final int mFontOffset;
    private int mState = STATE_REST;
    private Paint mPaint;
    private Paint mBackgroundPaint;
    private int mHeight = 0;
    private int mTime = 0;
    private Bitmap mBitmap;
    private Matrix mMatrix;
    private Point mOffsetPoint;
    private int mBitmapPaddingTop = DisplayUtils.dp2px(5);
    private int mTextPaddingTop = DisplayUtils.dp2px(5);
    private int mTextPaddingBottom = DisplayUtils.dp2px(7);

    private Context mContext;
    private String mPullToRefreshText;
    private String mReleaseToRefreshText;
    private String mRefreshingText;
    private String mRefreshSuccessText;
    private String mRefreshFailText;

    /**
     * FlowerHeaderEdge
     * @param context context
     */
    public FlowerHeaderEdge(Context context) {
        mContext = context;
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.FILL);
        int fontSize = context.getResources().getDimensionPixelSize(R.dimen.refresh_msg_text_size);
        mPaint.setTextSize(fontSize);
        mPaint.setTextAlign(Paint.Align.CENTER);
        mPaint.setColor(0xFFFFCC33);
        mHeight = context.getResources().getDimensionPixelOffset(R.dimen.refresh_header_height);

        mFontOffset = (int) -(mPaint.getFontMetrics().top + mPaint.getFontMetrics().bottom);

        mBackgroundPaint = new Paint();
        mBackgroundPaint.setAntiAlias(true);
        mBackgroundPaint.setStyle(Paint.Style.FILL);
        mBackgroundPaint.setColor(context.getResources().getColor(R.color.refresh_header_bg));

        try {
            mBitmap = BitmapFactory.decodeResource(context.getResources(), R.mipmap.refresh_icon);
        } catch (NoClassDefFoundError e) {
            e.printStackTrace();
        }
        mMatrix = new Matrix();

        mPullToRefreshText = context.getString(R.string.zrc_pull_to_refresh);
        mReleaseToRefreshText = context.getString(R.string.zrc_release_to_refresh);
        mRefreshingText = context.getString(R.string.zrc_refreshing);
        mRefreshSuccessText = context.getString(R.string.zrc_refresh_success);
        mRefreshFailText = context.getString(R.string.zrc_refresh_fail);
    }

    public void setTextColor(int color) {
        mPaint.setColor(color);
    }

    @Override
    public void onStateChanged(int state) {
        if (mState != state) {
            mTime = 0;
        }
        mState = state;
    }

    @Override
    public void setBackgroundColor(int color) {
        mBackgroundPaint.setColor(color);
    }

    @Override
    public int getState() {
        return mState;
    }

    @Override
    public boolean draw(Canvas canvas, int left, int top, int right, int bottom) {
        boolean more = false;
        final int width = right - left;
        final int height = mHeight;
        int offset = bottom - top;
        int rotate = offset * 3;
        canvas.save();
        canvas.drawRect(left, top, right, bottom, mBackgroundPaint);

        int center = bottom - top - mBitmap.getHeight() - mFontOffset - mBitmapPaddingTop - mTextPaddingTop - mTextPaddingBottom;
        if (center > 0) center = center / 2;
        switch (mState) {
            case STATE_REST:
            case STATE_PULL:
                mMatrix.reset();
                mMatrix.postRotate(rotate);
                mOffsetPoint = calcRotateOffset((float) rotate);
                mMatrix.postTranslate(width / 2 - mBitmap.getWidth() / 2 + mOffsetPoint.x, center + mBitmapPaddingTop + mOffsetPoint.y);
                canvas.drawBitmap(mBitmap, mMatrix, null);
                canvas.drawText(mPullToRefreshText, width / 2, center + mBitmap.getHeight() + mFontOffset + mBitmapPaddingTop + mTextPaddingTop, mPaint);
                break;
            case STATE_RELEASE:
                mMatrix.reset();
                mMatrix.postRotate(rotate);
                mOffsetPoint = calcRotateOffset((float) rotate);
                mMatrix.postTranslate(width / 2 - mBitmap.getWidth() / 2 + mOffsetPoint.x, center + mBitmapPaddingTop + mOffsetPoint.y);
                canvas.drawBitmap(mBitmap, mMatrix, null);
                canvas.drawText(mReleaseToRefreshText, width / 2, center + mBitmap.getHeight() + mBitmapPaddingTop + mFontOffset + mTextPaddingTop, mPaint);
                break;
            case STATE_LOADING:
                more = true;
                mMatrix.reset();
                rotate = rotate - mTime * 15;
                mMatrix.postRotate(rotate);
                mOffsetPoint = calcRotateOffset((float) rotate);
                mMatrix.postTranslate(width / 2 - mBitmap.getWidth() / 2 + mOffsetPoint.x, center + mBitmapPaddingTop + mOffsetPoint.y);
                canvas.drawBitmap(mBitmap, mMatrix, null);
                canvas.drawText(mRefreshingText, width / 2, center + mBitmap.getHeight() + mFontOffset + mBitmapPaddingTop + mTextPaddingTop, mPaint);
                mTime++;
                break;
            case STATE_SUCCESS:
            case STATE_FAIL:
                more = true;
                mMatrix.reset();
                offset = offset - mTime * 15;
                mMatrix.postRotate(offset);
                mOffsetPoint = calcRotateOffset((float) offset);
                mMatrix.postTranslate(width / 2 - mBitmap.getWidth() / 2 + mOffsetPoint.x, center + mBitmapPaddingTop + mOffsetPoint.y);
                canvas.drawBitmap(mBitmap, mMatrix, null);
                canvas.drawText(mState == STATE_SUCCESS ? mRefreshSuccessText : mRefreshFailText, width / 2, center + mBitmap.getHeight() + mFontOffset + mBitmapPaddingTop + mTextPaddingTop, mPaint);
                mTime++;
                break;
        }
        canvas.restore();
        return more;
    }

    @Override
    public int getHeight() {
        return mHeight;
    }

    @Override
    public void setPullStateText(String text) {
        mPullToRefreshText = text;
    }

    @Override
    public void setReleaseStateText(String text) {
        mReleaseToRefreshText = text;
    }

    @Override
    public void setRefreshingText(String text) {
        mRefreshingText = text;
    }

    @Override
    public void setRefreshSuccessText(String text) {
        mRefreshSuccessText = text;
    }

    @Override
    public void setRefreshFailText(String text) {
        mRefreshFailText = text;
    }

    private Point calcRotateOffset(float degree) {
        return RotateTranslateOffset.offset(mBitmap.getWidth(), mBitmap.getHeight(), degree);
    }
}
