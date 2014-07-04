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
public class PixelBorderQREffect extends PixelQREffect {

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
        int orgWidth = Math.max(opt.defaultQRSize, qrWidth);
        int orgHeight = Math.max(opt.defaultQRSize, qrHeight);

        QRBorder qrBorder = new QRCircleBorder(orgWidth, orgHeight, input.getWidth());
        int multiple = qrBorder.getBoxSize();

        BufferedImage pixelBt = Util.mosaic(opt.backgroundBitmap, qrBorder.getWidth(), qrBorder.getHeight(), multiple);

        BufferedImage out = new BufferedImage(qrBorder.getWidth(), qrBorder.getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = (Graphics2D) out.getGraphics();
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setColor(new Color(255, 255, 255, 200));
        g.fillRect(0, 0, out.getWidth(), out.getHeight());

        float colorMatrix[] = new float[]{
                2.853047f, -2.0667968f, -0.208125f, 0.0f, 0.0f,
                -0.61570317f, 1.4019532f, -0.208125f, 0.0f, 0.0f,
                -0.61570317f, -2.0667968f, 3.260625f, 0.0f, 0.0f,
                0.0f, 0.0f, 0.0f, 1.0f, 0.0f
        };
        pixelBt = Util.applyColorMatrix(pixelBt, colorMatrix);

        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 150.0f/255.0f));
        g.drawImage(pixelBt, 0, 0, null);

        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 160.0f/255.0f));
        drawQRCodeRect(g, input, qrBorder.getInsideArea(), qrBorder.getBoxSize(), inputWidth, inputHeight);

        //调整input矩阵
        //左上角
        for (int x = 0, x1 = qrWidth - 9; x < 8; ++x, ++x1) {
            for (int y = 0, y1 = qrHeight - 9; y < 8; ++y, ++y1) {
                input.set(x, y, input.get(x1, y1));
            }
        }

        //右上角
        for (int x = qrWidth - 9, x1 = qrWidth - 9; x < qrWidth - 1; ++x, ++x1) {
            for (int y = 0, y1 = qrHeight - 9; y < 8; ++y, ++y1) {
                input.set(x, y, input.get(x1, y1));
            }
        }

        //左下角
        for (int x = 0, x1 = qrWidth - 9; x < 8; ++x, ++x1) {
            for (int y = qrHeight - 9, y1 = qrHeight - 9; y < qrHeight - 1; ++y, ++y1) {
                input.set(x, y, input.get(x1, y1));
            }
        }

        //为了提高效率，先将二维码绘制到一个buffer
        Rectangle qrRegionRect = qrBorder.getInsideArea();
        BufferedImage qrCodeBuffer = new BufferedImage((int) qrRegionRect.getWidth(), (int) qrRegionRect.getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g1 = (Graphics2D) qrCodeBuffer.getGraphics();
        g1.setColor(new Color(0, 0, 0, 0));
        g1.fillRect(0, 0, qrCodeBuffer.getWidth(), qrCodeBuffer.getHeight());

        //将二维码绘制到buffer
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
        drawQRCodeRect(g, input, new Rectangle(0, 0, (int) qrRegionRect.getWidth(), (int) qrRegionRect.getHeight()), qrBorder.getBoxSize(), inputWidth, inputHeight);

        //左侧
        int moveLeft = (2 + inputWidth) * qrBorder.getBoxSize();
        qrRegionRect.x = qrRegionRect.x - moveLeft;
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 160.0f/255.0f));
        g.drawImage(qrCodeBuffer, qrRegionRect.x, qrRegionRect.y, null);
        //上侧
        qrRegionRect = qrBorder.getInsideArea();
        int moveTop = (2 + inputHeight) * qrBorder.getBoxSize();
        qrRegionRect.y = qrRegionRect.y - moveTop;
        g.drawImage(qrCodeBuffer, qrRegionRect.x, qrRegionRect.y, null);
        //右
        qrRegionRect = qrBorder.getInsideArea();
        int moveRight = (2 + inputWidth) * qrBorder.getBoxSize();
        qrRegionRect.x = qrRegionRect.x + moveRight;
        g.drawImage(qrCodeBuffer, qrRegionRect.x, qrRegionRect.y, null);
        //下
        qrRegionRect = qrBorder.getInsideArea();
        int moveBottom = (2 + inputHeight) * qrBorder.getBoxSize();
        qrRegionRect.y = qrRegionRect.y + moveBottom;
        g.drawImage(qrCodeBuffer, qrRegionRect.x, qrRegionRect.y, null);

        //左上
        qrRegionRect = qrBorder.getInsideArea();
        qrRegionRect.x = qrRegionRect.x - moveLeft;
        qrRegionRect.y = qrRegionRect.y - moveTop;
        g.drawImage(qrCodeBuffer, qrRegionRect.x, qrRegionRect.y, null);

        //右上
        qrRegionRect = qrBorder.getInsideArea();
        qrRegionRect.x = qrRegionRect.x + moveRight;
        qrRegionRect.y = qrRegionRect.y - moveTop;
        g.drawImage(qrCodeBuffer, qrRegionRect.x, qrRegionRect.y, null);

        //左下
        qrRegionRect = qrBorder.getInsideArea();
        qrRegionRect.x = qrRegionRect.x - moveRight;
        qrRegionRect.y = qrRegionRect.y + moveTop;
        g.drawImage(qrCodeBuffer, qrRegionRect.x, qrRegionRect.y, null);

        //右下
        qrRegionRect = qrBorder.getInsideArea();
        qrRegionRect.x = qrRegionRect.x + moveRight;
        qrRegionRect.y = qrRegionRect.y + moveTop;
        g.drawImage(qrCodeBuffer, qrRegionRect.x, qrRegionRect.y, null);

        return out;
    }

    private void drawQRCodeRect(Graphics2D g, ByteMatrix input, Rectangle qrRegionRect, int multiple
            , int qrWidth, int qrHeight) {
        Rectangle qrBox = new Rectangle();
        int r = (int) (multiple * 0.4);
        for (int inputY = 0, outputY = qrRegionRect.y; inputY < qrHeight; inputY++, outputY += multiple) {
            for (int inputX = 0, outputX = qrRegionRect.x; inputX < qrWidth; inputX++, outputX += multiple) {
                qrBox.setBounds(outputX, outputY, multiple, multiple);

                if (input.get(inputX, inputY) == 1) {
                    Util.drawRoundRect(g, qrBox, r,
                            !(isSet(input, inputX - 1, inputY - 1) || isSet(input, inputX, inputY - 1) || isSet(input, inputX - 1, inputY)),
                            !(isSet(input, inputX, inputY - 1) || isSet(input, inputX + 1, inputY - 1) || isSet(input, inputX + 1, inputY)),
                            !(isSet(input, inputX, inputY + 1) || isSet(input, inputX, inputY - 1) || isSet(input, inputX - 1, inputY)),
                            !(isSet(input, inputX + 1, inputY) || isSet(input, inputX + 1, inputY + 1) || isSet(input, inputX, inputY + 1)), null);
                }
            }
        }
    }

    private boolean isSet(ByteMatrix matrix, int row, int column) {
        if (matrix == null) {
            return false;
        }

        if (row >= matrix.getWidth() || row < 0) return false;
        if (column >= matrix.getHeight() || column < 0) return false;

        return matrix.get(row, column) == 1;
    }

}
