package com.cobatte.taxi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

public class WaitingActivity extends Activity {
	Config config;
	MsgString messageObj;
	TextView adminId;
	TextView meetTime;
	TextView meetPlace;
	ListView member;
	String roomInfoStr;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.waiting);
		setTitle("´ë±â½Ç");

		final Config config = new Config(this);

		Intent intent = getIntent();
		messageObj = (MsgString) intent.getExtras().getSerializable("message");
		
		adminId = (TextView) findViewById(R.id.roomAdmin);
		meetTime = (TextView) findViewById(R.id.runTime);
		meetPlace = (TextView) findViewById(R.id.meetplace);
		
		if (config.isNetworkAvailable()) {
			roomInfoStr = "5";
			
		}
	}
}
