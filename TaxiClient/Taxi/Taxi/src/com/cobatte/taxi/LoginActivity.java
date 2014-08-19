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

	EditText inputId; // ���̵� �Է�
	EditText inputPw; // ��й�ȣ �Է�
	Button loginBtn; // Ȯ�� ��ư
	Button joinIn; // ȸ������ ��ư
	MsgString messageObj; // �޼��� ��ü
	String tempStr; // �ӽ� ���ڿ� ���� ����

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
		setTitle("�α���");

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
							"���̵�� �н����带 �Է��� �ּ���.", Toast.LENGTH_LONG).show();
				}else{
					messageObj = new MsgString();
					SocketThread st = new SocketThread(messageObj);

					tempStr = "0"; // �α��� �޼��� ���
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
									ab.setMessage("���̵�� �н����带 �ٽ� Ȯ���� �ּ���.");
									ab.setPositiveButton("Ȯ��", null);
									ab.setTitle("�α��� �� �� �����ϴ�!");
									ab.show();
									break;
								} else {
									Toast.makeText(getApplicationContext(),
											"�α��� �õ��� �˼����� ������ �߻��Ͽ����ϴ�.", Toast.LENGTH_LONG).show();
									break;
								}
							}
						}
					} else
						Toast.makeText(getApplicationContext(),
								"��Ʈ��ũ�� ����� �� �����ϴ�.", Toast.LENGTH_LONG).show();
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
		ab.setMessage("���ø����̼��� �����Ͻðڽ��ϱ�?");
		ab.setPositiveButton(android.R.string.ok,
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						finish();
					}
				});
		ab.setNegativeButton(android.R.string.cancel, null);
		ab.setTitle("�˸�");
		ab.show();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
}
