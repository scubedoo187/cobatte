package com.cobatte.taxi;

import java.util.ArrayList;
import java.util.StringTokenizer;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

public class RoomListActivity extends Activity {
	int index = 0;
	ListView listView;
	MsgString messageObj;
	Button refreshBtn;
	String getroomInfo;
	String roomInfoStr;
	ArrayList<String> room;
	ArrayAdapter<String> adt;
	String admin[] = new String[500];
	String roomName[] = new String[500];
	String place[] = new String[500];
	String hour[] = new String[500];
	String min[] = new String[500];
	String person[] = new String[500];
	final Config config = new Config(this);
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.roomlist);
		setTitle("참가하기");

		Intent intent = getIntent();
		messageObj = (MsgString) intent.getExtras().getSerializable("message");
		
		room = new ArrayList<String>();
		adt = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, room);
		listView = (ListView) findViewById(R.id.list);
		listView.setAdapter(adt);
				
		refreshBtn = (Button) findViewById(R.id.refresh);				
		
		requestRoomList();
		
		refreshBtn.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				requestRoomList();
			}
		});
	}
	
	public void requestRoomList() {
		getroomInfo = "4";	
		
		if (config.isNetworkAvailable()) {
			messageObj.setActivityStr(getroomInfo);
			while (true) {
				if (messageObj.isThreadChange()) {
					roomInfoStr = messageObj.getThreadStr();
					break;
				}
			}
		} else 
			Toast.makeText(getApplicationContext(),
				"네트워크를 사용할 수 없습니다.", Toast.LENGTH_LONG).show();

		StringTokenizer roomsInfo = new StringTokenizer(roomInfoStr, "\t" );
		
		while (roomsInfo.hasMoreTokens()) {
			admin[index] = roomsInfo.nextToken();
			roomName[index] = roomsInfo.nextToken();
			place[index] = roomsInfo.nextToken();
			hour[index] = roomsInfo.nextToken();
			min[index] = roomsInfo.nextToken();
			person[index] = roomsInfo.nextToken();
			index++;
		}
		
		for (int i = 0; i < index; i++) {
			room.add(roomName[i] + "\n" + "모임 장소 - " + place[i] + "\t" + 
													"시각 - " + hour[i] + ":" + min[i]);
		}
	}
}