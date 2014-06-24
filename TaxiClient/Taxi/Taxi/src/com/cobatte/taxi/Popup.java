package com.cobatte.taxi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

public class Popup extends Activity{
	
	Button ok, cancel;
	TextView tv;
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);        
        setContentView(R.layout.popup);
               
        String temp;
        temp = "1.���ع��� ��λ��� ������ ���� �ϴ����� �����ϻ� �츮���󸸼�. ����ȭ ��õ�� ȭ������ ���ѻ�� �������� ���� �����ϼ�.    2. �������� �� �ҳ��� ö���� �θ��� �ٶ����� �Һ����� �츮����ϼ� ����ȭ ��õ�� ȭ������ ���ѻ�� �������� ���� �����ϼ�.1.���ع��� ��λ��� ������ ���� �ϴ����� �����ϻ� �츮���󸸼�. ����ȭ ��õ�� ȭ������ ���ѻ�� �������� ���� �����ϼ�.    2. �������� �� �ҳ��� ö���� �θ��� �ٶ����� �Һ����� �츮����ϼ� ����ȭ ��õ�� ȭ������ ���ѻ�� �������� ���� �����ϼ�.1.���ع��� ��λ��� ������ ���� �ϴ����� �����ϻ� �츮���󸸼�. ����ȭ ��õ�� ȭ������ ���ѻ�� �������� ���� �����ϼ�.    2. �������� �� �ҳ��� ö���� �θ��� �ٶ����� �Һ����� �츮����ϼ� ����ȭ ��õ�� ȭ������ ���ѻ�� �������� ���� �����ϼ�.1.���ع��� ��λ��� ������ ���� �ϴ����� �����ϻ� �츮���󸸼�. ����ȭ ��õ�� ȭ������ ���ѻ�� �������� ���� �����ϼ�.    2. �������� �� �ҳ��� ö���� �θ��� �ٶ����� �Һ����� �츮����ϼ� ����ȭ ��õ�� ȭ������ ���ѻ�� �������� ���� �����ϼ�.1.���ع��� ��λ��� ������ ���� �ϴ����� �����ϻ� �츮���󸸼�. ����ȭ ��õ�� ȭ������ ���ѻ�� �������� ���� �����ϼ�.    2. �������� �� �ҳ��� ö���� �θ��� �ٶ����� �Һ����� �츮����ϼ� ����ȭ ��õ�� ȭ������ ���ѻ�� �������� ���� �����ϼ�.1.���ع��� ��λ��� ������ ���� �ϴ����� �����ϻ� �츮���󸸼�. ����ȭ ��õ�� ȭ������ ���ѻ�� �������� ���� �����ϼ�.    2. �������� �� �ҳ��� ö���� �θ��� �ٶ����� �Һ����� �츮����ϼ� ����ȭ ��õ�� ȭ������ ���ѻ�� �������� ���� �����ϼ�.";
                        
        ok = (Button)findViewById(R.id.ok);
        cancel = (Button)findViewById(R.id.cancel);
        tv = (TextView)findViewById(R.id.contents);
        
        tv.setText(temp);
        
        ok.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				Intent intent = new Intent(Popup.this, Login.class);
				startActivity(intent);
				finish();
			}
		});
        
        cancel.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				finish();
				
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
