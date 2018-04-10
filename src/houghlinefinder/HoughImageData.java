/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package houghlinefinder;

import java.awt.image.BufferedImage;
import java.awt.Color;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author jackroper
 */
public class HoughImageData {
    private int[][] houghPlane;
    private int maxBrightness = 0;
    private final int maxDistance;
    private final int distanceOffset;
    public HoughImageData(int distanceRange){
        this.maxDistance = distanceRange;
        this.distanceOffset = maxDistance / 2;
        this.houghPlane = new int[180][distanceRange];
    }
    public void addPointToPlane(int x, int y){
        for(int theta = -89; theta < 90; theta++){
            double thetaRad = theta * Math.PI/180;
            int distance = (int) ((x*Math.cos(thetaRad)) + (y*Math.sin(thetaRad)));
            int arrPosition = distance + distanceOffset;
            if(arrPosition >= maxDistance){
                continue;
            }
            houghPlane[theta+89][arrPosition]++;
            if(houghPlane[theta+89][arrPosition] > maxBrightness){
                maxBrightness = houghPlane[theta+89][arrPosition];
            }
        }
    }
    public Set<GraphicsLineData> getLines(int threshold){
        Set<GraphicsLineData> lines = new HashSet<>();
        for(int x = 0; x < 180; x++){
            for(int y = 0; y<maxDistance; y++){
                int val = houghPlane[x][y];
                if(Math.abs(val - maxBrightness) < threshold){ //This pixel is bright enough to represent a line. 
                    int x1, y1, x2, y2;
                    int thetaDeg = x - 89;
                    double thetaRad = thetaDeg * Math.PI/180;
                    int distance = y - distanceOffset;
                    double slope = -1 * (Math.cos(thetaRad) / Math.sin(thetaRad));
                    double yIntercept = distance / Math.sin(thetaRad);
                    if(yIntercept == 0){
                        y1 = 0;
                        x1 = 0;
                    }
                    else if(yIntercept > 0){
                        y1 = (int) yIntercept;
                        x1 = 0;
                    }
                    else { 
                        y1 = 0;
                        x1 = (int) (-1 * (yIntercept / slope));
                    }
                    //Now determine the ending points of these lines
                    
                }
            }
        }
        return lines;
    }
    public BufferedImage toImage(){
        BufferedImage img = new BufferedImage(180, maxDistance, BufferedImage.TYPE_BYTE_GRAY);
        for(int x = 0; x < 180; x++){
            for(int y = 0; y<maxDistance; y++){
                int absoluteHoughVal = houghPlane[x][y];
                if(absoluteHoughVal != 0){
                    float scale = (float) Math.sqrt((float) houghPlane[x][y] / (float) maxBrightness); 
                    //Sqrt keeps ordering but makes everything slightly brighter, so it's easier to see. 
                    img.setRGB(x, y, new Color(scale, scale, scale).getRGB());                    
                }
            }
        }
        return img;
    }
}
