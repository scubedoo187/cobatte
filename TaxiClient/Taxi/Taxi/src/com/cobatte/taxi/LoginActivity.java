package com.cobatte.taxi;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends Activity {

	EditText inputId; // 아이디 입력
	EditText inputPw; // 비밀번호 입력
	Button loginBtn; // 확인 버튼
	Button joinIn; // 회원가입 버튼
	MsgString messageObj; // 메세지 객체
	String tempStr; // 임시 문자열 저장 변수

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
		setTitle("로그인");

		inputId = (EditText) findViewById(R.id.id);
		inputPw = (EditText) findViewById(R.id.passwd);
		loginBtn = (Button) findViewById(R.id.confirm);
		joinIn = (Button) findViewById(R.id.regist);

		loginBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String idTmp = inputId.getText().toString();
				String pwTmp = inputPw.getText().toString();

				if (idTmp.equals("") || pwTmp.equals("")){
					Toast.makeText(getApplicationContext(),
							"아이디와 패스워드를 입력해 주세요.", Toast.LENGTH_LONG).show();
				}else{
					messageObj = new MsgString();
					SocketThread st = new SocketThread(messageObj);

					tempStr = "0"; // 로그인 메세지 헤더
					tempStr += "\t";
					tempStr += idTmp;
					tempStr += "\t";
					tempStr += pwTmp;
					messageObj.setActivityStr(tempStr);

					if (isNetworkAvailable()){
						try {
							st.start();
						} catch (Exception e) {
							e.printStackTrace();
						}
						while (true) {
							if (messageObj.isThreadChange()) {
								tempStr = messageObj.getThreadStr();
								if (tempStr.equals("0")) {
									Intent intent = new Intent(
											LoginActivity.this, MainMenuActivity.class);
									intent.putExtra("message", messageObj);
									startActivity(intent);
									overridePendingTransition(R.anim.left_in, R.anim.left_out);
									break;
								} else if (tempStr.equals("quit")) {
									AlertDialog.Builder ab = null;
									ab = new AlertDialog.Builder(LoginActivity.this);
									ab.setMessage("아이디와 패스워드를 다시 확인해 주세요.");
									ab.setPositiveButton("확인", null);
									ab.setTitle("로그인 할 수 없습니다!");
									ab.show();
									break;
								} else {
									Toast.makeText(getApplicationContext(),
											"로그인 시도중 알수없는 오류가 발생하였습니다.", Toast.LENGTH_LONG).show();
									break;
								}
							}
						}
					} else
						Toast.makeText(getApplicationContext(),
								"네트워크를 사용할 수 없습니다.", Toast.LENGTH_LONG).show();
				}
			}
		});

		joinIn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(LoginActivity.this,
						JoinActivity.class);
				startActivity(intent);
				overridePendingTransition(R.anim.left_in, R.anim.left_out);
			}
		});

	}

	private boolean isNetworkAvailable() {
		boolean available = false;
		
		ConnectivityManager conn
			= (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo netInfo = conn.getActiveNetworkInfo();
		
		if (netInfo != null && netInfo.isAvailable())
			available = true;

		return available;
	}

	public void onBackPressed() {
		AlertDialog.Builder ab = null;
		ab = new AlertDialog.Builder(LoginActivity.this);
		ab.setMessage("애플리케이션을 종료하시겠습니까?");
		ab.setPositiveButton(android.R.string.ok,
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						finish();
					}
				});
		ab.setNegativeButton(android.R.string.cancel, null);
		ab.setTitle("알림");
		ab.show();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
}
