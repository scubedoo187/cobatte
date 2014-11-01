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
		setTitle("공지 사항");

		String tempStr;
		tempStr = "공지사항 및 약관, 업데이트 내용\n1. \n2. \n3. ....준비중";

		okBtn = (Button) findViewById(R.id.ok);
		cancelBtn = (Button) findViewById(R.id.cancel);
		textBox = (TextView) findViewById(R.id.contents);

		textBox.setText(tempStr);

		okBtn.setOnClickListener(new View.OnClickListener() { // 확인 버튼 클릭시 이벤트
			public void onClick(View v) {
				Intent intent = new Intent(PopupActivity.this,
						LoginActivity.class); // 로그인 액티비티로 인텐트~
				startActivity(intent);
				overridePendingTransition(R.anim.left_in, R.anim.left_out); // 애니메이션
																			// 효과
				finish(); // 현재 액티비티 끝냄
			}
		});

		cancelBtn.setOnClickListener(new View.OnClickListener() { // 취소 버튼 클릭시
																// 이벤트
		public void onClick(View v) {
			finish(); // 취소 클릭시 현재 액티비티 끝냄.
		}
		});
	}
}