package com.cobatte.taxi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class Logo extends Activity {
	
	ImageView intro;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.logo);
                 
        new Thread(new Runnable(){
        
        @Override
        public void run(){
        	try{
        		intro = (ImageView)findViewById(R.id.logo);
        		Animation alphaAnim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.intro);
        		intro.startAnimation(alphaAnim);
        		Thread.sleep(2500);
        		logoIntro();
        	}catch(Exception e){}
        }
        }).start();
    	}
        
        private void logoIntro()
        {
        	Intent i = new Intent(Logo.this, Popup.class);
        	startActivity(i);
        	finish();
        }
   
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
}
