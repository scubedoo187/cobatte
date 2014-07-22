package com.cobatte.taxi;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class Register extends Activity{

	EditText idcon, pw, repw, email;
	Button idButton, done, clearAll;
	String birthYear, birthMonth, birthDay;
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        setTitle("회원가입");
        
        Spinner year = (Spinner)findViewById(R.id.birth_y);
        Spinner month = (Spinner)findViewById(R.id.birth_m);
        Spinner day = (Spinner)findViewById(R.id.birth_d);
        
        idcon = (EditText)findViewById(R.id.idcon);
        pw = (EditText)findViewById(R.id.pw);
        repw = (EditText)findViewById(R.id.pwcon);
        email = (EditText)findViewById(R.id.email);
        idButton = (Button)findViewById(R.id.idBtn);
        done = (Button)findViewById(R.id.done);
        clearAll = (Button)findViewById(R.id.clearAll);
        
        ArrayAdapter<CharSequence> adtY = ArrayAdapter.createFromResource(this, R.array.year_arr, android.R.layout.simple_spinner_dropdown_item);
        year.setAdapter(adtY);
        ArrayAdapter<CharSequence> adtM = ArrayAdapter.createFromResource(this, R.array.month_arr, android.R.layout.simple_spinner_dropdown_item);
        month.setAdapter(adtM);
        ArrayAdapter<CharSequence> adtD = ArrayAdapter.createFromResource(this, R.array.day_arr, android.R.layout.simple_spinner_dropdown_item);
        day.setAdapter(adtD);
        
        year.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int pos, long id) {
				// TODO Auto-generated method stub
				birthYear = parent.getItemAtPosition(pos).toString();
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub				
			}
		});
        month.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int pos, long id) {
				// TODO Auto-generated method stub
				birthMonth = parent.getItemAtPosition(pos).toString();
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub				
			}
		});
        day.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int pos, long id) {
				// TODO Auto-generated method stub
				birthDay = parent.getItemAtPosition(pos).toString();
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub				
			}
		});
        
        idButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				//sql 연동 하여 idcon 입력된 id 값과 DB 비교
				String temp = idcon.getText().toString();
				Toast.makeText(getApplicationContext(), temp, Toast.LENGTH_LONG).show();				
			}
		});
        
        done.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String idtmp, pwTmp1, pwTmp2, mTmp;
				idtmp = idcon.getText().toString();
				pwTmp1 = pw.getText().toString();
				pwTmp2 = repw.getText().toString();
				mTmp = email.getText().toString();
				
				if( idtmp.equals("") || idtmp == null )  
				{
					AlertDialog.Builder ab = null;
					ab = new AlertDialog.Builder( Register.this);
					ab.setMessage("아이디를 입력해 주세요.");
					ab.setPositiveButton(android.R.string.ok, null);
					ab.setTitle("경고");
					ab.show();
				}else if( pwTmp1.equals("") || pwTmp1 == null )
				{
					AlertDialog.Builder ab = null;
					ab = new AlertDialog.Builder( Register.this);
					ab.setMessage("비밀번호를 입력해 주세요.");
					ab.setPositiveButton(android.R.string.ok, null);
					ab.setTitle("경고");
					ab.show();
				}else
				{
					if(pwTmp2.equals("") || pwTmp2 == null )				
					{
						AlertDialog.Builder ab = null;
						ab = new AlertDialog.Builder( Register.this);
						ab.setMessage("비밀번호 확인란을 입력해 주세요.");
						ab.setPositiveButton(android.R.string.ok, null);
						ab.setTitle("경고");
						ab.show();
					}else
						{
						if( mTmp.equals("") || mTmp == null )						
						{
							AlertDialog.Builder ab = null;
							ab = new AlertDialog.Builder( Register.this);
							ab.setMessage("이메일주소를 입력해 주세요.");
							ab.setPositiveButton(android.R.string.ok, null);
							ab.setTitle("경고");
							ab.show();
						}else
						{
							if( !pwTmp1.equals(pwTmp2) )							
							{
								AlertDialog.Builder ab = null;
								ab = new AlertDialog.Builder( Register.this);
								ab.setMessage("비밀번호가 일치하지 않습니다.");
								ab.setPositiveButton(android.R.string.ok, null);
								ab.setTitle("경고");
								ab.show();
							}else if( pwTmp1.equals(pwTmp2) ){
								finish();
								// 		서버에 각 값들 전송.
								// 		birthYear, birthMonth, birthDay;
								
							}
						}
						}
						
				}
			}
			
		});
        
        clearAll.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				idcon.setText("");
				pw.setText("");
				repw.setText("");
				email.setText("");				
			}
		});
        
        
                		
	}
	public void onBackPressed(){
		AlertDialog.Builder ab = null;
		ab = new AlertDialog.Builder( Register.this );
		ab.setMessage("회원가입을 그만하시겠습니까?");
		ab.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		ab.setNegativeButton(android.R.string.cancel, null);
		ab.setTitle("이 페이지를 닫으려고 합니다.");
		ab.show();
	}
}
