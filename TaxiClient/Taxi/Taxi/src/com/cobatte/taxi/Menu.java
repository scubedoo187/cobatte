package com.cobatte.taxi;

import android.app.Activity;
import android.os.Bundle;

public class Menu extends Activity{
	
	@Override
	 protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu);
        setTitle("행선지 선택");
        
	}
}
