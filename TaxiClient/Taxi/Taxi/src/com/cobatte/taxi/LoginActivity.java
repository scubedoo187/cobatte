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
	String idInfoStr; // �ӽ� ���ڿ� ���� ����

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
		setTitle("�α���");

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
							"���̵�� �н����带 �Է��� �ּ���.", Toast.LENGTH_LONG).show();
				} else {
					messageObj = new MsgString();
					SocketThread st = new SocketThread(messageObj);

					idInfoStr = "0"; // �α��� �޼��� ���
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
									ab.setMessage("���̵�� �н����带 �ٽ� Ȯ���� �ּ���.");
									ab.setPositiveButton("Ȯ��", null);
									ab.setTitle("�α��� �� �� �����ϴ�!");
									ab.show();
									break;
								} else {
									Toast.makeText(getApplicationContext(),
											"�α��� �õ��� �˼����� ������ �߻��Ͽ����ϴ�.",
											Toast.LENGTH_LONG).show();
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
		ab.setMessage("���ø����̼��� �����Ͻðڽ��ϱ�?");
		ab.setPositiveButton(android.R.string.ok,
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						finish();
					}
				});
		ab.setNegativeButton(android.R.string.cancel, null);
		ab.setTitle("�˸�");
		ab.show();
	}
}
