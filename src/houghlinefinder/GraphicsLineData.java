/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package houghlinefinder;

/**
 *
 * @author jackroper
 */
public class GraphicsLineData {
    public int x1, y1, x2, y2;
    public GraphicsLineData(double x1, double y1, double x2, double y2){
        this.x1 = (int) x1;
        this.x2 = (int) x2;
        this.y1 = (int) y1;
        this.y2 = (int) y2;
    }
    
    public String toString(){
        return "(" + x1 + "," + y1 + "), (" + x2 + "," + y2 + ")";
    }
}
