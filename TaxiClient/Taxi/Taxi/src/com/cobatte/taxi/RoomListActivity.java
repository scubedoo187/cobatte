package com.cobatte.taxi;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ExpandableListView;

public class RoomListActivity extends Activity{

	ExpandableListView list;
	
	@Override
	 protected void onCreate(Bundle savedInstanceState) {
       super.onCreate(savedInstanceState);
       setContentView(R.layout.roomlist);
       setTitle("�����ϱ�");
       
       list = (ExpandableListView)findViewById(R.id.list);
       
       
       
       
	}
}
