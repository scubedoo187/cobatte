package com.cobatte.taxi;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.widget.Button;
import android.widget.EditText;

public class Login extends Activity{
	
	EditText id, passwd;				// 아이디와 비밀번호 입력 창
	Button confirm, cancel;				// 확인, 취소 버튼 
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        
        id = (EditText)findViewById(R.id.id);
        passwd = (EditText)findViewById(R.id.passwd);
        
        confirm = (Button)findViewById(R.id.confirm);
        cancel = (Button)findViewById(R.id.cancel);
        
        
        
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
}
