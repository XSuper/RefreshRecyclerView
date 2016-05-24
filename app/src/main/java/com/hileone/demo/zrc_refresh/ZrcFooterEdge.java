package com.hileone.demo.zrc_refresh;

import android.content.Context;
import android.graphics.Canvas;
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
public class ZrcFooterEdge implements RefreshEdge {

    private static final int PICE = 6;

    private int mState = RefreshEdge.STATE_REST;
    private Paint mPaint;
    private int mTime = 0;
    private int mCircleColor;
    private int mHeight;

    /**
     * ZrcFooterEdge
     * @param context context
     */
    public ZrcFooterEdge(Context context) {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Style.FILL);
        int fontSize = context.getResources().getDimensionPixelOffset(R.dimen.show_text_size);
        mPaint.setTextSize(fontSize);
        mPaint.setTextAlign(Align.CENTER);
        mCircleColor = 0xff303F9F;
        mHeight = context.getResources().getDimensionPixelOffset(R.dimen.footer_edge_height);
    }

    @Override
    public boolean draw(Canvas canvas, int left, int top, int right, int bottom) {
        final int width = right - left;
        final int height = mHeight;
        final int viewHeight = bottom - top;
        canvas.save();
        canvas.clipRect(left + 5, top + 1, right + 5, bottom - 1);
        mPaint.setColor(mCircleColor);
        for (int i = 0; i < PICE; i++) {
            int angleParam = mTime * 5;
            float angle = -(i * (360 / PICE) - angleParam) * (float) Math.PI / 180;
            float radius = height / 4;
            float x = (float) (width / 2 + radius * Math.cos(angle));
            float y = (float) (top + Math.max(height, viewHeight) / 2 + radius * Math.sin(angle));
            canvas.drawCircle(x, y, height / 15, mPaint);
        }
        mTime++;
        canvas.restore();
        return true;
    }

    @Override
    public int getState() {
        return mState;
    }

    @Override
    public void onStateChanged(int state) {
        mState = state;
    }

    @Override
    public int getHeight() {
        return mHeight;
    }
}
