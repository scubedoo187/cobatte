package com.cobatte.taxi;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ExpandableListView;

public class RoomListActivity extends Activity {
	ExpandableListView listView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.roomlist);
		setTitle("�����ϱ�");

		listView = (ExpandableListView) findViewById(R.id.list);
	}
}
