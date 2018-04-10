/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package houghlinefinder;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import javafx.util.Pair;
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
    public static FilteredImageData filterGreyScaleImg(BufferedImage greyScaleImg, int minBrightness, int maxBrightness){
        int width = greyScaleImg.getWidth();
        int height = greyScaleImg.getHeight();
        BufferedImage filteredImg = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_GRAY);
        boolean[][] isFilteredArray = new boolean[width][height];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                int currentRGB = greyScaleImg.getRGB(j, i);
                int currentBrightness = new Color(currentRGB).getRed(); //All colors are the same, just use red
                if(currentBrightness > minBrightness && currentBrightness < maxBrightness){
                    filteredImg.setRGB(j, i, currentRGB);
                    isFilteredArray[j][i] = true;
                }
                else{
                    filteredImg.setRGB(j, i, 0);
                }
            }
        }
        return new FilteredImageData(filteredImg, isFilteredArray);
    }
    public static HoughImageData getHoughTransformData(FilteredImageData filteredData){
        int width = filteredData.actualImg.getWidth();
        int height = filteredData.actualImg.getHeight();
        int maxDistance = 2 * (int) Math.ceil(Math.sqrt((width * width) + (height * height)));
        HoughImageData houghData = new HoughImageData(maxDistance);
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if(filteredData.isFiltered[x][y]){
                    houghData.addPointToPlane(x, y);
                }
            }
        }
        return houghData;
    }
    public static BufferedImage getLineDrawnImage(HoughImageData houghData, BufferedImage originalImage){
        //Copy the original image so we can draw on it. 
        ColorModel colorModel = originalImage.getColorModel();
        boolean isAlphaPremultiplied = colorModel.isAlphaPremultiplied();
        WritableRaster raster = originalImage.copyData(null);
        BufferedImage modifyableImage = new BufferedImage(colorModel, raster, isAlphaPremultiplied, null);
        Graphics imgGraphics = modifyableImage.createGraphics();
        for(GraphicsLineData lineData : houghData.getLines(10)){ //10 is completely arbitrary
            imgGraphics.drawLine(lineData.x1, lineData.y1, lineData.x2, lineData.y2);
        }
        return modifyableImage;
        
    }
}

