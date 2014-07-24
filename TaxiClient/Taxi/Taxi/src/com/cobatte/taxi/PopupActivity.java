package com.cobatte.taxi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

public class PopupActivity extends Activity{
	
	Button ok, cancel;			// 확인 취소 버튼.
	TextView tv;				// 텍스트 뷰 컴포넌트
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);        
        setContentView(R.layout.popup);
        setTitle("공지 사항");       
        
        String temp;
        temp = "1.동해물과 백두산이 마르고 닳고록 하느님이 보우하사 우리나라만세. 무궁화 삼천리 화려강산 대한사람 대한으로 길이 보전하세.    2. 남산위의 저 소나무 철갑을 두른듯 바람서리 불변함은 우리기상일세 무궁화 삼천리 화려강산 대한사람 대한으로 길이 보전하세.1.동해물과 백두산이 마르고 닳고록 하느님이 보우하사 우리나라만세. 무궁화 삼천리 화려강산 대한사람 대한으로 길이 보전하세.    2. 남산위의 저 소나무 철갑을 두른듯 바람서리 불변함은 우리기상일세 무궁화 삼천리 화려강산 대한사람 대한으로 길이 보전하세.1.동해물과 백두산이 마르고 닳고록 하느님이 보우하사 우리나라만세. 무궁화 삼천리 화려강산 대한사람 대한으로 길이 보전하세.    2. 남산위의 저 소나무 철갑을 두른듯 바람서리 불변함은 우리기상일세 무궁화 삼천리 화려강산 대한사람 대한으로 길이 보전하세.1.동해물과 백두산이 마르고 닳고록 하느님이 보우하사 우리나라만세. 무궁화 삼천리 화려강산 대한사람 대한으로 길이 보전하세.    2. 남산위의 저 소나무 철갑을 두른듯 바람서리 불변함은 우리기상일세 무궁화 삼천리 화려강산 대한사람 대한으로 길이 보전하세.1.동해물과 백두산이 마르고 닳고록 하느님이 보우하사 우리나라만세. 무궁화 삼천리 화려강산 대한사람 대한으로 길이 보전하세.    2. 남산위의 저 소나무 철갑을 두른듯 바람서리 불변함은 우리기상일세 무궁화 삼천리 화려강산 대한사람 대한으로 길이 보전하세.1.동해물과 백두산이 마르고 닳고록 하느님이 보우하사 우리나라만세. 무궁화 삼천리 화려강산 대한사람 대한으로 길이 보전하세.    2. 남산위의 저 소나무 철갑을 두른듯 바람서리 불변함은 우리기상일세 무궁화 삼천리 화려강산 대한사람 대한으로 길이 보전하세.";
                        		// 팝업창 텍스트 테스트...
        ok = (Button)findViewById(R.id.ok);
        cancel = (Button)findViewById(R.id.cancel);
        tv = (TextView)findViewById(R.id.contents);
        
        tv.setText(temp);
        
        ok.setOnClickListener(new View.OnClickListener() {		// 확인 버튼 클릭시 이벤트
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				Intent intent = new Intent(PopupActivity.this, LoginActivity.class);		// 로그인 액티비티로 인텐트~
				startActivity(intent);
				overridePendingTransition(R.anim.left_in, R.anim.left_out);	// 애니메이션 효과
				finish();		// 현재 액티비티 끝냄
			}
		});
        
        cancel.setOnClickListener(new View.OnClickListener() {	// 취소 버튼 클릭시 이벤트 
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				finish();		// 취소 클릭시 현재 액티비티 끝냄.
				
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
