package com.dajie.wika.qrcode;

import com.google.zxing.Binarizer;
import com.google.zxing.LuminanceSource;
import com.google.zxing.NotFoundException;
import com.google.zxing.RGBLuminanceSource;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by robert on 14-1-2.
 */
public class Util {

    public static BufferedImage convertToArgb(BufferedImage image) {
        if (image.getType() == BufferedImage.TYPE_INT_ARGB) {
            return image;
        }

        BufferedImage result = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = (Graphics2D) result.getGraphics();
        g.drawImage(image, 0, 0, null);
        g.dispose();
        return result;
    }

    public static BufferedImage getScaledImage(BufferedImage original, int w, int h) {
        BufferedImage newImage = new BufferedImage(w, h,
                original.getType());
        Graphics2D grph = (Graphics2D) newImage.getGraphics();
        grph.scale(w / (float) original.getWidth(), h / (float) original.getHeight());
        grph.drawImage(original, 0, 0, null);
        grph.dispose();

        return newImage;
    }

    public static BufferedImage mosaic(BufferedImage original, int outWidth, int outHeight, int dot) {
        BufferedImage newImage = getScaledImage(original, outWidth, outHeight);
        Graphics2D graphics2D = (Graphics2D) newImage.getGraphics();

        int dotS = dot * dot;
        int w_count = outWidth / dot;
        int h_count = outHeight / dot;

        for (int i = 0; i < w_count; i++) {
            for (int j = 0; j < h_count; j++) {
                int rr = 0;
                int gg = 0;
                int bb = 0;
                for (int k = 0; k < dot; k++) {
                    for (int l = 0; l < dot; l++) {
                        Color dotColor = new Color(newImage.getRGB(i * dot + k, j * dot + l));
                        rr += dotColor.getRed();
                        gg += dotColor.getGreen();
                        bb += dotColor.getBlue();
                    }
                }
                rr = rr / dotS;
                gg = gg / dotS;
                bb = bb / dotS;

                for (int k = 0; k < dot; k++) {
                    for (int l = 0; l < dot; l++) {
                        newImage.setRGB(i * dot + k, j * dot + l, new Color(rr, gg, bb).getRGB());
                    }
                }
            }
        }

        return newImage;
    }

    public static BufferedImage applyColorMatrix(BufferedImage image, float[] colorMatrix) {
        if (colorMatrix.length != 20) {
            return image;
        }

        int width = image.getWidth();
        int height = image.getHeight();

        int color;
        int r, g, b, a;
        int r1, g1, b1, a1;
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                color = image.getRGB(row, col);
                r = (color >> 16) & 0xFF;
                g = (color >> 8) & 0xFF;
                b = color & 0xFF;
                a = (color >> 24) & 0xFF;

                r1 = clamp(colorMatrix[0] * r + colorMatrix[1] * g + colorMatrix[2] * b + colorMatrix[3] * a + colorMatrix[4]);
                g1 = clamp(colorMatrix[5] * r + colorMatrix[6] * g + colorMatrix[7] * b + colorMatrix[8] * a + colorMatrix[9]);
                b1 = clamp(colorMatrix[10] * r + colorMatrix[11] * g + colorMatrix[12] * b + colorMatrix[13] * a + colorMatrix[14]);
                a1 = clamp(colorMatrix[15] * r + colorMatrix[16] * g + colorMatrix[17] * b + colorMatrix[18] * a + colorMatrix[19]);
                ;

                image.setRGB(row, col, (a1 << 24) | (r1 << 16) | (g1 << 8) | b1);
            }
        }

        return image;
    }

    public static int clamp(double color) {
        return color < 0 ? 0 : color > 255 ? 255 : (int) color;
    }

    public static BufferedImage binarization(BufferedImage image, int lowColor, int highStartColor, int highEndColor) {
        int width = image.getWidth();
        int height = image.getHeight();
        int pixels[] = image.getRGB(0, 0, width, height, null, 0, width);
        ;
        LuminanceSource source = new RGBLuminanceSource(width, height, pixels);
        Binarizer binarizer = new HybridBinarizer(source);

        try {
            BitMatrix matrix = binarizer.getBlackMatrix();
            int highColor;
            for (int i = 0; i < height; i++) {
                highColor = getGradientColor(highStartColor, highEndColor, i / (float) height);
                for (int j = 0; j < width; j++) {
                    if (matrix.get(j, i)) {
                        image.setRGB(j, i, highColor);
                    } else {
                        image.setRGB(j, i, lowColor);
                    }
                }
            }
        } catch (NotFoundException e) {
            e.printStackTrace();
        }

        return image;
    }

    public static int getGradientColor(int startColor, int endColor, float ratio) {
        if (ratio <= 0.000001) {
            return startColor;
        }
        if (ratio >= 1.0) {
            return endColor;
        }

        int a1 = (startColor >> 24) & 0xFF;
        int r1 = (startColor >> 16) & 0xFF;
        int g1 = (startColor >> 8) & 0xFF;
        int b1 = (startColor) & 0xFF;

        int a2 = (endColor >> 24) & 0xFF;
        int r2 = (endColor >> 16) & 0xFF;
        int g2 = (endColor >> 8) & 0xFF;
        int b2 = (endColor) & 0xFF;

        int a3 = (int) (a1 + (a2 - a1) * ratio);
        int r3 = (int) (r1 + (r2 - r1) * ratio);
        int g3 = (int) (g1 + (g2 - g1) * ratio);
        int b3 = (int) (b1 + (b2 - b1) * ratio);

        return (a3 << 24) | (r3 << 16) | (g3 << 8) | b3;
    }

    public static Color getGradientColor(Color startColor, Color endColor, float ratio) {
        if (ratio <= 0.000001) {
            return startColor;
        }
        if (ratio >= 1.0) {
            return endColor;
        }

        int a1 = startColor.getAlpha();
        int r1 = startColor.getRed();
        int g1 = startColor.getGreen();
        int b1 = startColor.getBlue();

        int a2 = endColor.getAlpha();
        int r2 = endColor.getRed();
        int g2 = endColor.getGreen();
        int b2 = endColor.getBlue();

        int a3 = (int) (a1 + (a2 - a1) * ratio);
        int r3 = (int) (r1 + (r2 - r1) * ratio);
        int g3 = (int) (g1 + (g2 - g1) * ratio);
        int b3 = (int) (b1 + (b2 - b1) * ratio);

        return new Color(r3, g3, b3, a3);
    }

    public static int colorIntValue(Color color) {
        int a = color.getAlpha();
        int r = color.getRed();
        int g = color.getGreen();
        int b = color.getBlue();

        return (a << 24) | (r << 16) | (g << 8) | b;
    }

    /**
     * 灰度图转化
     *
     * @param image
     * @return
     */
    public static BufferedImage convertImageToGray(BufferedImage image) {
        int width = image.getWidth();
        int height = image.getHeight();
        BufferedImage newImage = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_GRAY);
        Graphics2D g = (Graphics2D) newImage.getGraphics();
        g.drawImage(image, 0, 0, null);
        g.dispose();
        return newImage;
    }

    public static void drawRoundRect(Graphics2D g, Rectangle rectangle, int radius, boolean leftTop, boolean rightTop, boolean leftBottom, boolean rightBottom, Color color) {
        int x = rectangle.x;
        int y = rectangle.y;
        int width = (int) rectangle.getWidth();
        int height = (int) rectangle.getHeight();

        if (color != null) {
            g.setColor(color);
        }

        if (radius <= 0) {
            g.fillRect(x, y, width, height);
            return;
        }

        if (radius > width / 2) {
            radius = width / 2;
        }

        if (leftTop) {
            g.fillArc(x, y, 2 * radius, 2 * radius, 90, 90);
        } else {
            g.fillRect(x, y, radius, radius);
        }

        if (rightTop) {
            g.fillArc(x + width - 2 * radius, y, 2 * radius, 2 * radius, 0, 90);
        } else {
            g.fillRect(x + width - radius, y, radius, radius);
        }

        if (rightBottom) {
            g.fillArc(x + width - 2 * radius, y + height - 2 * radius, 2 * radius, 2 * radius, 270, 90);
        } else {
            g.fillRect(x + width - radius, y + height - radius, radius, radius);
        }

        if (leftBottom) {
            g.fillArc(x, y + height - 2 * radius, 2 * radius, 2 * radius, 180, 90);
        } else {
            g.fillRect(x, y + height - radius, radius, radius);
        }

        g.fillRect(x + radius, y, width - 2 * radius, radius);
        g.fillRect(x + radius, y + height - radius, width - 2 * radius, radius);
        g.fillRect(x, y + radius, width, height - 2 * radius);
    }

    public static void drawAntiRoundRect(Graphics2D g, Rectangle rectangle, int radius, boolean leftTop, boolean rightTop, boolean leftBottom, boolean rightBottom, Color backgroundColor, Color foregroundColor) {
        int x = rectangle.x;
        int y = rectangle.y;
        int width = (int) rectangle.getWidth();
        int height = (int) rectangle.getHeight();

        g.setColor(foregroundColor);
        g.fillRect(x, y, width, height);
        drawRoundRect(g, rectangle, radius, leftTop, rightTop, leftBottom, rightBottom, backgroundColor);
    }

    public static void saveImageToDisk(BufferedImage image, String path) {
        BufferedImage out = null;
        if (image.getType() != BufferedImage.TYPE_3BYTE_BGR) {
            int width = image.getWidth();
            int height = image.getHeight();
            out = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);
            out.createGraphics().drawImage(image, 0, 0, null);
        } else {
            out = image;
        }
        try {
            ImageIO.write(out, "jpg", new File(path));
        } catch (IOException e) {
        }
    }

    public static void saveImageToStream(BufferedImage image, OutputStream stream) {
        BufferedImage out = null;
        if (image.getType() != BufferedImage.TYPE_3BYTE_BGR) {
            int width = image.getWidth();
            int height = image.getHeight();
            out = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);
            out.createGraphics().drawImage(image, 0, 0, null);
        } else {
            out = image;
        }
        try {
            ImageIO.write(out, "jpg", stream);
        } catch (IOException e) {
        }
    }

    public static int hsvToRgb(float h, float s, float v) {
        h /= 360.0;
        return Color.HSBtoRGB(h, s, v);
    }

    public static float[] rgbToHsv(int r, int g, int b, float[] hsvColor) {
        Color.RGBtoHSB(r, g, b, hsvColor);
        hsvColor[0] *= 360;
        return hsvColor;
    }

    public static float[] rgbToHsv(Color color, float[] hsvColor) {
        return rgbToHsv(color.getRed(), color.getGreen(), color.getBlue(), hsvColor);
    }

    public static int getRGB(BufferedImage image, int x, int y, int w, int h) {
        if (x < 0) {
            x = 0;
        } else if (x >= w) {
            x = w - 1;
        }
        if (y < 0) {
            y = 0;
        } else if (y >= h) {
            y = h - 1;
        }
        return image.getRGB(x, y);
    }

    public static void setRGB(BufferedImage image, int x, int y, int w, int h, int color) {
        if (x < 0) {
            x = 0;
        } else if (x >= w) {
            x = w - 1;
        }
        if (y < 0) {
            y = 0;
        } else if (y >= h) {
            y = h - 1;
        }
        image.setRGB(x, y, color);
    }

    public static BufferedImage convolutionFilter(BufferedImage image) {
        int factor = 1;
        int offset = 1;
        float[][] kernel = new float[3][3];
        kernel[0][0] = 0;
        kernel[0][1] = 0;
        kernel[0][2] = 0;
        kernel[1][0] = 0;
        kernel[1][1] = 1;
        kernel[1][2] = 0;
        kernel[2][0] = 0;
        kernel[2][1] = 0;
        kernel[2][2] = 0.4f;

        int width = image.getWidth();
        int height = image.getHeight();

        int color;
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                int r = 0, g = 0, b = 0;
                float value = kernel[0][0];
                if (value != 0) {
                    color = getRGB(image, x - 1, y - 1, width, height);
                    r += ((0x00FF0000 & color) >> 16) * value;
                    g += ((0x0000FF00 & color) >> 8) * value;
                    b += (0x000000FF & color) * value;
                }
                value = kernel[0][1];
                if (value != 0) {
                    color = getRGB(image, x, y - 1, width, height);
                    r += ((0x00FF0000 & color) >> 16) * value;
                    g += ((0x0000FF00 & color) >> 8) * value;
                    b += (0x000000FF & color) * value;
                }
                value = kernel[0][2];
                if (value != 0) {
                    color = getRGB(image, x + 1, y - 1, width, height);
                    r += ((0x00FF0000 & color) >> 16) * value;
                    g += ((0x0000FF00 & color) >> 8) * value;
                    b += (0x000000FF & color) * value;
                }
                value = kernel[1][0];
                if (value != 0) {
                    color = getRGB(image, x - 1, y, width, height);
                    r += ((0x00FF0000 & color) >> 16) * value;
                    g += ((0x0000FF00 & color) >> 8) * value;
                    b += (0x000000FF & color) * value;
                }
                value = kernel[1][1];
                if (value != 0) {
                    color = getRGB(image, x, y, width, height);
                    r += ((0x00FF0000 & color) >> 0x10) * value;
                    g += ((0x0000FF00 & color) >> 8) * value;
                    b += (0x000000FF & color) * value;
                }
                value = kernel[1][2];
                if (value != 0) {
                    color = getRGB(image, x + 1, y, width, height);
                    r += ((0x00FF0000 & color) >> 16) * value;
                    g += ((0x0000FF00 & color) >> 8) * value;
                    b += (0x000000FF & color) * value;
                }
                value = kernel[2][0];
                if (value != 0) {
                    color = getRGB(image, x - 1, y + 1, width, height);
                    r += ((0x00FF0000 & color) >> 16) * value;
                    g += ((0x0000FF00 & color) >> 8) * value;
                    b += (0x000000FF & color) * value;
                }
                value = kernel[2][1];
                if (value != 0) {
                    color = getRGB(image, x, y + 1, width, height);
                    r += ((0x00FF0000 & color) >> 16) * value;
                    g += ((0x0000FF00 & color) >> 8) * value;
                    b += (0x000000FF & color) * value;
                }
                value = kernel[2][2];
                if (value != 0) {
                    color = getRGB(image, x + 1, y + 1, width, height);
                    r += ((0x00FF0000 & color) >> 16) * value;
                    g += ((0x0000FF00 & color) >> 8) * value;
                    b += (0x000000FF & color) * value;
                }
                r = (r / factor) + offset;
                g = (g / factor) + offset;
                b = (b / factor) + offset;
                if (r < 0) {
                    r = 0;
                }
                if (r > 0xff) {
                    r = 0xff;
                }
                if (b < 0) {
                    b = 0;
                }
                if (b > 0xff) {
                    b = 0xff;
                }
                if (g < 0) {
                    g = 0;
                }
                if (g > 0xff) {
                    g = 0xff;
                }
                image.setRGB(x, y, (0xFF << 24) | (r << 16) | (g << 8) | b);
            }
        }

        return image;
    }

    public static BufferedImage bigBrotherCustomFilter(BufferedImage image, Color color) {
        final int DOT_AREA = 10;
        final int arrDither[] = {
                167, 200, 230, 216, 181,
                94, 72, 193, 242, 232,
                36, 52, 222, 167, 200,
                181, 126, 210, 94, 72,
                232, 153, 111, 36, 52,
                167, 200, 230, 216, 181,
                94, 72, 193, 242, 232,
                36, 52, 222, 167, 200,
                181, 126, 210, 94, 72,
                232, 153, 111, 36, 52,
                167, 200, 230, 216, 181,
                94, 72, 193, 242, 232,
                36, 52, 222, 167, 200,
                181, 126, 210, 94, 72,
                232, 153, 111, 36, 52,
                167, 200, 230, 216, 181,
                94, 72, 193, 242, 232,
                36, 52, 222, 167, 200,
                181, 126, 210, 94, 72,
                232, 153, 111, 36, 52
        };

        int red = color.getRed();
        int green = color.getGreen();
        int blue = color.getBlue();

        int width = image.getWidth();
        int height = image.getHeight();

        for (int x = 0; x < width; x += DOT_AREA) {
            for (int y = 0; y < height; y += DOT_AREA) {
                int l_grayIntensity;
                int l_x;
                int l_y;

                for (int i = 0; i < DOT_AREA * DOT_AREA; i++) {
                    l_x = i % DOT_AREA;
                    l_y = i / DOT_AREA;

                    if (x + l_x < width && x + l_y < height) {
                        l_grayIntensity = 255 - ((getRGB(image, x + l_x, y + l_y, width, height) >> 16) & 0xFF);
                        if (l_grayIntensity > arrDither[i]) {
                            setRGB(image, x + l_x, y + l_y, width, height, (0xFF << 24) | (red << 16) | (green << 8) | blue);
                        } else {
                            setRGB(image, x + l_x, y + l_y, width, height, (0xFF << 24) | (0xFF << 16) | (0xFF << 8) | 0xFF);
                        }
                    }
                }
            }
        }

        return image;
    }
}
