package com.hileone.demo.new_refresh;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Paint.Style;

import com.hileone.demo.R;
import com.hileone.recyclerview.RefreshEdge;

/**
 * The creator is Leone && E-mail: butleone@163.com
 *
 * @author Leone
 * @date 5/23/16
 * @description Edit it! Change it! Beat it! Whatever, just do it!
 */
public class NewHeaderEdge implements RefreshEdge {

    private final int mFontOffset;
    private int mState = STATE_REST;
    private Paint mPaint;
    private Paint mBackgroundPaint;
    private int mHeight = 0;
    private int mTime = 0;
    private Bitmap mBitmap;
    private Matrix mMatrix;
    private Point mOffsetPoint;
    private int mBitmapPaddingTop;
    private int mTextPaddingTop;
    private int mTextPaddingBottom;

    private String mPullToRefreshText;
    private String mReleaseToRefreshText;
    private String mRefreshingText;
    private String mRefreshSuccessText;
    private String mRefreshFailText;

    /**
     * NewHeaderEdge
     * @param context context
     */
    public NewHeaderEdge(Context context) {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Style.FILL);
        int fontSize = context.getResources().getDimensionPixelSize(R.dimen.refresh_msg_text_size);
        mPaint.setTextSize(fontSize);
        mPaint.setTextAlign(Align.CENTER);
        mPaint.setColor(0xff353535);
        mHeight = context.getResources().getDimensionPixelOffset(R.dimen.refresh_header_height);

        mFontOffset = (int) -(mPaint.getFontMetrics().top + mPaint.getFontMetrics().bottom);

        mBackgroundPaint = new Paint();
        mBackgroundPaint.setAntiAlias(true);
        mBackgroundPaint.setStyle(Style.FILL);
        mBackgroundPaint.setColor(context.getResources().getColor(R.color.refresh_header_bg));

        mBitmap = BitmapFactory.decodeResource(context.getResources(), R.mipmap.refresh_icon);
        mMatrix = new Matrix();

        mBitmapPaddingTop = context.getResources().getDimensionPixelOffset(R.dimen.bitmap_padding_top);
        mTextPaddingTop = context.getResources().getDimensionPixelOffset(R.dimen.text_padding_top);
        mTextPaddingBottom = context.getResources().getDimensionPixelOffset(R.dimen.text_padding_bottom);

        mPullToRefreshText = context.getString(R.string.zrc_pull_to_refresh);
        mReleaseToRefreshText = context.getString(R.string.zrc_release_to_refresh);
        mRefreshingText = context.getString(R.string.zrc_refreshing);
        mRefreshSuccessText = context.getString(R.string.zrc_refresh_success);
        mRefreshFailText = context.getString(R.string.zrc_refresh_fail);
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
    public void onStateChanged(int state) {
        mState = state;
    }

    @Override
    public int getState() {
        return mState;
    }

    /**
     * caculate from degree
     * @param degree degree
     * @return Point
     */
    private Point calcRotateOffset(float degree) {
        return calcRotateOffset(mBitmap.getWidth(), mBitmap.getHeight(), degree);
    }

    /**
     * caculate rotate distance
     * @param bitmapWidth bitmapWidth
     * @param bitmapHeight bitmapHeight
     * @param degrees degrees
     * @return Point
     */
    private Point calcRotateOffset(int bitmapWidth, int bitmapHeight, float degrees) {
        float radius = (float) (Math.sqrt(bitmapWidth * bitmapWidth + bitmapHeight * bitmapHeight) / 2f);
        float ox = (float) (radius * Math.cos(2 * - 45 * Math.PI / 360d));
        float oy = (float) (radius * Math.sin(2 * - 45 * Math.PI / 360d));

        float offsetX = (float) (radius * Math.cos(2 * - (degrees + 45) * Math.PI / 360d)) - ox;
        float offsetY = (float) (radius * Math.sin(2 * - (degrees + 45) * Math.PI / 360d)) - oy;

        return new Point(-offsetX, offsetY);
    }
}
