package com.cobatte.taxi;

import java.util.StringTokenizer;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class WaitingActivity extends Activity {
	Config config;
	MsgString messageObj;
	TextView roomName;
	TextView adminId;
	TextView meetTime;
	TextView meetPlace;
	TextView user1Id;
	TextView user2Id;
	TextView user3Id;
	String roomInfoStr;
	String tmpmyId;
	String tmpadminId;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.waiting);
		setTitle("����");

		final Config config = new Config(this);

		Intent intent = getIntent();
		messageObj = (MsgString) intent.getExtras().getSerializable("message");
		tmpmyId = messageObj.getId();
		
		roomName = (TextView) findViewById(R.id.roomName);
		adminId = (TextView) findViewById(R.id.roomAdmin);
		meetTime = (TextView) findViewById(R.id.runTime);
		meetPlace = (TextView) findViewById(R.id.meetplace);
		user1Id = (TextView) findViewById(R.id.user1);
		user2Id = (TextView) findViewById(R.id.user2);
		user3Id = (TextView) findViewById(R.id.user3);
		
		roomInfoStr = "7";
		roomInfoStr += "\t";
		roomInfoStr += tmpmyId;
		if (config.isNetworkAvailable()) {	// �� ���� ���� �� ������ �޾ƿ��� �޼���
			// roonInfoStr ������ ������ 
			messageObj.setActivityStr(roomInfoStr);
			while (true) {
				if (messageObj.isThreadChange()) {
					roomInfoStr = messageObj.getThreadStr();
					break;
				}
			}
		} 
		
		StringTokenizer roomInfos = new StringTokenizer(roomInfoStr, "\t");
		adminId.setText(roomInfos.nextToken());
		user1Id.setText(roomInfos.nextToken());
		user2Id.setText(roomInfos.nextToken());
		user3Id.setText(roomInfos.nextToken());
		roomName.setText(roomInfos.nextToken());
		meetPlace.setText(roomInfos.nextToken());
		meetTime.setText(roomInfos.nextToken() + " : " + roomInfos.nextToken());
		tmpadminId = adminId.getText().toString();
	}
	
	public void onBackPressed(){
		AlertDialog.Builder ab = null;
		ab = new AlertDialog.Builder(WaitingActivity.this);
		ab.setMessage("�濡�� �����Ͻðڽ��ϱ�?");
		ab.setNegativeButton("���", null);
		ab.setPositiveButton("������", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				if (config.isNetworkAvailable()) {
					// 6 + tap + admin + tap + myId
					String quitStr = "6" + "\t" + tmpadminId + "\t" + tmpmyId;
					messageObj.setActivityStr(quitStr);
					while (true) {
						if (messageObj.isThreadChange()) {
							quitStr = messageObj.getThreadStr();
							break;
						}
					}
					if (quitStr == "6")
						finish();
				} else 
					Toast.makeText(WaitingActivity.this, "��Ʈ��ũ�� ����� �� �����ϴ�.",
							Toast.LENGTH_LONG).show();
			}
		});
		ab.setTitle("�˸�");
		ab.show();
	}
}
