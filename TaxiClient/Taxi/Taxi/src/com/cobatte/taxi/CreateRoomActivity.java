package com.cobatte.taxi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TimePicker;

public class CreateRoomActivity extends Activity{
	Button createBtn;
	Button backBtn;
	EditText roomName;
	EditText placeName;
	TimePicker timePick;
	
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
	    setContentView(R.layout.newroom);
	    setTitle("새로운 방 만들기");
	     
	    createBtn = (Button)findViewById(R.id.create);
	    backBtn = (Button)findViewById(R.id.back);
	    roomName = (EditText)findViewById(R.id.roomName);
	    placeName = (EditText)findViewById(R.id.place);
	    timePick = (TimePicker)findViewById(R.id.timepick);
	      
	    createBtn.setOnClickListener(new View.OnClickListener() {			
	    	public void onClick(View v) {
				Intent intent = new Intent(CreateRoomActivity.this, WaitingActivity.class);
				startActivity(intent);
				overridePendingTransition(R.anim.left_in, R.anim.left_out);
			}
	     });
      
	    backBtn.setOnClickListener(new View.OnClickListener() {
	    	public void onClick(View v) {
	    		finish();
			}
	    });
	}
}
