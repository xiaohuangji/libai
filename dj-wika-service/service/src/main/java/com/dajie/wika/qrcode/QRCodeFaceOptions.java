package com.dajie.wika.qrcode;

import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Created by robert on 13-12-23.
 */
public class QRCodeFaceOptions implements QRCodeOptionsInterface {
    private static final int DEFAULT_SIZE = 500;
    private static final Color PREDEFINED_COLOR[] = new Color[]{
            Color.getColor("#dd349d"), Color.getColor("#868686"), Color.getColor("#128b37"), Color.getColor("#287ed4"),
    };

    public String mQrContent;
    public BufferedImage mFaceBmp;

    public ErrorCorrectionLevel errorLevel;

    public int mSize;
    public Color mColor = Color.getColor("#dd349d");

    @Override
    public void checkArguments() {
        if (mQrContent == null) {
            throw new IllegalArgumentException("content should not be null.");
        }

        if (mFaceBmp == null) {
            throw new IllegalArgumentException("must have a background image");
        }

        if (mSize <= 0) {
            mSize = DEFAULT_SIZE;
        }

//        if (mColor <= 0) {
//            mColor = PREDEFINED_COLOR[(int) (Math.random() * 4)];
//        }
    }

    @Override
    public QRCodePixelReleaseEffect getQRCodeReleaseEffect() {
        return QRCodePixelReleaseEffect.FACE;
    }

    @Override
    public String getContent() {
        return mQrContent;
    }
}
