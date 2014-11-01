package com.cobatte.taxi;

import java.util.Calendar;

import android.app.Activity;
import android.app.TimePickerDialog;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

public class CreateRoomActivity extends Activity {
	Button createBtn;
	Button backBtn;
	Button timePickBtn;
	EditText roomName;
	EditText placeName;
	TextView hourView;
	TextView minView;
	MsgString messageObj;
	String roomInfoStr;
	String adminId;
	String rName;
	String pName;
	final Config config = new Config(this);

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.newroom);
		setTitle("���ο� �� �����");

		Intent intent = getIntent();
		messageObj = (MsgString) intent.getExtras().getSerializable("message");

		createBtn = (Button) findViewById(R.id.create);
		backBtn = (Button) findViewById(R.id.back);
		timePickBtn = (Button) findViewById(R.id.timePickBtn);
		roomName = (EditText) findViewById(R.id.roomName);
		placeName = (EditText) findViewById(R.id.place);
		hourView = (TextView) findViewById(R.id.hourView);
		minView = (TextView) findViewById(R.id.minView);

		final TimePickerDialog.OnTimeSetListener timeListener = new OnTimeSetListener() {
			public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
				hourView.setText(String.format("%2d", hourOfDay));
				minView.setText(String.format("%2d", minute));
			}
		};

		timePickBtn.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Calendar cal = Calendar.getInstance();
				TimePickerDialog timePickDlg = new TimePickerDialog(
						CreateRoomActivity.this, timeListener, cal
								.get(Calendar.HOUR_OF_DAY), cal
								.get(Calendar.MINUTE), true);
				timePickDlg.setTitle("���� �ð� ����");
				timePickDlg.show();
			}
		});

		createBtn.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {

				if (rName.equals("") || pName.equals("")) {
					Toast.makeText(getApplicationContext(),
							"�� �̸�, Ȥ�� ���� ��Ҹ� ��� �Է� �� �ּ���", Toast.LENGTH_LONG)
							.show();
				} else if (hourView.getText().toString().equals("�ð��� �����ϼ���")) {
					Toast.makeText(getApplicationContext(), "�ð��� �����ϼ���",
							Toast.LENGTH_LONG).show();
				} else {
					sendLoginMessage();
				}
			}
		});

		backBtn.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(CreateRoomActivity.this, MainMenuActivity.class);
				intent.putExtra("message", messageObj);
				startActivity(intent);
				finish();
			}
		});
	}
	
	public void sendLoginMessage() {
		adminId = messageObj.getId();
		rName = roomName.getText().toString();
		pName = placeName.getText().toString();
		roomInfoStr = "3";
		roomInfoStr += "\t";
		roomInfoStr += adminId;
		roomInfoStr += "\t";
		roomInfoStr += rName;
		roomInfoStr += "\t";
		roomInfoStr += pName;
		roomInfoStr += "\t";
		roomInfoStr += hourView.getText().toString();
		roomInfoStr += "\t";
		roomInfoStr += minView.getText().toString();
		
		if (config.isNetworkAvailable()) {
			messageObj.setActivityStr(roomInfoStr);
			while (true) {
				if (messageObj.isThreadChange()) {
					roomInfoStr = messageObj.getThreadStr();
					System.out.println("�游��� ��û �� �����κ��� ���� �޼��� "+roomInfoStr);
					if (roomInfoStr.equals("3")) {
						Intent intent = new Intent(
								CreateRoomActivity.this,
								WaitingActivity.class);
						intent.putExtra("message", messageObj);
						startActivity(intent);
						overridePendingTransition(R.anim.left_in,
								R.anim.left_out);
						finish();
						break;
					} else if (roomInfoStr.equals("quit")) {
						Toast.makeText(getApplicationContext(),
								"������ �߻��Ͽ����ϴ�. ����� ��õ� ���ּ���.",
								Toast.LENGTH_LONG).show();
						break;
					} else {
						Toast.makeText(getApplicationContext(), "�� ���� ����",
								Toast.LENGTH_LONG).show();
					}
				}
			}
		} else
			Toast.makeText(getApplicationContext(),
					"��Ʈ��ũ�� ����� �� �����ϴ�.", Toast.LENGTH_LONG).show();
	}
	
	public void onBackPressed() {
		Intent intent = new Intent(CreateRoomActivity.this, MainMenuActivity.class);
		intent.putExtra("message", messageObj);
		startActivity(intent);
		overridePendingTransition(R.anim.left_in, R.anim.left_out);
		finish();
	}
}
