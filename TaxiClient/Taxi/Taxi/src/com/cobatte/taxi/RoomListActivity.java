package com.cobatte.taxi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ExpandableListView;

public class RoomListActivity extends Activity {
	ExpandableListView listView;
	Config config;
	MsgString messageObj;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.roomlist);
		setTitle("참가하기");

		Intent intent = getIntent();
		messageObj = (MsgString) intent.getExtras().getSerializable("message");
		
		listView = (ExpandableListView) findViewById(R.id.list);
	}
}
