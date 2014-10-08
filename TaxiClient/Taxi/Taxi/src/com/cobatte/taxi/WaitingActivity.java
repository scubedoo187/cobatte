package com.cobatte.taxi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class WaitingActivity extends Activity {
	Config config;
	MsgString messageObj;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.waiting);
		setTitle("´ë±â½Ç");

		Intent intent = getIntent();
		messageObj = (MsgString) intent.getExtras().getSerializable("message");
	}
}
