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
	String idInfoStr; // 임시 문자열 저장 변수

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
		setTitle("로그인");

		final Config config = new Config(this);

		inputId = (EditText) findViewById(R.id.id);
		inputPw = (EditText) findViewById(R.id.passwd);
		loginBtn = (Button) findViewById(R.id.confirm);
		joinIn = (Button) findViewById(R.id.regist);

		loginBtn.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				String idTmp = inputId.getText().toString();
				String pwTmp = inputPw.getText().toString();

				if (idTmp.equals("") || pwTmp.equals("")) {
					Toast.makeText(getApplicationContext(),
							"아이디와 패스워드를 입력해 주세요.", Toast.LENGTH_LONG).show();
				} else {
					messageObj = new MsgString();
					SocketThread st = new SocketThread(messageObj);

					idInfoStr = "0"; // 로그인 메세지 헤더
					idInfoStr += "\t";
					idInfoStr += idTmp;
					idInfoStr += "\t";
					idInfoStr += pwTmp;
					messageObj.setActivityStr(idInfoStr);

					if (config.isNetworkAvailable()) {
						try {
							st.start();
						} catch (Exception e) {
							e.printStackTrace();
						}
						while (true) {
							if (messageObj.isThreadChange()) {
								idInfoStr = messageObj.getThreadStr();
								if (idInfoStr.equals("0")) {
									messageObj.setId(idTmp);
									Intent intent = new Intent(
											LoginActivity.this,
											MainMenuActivity.class);
									intent.putExtra("message", messageObj);
									startActivity(intent);
									overridePendingTransition(R.anim.left_in,
											R.anim.left_out);
									finish();
									break;
								} else if (idInfoStr.equals("quit")) {
									AlertDialog.Builder ab = null;
									ab = new AlertDialog.Builder(
											LoginActivity.this);
									ab.setMessage("아이디와 패스워드를 다시 확인해 주세요.");
									ab.setPositiveButton("확인", null);
									ab.setTitle("로그인 할 수 없습니다!");
									ab.show();
									break;
								} else {
									Toast.makeText(getApplicationContext(),
											"로그인 시도중 알수없는 오류가 발생하였습니다.",
											Toast.LENGTH_LONG).show();
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
			public void onClick(View v) {
				Intent intent = new Intent(LoginActivity.this,
						JoinActivity.class);
				startActivity(intent);
				overridePendingTransition(R.anim.left_in, R.anim.left_out);
			}
		});
	}

	public void onBackPressed() {
		AlertDialog.Builder ab = null;
		ab = new AlertDialog.Builder(LoginActivity.this);
		ab.setMessage("애플리케이션을 종료하시겠습니까?");
		ab.setPositiveButton(android.R.string.ok,
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						finish();
					}
				});
		ab.setNegativeButton(android.R.string.cancel, null);
		ab.setTitle("알림");
		ab.show();
	}
}
