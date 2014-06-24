package com.cobatte.taxi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

public class Popup extends Activity{
	
	Button ok, cancel;
	TextView tv;
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);        
        setContentView(R.layout.popup);
               
        String temp;
        temp = "1.동해물과 백두산이 마르고 닳고록 하느님이 보우하사 우리나라만세. 무궁화 삼천리 화려강산 대한사람 대한으로 길이 보전하세.    2. 남산위의 저 소나무 철갑을 두른듯 바람서리 불변함은 우리기상일세 무궁화 삼천리 화려강산 대한사람 대한으로 길이 보전하세.1.동해물과 백두산이 마르고 닳고록 하느님이 보우하사 우리나라만세. 무궁화 삼천리 화려강산 대한사람 대한으로 길이 보전하세.    2. 남산위의 저 소나무 철갑을 두른듯 바람서리 불변함은 우리기상일세 무궁화 삼천리 화려강산 대한사람 대한으로 길이 보전하세.1.동해물과 백두산이 마르고 닳고록 하느님이 보우하사 우리나라만세. 무궁화 삼천리 화려강산 대한사람 대한으로 길이 보전하세.    2. 남산위의 저 소나무 철갑을 두른듯 바람서리 불변함은 우리기상일세 무궁화 삼천리 화려강산 대한사람 대한으로 길이 보전하세.1.동해물과 백두산이 마르고 닳고록 하느님이 보우하사 우리나라만세. 무궁화 삼천리 화려강산 대한사람 대한으로 길이 보전하세.    2. 남산위의 저 소나무 철갑을 두른듯 바람서리 불변함은 우리기상일세 무궁화 삼천리 화려강산 대한사람 대한으로 길이 보전하세.1.동해물과 백두산이 마르고 닳고록 하느님이 보우하사 우리나라만세. 무궁화 삼천리 화려강산 대한사람 대한으로 길이 보전하세.    2. 남산위의 저 소나무 철갑을 두른듯 바람서리 불변함은 우리기상일세 무궁화 삼천리 화려강산 대한사람 대한으로 길이 보전하세.1.동해물과 백두산이 마르고 닳고록 하느님이 보우하사 우리나라만세. 무궁화 삼천리 화려강산 대한사람 대한으로 길이 보전하세.    2. 남산위의 저 소나무 철갑을 두른듯 바람서리 불변함은 우리기상일세 무궁화 삼천리 화려강산 대한사람 대한으로 길이 보전하세.";
                        
        ok = (Button)findViewById(R.id.ok);
        cancel = (Button)findViewById(R.id.cancel);
        tv = (TextView)findViewById(R.id.contents);
        
        tv.setText(temp);
        
        ok.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				Intent intent = new Intent(Popup.this, Login.class);
				startActivity(intent);
				finish();
			}
		});
        
        cancel.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				finish();
				
			}
		});
        
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
}
