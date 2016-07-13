package com.example.pic_test;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.RectF;
import android.media.FaceDetector;
import android.util.Log;


//android face detection API
public class FaceD {
	private static final int MAX_FACES = 3;
	private Bitmap bitmap;
	private FaceDetector faceDet;
	private FaceDetector.Face[] faceList;
	
	
	
	public FaceD(Bitmap bmp){
		this.bitmap = bmp.copy(Bitmap.Config.RGB_565, true);
		int width = this.bitmap.getWidth();
		int height = this.bitmap.getHeight();
		if(width%2 != 0){
			this.bitmap = Tools.ImageCrop(this.bitmap, 0, width - 1, 0, height);
		}
		this.faceDet = new FaceDetector(bitmap.getWidth(), bitmap.getHeight(), MAX_FACES);
		this.faceList = new FaceDetector.Face[MAX_FACES];
		
	}
	
	
	public void faceBitmap(){
		
		this.faceDet.findFaces(this.bitmap, this.faceList);
		
		for (int i=0; i < faceList.length; i++) {
            FaceDetector.Face face = faceList[i];
       
            if (face != null) {
                PointF pf = new PointF();
                face.getMidPoint(pf);               
                RectF r = new RectF();
                r.left = pf.x - face.eyesDistance() / 2;
                r.right = pf.x + face.eyesDistance() / 2;
                r.top = pf.y - face.eyesDistance() / 2;
                r.bottom = pf.y + face.eyesDistance() / 2;
                drawRectangles(r.left,r.right,r.top,r.bottom);
                
            }
        }
	}
	
	public Bitmap getBitmap(){
		
		return this.bitmap;
	}
	
	
	
	private void drawRectangles(float left,float right,float top,float bottom) {
			
			this.bitmap = this.bitmap.copy(Bitmap.Config.ARGB_8888, true);
			Canvas canvas = new Canvas(bitmap);
			Paint paint = new Paint();
			
			paint.setColor(Color.RED);
			paint.setStyle(Paint.Style.STROKE);
			paint.setStrokeWidth(5);  
			canvas.drawRect(left, top, right, bottom, paint);			
	}
	
	
	
}
