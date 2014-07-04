package com.dajie.wika.qrcode;

import java.awt.*;
import java.awt.geom.Path2D;

/**
 * 边框装饰父类
 */
public abstract class QRBorder {

    protected int mWidth;

    protected int mHeight;

    protected Rectangle mInsideRect;

    protected int mBoxSize;

    protected int mWidthLeftBoxCount;
    protected int mWidthRightBoxCount;
    protected int mHeightTopBoxCount;
    protected int mHeightBottomBoxCount;

    public QRBorder() {
    }

    public int getWidth() {
        return mWidth;
    }

    public int getHeight() {
        return mHeight;
    }

    public Rectangle getInsideArea() {
        return new Rectangle(mInsideRect);
    }

    public int getBoxSize() {
        return mBoxSize;
    }

    public abstract Path2D getClipPath();

    public int getWidthExtendBoxCount() {
        return mWidthLeftBoxCount;
    }

    public int getHeightExtendBoxCount() {
        return mHeightTopBoxCount;
    }
}
