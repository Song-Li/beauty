package com.example.pic_test;

import android.graphics.Bitmap;


//key parameters: factor, ds, rs

public class BilateralFilter {  
    private double factor = -0.01d;  //-0.5, smaller, more obvious
    private double ds = 5.0f; // distance sigma  
    private double rs = 5.0f; // range sigma  
    private int radius; // half length of Gaussian kernel Adobe Photoshop   
    private double[][] cWeightTable;  
    private double[] sWeightTable;  
    private int width;  
    private int height;  
    
    public BilateralFilter(){
    	
    }
      
    public BilateralFilter(double factor) {
    	this.factor = factor;
          
    }  
      
    private void buildDistanceWeightTable() {  
        int size = 2 * radius + 1;  
        cWeightTable = new double[size][size];  
        for(int semirow = -radius; semirow <= radius; ++semirow) {  
            for(int semicol = - radius; semicol <= radius; ++semicol) {  
                // calculate Euclidean distance between center point and close pixels  
                double delta = Math.sqrt(semirow * semirow + semicol * semicol)/ds;   
                cWeightTable[semirow+radius][semicol+radius] = Math.exp(delta* delta * factor);  
            }  
        }  
    }  
      
    /** 
     * for gray image 
     * @param row 
     * @param col 
     * @param inPixels 
     */  
    private void buildSimilarityWeightTable() {  
        sWeightTable = new double[256]; // since the color scope is 0 ~ 255  
        for(int i=0; i<256; ++i) {  
            double delta = Math.sqrt(i * i ) / rs;  
            double deltaDelta = delta * delta;  
            sWeightTable[i] = Math.exp(deltaDelta * factor);  
        }  
    }  
      
    public void setDistanceSigma(double ds) {  
        this.ds = ds;  
    }  
      
    public void setRangeSigma(double rs) {  
        this.rs = rs;  
    }  
  
    public Bitmap bilateralFilter(Bitmap src) {  
    	
      
    	
    	Bitmap bmp = src.copy(src.getConfig(), true); 
    	
    	
        width = src.getWidth();  
        height = src.getHeight();  
        System.out.println(width + " " + height);
       
        radius = (int)Math.max(ds, rs);  
        buildDistanceWeightTable();  
        buildSimilarityWeightTable();  
     
      
        int pixel;
        double redSum = 0, greenSum = 0, blueSum = 0;  
        double csRedWeight = 0, csGreenWeight = 0, csBlueWeight = 0;  
        double csSumRedWeight = 0, csSumGreenWeight = 0, csSumBlueWeight = 0;  
        for(int row=0; row<width; ++row) {  
            int ta = 0, tr = 0, tg = 0, tb = 0;  
            for(int col=0; col<height; ++col) {  
            	
               
            	pixel = src.getPixel(row, col);
                
            	
            	ta = (pixel >> 24) & 0xff;  
                tr = (pixel >> 16) & 0xff;  
                tg = (pixel >> 8) & 0xff;  
                tb = pixel & 0xff;  
                int rowOffset = 0, colOffset = 0;  
                int tr2 = 0, tg2 = 0, tb2 = 0;  
                int pixel2;
                
                
              
                for(int semirow = -radius; semirow <= radius; ++semirow) {  
                    for(int semicol = - radius; semicol <= radius; ++semicol) {  
                    	
            
                    	rowOffset = (((row + semirow) >= 0) && ((row + semirow) < width))?(row + semirow):0;
                    	colOffset = (((semicol + col) >= 0) && ((semicol + col) < height))?(col + semicol):0;
                       
                      
                        pixel2 = src.getPixel(rowOffset, colOffset);
                       
                        
                        tr2 = (pixel2 >> 16) & 0xff;  
                        tg2 = (pixel2 >> 8) & 0xff;  
                        tb2 = pixel2 & 0xff;  
                       
                        csRedWeight = cWeightTable[semirow+radius][semicol+radius]  * sWeightTable[(Math.abs(tr2 - tr))];  
                        csGreenWeight = cWeightTable[semirow+radius][semicol+radius]  * sWeightTable[(Math.abs(tg2 - tg))];  
                        csBlueWeight = cWeightTable[semirow+radius][semicol+radius]  * sWeightTable[(Math.abs(tb2 - tb))];  
                          
                        csSumRedWeight += csRedWeight;  
                        csSumGreenWeight += csGreenWeight;  
                        csSumBlueWeight += csBlueWeight;  
                        redSum += (csRedWeight * (double)tr2);
                        greenSum += (csGreenWeight * (double)tg2);  
                        blueSum += (csBlueWeight * (double)tb2);  
                       
                    }  
                }  
                  
                tr = (int)Math.floor(redSum / csSumRedWeight);  
                tg = (int)Math.floor(greenSum / csSumGreenWeight);  
                tb = (int)Math.floor(blueSum / csSumBlueWeight); 
                
                int x = (ta << 24) | (Tools.clamp(tr) << 16) | (Tools.clamp(tg) << 8) | Tools.clamp(tb);
               
                bmp.setPixel(row,col,x);
                
                // clean value for next time...  
                redSum = greenSum = blueSum = 0;  
                csRedWeight = csGreenWeight = csBlueWeight = 0;  
                csSumRedWeight = csSumGreenWeight = csSumBlueWeight = 0; 
                
                  
            }  
        }
        
        
        return bmp;  
    }  
      
    
  
}