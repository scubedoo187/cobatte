package com.cobatte.taxi;

import java.util.Calendar;

import android.app.Activity;
import android.app.TimePickerDialog;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

public class CreateRoomActivity extends Activity implements OnTimeSetListener{
	Button createBtn;
	Button backBtn;
	Button timePickBtn;
	EditText roomName;
	EditText placeName;
	TextView timeView;
	MsgString messageObj;
	String tempStr;
  	
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
	    setContentView(R.layout.newroom);
	    setTitle("새로운 방 만들기");
	    
	    Intent intent = getIntent();
		messageObj = (MsgString)intent.getExtras().getSerializable("message");
	    
	    createBtn = (Button)findViewById(R.id.create);
	    backBtn = (Button)findViewById(R.id.back);
	    timePickBtn = (Button)findViewById(R.id.timePickBtn);
	    roomName = (EditText)findViewById(R.id.roomName);
	    placeName = (EditText)findViewById(R.id.place);
	    timeView = (TextView)findViewById(R.id.timeView);
 
	    final TimePickerDialog.OnTimeSetListener timeListener = new OnTimeSetListener() {
			public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
				timeView.setText(String.format("%d : %d", hourOfDay, minute));
			}
		};
		
	    timePickBtn.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Calendar cal = Calendar.getInstance();
				TimePickerDialog timePickDlg = new TimePickerDialog(CreateRoomActivity.this, timeListener, cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), true);
				timePickDlg.setTitle("모임 시간 설정");
				timePickDlg.show();
			}
		});
	    
	    createBtn.setOnClickListener(new View.OnClickListener() {			
	    	public void onClick(View v){
	    		String adminId;
	    		String rName;
	    		String pName;
	    		
	    		adminId = messageObj.getId();
	    		rName = roomName.getText().toString();
	    		pName = placeName.getText().toString();
	    		
	    		if( rName.equals("") || pName.equals("") ){
	    			Toast.makeText(getApplicationContext(), "방 이름, 혹은 모임 장소를 모두 입력 해 주세요", Toast.LENGTH_LONG).show();
	    		}else if( timeView.getText().toString().equals("시간을 선택하세요") ){
	    			Toast.makeText(getApplicationContext(), "시간을 선택하세요", Toast.LENGTH_LONG).show();
	    		}else{
	    			tempStr = "3";
	    			tempStr += "\t";
	    			tempStr += adminId;
	    			tempStr += "\t";
	    			tempStr += rName;
	    			tempStr += "\t";
	    			tempStr += pName;
	    			tempStr += "\t";
	    			tempStr += timeView.getText().toString();
	    			//messageObj.send Message Query (tempStr);
	    			// test Message 
	    			Toast.makeText(getApplicationContext(), tempStr, Toast.LENGTH_LONG).show();
	    			
	    			Intent intent = new Intent(CreateRoomActivity.this, WaitingActivity.class);
	    			intent.putExtra("message", messageObj);
	    			startActivity(intent);
	    			overridePendingTransition(R.anim.left_in, R.anim.left_out);
	    		}
			}
	     });
      
	    backBtn.setOnClickListener(new View.OnClickListener() {
	    	public void onClick(View v) {
	    		finish();
			}
	    });
	}

	public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
		timeView.setText(String.format("%d : %d", hourOfDay, minute));
	}
}
