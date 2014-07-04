package com.dajie.wika.qrcode;

import com.google.zxing.qrcode.encoder.ByteMatrix;
import com.google.zxing.qrcode.encoder.QRCode;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class GradientQREffect extends QREffectInterface {

    @Override
    public BufferedImage makeEffectQRCode(String content, QRCodeOptionsInterface options) {
        QRCodeGradientOptions opt = (QRCodeGradientOptions) options;

        QRCode qrCode = encodeQrcode(opt.qrContent, opt.errorLevel);

        ByteMatrix input = qrCode.getMatrix();
        if (input == null) {
            throw new IllegalStateException();
        }
        int inputWidth = input.getWidth();
        int inputHeight = input.getHeight();
        // 四周各有1点阵的边框和2点阵的padding
        int qrWidth = inputWidth + 6;
        int qrHeight = inputHeight + 6;
        int outputWidth = Math.max(opt.defaultQRSize, qrWidth);
        int outputHeight = Math.max(opt.defaultQRSize, qrHeight);

        int multiple = Math.min(outputWidth / qrWidth, outputHeight / qrHeight);
        int realWidth = qrWidth * multiple;
        int realHeight = qrHeight * multiple;

        Color foregroundColor;
        Color backgroundColor = Color.WHITE;

        BufferedImage out = new BufferedImage(realWidth, realHeight, BufferedImage.TYPE_4BYTE_ABGR);
        Graphics2D g = out.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setColor(backgroundColor);
        g.fillRect(0, 0, realWidth, realHeight);

        // 渐变开始结束颜色
        Color startColor = opt.startColor;
        Color endColor = opt.endColor;

        int roundRectRadius = (int) (0.3 * multiple);

        Rectangle rectangle = new Rectangle();

        // inputX从－3到inputWidth+2, inputY从－3到inputHeight+2
        for (int inputY = -3, outputY = 0; inputY < inputHeight + 3; inputY++, outputY += multiple) {
            for (int inputX = -3, outputX = 0; inputX < inputWidth + 3; inputX++, outputX += multiple) {
                rectangle.setBounds(outputX, outputY, multiple, multiple);

                //float ratio = (float) (inputY + 3) / qrHeight;
                //foregroundColor = Util.getGradientColor(startColor, endColor, ratio);
                foregroundColor = getGradientColorByCurve(startColor, endColor, 0, qrHeight, inputY + 3);
                g.setColor(foregroundColor);

                // 边框和padding不做液化
                if (inputX < 0 || inputX > inputWidth - 1 || inputY < 0 || inputY > inputWidth - 1) {
                    if (isSet(input, inputX, inputY)) {
                        g.fillRect(outputX, outputY, multiple, multiple);
                    }
                } else {
                    if (isSet(input, inputX, inputY)) {
                        Util.drawRoundRect(g, rectangle, roundRectRadius,
                                !(isSet(input, inputX - 1, inputY - 1)
                                        || isSet(input, inputX, inputY - 1)
                                        || isSet(input, inputX - 1, inputY)),
                                !(isSet(input, inputX, inputY - 1)
                                        || isSet(input, inputX + 1,
                                        inputY - 1)
                                        || isSet(input, inputX + 1, inputY)),
                                !(isSet(input, inputX, inputY + 1)
                                        || isSet(input, inputX - 1,
                                        inputY + 1)
                                        || isSet(input, inputX - 1, inputY)),
                                !(isSet(input, inputX + 1, inputY)
                                        || isSet(input, inputX + 1,
                                        inputY + 1)
                                        || isSet(input, inputX, inputY + 1)),
                                foregroundColor);
                    } else {
                        Util.drawAntiRoundRect(g, rectangle, roundRectRadius,
                                isSet(input, inputX, inputY - 1)
                                        && isSet(input, inputX - 1, inputY),
                                isSet(input, inputX, inputY - 1)
                                        && isSet(input, inputX + 1, inputY),
                                isSet(input, inputX - 1, inputY)
                                        && isSet(input, inputX, inputY + 1),
                                isSet(input, inputX, inputY + 1)
                                        && isSet(input, inputX + 1, inputY), backgroundColor, foregroundColor);
                    }
                }
            }
        }

        int frontWidth = (int) (0.4 * realWidth);
        int frontHeight = (int) (0.4 * realHeight);

        Color binStartColor = getGradientColorByCurve(startColor, endColor, 0, realHeight, (realHeight - frontHeight) / 2.0f);
        Color binEndColor = getGradientColorByCurve(startColor, endColor, 0, realHeight, (realHeight + frontHeight) / 2.0f);

        Rectangle frontRect = new Rectangle((realWidth - frontWidth) / 2, (realHeight - frontHeight) / 2, frontWidth, frontHeight);
        BufferedImage scaleFront = Util.getScaledImage(opt.frontBitmap, frontWidth, frontHeight);

        //现将图片做一次缩放，目的是为了减小处理的像素数量
        scaleFront = convertGrayImg(scaleFront);
        BufferedImage front = bitmapHSB(scaleFront, binStartColor, binEndColor);

        BufferedImage scaleBorder = Util.getScaledImage(opt.borderBitmap, frontWidth, frontHeight);
        BufferedImage border = borderGradient(scaleBorder, Util.colorIntValue(binStartColor), Util.colorIntValue(binEndColor));

        // 加边框
        front = borderFront(border, front);
        // 遮盖切割
        BufferedImage scaleMask = Util.getScaledImage(opt.maskBitmap, frontWidth + 12, frontHeight + 12);
        front = maskFront(scaleMask, front);

        g.drawImage(front, (realWidth - front.getWidth()) / 2, (realHeight - front.getHeight()) / 2, null);

        return out;
    }

    private boolean isSet(ByteMatrix input, int inputX, int inputY) {
        if (inputX == -3 || inputX == input.getWidth() + 2 || inputY == -3 || inputY == input.getHeight() + 2)
            return true;
        if ((inputX >= -2 && inputX <= -1) || (inputX >= input.getWidth() && inputX <= input.getWidth() + 1))
            return false;
        if ((inputY >= -2 && inputY <= -1) || (inputY >= input.getHeight() && inputY <= input.getHeight() + 1))
            return false;
        if (inputX < -3 || inputX > input.getWidth() + 2 || inputY < -3 || inputY > input.getHeight() + 2)
            return false;
        return input.get(inputX, inputY) == 1;
    }

    private BufferedImage bitmapHSB(BufferedImage bt, Color startColor, Color endColor) {
        float[] start = new float[3];
        float[] end = new float[3];
        Util.rgbToHsv(startColor, start);
        start[1] = 0;
        start[2] = 0;
        Util.rgbToHsv(endColor, end);
        end[1] = 0;
        end[2] = 0;

        bt = covertBitmapWithHSBWithHChanged(bt, start, end);

        float colorMatrix[] = new float[]{
                2.97144f, 0.0f, 0.0f, 0.0f, -150.08688f,
                0.0f, 2.97144f, 0.0f, 0.0f, -150.08688f,
                0.0f, 0.0f, 2.97144f, 0.0f, -150.08688f,
                0.0f, 0.0f, 0.0f, 1.0f, 0.0f
        };
        BufferedImage out = Util.applyColorMatrix(bt, colorMatrix);
        return out;
    }

    private BufferedImage covertBitmapWithHSBWithHChanged(BufferedImage bt, float[] startHSVAdjust, float[] endHSVAdjust) {
        int width = bt.getWidth();
        int height = bt.getHeight();

        float hueDeta = (endHSVAdjust[0] - startHSVAdjust[0]) / height;
        float[] pixelHSV = new float[3];
        int alpha = 0xFF << 24;
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                int color = bt.getRGB(i, j);
                Util.rgbToHsv((color >> 16) & 0xFF, (color >> 8) & 0xFF, (color) & 0xFF, pixelHSV);

                pixelHSV[0] = startHSVAdjust[0] + hueDeta * i;
                pixelHSV[1] = 1 - pixelHSV[2];
                color = Util.hsvToRgb(pixelHSV[0], pixelHSV[1], pixelHSV[2]);
                color = alpha | color;
                bt.setRGB(i, j, color);
            }
        }
        return bt;
    }

    private BufferedImage borderFront(BufferedImage border, BufferedImage front) {
        BufferedImage result = Util.getScaledImage(front, border.getWidth(), border.getHeight());
        Graphics2D g = (Graphics2D) result.getGraphics();
        g.drawImage(border, 0, 0, null);
        g.dispose();

        return result;
    }

    private BufferedImage maskFront(BufferedImage mask, BufferedImage front) {
        int width = mask.getWidth();
        int height = mask.getHeight();
        BufferedImage result = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = (Graphics2D) result.getGraphics();
        g.drawImage(front, (width - front.getWidth()) / 2, (height - front.getHeight()) / 2, null);

        g.setComposite(AlphaComposite.getInstance(AlphaComposite.DST_IN, 1.0f));
        g.drawImage(mask, 0, 0, null);
        g.dispose();

        return result;
    }

    private BufferedImage borderGradient(BufferedImage border, int startColor, int endColor) {
        int width = border.getWidth();
        int height = border.getHeight();

        int color;
        int gradientColor;
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                color = border.getRGB(y, x);
                if (((color & 0xFFFFFF) == 0xFFFFFF)
                        || ((color & 0xff000000) == 0)) {
                } else {
                    gradientColor = Util.getGradientColor(startColor, endColor, y / (float) height);
                    gradientColor = (gradientColor & 0x00ffffff) | (color & 0xff000000);
                    border.setRGB(y, x, gradientColor);
                }
            }
        }

        return border;
    }

    public BufferedImage convertGrayImg(BufferedImage bt) {
        int w = bt.getWidth();
        int h = bt.getHeight();

        int color;
        int alpha = 0xFF << 24;
        for (int i = 0; i < h; i++) {
            for (int j = 0; j < w; j++) {
                // 获得像素的颜色
                color = bt.getRGB(i, j);
                int red = (color >> 16) & 0xFF;
                int green = (color >> 8) & 0xFF;
                int blue = color & 0xFF;
                color = (red * 77 + green * 151 + blue * 28) >> 8;
                color = alpha | (color << 16) | (color << 8) | color;
                bt.setRGB(i, j, color);
            }
        }
        return bt;
    }

    /**
     * 二次曲线获取渐变色
     *
     * @param startColor
     * @param endColor
     * @param start
     * @param end
     * @param offset     相对于start的偏移
     * @return
     */
    private Color getGradientColorByCurve(Color startColor, Color endColor, float start, float end, float offset) {
        if (offset <= 0) {
            return startColor;
        }
        if (offset >= end - start) {
            return endColor;
        }

        float x = start + offset;
        float x3 = (start + end) / 2.0f;
        float y3;

        int a1 = startColor.getAlpha();
        int a2 = endColor.getAlpha();
        y3 = a1 + (a2 - a1) / 4.0f;
        int a3 = (int) getParabolaY(a1, a2, y3, start, end, x3, x);

        int r1 = startColor.getRed();
        int r2 = endColor.getRed();
        y3 = r1 + (r2 - r1) / 4.0f;
        int r3 = (int) getParabolaY(r1, r2, y3, start, end, x3, x);

        int g1 = startColor.getGreen();
        int g2 = endColor.getGreen();
        y3 = g1 + (g2 - g1) / 4.0f;
        int g3 = (int) getParabolaY(g1, g2, y3, start, end, x3, x);

        int b1 = startColor.getBlue();
        int b2 = endColor.getBlue();
        y3 = b1 + (b2 - b1) / 4.0f;
        int b3 = (int) getParabolaY(b1, b2, y3, start, end, x3, x);

        return new Color(r3, g3, b3, a3);
    }

    private float getParabolaY(float y1, float y2, float y3, float x1, float x2, float x3, float x) {
        float p = y1 / ((x1 - x2) * (x1 - x3));
        float q = y2 / ((x2 - x1) * (x2 - x3));
        float r = y3 / ((x3 - x1) * (x3 - x2));
        float a = p + q + r;
        float b = -p * (x2 + x3) - q * (x1 + x3) - r * (x1 + x2);
        float c = p * x2 * x3 + q * x1 * x3 + r * x1 * x2;
        return a * x * x + b * x + c;
    }
}
