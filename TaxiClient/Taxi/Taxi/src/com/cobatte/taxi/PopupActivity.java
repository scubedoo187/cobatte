package com.cobatte.taxi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

public class PopupActivity extends Activity{
	
	Button ok, cancel;			// Ȯ�� ��� ��ư.
	TextView tv;				// �ؽ�Ʈ �� ������Ʈ
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);        
        setContentView(R.layout.popup);
        setTitle("���� ����");       
        
        String temp;
        temp = "1.���ع��� ��λ��� ������ ���� �ϴ����� �����ϻ� �츮���󸸼�. ����ȭ ��õ�� ȭ������ ���ѻ�� �������� ���� �����ϼ�.    2. �������� �� �ҳ��� ö���� �θ��� �ٶ����� �Һ����� �츮����ϼ� ����ȭ ��õ�� ȭ������ ���ѻ�� �������� ���� �����ϼ�.1.���ع��� ��λ��� ������ ���� �ϴ����� �����ϻ� �츮���󸸼�. ����ȭ ��õ�� ȭ������ ���ѻ�� �������� ���� �����ϼ�.    2. �������� �� �ҳ��� ö���� �θ��� �ٶ����� �Һ����� �츮����ϼ� ����ȭ ��õ�� ȭ������ ���ѻ�� �������� ���� �����ϼ�.1.���ع��� ��λ��� ������ ���� �ϴ����� �����ϻ� �츮���󸸼�. ����ȭ ��õ�� ȭ������ ���ѻ�� �������� ���� �����ϼ�.    2. �������� �� �ҳ��� ö���� �θ��� �ٶ����� �Һ����� �츮����ϼ� ����ȭ ��õ�� ȭ������ ���ѻ�� �������� ���� �����ϼ�.1.���ع��� ��λ��� ������ ���� �ϴ����� �����ϻ� �츮���󸸼�. ����ȭ ��õ�� ȭ������ ���ѻ�� �������� ���� �����ϼ�.    2. �������� �� �ҳ��� ö���� �θ��� �ٶ����� �Һ����� �츮����ϼ� ����ȭ ��õ�� ȭ������ ���ѻ�� �������� ���� �����ϼ�.1.���ع��� ��λ��� ������ ���� �ϴ����� �����ϻ� �츮���󸸼�. ����ȭ ��õ�� ȭ������ ���ѻ�� �������� ���� �����ϼ�.    2. �������� �� �ҳ��� ö���� �θ��� �ٶ����� �Һ����� �츮����ϼ� ����ȭ ��õ�� ȭ������ ���ѻ�� �������� ���� �����ϼ�.1.���ع��� ��λ��� ������ ���� �ϴ����� �����ϻ� �츮���󸸼�. ����ȭ ��õ�� ȭ������ ���ѻ�� �������� ���� �����ϼ�.    2. �������� �� �ҳ��� ö���� �θ��� �ٶ����� �Һ����� �츮����ϼ� ����ȭ ��õ�� ȭ������ ���ѻ�� �������� ���� �����ϼ�.";
                        		// �˾�â �ؽ�Ʈ �׽�Ʈ...
        ok = (Button)findViewById(R.id.ok);
        cancel = (Button)findViewById(R.id.cancel);
        tv = (TextView)findViewById(R.id.contents);
        
        tv.setText(temp);
        
        ok.setOnClickListener(new View.OnClickListener() {		// Ȯ�� ��ư Ŭ���� �̺�Ʈ
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				Intent intent = new Intent(PopupActivity.this, LoginActivity.class);		// �α��� ��Ƽ��Ƽ�� ����Ʈ~
				startActivity(intent);
				overridePendingTransition(R.anim.left_in, R.anim.left_out);	// �ִϸ��̼� ȿ��
				finish();		// ���� ��Ƽ��Ƽ ����
			}
		});
        
        cancel.setOnClickListener(new View.OnClickListener() {	// ��� ��ư Ŭ���� �̺�Ʈ 
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				finish();		// ��� Ŭ���� ���� ��Ƽ��Ƽ ����.
				
			}
		});
        
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
}
