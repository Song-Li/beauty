package com.example.pic_test;

import android.graphics.Bitmap;
import android.graphics.Color;

public class Style {
	
	private double factor_r;
	private double factor_g;
	private double factor_b;
	
	public Style(){
	
	}
	
	public Style(double factor_r,double factor_g,double factor_b){
		this.factor_r = factor_r; 
		this.factor_g = factor_g; 
		this.factor_b = factor_b; 
	}
	
	
	public Bitmap lightBalance(Bitmap src){
		
		Bitmap bmp = src.copy(src.getConfig(), true);
		
		int width = src.getWidth();  
	    int height = src.getHeight();
	    int pixel;
	    
	    double y = 0;
	    
	    for (int row = 0; row < width; ++row){
	    	for (int col = 0; col < height; ++col){
	    		pixel = src.getPixel(row, col);    
          
	    		y += 0.257*((pixel>>16)&0xff) + 0.564*((pixel>>8)&0xff) + 0.098*(pixel & 0xff) + 16;
     
	    	}
	    }
	    
	    int wh = width * height;
	    y /= wh;  //average y in this picture
	  
	    
	    
	    //y = 50 + 2/5*y;  //the whole image in soft tone
	    
	    
	    if(y < 70){  //too dim -> 50~85
	    	y = y/2 + 50;
	    }else if(y > 130){  //too light -> 126~151
	    	y = y/5 + 100;
	    }
	    
	    
	  
	    
	    for (int row = 0; row < width; ++row){
	    	for (int col = 0; col < height; ++col){
	    		pixel = src.getPixel(row, col);    
          
                int r = (pixel >> 16) & 0xff;  
                int g = (pixel >> 8) & 0xff;  
                int b = pixel & 0xff;
                
                double y_temp = 0.257*r+0.564*g+0.098*b+16; //Y in this pixel
                
                
                double factor;
                if(y_temp != 0){
                	factor = y/y_temp;
                }else{
                	factor = 1; 
                }
                
    
                //brighten it
                if(factor > 1){ 
                	factor = 1 / (1 + Math.pow(Math.E,(-3)*(factor - 1))) + 0.5;
                }
                
                //darken it
                if(factor < 1){
                	factor = 1 / (1 + Math.pow(Math.E,(-1)*(factor - 1))) + 0.5;
                }
                
                
                //too obvious
                /*
                r = Tools.clamp((int)(Math.pow(r, factor)));
                g = Tools.clamp((int)(Math.pow(g, factor)));
                b = Tools.clamp((int)(Math.pow(b, factor)));
               */
                
                
                r = Tools.clamp((int)(r*factor));
                g = Tools.clamp((int)(g*factor));
                b = Tools.clamp((int)(b*factor));
                
                
                bmp.setPixel(row,col,Color.rgb(r, g, b));
                         
	    	}
	    }
	    
   
		return bmp;
	}
	
	
	
	public Bitmap whiten(Bitmap src){
		Bitmap bmp = src.copy(src.getConfig(), true);
		
		  int width = src.getWidth();  
	      int height = src.getHeight();
	      int pixel;
	      
	      for(int row=0; row<width; ++row) {  
	            int ta = 0, tr = 0, tg = 0, tb = 0;  
	            for(int col=0; col<height; ++col) { 
	            	pixel = src.getPixel(row, col);
	                
	            	
	            	ta = (pixel >> 24) & 0xff;  
	                tr = (pixel >> 16) & 0xff;  
	                tg = (pixel >> 8) & 0xff;  
	                tb = pixel & 0xff;  
	                
	                
	                tr *= factor_r;
	                tg *= factor_g;
	                tb *= factor_b;
                      
	                
	                int x = (ta << 24) | (Tools.clamp(tr) << 16) | (Tools.clamp(tg) << 8) | Tools.clamp(tb);
	                
	                bmp.setPixel(row,col,x);
	            }
	      }
		
		return bmp;
	}

}
