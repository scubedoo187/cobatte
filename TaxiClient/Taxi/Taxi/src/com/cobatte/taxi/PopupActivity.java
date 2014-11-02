package com.cobatte.taxi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

public class PopupActivity extends Activity {
	Button okBtn;
	Button cancelBtn;
	TextView textBox;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.popup);
		setTitle("���� ����");

		String tempStr;
		tempStr = "�������� �� ���, ������Ʈ ����\n1. \n2. \n3. ....�غ���";

		okBtn = (Button) findViewById(R.id.ok);
		cancelBtn = (Button) findViewById(R.id.cancel);
		textBox = (TextView) findViewById(R.id.contents);

		textBox.setText(tempStr);

		okBtn.setOnClickListener(new View.OnClickListener() { // Ȯ�� ��ư Ŭ���� �̺�Ʈ
			public void onClick(View v) {
				Intent intent = new Intent(PopupActivity.this,
						LoginActivity.class); // �α��� ��Ƽ��Ƽ�� ����Ʈ~
				startActivity(intent);
				overridePendingTransition(R.anim.left_in, R.anim.left_out); // �ִϸ��̼�
																			// ȿ��
				finish(); // ���� ��Ƽ��Ƽ ����
			}
		});

		cancelBtn.setOnClickListener(new View.OnClickListener() { // ��� ��ư Ŭ����
																// �̺�Ʈ
		public void onClick(View v) {
			finish(); // ��� Ŭ���� ���� ��Ƽ��Ƽ ����.
		}
		});
	}
}