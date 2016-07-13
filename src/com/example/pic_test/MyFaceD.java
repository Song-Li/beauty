package com.example.pic_test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.graphics.Bitmap;
import android.graphics.Color;

public class MyFaceD {
	
	private Bitmap bitmap;
	
	public MyFaceD(Bitmap bmp){
		this.bitmap = bmp;
	}
	
	
	public Bitmap getBitmap(){
		return Binaryzation(this.bitmap);
	}
	
	
	
   public Bitmap Binaryzation(Bitmap src) {  
	    
	    Bitmap bmp = src.copy(src.getConfig(), true); 
       
	    int width = src.getWidth();  
        int height = src.getHeight();  
       
      
        int pixel;
          
        
        for(int row=0; row<width; ++row) { 
        	System.out.println(row + " ");
      
            for(int col=0; col<height; ++col) {  
            	
            	
            	pixel = src.getPixel(row, col);
      
                int tr = (pixel >> 16) & 0xff; 
                int tg = (pixel >> 8) & 0xff;  
                int tb = pixel & 0xff;
                
                double cb = (-0.1687)*tr + (-0.3313)*tg + 0.5*tb + 128;
                double cr = 0.5*tr + (-0.4187)*tg + (-0.0813)*tb + 128;
            
    
                //Gaussian Probability, seriously doubt the accuracy of covariance matrix
                double[][] invC = {{0.0063,-0.0003},{-0.0003,0.0034}};  //inverse of covariance matrix
              
       
                double[][] cbcr = {{cb},{cr}};
                double[][] mean = {{115.0},{144.0}};
                
                double[][] cbcrSubmean = Tools.matrixSubtract(cbcr,mean);
                double[][] temp = Tools.matrixMultiply(Tools.matrixTranspose(cbcrSubmean),invC);
                double[][] temp2 = Tools.matrixMultiply(temp,cbcrSubmean);
                double pixelProb = temp2[0][0];
               
        
                
                //double fThreshold = 0.5; //smaller, more strict   
                
                //more likely to be skin, whiter
                if(pixelProb< 0.1){
                	  bmp.setPixel(row,col,Color.rgb(255,255,255)); 
                }else if(pixelProb < 0.3){
                	  bmp.setPixel(row,col,Color.rgb(200,200,200)); 
                }else if (pixelProb < 0.5){
                	bmp.setPixel(row,col,Color.rgb(150,150,150)); 
                }else if(pixelProb < 1.0){
                	bmp.setPixel(row,col,Color.rgb(100,100,100)); 
                }else if(pixelProb < 2.0){
                	bmp.setPixel(row,col,Color.rgb(50,50,50)); 
                }else{
                	bmp.setPixel(row,col,Color.rgb(0,0,0)); 
                }
              
                
  
                /*
                //use scope of cr and cb
                if(cb > 100 && cb < 120 && cr > 140 && cr < 170){
                	bmp.setPixel(row,col,Color.rgb(255,255,255));
		        }else{
		        	bmp.setPixel(row,col,Color.rgb(0,0,0));
		        }  
		        */                  
         
            }  
        }
       
        
        return bmp;  
    } 
   
   
   /** save image */
   private void saveBitmap(Bitmap bm,String picName) {

	   File f = new File("/sdcard/", picName);
	   if (f.exists()) {
		   f.delete();
	   }
	   try {
		   FileOutputStream out = new FileOutputStream(f);
		   bm.compress(Bitmap.CompressFormat.PNG, 90, out);
		   out.flush();
		   out.close();
	   } catch (FileNotFoundException e) {
	   // TODO Auto-generated catch block
		   e.printStackTrace();
	   } catch (IOException e) {
	   // TODO Auto-generated catch block
		   e.printStackTrace();
	   }
   } 



		
		

}
