package com.cobatte.taxi;

import android.app.Activity;
import android.os.Bundle;

public class MainMenu extends Activity{
	
	@Override
	 protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainmenu);
        setTitle("행선지 선택");
        
	}
}
