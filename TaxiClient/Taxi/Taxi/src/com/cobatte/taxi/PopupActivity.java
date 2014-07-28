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
        temp = "공지사항 및 약관, 업데이트 내용\n1. \n2. \n3. ....준비중";
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
