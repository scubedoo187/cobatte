package com.cobatte.taxi;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class Resist extends Activity{

	EditText idcon, pw, repw, email;
	Button overrap, done, clearAll;
	String birthYear, birthMonth, birthDay;
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.resist);
        setTitle("회원가입");
        
        Spinner year = (Spinner)findViewById(R.id.birth_y);
        Spinner month = (Spinner)findViewById(R.id.birth_m);
        Spinner day = (Spinner)findViewById(R.id.birth_d);
        
        idcon = (EditText)findViewById(R.id.idcon);
        pw = (EditText)findViewById(R.id.pw);
        repw = (EditText)findViewById(R.id.pwcon);
        email = (EditText)findViewById(R.id.email);
        overrap = (Button)findViewById(R.id.overrap);
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
        
        overrap.setOnClickListener(new View.OnClickListener() {
			
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
				
				
				if( idtmp.equals("") || idtmp == null || pwTmp1.equals("") || pwTmp1 == null || pwTmp2.equals("") || pwTmp2 == null || mTmp.equals("") || mTmp == null )  
				{
					AlertDialog.Builder ab = null;
					ab = new AlertDialog.Builder( Resist.this);
					ab.setMessage("빈 칸이 있습니다. 모두 입력해 주세요.");
					ab.setPositiveButton(android.R.string.ok, null);
					ab.setTitle("경고창");
					ab.show();
				}
								
				if(pwTmp1 != pwTmp2)
					Toast.makeText(getApplicationContext(), "패스워드가 일치하지 않습니다.", Toast.LENGTH_LONG).show();
				
				
				// DB 연동하여 각 값들 전송.
				// birthYear, birthMonth, birthDay;				
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
}
