package com.cobatte.taxi;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class RegisterActivity extends Activity{

	EditText idcon, pw, repw, email;
	Button idButton, done, clearAll;
	String birthYear, birthMonth, birthDay;
	messageStr ms;
	String temp;
	
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        setTitle("ȸ������");
        
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
        
        done.setEnabled(false);
        
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
				AlertDialog.Builder ab = null;
				ab = new AlertDialog.Builder( RegisterActivity.this);
				ab.setMessage(idcon.getText().toString());
				ab.setNegativeButton("�ߺ�  Ȯ��", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						String idtmp = idcon.getText().toString();
						ms = new messageStr();
						SocketThread st = new SocketThread(ms);
						temp = "1";
						temp += "\t";
						temp += idtmp;						
						ms.setaStr(temp);
						
						if( isNetworkAvailable() )
						{	
							try{
								st.start();
							}catch(Exception e){ e.printStackTrace();}
							while( true ){
								if(ms.tChange())
								{											
									temp = ms.gettStr();
									System.out.println(temp);
									if( temp.equals("1") ) //�ߺ��� ���� ��,
									{
										idtmp+="�� ����� �����մϴ�.";
										done.setEnabled(true);
										break;
									}else
									{
										idtmp+="�� ����� �Ұ��մϴ�.";
										done.setEnabled(false);
										break;
									}
								}					
							}
							
						}
						else
						{	
							Toast.makeText(RegisterActivity.this, "��Ʈ��ũ�� ����� �� �����ϴ�." , Toast.LENGTH_LONG).show();
							
						}
						AlertDialog.Builder ab = null;
						ab = new AlertDialog.Builder( RegisterActivity.this );
						ab.setMessage(idtmp);
						ab.setNegativeButton("Ȯ��", null);
						ab.setTitle("��� ����");
						ab.show();
					}
				});
				ab.setPositiveButton("���", null);
				ab.setTitle("����� �� ���̵��� �ߺ� ���θ� üũ�մϴ�.");
				ab.show();
				//String temp = idcon.getText().toString();
				//Toast.makeText(getApplicationContext(), temp, Toast.LENGTH_LONG).show();				
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
					ab = new AlertDialog.Builder( RegisterActivity.this);
					ab.setMessage("���̵� �Է��� �ּ���.");
					ab.setPositiveButton(android.R.string.ok, null);
					ab.setTitle("���");
					ab.show();
					
				}else if( pwTmp1.equals("") || pwTmp1 == null )
				{
					AlertDialog.Builder ab = null;
					ab = new AlertDialog.Builder( RegisterActivity.this);
					ab.setMessage("��й�ȣ�� �Է��� �ּ���.");
					ab.setPositiveButton(android.R.string.ok, null);
					ab.setTitle("���");
					ab.show();
					pw.setFocusable(true);
				}else
				{
					if(pwTmp2.equals("") || pwTmp2 == null )				
					{
						AlertDialog.Builder ab = null;
						ab = new AlertDialog.Builder( RegisterActivity.this);
						ab.setMessage("��й�ȣ�� Ȯ���� �ּ���.");
						ab.setPositiveButton(android.R.string.ok, null);
						ab.setTitle("���");
						ab.show();
						repw.setFocusable(true);
					}else
						{
						if( mTmp.equals("") || mTmp == null )						
						{
							AlertDialog.Builder ab = null;
							ab = new AlertDialog.Builder( RegisterActivity.this);
							ab.setMessage("�̸����ּҸ� �Է��� �ּ���.");
							ab.setPositiveButton(android.R.string.ok, null);
							ab.setTitle("���");
							ab.show();
							email.setFocusable(true);
						}else
						{
							if( !pwTmp1.equals(pwTmp2) )							
							{
								AlertDialog.Builder ab = null;
								ab = new AlertDialog.Builder( RegisterActivity.this);
								ab.setMessage("��й�ȣ�� ��ġ���� �ʽ��ϴ�.");
								ab.setPositiveButton(android.R.string.ok, null);
								ab.setTitle("���");
								ab.show();
								pw.setFocusable(true);
							}else if( pwTmp1.equals(pwTmp2) ){
								ms = new messageStr();
								SocketThread st = new SocketThread(ms);								
								temp = "2";
								temp += "\t";
								temp += idtmp;
								temp += "\t";
								temp += pwTmp1;
								ms.setaStr(temp);
								
								if(isNetworkAvailable())
								{									
									try{
										st.start();
									}catch(Exception e){ e.printStackTrace(); }
									
									while(true){										
										if(ms.tChange())
										{											
											temp = ms.gettStr();
											if(temp.equals("quit"))//ȸ������ ����
											{
												finish();
												break;
											}else
												Toast.makeText(RegisterActivity.this, "ȸ������ ���� ������ �߻��Ͽ����ϴ�.", Toast.LENGTH_LONG).show();
										}
									}
								}
								else
									Toast.makeText(RegisterActivity.this, "��Ʈ��ũ�� ����� �� �����ϴ�.", Toast.LENGTH_LONG).show();
								
								//finish();
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
	
	private boolean isNetworkAvailable() {
		boolean available = false;
		
		ConnectivityManager conn = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		
		NetworkInfo netInfo = conn.getActiveNetworkInfo();
		
		if(netInfo != null && netInfo.isAvailable())
			available = true;
		
		return available;
    }
	
	public void onBackPressed(){
		AlertDialog.Builder ab = null;
		ab = new AlertDialog.Builder( RegisterActivity.this );
		ab.setMessage("ȸ�������� �׸��Ͻðڽ��ϱ�?");
		ab.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		ab.setNegativeButton(android.R.string.cancel, null);
		ab.setTitle("�� �������� �������� �մϴ�.");
		ab.show();
	}
}
