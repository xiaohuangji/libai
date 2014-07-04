package com.dajie.wika.qrcode;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;

import com.dajie.common.file.enums.FileSavedType;
import com.dajie.common.file.model.UploadReturnModel;
import com.dajie.common.file.service.FileUploadService;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

/**
 * Created by robert on 14-1-6.
 */
public class QRCodeFactory {
    public static final int DEFAULT_QR_SIZE = 960;
    public static final ErrorCorrectionLevel DEFAULT_ERROR_CORRECTION_LEVEL = ErrorCorrectionLevel.H;
    static String absolutePath =Thread.currentThread()
            .getContextClassLoader().getResource("").getFile();

    public static byte[] makeQrcode(int templateId, String qrContent, String url) throws IOException {
        BufferedImage out = null;
        BufferedImage image = ImageIO.read(new URL(url));

        return makeQrcode(templateId, qrContent, image);
    }

    public static byte[] makeQrcode(int templateId, String qrContent, BufferedImage image) throws IOException {
        if (image == null) {
            return null;
        }

        BufferedImage out = null;

        if (templateId == 1) {
            QRCodeGradientOptions opt = new QRCodeGradientOptions();
            opt.qrContent = qrContent;
            opt.defaultQRSize = DEFAULT_QR_SIZE;
            opt.startColor = new Color(247, 4, 85);
            opt.endColor = new Color(249, 85, 35);
            try {
                opt.maskBitmap = ImageIO.read(new File(absolutePath+"image/gradient_mask1.png"));
                opt.borderBitmap = ImageIO.read(new File(absolutePath+"image/gradient_border1.png"));
            } catch (IOException e) {
                e.printStackTrace();
            }
            opt.frontBitmap = image;
            opt.errorLevel = DEFAULT_ERROR_CORRECTION_LEVEL;
            out = QRCodeGenerator.createQRCode(opt);
        } else if (templateId == 2) {
            QRCodeGradientOptions opt = new QRCodeGradientOptions();
            opt.qrContent = qrContent;
            opt.defaultQRSize = DEFAULT_QR_SIZE;
            opt.startColor = new Color(39, 181, 42);
            opt.endColor = new Color(28, 160, 138);
            try {
                opt.maskBitmap = ImageIO.read(new File(absolutePath+"image/gradient_mask2.png"));
                opt.borderBitmap = ImageIO.read(new File(absolutePath+"image/gradient_border2.png"));
            } catch (IOException e) {
                e.printStackTrace();
            }
            opt.frontBitmap = image;
            opt.errorLevel = DEFAULT_ERROR_CORRECTION_LEVEL;
            out = QRCodeGenerator.createQRCode(opt);
        } else if (templateId == 3) {
            QRCodeGradientOptions opt = new QRCodeGradientOptions();
            opt.qrContent = qrContent;
            opt.defaultQRSize = DEFAULT_QR_SIZE;
            opt.startColor = new Color(44, 189, 249);
            opt.endColor = new Color(28, 81, 232);
            try {
                opt.maskBitmap = ImageIO.read(new File(absolutePath+"image/gradient_mask3.png"));
                opt.borderBitmap = ImageIO.read(new File(absolutePath+"image/gradient_border4.png"));
            } catch (IOException e) {
                e.printStackTrace();
            }
            opt.frontBitmap = image;
            opt.errorLevel = DEFAULT_ERROR_CORRECTION_LEVEL;
            out = QRCodeGenerator.createQRCode(opt);
        } else if (templateId == 4) {
            QRCodeGradientOptions opt = new QRCodeGradientOptions();
            opt.qrContent = qrContent;
            opt.defaultQRSize = DEFAULT_QR_SIZE;
            opt.startColor = new Color(241, 131, 7);
            opt.endColor = new Color(251, 72, 19);
            try {
                opt.maskBitmap = ImageIO.read(new File(absolutePath+"image/gradient_mask4.png"));
                opt.borderBitmap = ImageIO.read(new File(absolutePath+"image/gradient_border4.png"));
            } catch (IOException e) {
                e.printStackTrace();
            }
            opt.frontBitmap = image;
            opt.errorLevel = DEFAULT_ERROR_CORRECTION_LEVEL;
            out = QRCodeGenerator.createQRCode(opt);
        } else if (templateId == 5) {
            QRCodeFaceOptions opt = new QRCodeFaceOptions();
            opt.mQrContent = qrContent;
            opt.mSize = DEFAULT_QR_SIZE;
            opt.mColor = new Color(214, 1, 143);
            opt.mFaceBmp = image;
            opt.errorLevel = DEFAULT_ERROR_CORRECTION_LEVEL;
            out = QRCodeGenerator.createQRCode(opt);
        } else if (templateId == 6) {
            QRCodeFaceOptions opt = new QRCodeFaceOptions();
            opt.mQrContent = qrContent;
            opt.mSize = DEFAULT_QR_SIZE;
            opt.mColor = new Color(115, 115, 115);
            opt.mFaceBmp = image;
            opt.errorLevel = DEFAULT_ERROR_CORRECTION_LEVEL;
            out = QRCodeGenerator.createQRCode(opt);
        } else if (templateId == 7) {
            QRCodeFaceOptions opt = new QRCodeFaceOptions();
            opt.mQrContent = qrContent;
            opt.mSize = DEFAULT_QR_SIZE;
            opt.mColor = new Color(28, 99, 209);
            opt.mFaceBmp = image;
            opt.errorLevel = DEFAULT_ERROR_CORRECTION_LEVEL;
            out = QRCodeGenerator.createQRCode(opt);
        } else if (templateId == 8) {
            QRCodeFaceOptions opt = new QRCodeFaceOptions();
            opt.mQrContent = qrContent;
            opt.mSize = DEFAULT_QR_SIZE;
            opt.mColor = new Color(16, 125, 40);
            opt.mFaceBmp = image;
            opt.errorLevel = DEFAULT_ERROR_CORRECTION_LEVEL;
            out = QRCodeGenerator.createQRCode(opt);
        } else {
            QRCodeGradientOptions opt = new QRCodeGradientOptions();
            opt.qrContent = qrContent;
            opt.defaultQRSize = DEFAULT_QR_SIZE;
            opt.startColor = new Color(241, 131, 7);
            opt.endColor = new Color(251, 72, 19);
            try {
                opt.maskBitmap = ImageIO.read(new File(absolutePath+"image/gradient_mask2.png"));
                opt.borderBitmap = ImageIO.read(new File(absolutePath+"image/gradient_border2.png"));
            } catch (IOException e) {
                e.printStackTrace();
            }
            opt.frontBitmap = image;
            opt.errorLevel = DEFAULT_ERROR_CORRECTION_LEVEL;
            out = QRCodeGenerator.createQRCode(opt);
        }

        byte[] data = null;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        if (out != null) {
            Util.saveImageToStream(out, baos);
            data = baos.toByteArray();
        }
        return data;
    }

    public static void main(String[] args) throws IOException {
    	System.out.println(absolutePath);
    	
    	byte[] data=QRCodeFactory.makeQrcode(1, "i am here", "http://1.f1.dajieimg.com/group1/M00/39/CF/CgpAmVK6qaeASz1rAAABHVKxYO8633c.jpg");
    	UploadReturnModel  result=FileUploadService.uploadFromBytes(data, "1.jpg", FileSavedType.wika_avatar);
    	System.out.println(result.getLocalUrl());
    	
	}
}
