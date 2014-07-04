package com.dajie.wika.qrcode;

import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Created by zhangdi on 13-12-20.
 */
public class QRCodeGradientOptions implements QRCodeOptionsInterface {

    /**
     * 遮盖的图片
     */
    public BufferedImage maskBitmap;

    /**
     * 带边框的图片
     */
    public BufferedImage borderBitmap;

    /**
     * 进行遮盖的图片
     */
    public BufferedImage frontBitmap;

    /**
     * 渐变开始颜色
     */
    public Color startColor = Color.RED;

    /**
     * 渐变结束颜色
     */
    public Color endColor = Color.BLACK;

    /**
     * 默认的QR输出大小
     */
    public int defaultQRSize;

    /**
     * 编码内容
     */
    public String qrContent;

    /**
     * 纠错级别
     */
    public ErrorCorrectionLevel errorLevel;

    @Override
    public void checkArguments() {
        if (defaultQRSize == 0) {
            throw new IllegalArgumentException("defaultQRSize can't be 0");
        }
        if (maskBitmap == null) {
            throw new IllegalArgumentException("maskBitmap can't be NULL");
        }
        if (borderBitmap == null) {
            throw new IllegalArgumentException("borderBitmap can't be NULL");
        }
        if (frontBitmap == null) {
            throw new IllegalArgumentException("frontBitmap can't be NULL");
        }
        if (errorLevel == null) {
            errorLevel = ErrorCorrectionLevel.H;
        }
    }

    @Override
    public QRCodePixelReleaseEffect getQRCodeReleaseEffect() {
        return QRCodePixelReleaseEffect.GRADIENT;
    }

    @Override
    public String getContent() {
        return qrContent;
    }

}
