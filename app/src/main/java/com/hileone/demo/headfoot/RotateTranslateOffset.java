package com.hileone.demo.headfoot;


/**
 * The creator is Leone && E-mail: butleone@163.com
 *
 * @author Leone
 * @date 5/12/16
 * @description Edit it! Change it! Beat it! Whatever, just do it!
 */
public class RotateTranslateOffset {

    /**
     * 计算旋转产生的位移量
     * @param bitmapWidth bitmapWidth
     * @param bitmapHeight bitmapHeight
     * @param degrees degrees
     * @return Point
     */
    public static Point offset(int bitmapWidth, int bitmapHeight, float degrees) {
        float radius = (float) (Math.sqrt(bitmapWidth * bitmapWidth + bitmapHeight * bitmapHeight) / 2f);
        float ox = (float) (radius * Math.cos(2 * - 45 * Math.PI / 360d));
        float oy = (float) (radius * Math.sin(2 * - 45 * Math.PI / 360d));

        float offsetX = (float) (radius * Math.cos(2 * - (degrees + 45) * Math.PI / 360d)) - ox;
        float offsetY = (float) (radius * Math.sin(2 * - (degrees + 45) * Math.PI / 360d)) - oy;

        return new Point(-offsetX, offsetY);
    }
}
