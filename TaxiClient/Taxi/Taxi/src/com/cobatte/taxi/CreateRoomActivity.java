package com.cobatte.taxi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TimePicker;

public class CreateRoomActivity extends Activity{

	Button create, back;
	EditText roomName, roomPasswd, place;
	TimePicker time;
	
	@Override
	 protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.newroom);
      setTitle("새로운 방 만들기");
      
      create = (Button)findViewById(R.id.create);
      back = (Button)findViewById(R.id.back);
      roomName = (EditText)findViewById(R.id.roomName);
      roomPasswd = (EditText)findViewById(R.id.roomPasswd);
      place = (EditText)findViewById(R.id.place);
      time = (TimePicker)findViewById(R.id.timepick);
      
      create.setOnClickListener(new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent intent = new Intent(CreateRoomActivity.this, WaitingActivity.class);
			startActivity(intent);
			overridePendingTransition(R.anim.left_in, R.anim.left_out);
		}
	});
      
      back.setOnClickListener(new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			finish();
		}
	});
      
      
	}
	
}
