package com.dajie.wika.qrcode;

import com.google.zxing.WriterException;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.google.zxing.qrcode.encoder.Encoder;
import com.google.zxing.qrcode.encoder.QRCode;

import java.awt.image.BufferedImage;

/**
 * Created by michael on 13-12-19.
 */
public abstract class QREffectInterface {

    abstract BufferedImage makeEffectQRCode(String content, QRCodeOptionsInterface opt);

    protected QRCode encodeQrcode(String content, ErrorCorrectionLevel level) {
        if (content == null || "".equals(content)) {
            return null;
        }

        QRCode code = null;
        try {
            code = Encoder.encode(content, level, null);
        } catch (WriterException e) {
            e.printStackTrace();
        }
        return code;
    }

}
