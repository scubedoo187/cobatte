package com.cobatte.taxi;

import java.util.StringTokenizer;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class WaitingActivity extends Activity {
	MsgString messageObj;
	TextView roomName;
	TextView adminId;
	TextView meetTime;
	TextView meetPlace;
	TextView user1Id;
	TextView user2Id;
	TextView user3Id;
	Button usrInfoRefBtn;
	Button quitBtn;
	String roomInfoStr;
	String tmpmyId;
	String tmpadminId;
	final Config config = new Config(this);
		
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.waiting);
		setTitle("����");

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
		usrInfoRefBtn = (Button) findViewById(R.id.usrInfoRefBtn);
		quitBtn = (Button) findViewById(R.id.quitBtn);
		
		getRoomInfo();		
		
		usrInfoRefBtn.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				getRoomInfo();
			}
		});
		
		quitBtn.setOnClickListener(new View.OnClickListener() {
	         public void onClick(View v) {
	            String quitStr = null;
	            if (config.isNetworkAvailable()) {
	               quitStr = "6" + "\t" + tmpadminId + "\t" + tmpmyId;
	               messageObj.setActivityStr(quitStr);
	               while (true) {
	                  if (messageObj.isThreadChange()) {
	                     quitStr = messageObj.getThreadStr();
	                     break;
	                  }
	               }
	            } else
	               Toast.makeText(getApplicationContext(),
	                     "��Ʈ��ũ�� ������ �� �����ϴ�.", Toast.LENGTH_LONG).show();
	            if (quitStr.equals("6")) {
	               Intent intent = new Intent(WaitingActivity.this, MainMenuActivity.class);
	               intent.putExtra("message", messageObj);
	               startActivity(intent);
	               overridePendingTransition(R.anim.left_in, R.anim.left_out);
	               finish();
	            }
	         }
	      });
	}	

	public void getRoomInfo() {
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
		
		if (roomInfoStr.equals("0")) {
			AlertDialog.Builder ab = null;
			ab = new AlertDialog.Builder(WaitingActivity.this);
			ab.setTitle("�˸� �޼���");
			ab.setMessage("������ �����Ͽ� ���� ��������ϴ�.");
			ab.setNegativeButton("Ȯ��", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {
					Intent intent = new Intent(WaitingActivity.this, MainMenuActivity.class);
					intent.putExtra("message", messageObj);
					startActivity(intent);
					overridePendingTransition(R.anim.left_in, R.anim.left_out);
					finish();
				}
			});
			ab.show();
		} else {
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
		
		
	}
	
	public void onBackPressed(){
		AlertDialog.Builder ab = null;
		ab = new AlertDialog.Builder(WaitingActivity.this);
		ab.setMessage("�α׾ƿ� �Ͻðڽ��ϱ�?");
		ab.setNegativeButton("���", null);
		ab.setPositiveButton("������", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
					// 6 + tap + admin + tap + myId
				messageObj.setActivityStr("9");
				System.out.println("quit message has been send");
				Intent intent = new Intent(WaitingActivity.this,
						LoginActivity.class);
				startActivity(intent);
				overridePendingTransition(R.anim.left_in, R.anim.left_out);
				finish();
			}
		});
		ab.setTitle("�˸� �޼���");
		ab.show();
	}
}
