package com.dajie.wika.qrcode;

import com.google.zxing.qrcode.encoder.ByteMatrix;
import com.google.zxing.qrcode.encoder.QRCode;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by michael on 13-12-19.
 */
public class PixelQREffect extends QREffectInterface {

    @Override
    public BufferedImage makeEffectQRCode(String content, QRCodeOptionsInterface option) {
        QRCodePixelOptions opt = (QRCodePixelOptions) option;

        QRCode qrCode = encodeQrcode(opt.qrContent, opt.errorLevel);

        ByteMatrix input = qrCode.getMatrix();
        if (input == null) {
            throw new IllegalStateException();
        }
        int inputWidth = input.getWidth();
        int inputHeight = input.getHeight();
        int qrWidth = inputWidth;
        int qrHeight = inputHeight;
        int outputWidth = Math.max(opt.defaultQRSize, qrWidth);
        int outputHeight = Math.max(opt.defaultQRSize, qrHeight);

        int multiple = Math.min(outputWidth / qrWidth, outputHeight / qrHeight);

        int maskImageSize = multiple * opt.maskRectCount;
        BufferedImage maskScaleBt = (maskImageSize != 0)
                ? Util.getScaledImage(opt.maskBitmap, maskImageSize, maskImageSize) : null;

        //四周各空两个点整
        int realWidth = multiple * (inputWidth + 4);
        int realHeight = multiple * (inputHeight + 4);

        int leftPadding = multiple * 2;
        int topPadding = multiple * 2;

        BufferedImage pixelBt = Util.mosaic(opt.backgroundBitmap, realWidth, realHeight, multiple);
        float colorMatrix[] = new float[] {
                8.4616995f, -6.5064993f, -0.6551999f, 0.0f, 0.0f,
                -1.9382999f, 3.8935003f, -0.6551999f, 0.0f, 0.0f,
                -1.9382999f, -6.5064993f, 9.7448f, 0.0f, 0.0f,
                0.0f, 0.0f, 0.0f, 1.0f, 0.0f
        };
        pixelBt = Util.applyColorMatrix(pixelBt, colorMatrix);

        BufferedImage out = new BufferedImage(realWidth, realHeight, pixelBt.getType());
        Graphics2D g = (Graphics2D) out.getGraphics();
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setColor(Color.getColor("#FFFFFFFF"));
        g.fillRect(0, 0, realWidth, realHeight);
        g.drawImage(pixelBt, 0, 0, null);

        g.setColor(Color.WHITE);
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 210.0f / 255.0f));

        //因为周边有2 box的空位置，所以现将此填满
        //上
        g.fillRect(0, 0, realWidth, 2 * multiple);
        //下
        g.fillRect(0, realHeight - 2 * multiple, realWidth, 2 * multiple);
        //左
        g.fillRect(0, 0, 2 * multiple, realHeight);
        //右
        g.fillRect(realWidth - 2 * multiple, 0, multiple * 2, realHeight);

        for (int inputY = 0, outputY = topPadding; inputY < inputHeight; inputY++, outputY += multiple) {
            for (int inputX = 0, outputX = leftPadding; inputX < inputWidth; inputX++, outputX += multiple) {
                if (input.get(inputX, inputY) == 1) {
                    g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 100.0f / 255.0f));
                    g.setColor(Color.BLACK);
                    g.fillRect(outputX, outputY, multiple, multiple);
                } else {
                    g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 210.0f / 255.0f));
                    g.setColor(Color.WHITE);
                    g.fillRect(outputX, outputY, multiple, multiple);
                }
            }
        }

        if (maskScaleBt != null) {
            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 150.0f / 255.0f));
            for (int i = 0; i < (inputWidth + 4); i += opt.maskRectCount) {
                for (int y = 0; y < (inputWidth + 4); y += opt.maskRectCount) {
                    g.drawImage(maskScaleBt, i * multiple, y * multiple, null);
                }
            }
        }

        if (opt.frontBitmap != null) {
            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 85.0f / 255.0f));
            g.drawImage(opt.frontBitmap, 0, 0, realWidth, realHeight, null);
        }

        return out;
    }



}
