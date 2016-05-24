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
public class NewFooterEdge implements RefreshEdge {

    private final int mFontOffset;
    private Paint mPaint;
    private int mTime = 0;
    private int mHeight;
    private Paint mBackgroundPaint;
    private Bitmap mBitmap;
    private Matrix mMatrix;
    private int mState = RefreshEdge.STATE_REST;
    private int mBitmapPaddingTop;
    private int mTextPaddingTop;
    private int mTextPaddingBottom;
    private Point mOffsetPoint;

    /**
     * NewFooterEdge
     * @param context context
     */
    public NewFooterEdge(Context context) {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Style.FILL);
        int fontSize = context.getResources().getDimensionPixelSize(R.dimen.refresh_msg_text_size);
        mPaint.setTextSize(fontSize);
        mPaint.setTextAlign(Align.CENTER);
        mPaint.setColor(0xff353535);
        mFontOffset = (int) -(mPaint.getFontMetrics().top + mPaint.getFontMetrics().bottom);
        mHeight = context.getResources().getDimensionPixelOffset(R.dimen.refresh_footer_height);

        mBackgroundPaint = new Paint();
        mBackgroundPaint.setAntiAlias(true);
        mBackgroundPaint.setStyle(Style.FILL);
        mBackgroundPaint.setColor(context.getResources().getColor(R.color.refresh_header_bg));

        mBitmap = BitmapFactory.decodeResource(context.getResources(), R.mipmap.refresh_icon);
        mMatrix = new Matrix();

        mBitmapPaddingTop = context.getResources().getDimensionPixelOffset(R.dimen.bitmap_padding_top);
        mTextPaddingTop = context.getResources().getDimensionPixelOffset(R.dimen.text_padding_top);
        mTextPaddingBottom = context.getResources().getDimensionPixelOffset(R.dimen.text_padding_bottom);
    }

    public void setTextColor(int color) {
        mPaint.setColor(color);
    }

    @Override
    public boolean draw(Canvas canvas, int left, int top, int right, int bottom) {
        final int width = right - left;
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

        return new com.hileone.demo.new_refresh.Point(-offsetX, offsetY);
    }
}
