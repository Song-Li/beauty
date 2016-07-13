package com.example.pic_test;

import android.os.Bundle;
import android.app.Activity;
import java.io.FileNotFoundException;    
import android.content.ContentResolver;  
import android.content.Intent;  
import android.graphics.Bitmap;  
import android.graphics.BitmapFactory;  
import android.net.Uri;  
import android.util.Log;  
import android.view.View;  
import android.widget.Button;  
import android.widget.ImageView;  
import android.widget.SeekBar;


public class MainActivity extends Activity {  
    /** Called when the activity is first created. */  
   
    
    Bitmap original_bitmap;
    
    public void onCreate(Bundle savedInstanceState) {  
        super.onCreate(savedInstanceState);  
        setContentView(R.layout.activity_main);  
          
        Button button_select = (Button)findViewById(R.id.button_select);  
  
        /*select a photo from gallary*/
        button_select.setOnClickListener(new Button.OnClickListener(){  
            @Override  
            public void onClick(View v) {  
                Intent intent = new Intent();  
                intent.setType("image/*");  
                intent.setAction(Intent.ACTION_GET_CONTENT);   
                startActivityForResult(intent, 1);  
            }         
        }); 
        
        /*jump to color experiment*/
        Button button_color = (Button)findViewById(R.id.button_color);
        
        button_color.setOnClickListener(new Button.OnClickListener(){  
            @Override  
            public void onClick(View v) {  
            	 Intent intent = new Intent(); 
                 intent.setClass(MainActivity.this, ColorActivity.class);
                 MainActivity.this.startActivity(intent);	       	
            }
        }); 
    }  
    
    
    
  
    /*face detection experiment*/  
    @Override  
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {  
      
    	
    	if (resultCode == RESULT_OK) {  
            Uri uri = data.getData();   
            ContentResolver cr = this.getContentResolver();  
            try {  
            	original_bitmap= BitmapFactory.decodeStream(cr.openInputStream(uri));
            	ImageView imageOriginal = (ImageView) findViewById(R.id.image_original); 
            	ImageView imageFiltered = (ImageView) findViewById(R.id.image_filtered); 
            	
            	
            	//faceD
            	/*
            	FaceD faceD = new FaceD(original_bitmap);
            	faceD.faceBitmap();
            	
            	imageOriginal.setImageBitmap(faceD.getBitmap());
            	*/
            	
            	
            	//light experiment
            	/*
            	Style myStyle = new Style();
            	Bitmap icBitmap = myStyle.lightBalance(original_bitmap);
            	imageOriginal.setImageBitmap(original_bitmap);
            	imageFiltered.setImageBitmap(icBitmap);
            	*/
            	
            
            	
            	//MyFaceD after light experiment
            	Style myStyle = new Style();
            	Bitmap icBitmap = myStyle.lightBalance(original_bitmap);
            	MyFaceD myFaceD = new MyFaceD(icBitmap);
            	Bitmap faceBinary = myFaceD.getBitmap();
            	
            	imageOriginal.setImageBitmap(original_bitmap);
            	imageFiltered.setImageBitmap(faceBinary);
            	
   	
            	
            } catch (FileNotFoundException e) {  
                Log.e("Exception", e.getMessage(),e);  
            }  
        }  
        super.onActivityResult(requestCode, resultCode, data);  
    }

    
}  