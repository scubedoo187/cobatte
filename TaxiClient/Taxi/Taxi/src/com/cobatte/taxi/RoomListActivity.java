package com.cobatte.taxi;

import java.util.ArrayList;
import java.util.StringTokenizer;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

public class RoomListActivity extends Activity implements OnItemClickListener {
	int index = 0;
	public static final int MAXUSERS = 500;
	ListView listView;
	MsgString messageObj;
	Button refreshBtn;
	String getroomInfo;
	String roomInfoStr;
	ArrayList<String> room = new ArrayList<String>();
	ArrayAdapter<String> adt;
	final Config config = new Config(this);
	RoomInfo roominfo[] = new RoomInfo[MAXUSERS];
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.roomlist);
		setTitle("�����ϱ�");

		Intent intent = getIntent();
		messageObj = (MsgString) intent.getExtras().getSerializable("message");
		
		adt = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, room);
		listView = (ListView) findViewById(R.id.list);
		listView.setAdapter(adt);
		listView.setOnItemClickListener(this);
				
		refreshBtn = (Button) findViewById(R.id.refresh);				
		
		requestRoomList();
		
		refreshBtn.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				requestRoomList();
			}
		});
	}
	
	public void onItemClick(AdapterView<?> parent, View view, int pos,	long id) {
		String roomInfoStr;
		roomInfoStr = "������ : " + roominfo[pos].getAdmin();
		roomInfoStr += "\n";
		roomInfoStr += "������� : " + roominfo[pos].getPlace();
		roomInfoStr += "\n";
		roomInfoStr += "���ӽð� : " + roominfo[pos].getHour() + " : " + roominfo[pos].getMin();
		roomInfoStr += "\n";
		roomInfoStr += "�ο� : " + roominfo[pos].getPerson() + "/4";
		
		final String joinInfoStr;
		joinInfoStr = "5" + "\t" + roominfo[pos].getAdmin() + "\t" + messageObj.getId();
				
		AlertDialog.Builder ab = null;
		ab = new AlertDialog.Builder(RoomListActivity.this);
		ab.setMessage(roomInfoStr);
		ab.setPositiveButton("�� ����", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				if (config.isNetworkAvailable()) {
					messageObj.setActivityStr(joinInfoStr);
					while (true) {
						if (messageObj.isThreadChange()) {
							String thdStr = messageObj.getThreadStr();
							if (thdStr.equals("5")) {
								Intent intent = new Intent(RoomListActivity.this,
										WaitingActivity.class);
								intent.putExtra("message", messageObj);
								startActivity(intent);
								overridePendingTransition(R.anim.left_in, R.anim.left_out);
								finish();
								break;
							} else if (thdStr.equals("full")) {
								Toast.makeText(getApplicationContext(), "���� ���� á���ϴ�.",
										Toast.LENGTH_LONG).show();
								break;
							} else {
								Toast.makeText(getApplicationContext(), "�� �� ���� ����",
										Toast.LENGTH_LONG).show();
								break;
							}
						}
					}
				}
			}
		});
		ab.setNegativeButton("���", null);
		ab.setTitle("�� ����");
		ab.show();
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
				"��Ʈ��ũ�� ����� �� �����ϴ�.", Toast.LENGTH_LONG).show();

		StringTokenizer roomsInfo = new StringTokenizer(roomInfoStr, "\t" );
		
		while (roomsInfo.hasMoreTokens()) {
			roominfo[index].setAdmin(roomsInfo.nextToken());
			roominfo[index].setRoomname(roomsInfo.nextToken());
			roominfo[index].setPlace(roomsInfo.nextToken());
			roominfo[index].setHour(roomsInfo.nextToken());
			roominfo[index].setMin(roomsInfo.nextToken());
			roominfo[index].setPerson(roomsInfo.nextToken());
			index++;
		}
		
		for (int i = 0; i < index; i++) {
			room.add("�� �̸� - " + roominfo[i].getRoomname() + "\n" + "���� ��� - " + 
								roominfo[i].getPlace() + "\n" + "�ð� - " + 
								roominfo[i].getHour() + ":" + roominfo[i].getMin());
		}
	}
	
	public void onBackPressed() {
		Intent intent = new Intent(RoomListActivity.this, MainMenuActivity.class);
		intent.putExtra("message", messageObj);
		startActivity(intent);
		overridePendingTransition(R.anim.left_in, R.anim.left_out);
		finish();
	}
}