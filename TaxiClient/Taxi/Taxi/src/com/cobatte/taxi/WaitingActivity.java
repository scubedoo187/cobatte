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
		setTitle("대기실");

		final Config config = new Config(this);

		Intent intent = getIntent();
		messageObj = (MsgString) intent.getExtras().getSerializable("message");
		
		adminId = (TextView) findViewById(R.id.roomAdmin);
		meetTime = (TextView) findViewById(R.id.runTime);
		meetPlace = (TextView) findViewById(R.id.meetplace);
		
		roomInfoStr = "7";
		roomInfoStr += "\t";
		roomInfoStr += messageObj.getId();
		if (config.isNetworkAvailable()) {	// 방 대기실 진입 후 방정보 받아오기 메세지
			// roonInfoStr 서버에 보내기 
		}
	}
	
	public void onBackPressed(){
		AlertDialog.Builder ab = null;
		ab = new AlertDialog.Builder(WaitingActivity.this);
		ab.setMessage("");
		ab.setNegativeButton("취소", null);
		ab.setPositiveButton("나가기", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				if (config.isNetworkAvailable()) {
					// 6 + tap + admin + tap + myId
				} else 
					Toast.makeText(WaitingActivity.this, "네트워크를 사용할 수 없습니다.",
							Toast.LENGTH_LONG).show();
			}
		});
		ab.setTitle("알림");
		ab.show();
	}
}
