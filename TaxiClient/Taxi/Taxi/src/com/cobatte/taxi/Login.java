package com.cobatte.taxi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Login extends Activity{
	
	EditText id, passwd;				// 아이디와 비밀번호 입력 창
	Button confirm, cancel, regist;				// 확인, 취소, 가입 버튼 
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        setTitle("로그인");
        
        id = (EditText)findViewById(R.id.id);
        passwd = (EditText)findViewById(R.id.passwd);
        
        confirm = (Button)findViewById(R.id.confirm);
        cancel = (Button)findViewById(R.id.cancel);
        regist = (Button)findViewById(R.id.regist);
        
        confirm.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				Intent intent = new Intent(Login.this, Menu.class);
				startActivity(intent);
				overridePendingTransition(R.anim.left_in, R.anim.left_out);							
				
			}
		});
        
        cancel.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				id.setText("");
				passwd.setText("");
			}
		});
        
        regist.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				Intent intent = new Intent(Login.this, Register.class );
				startActivity(intent);
				overridePendingTransition(R.anim.left_in, R.anim.left_out);
			
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
