package com.cobatte.taxi;

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
	TextView adminId;
	TextView meetTime;
	TextView meetPlace;
	ListView member;
	String roomInfoStr;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.waiting);
		setTitle("����");

		final Config config = new Config(this);

		Intent intent = getIntent();
		messageObj = (MsgString) intent.getExtras().getSerializable("message");
		
		adminId = (TextView) findViewById(R.id.roomAdmin);
		meetTime = (TextView) findViewById(R.id.runTime);
		meetPlace = (TextView) findViewById(R.id.meetplace);
		
		roomInfoStr = "7";
		roomInfoStr += "\t";
		roomInfoStr += messageObj.getId();
		if (config.isNetworkAvailable()) {	// �� ���� ���� �� ������ �޾ƿ��� �޼���
			// roonInfoStr ������ ������ 
		}
	}
	
	public void onBackPressed(){
		AlertDialog.Builder ab = null;
		ab = new AlertDialog.Builder(WaitingActivity.this);
		ab.setMessage("");
		ab.setNegativeButton("���", null);
		ab.setPositiveButton("������", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				if (config.isNetworkAvailable()) {
					// 6 + tap + admin + tap + myId
				} else 
					Toast.makeText(WaitingActivity.this, "��Ʈ��ũ�� ����� �� �����ϴ�.",
							Toast.LENGTH_LONG).show();
			}
		});
		ab.setTitle("�˸�");
		ab.show();
	}
}
