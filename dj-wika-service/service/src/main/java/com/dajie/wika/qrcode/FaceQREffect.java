package com.dajie.wika.qrcode;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

import com.google.zxing.qrcode.encoder.ByteMatrix;
import com.google.zxing.qrcode.encoder.QRCode;

/**
 * Created by robert on 13-12-23.
 */
public class FaceQREffect extends QREffectInterface {
    private static final float CENTER_PERCENT = 0.3f;
    private static final int DEFAULT_BORDER = 2;

    @Override
    BufferedImage makeEffectQRCode(String content, QRCodeOptionsInterface opt) {
        QRCodeFaceOptions options = (QRCodeFaceOptions) opt;
        int width = options.mSize;
        int height = options.mSize;

        Color color = options.mColor;
        BufferedImage faceBmp = options.mFaceBmp;
        int border = DEFAULT_BORDER;

        //如果图片小余二维码输出的大小,将图片处理成一样的大小
        if (faceBmp.getWidth() < width) {
            faceBmp = Util.getScaledImage(faceBmp, width, height);
        }

        if (faceBmp.getWidth() > faceBmp.getHeight()) {
            if (faceBmp.getWidth() > width) {
                faceBmp = Util.getScaledImage(faceBmp, width, width * faceBmp.getHeight() / faceBmp.getWidth());
            }
        } else {
            if (faceBmp.getHeight() > width) {
                faceBmp = Util.getScaledImage(faceBmp, width * faceBmp.getWidth() / faceBmp.getHeight(), width);
            }
        }

        int faceLeftPos = (width - faceBmp.getWidth()) / 2;
        int faceTopPos = (width - faceBmp.getHeight()) / 2;

        QRCode qrcode = encodeQrcode(options.getContent(), options.errorLevel);
        ByteMatrix input = qrcode.getMatrix();
        if (input == null) {
            throw new IllegalStateException();
        }
        int inputWidth = input.getWidth();
        int inputHeight = input.getHeight();
        int qrWidth = inputWidth + border;
        int qrHeight = inputHeight + border;
        int outputWidth = Math.max(width, qrWidth);
        int outputHeight = Math.max(height, qrHeight);

        int multiple = Math.min(outputWidth / qrWidth, outputHeight / qrHeight);
        int leftPadding = (outputWidth - (inputWidth * multiple)) / 2;
        int topPadding = (outputHeight - (inputHeight * multiple)) / 2;

        int maxCenterSize = (int) (width * CENTER_PERCENT);
        int centerLeft = (width - maxCenterSize) / 2;
        Rectangle centerRect = new Rectangle(centerLeft, centerLeft, maxCenterSize, maxCenterSize);

        Rectangle faceRect = new Rectangle();
        float faceScaleCoefficient = 1.0f;

        // 没法做人脸检测，默认无脸
        boolean findFace = false;
        faceBmp = Util.getScaledImage(faceBmp, maxCenterSize, maxCenterSize);
        faceLeftPos = (width - faceBmp.getWidth()) / 2;
        faceTopPos = (width - faceBmp.getHeight()) / 2;
        faceRect = centerRect;

        faceBmp = Util.convolutionFilter(faceBmp);
        faceBmp = Util.bigBrotherCustomFilter(faceBmp, color);

        BufferedImage out = new BufferedImage(width, height, BufferedImage.TYPE_4BYTE_ABGR);
        Graphics2D g = out.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, width, height);

        g.setColor(color);

        for (int inputY = 0, outputY = topPadding; inputY < inputHeight; inputY++, outputY += multiple) {
            for (int inputX = 0, outputX = leftPadding; inputX < inputWidth; inputX++, outputX += multiple) {
                if (input.get(inputX, inputY) == 1) {
                    if (isFinderPatterns(input, inputX, inputY)) {
                        g.fillRect(outputX, outputY, multiple, multiple);
                        continue;
                    }

                    if (!(findFace && isInArea(outputX, outputY, faceRect))) {
                        g.setColor(color);
                        g.fillRect(outputX, outputY, multiple, multiple);
                    }
                }
            }
        }

        if (!findFace) {
            g.drawImage(faceBmp, faceLeftPos, faceTopPos, null);
        }

        return out;
    }

    private boolean isFinderPatterns(ByteMatrix matrix, int row, int col) {
        int width = matrix.getWidth();
        int height = matrix.getHeight();

        if (row >= 0 && row <= 6 && col >= 0 && col <= 6) {
            return true;
        }
        if (row >= 0 && row <= 6 && col >= width - 7 && col <= width - 1) {
            return true;
        }
        if (col >= 0 && col <= 6 && row >= height - 7 && row <= height - 1) {
            return true;
        }
        return false;
    }

    private boolean isInArea(int x, int y, Rectangle area) {
        return x > area.x && x < area.x + area.width && y > area.y && y < area.y + area.height;
    }

}
