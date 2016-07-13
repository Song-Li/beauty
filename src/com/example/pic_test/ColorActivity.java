package com.example.pic_test;

import java.io.FileNotFoundException;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;

public class ColorActivity extends Activity{

	
    Bitmap original_bitmap2;
    
    public void onCreate(Bundle savedInstanceState) {  
        super.onCreate(savedInstanceState);  
        setContentView(R.layout.activity_color);  
          
        Button button = (Button)findViewById(R.id.button_select2);  
        SeekBar seekbar = (SeekBar)findViewById(R.id.seekbar);
        seekbar.setOnSeekBarChangeListener(new OnSeekBarChangeListenerImp());
        button.setOnClickListener(new Button.OnClickListener(){  
            @Override  
            public void onClick(View v) {  
                Intent intent = new Intent();          
                intent.setType("image/*");                
                intent.setAction(Intent.ACTION_GET_CONTENT);                 
                startActivityForResult(intent, 2);  
            }  
              
        });  
    }  
    
    
    
   
 //example: change red channel when drag seekbar
    private class OnSeekBarChangeListenerImp implements SeekBar.OnSeekBarChangeListener{

	   
	    public void onStopTrackingTouch(SeekBar seekBar) {
	    // TODO Auto-generated method stub
	         
	    	 double pro = seekBar.getProgress();
	    	 double pro_res = (pro<=50)?(0.018*pro+0.1):((9.0/49*pro - 410.0/49));
	   
	    	 Style style1 = new Style(pro_res,1.0,1.0);
	    	 Bitmap style_bitmap = style1.whiten(original_bitmap2);
	    	 
             ImageView imageStyle = (ImageView) findViewById(R.id.image_filtered); 
              
             imageStyle.setImageBitmap(style_bitmap); 
	    }

		@Override
		public void onProgressChanged(SeekBar arg0, int arg1, boolean arg2) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onStartTrackingTouch(SeekBar arg0) {
			// TODO Auto-generated method stub
			
		}
    }
    
      
    @Override  
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {  
      
    	
    	if (resultCode == RESULT_OK) {  
            Uri uri = data.getData();   
            ContentResolver cr = this.getContentResolver();  
            try {  
            	original_bitmap2= BitmapFactory.decodeStream(cr.openInputStream(uri));
                BilateralFilter bf = new BilateralFilter();               
            	ImageView imageOriginal = (ImageView) findViewById(R.id.image_original2); 
                Bitmap filtered_bitmap = bf.bilateralFilter(original_bitmap2);          	          	
                ImageView imageFiltered = (ImageView) findViewById(R.id.image_filtered);               
                imageFiltered.setImageBitmap(filtered_bitmap); 
            } catch (FileNotFoundException e) {  
                Log.e("Exception", e.getMessage(),e);  
            }  
        }  
        super.onActivityResult(requestCode, resultCode, data);  
    }

    
}  