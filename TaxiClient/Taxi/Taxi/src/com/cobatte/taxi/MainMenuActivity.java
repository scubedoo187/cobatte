package com.cobatte.taxi;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;

public class MainMenuActivity extends Activity{	
	SocketThread sktThread;
	MsgString messageObj;
	Button createBtn;
	Button joinBtn;
	
	protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainmenu);
        setTitle("메뉴");
        
        Intent intent = getIntent();
		messageObj = (MsgString)intent.getExtras().getSerializable("message");
		
        createBtn = (Button)findViewById(R.id.create);
        joinBtn = (Button)findViewById(R.id.join);
        
        createBtn.setOnClickListener(new View.OnClickListener() {			
			public void onClick(View v) {
				Intent intent = new Intent( MainMenuActivity.this, CreateRoomActivity.class );
									intent.putExtra("message", messageObj);
				startActivity(intent);
				overridePendingTransition(R.anim.left_in, R.anim.left_out);
			}
		});
        
        joinBtn.setOnClickListener(new View.OnClickListener() {			
			public void onClick(View v) {				
				Intent intent = new Intent(MainMenuActivity.this, RoomListActivity.class);
				startActivity(intent);
				overridePendingTransition(R.anim.left_in, R.anim.left_out);
			}
		});
	}
	
	public void onBackPressed(){
		AlertDialog.Builder ab = null;
		ab = new AlertDialog.Builder( MainMenuActivity.this );
		ab.setMessage("로그아웃 하시겠습니까?");
		ab.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {			
			public void onClick(DialogInterface dialog, int which) {
				messageObj.setActivityStr("9");
				System.out.println("quit Message has been send");
				Intent intent = new Intent(MainMenuActivity.this, LoginActivity.class);
				startActivity(intent);
				overridePendingTransition(R.anim.left_in, R.anim.left_out);
				finish();	
			}
		});
		ab.setNegativeButton(android.R.string.cancel, null);
		ab.setTitle("알림 메세지.");
		ab.show();
	}
}