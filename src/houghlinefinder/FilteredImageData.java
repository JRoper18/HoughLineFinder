/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package houghlinefinder;

import java.awt.image.BufferedImage;

/**
 *
 * @author jackroper
 */
public class FilteredImageData {
    public BufferedImage actualImg; 
    public boolean[][] isFiltered;
    public FilteredImageData(BufferedImage actualImg, boolean[][] isFiltered){
        this.actualImg = actualImg;
        this.isFiltered = isFiltered;
    }
}
