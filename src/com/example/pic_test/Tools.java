package com.example.pic_test;

import android.graphics.Bitmap;

public class Tools {
	 public static int clamp(int p) {  
	        return p < 0 ? 0 : ((p > 255) ? 255 : p);  
	    } 
	 
	 public static Bitmap ImageCrop(Bitmap bitmap,int x1, int x2, int y1, int y2) {
		   
		    return Bitmap.createBitmap(bitmap, x1, y1, x2 - x1, y2 - y1, null, false);
		}
	 
	 
	 
	 public static double[][] matrixMultiply(double[][] A, double[][] B){
		 
		 int A_rows = A.length;
		 int A_cols = A[0].length;
		 int B_rows = B.length;
		 int B_cols = B[0].length;
		 
		 
		 if(A_cols != B_rows){
			 System.out.println("multiply wrong");
			 return null;
		 }
		 
		 double[][] res = new double[A_rows][B_cols];
		 
		 int i,j,k;
		 for(i = 0; i < A_rows; ++i){
			 for (j = 0; j < B_cols; ++j){
				 res[i][j] = 0;
				 for (k = 0; k < A_cols; ++k){
					 res[i][j] += A[i][k] * B[k][j]; 
				 }
			 }
		 }
 
		 
		 return res;
		 
	 }
	 
	 
	 
	 
	 public static double[][] matrixSubtract(double[][] A, double[][] B){
		 
		 int A_rows = A.length;
		 int A_cols = A[0].length;
		 int B_rows = B.length;
		 int B_cols = B[0].length;
		 
		 if(A_rows != B_rows || A_cols != B_cols){
			 System.out.println("subtract wrong");
			 return null;
		 }
		 
		 double[][] res = new double[A_rows][A_cols]; 
		 
		 int i,j;
		 for(i = 0; i < A_rows; ++i){
			 for (j = 0; j < B_cols; ++j){
				 res[i][j] = A[i][j] - B[i][j];
			 }
		}
		 
		return res;
	 
	 }
	 
	 
	 public static double[][] matrixTranspose(double[][] A){
		
		 int A_rows = A.length;
		 int A_cols = A[0].length;
		
	   	 double[][] res = new double[A_cols][A_rows]; 
	   	 
	   	 int i,j;
		 for(i = 0; i < A_cols; ++i){
			 for (j = 0; j < A_rows; ++j){
				 res[i][j] = A[j][i];
			 }
		}
	   	 	 
	   	return res;
		 
	 }
	 
	 public static double[][] matrixMultiplyNum(double num,double[][] A){
		 int A_rows = A.length;
		 int A_cols = A[0].length;
		 
		 double[][] res = new double[A_rows][A_cols]; 
		 	 
		 int i,j;
		 for(i = 0; i < A_rows; ++i){
			 for (j = 0; j < A_cols; ++j){
				 res[i][j] = num * A[i][j];
			 }
		}
		 
		return res;
	 }
		 

}


