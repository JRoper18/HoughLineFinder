/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package houghlinefinder;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 *
 * @author jackroper
 */
public class ImageProcessor {
    public static BufferedImage getImage(String fileName) {
        try {         
            return ImageIO.read(new File(fileName));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    public static BufferedImage greyScale(BufferedImage coloredImg){
        int width = coloredImg.getWidth();
        int height = coloredImg.getHeight();
        BufferedImage greyScaleImg = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_GRAY);
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                    Color c = new Color(coloredImg.getRGB(j, i));
                    int red = (int) (c.getRed() * 0.299);
                    int green = (int) (c.getGreen() * 0.587);
                    int blue = (int) (c.getBlue() * 0.114);
                    int brightness = red + green + blue;
                    Color newColor = new Color(brightness, brightness, brightness);
                    greyScaleImg.setRGB(j, i, newColor.getRGB());
            }
        }
        return greyScaleImg;
    }
    public static BufferedImage filterGreyScaleImg(BufferedImage greyScaleImg, int minBrightness, int maxBrightness){
        int width = greyScaleImg.getWidth();
        int height = greyScaleImg.getHeight();
        BufferedImage filteredImg = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_GRAY);
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                int currentRGB = greyScaleImg.getRGB(j, i);
                int currentBrightness = new Color(currentRGB).getRed(); //All colors are the same, just use red
                if(currentBrightness > minBrightness && currentBrightness < maxBrightness){
                    filteredImg.setRGB(j, i, currentRGB);
                }
                else{
                    filteredImg.setRGB(j, i, 0);
                }
            }
        }
        return filteredImg;
    }
    public static BufferedImage getHoughTransformImage(BufferedImage filteredImg){
        int width = filteredImg.getWidth();
        int height = filteredImg.getHeight();
        BufferedImage houghImg = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_GRAY);
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                
            }
        }
    }
}

