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

public class JoinActivity extends Activity{
	EditText inputId;
	EditText inputPw;
	EditText inputRePw;
	EditText inputEmail;
	Button checkId;
	Button doneBtn;
	Button clearAll;
	String birthYear;
	String birthMonth;
	String birthDay;
	MsgString messageObj;
	String tempStr;
	String checkIdTemp;
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        setTitle("ȸ������");
        
        Spinner year = (Spinner)findViewById(R.id.birth_y);
        Spinner month = (Spinner)findViewById(R.id.birth_m);
        Spinner day = (Spinner)findViewById(R.id.birth_d);
        
        inputId = (EditText)findViewById(R.id.idcon);
        inputPw = (EditText)findViewById(R.id.pw);
        inputRePw = (EditText)findViewById(R.id.pwcon);
        inputEmail = (EditText)findViewById(R.id.email);
        checkId = (Button)findViewById(R.id.idBtn);
        doneBtn = (Button)findViewById(R.id.done);
        clearAll = (Button)findViewById(R.id.clearAll);
        
        doneBtn.setEnabled(false);
        
        ArrayAdapter<CharSequence> adtYear =
        		ArrayAdapter.createFromResource(this, 
        				R.array.year_arr, android.R.layout.simple_spinner_dropdown_item);        
        year.setAdapter(adtYear);
        
        ArrayAdapter<CharSequence> adtMonth = 
        		ArrayAdapter.createFromResource(this,
        				R.array.month_arr, android.R.layout.simple_spinner_dropdown_item);
        month.setAdapter(adtMonth);
        
        ArrayAdapter<CharSequence> adtDay = 
        		ArrayAdapter.createFromResource(this,
        				R.array.day_arr, android.R.layout.simple_spinner_dropdown_item);
        day.setAdapter(adtDay);
        
        year.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
				// TODO Auto-generated method stub
				birthYear = parent.getItemAtPosition(pos).toString();
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		});
        
        month.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int pos, long id) {
				// TODO Auto-generated method stub
				birthMonth = parent.getItemAtPosition(pos).toString();
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		});
        
        day.setOnItemSelectedListener(new OnItemSelectedListener() {        	
			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int pos, long id) {
				// TODO Auto-generated method stub
				birthDay = parent.getItemAtPosition(pos).toString();
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		});
        
        checkId.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if( inputId.getText().toString().equals("") ){
					AlertDialog.Builder ab = null;
					ab = new AlertDialog.Builder( JoinActivity.this);
					ab.setMessage("���̵� ���� �Է��ϼ���.");
					ab.setTitle("���");
					ab.setPositiveButton("Ȯ��", null);
					ab.show();
				}else{
					checkIdTemp = inputId.getText().toString();
					AlertDialog.Builder ab = null;
					ab = new AlertDialog.Builder( JoinActivity.this);
					ab.setMessage(checkIdTemp);
					ab.setNegativeButton("�ߺ�  Ȯ��", new DialogInterface.OnClickListener() {						
						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
							String idtmp = inputId.getText().toString();
							messageObj = new MsgString();
							SocketThread st = new SocketThread(messageObj);
							tempStr = "1";
							tempStr += "\t";
							tempStr += idtmp;
							messageObj.setActivityStr(tempStr);
							
							try{							
								if( isNetworkAvailable() ){
									try{
										st.start();
									}catch(Exception e){ e.printStackTrace();}
									
									while( true ){
										if(messageObj.isThreadChange()){
											tempStr = messageObj.getThreadStr();
											System.out.println(tempStr);
											if( tempStr.equals("1") ){ //�ߺ��� ���� ��,
												idtmp+="�� ����� �����մϴ�.";
												doneBtn.setEnabled(true);
												break;
											}else{
												idtmp += "�� ����� �Ұ��մϴ�.";
												doneBtn.setEnabled(false);
												break;
											}
										}					
									}
									messageObj.setActivityStr("quit");	
								}
							else{	
								Toast.makeText(JoinActivity.this, "��Ʈ��ũ�� ����� �� �����ϴ�." , Toast.LENGTH_LONG).show();						
							}
							}catch(Exception e){
								Toast.makeText(getApplicationContext(), "����ġ ���� ������ �߻��߽��ϴ�.", Toast.LENGTH_LONG).show();
							}
							
							AlertDialog.Builder ab = null;
							ab = new AlertDialog.Builder( JoinActivity.this );
							ab.setMessage(idtmp);
							ab.setNegativeButton("Ȯ��", null);
							ab.setTitle("��� ����");
							ab.show();
						}
					});
					ab.setPositiveButton("���", null);
					ab.setTitle("����� �� ���̵��� �ߺ� ���θ� üũ�մϴ�.");
					ab.show();
				}					
			}
		});
        
        doneBtn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String idtmp, pwTmp1, pwTmp2, mTmp;
				idtmp = inputId.getText().toString();
				pwTmp1 = inputPw.getText().toString();
				pwTmp2 = inputRePw.getText().toString();
				mTmp = inputEmail.getText().toString();
				
				if( idtmp.equals(checkIdTemp) ){
					if( idtmp.equals("") || idtmp == null ){
						AlertDialog.Builder ab = null;
						ab = new AlertDialog.Builder( JoinActivity.this);
						ab.setMessage("���̵� �Է��� �ּ���.");
						ab.setPositiveButton(android.R.string.ok, null);
						ab.setTitle("���");
						ab.show();
					}else if( pwTmp1.equals("") || pwTmp1 == null ){
						AlertDialog.Builder ab = null;
						ab = new AlertDialog.Builder( JoinActivity.this);
						ab.setMessage("��й�ȣ�� �Է��� �ּ���.");
						ab.setPositiveButton(android.R.string.ok, null);
						ab.setTitle("���");
						ab.show();
						inputPw.setFocusable(true);
					}else{
						if(pwTmp2.equals("") || pwTmp2 == null ){
							AlertDialog.Builder ab = null;
							ab = new AlertDialog.Builder( JoinActivity.this);
							ab.setMessage("��й�ȣ�� Ȯ���� �ּ���.");
							ab.setPositiveButton(android.R.string.ok, null);
							ab.setTitle("���");
							ab.show();
							inputRePw.setFocusable(true);
						}else{
							if( mTmp.equals("") || mTmp == null ){
								AlertDialog.Builder ab = null;
								ab = new AlertDialog.Builder( JoinActivity.this);
								ab.setMessage("�̸����ּҸ� �Է��� �ּ���.");
								ab.setPositiveButton(android.R.string.ok, null);
								ab.setTitle("���");
								ab.show();
								inputEmail.setFocusable(true);
							}else{
								if( !pwTmp1.equals(pwTmp2) ){
									AlertDialog.Builder ab = null;
									ab = new AlertDialog.Builder( JoinActivity.this);
									ab.setMessage("��й�ȣ�� ��ġ���� �ʽ��ϴ�.");
									ab.setPositiveButton(android.R.string.ok, null);
									ab.setTitle("���");
									ab.show();
									inputPw.setFocusable(true);
								}else if( pwTmp1.equals(pwTmp2) ){
									messageObj = new MsgString();
									SocketThread st = new SocketThread(messageObj);								
									tempStr = "2";
									tempStr += "\t";
									tempStr += idtmp;
									tempStr += "\t";
									tempStr += pwTmp1;
									messageObj.setActivityStr(tempStr);
									
									if(isNetworkAvailable()){									
										try{
											st.start();
										}catch(Exception e){ e.printStackTrace(); }
										
										while(true){								
											if(messageObj.isThreadChange()){											
												tempStr = messageObj.getThreadStr();
												if(tempStr.equals("quit")){//ȸ������ ����												
													finish();
													break;
												}else{
													Toast.makeText(JoinActivity.this, "ȸ������ ���� ������ �߻��Ͽ����ϴ�.", Toast.LENGTH_LONG).show();
												}
											}
										}
									}else{
										Toast.makeText(JoinActivity.this, "��Ʈ��ũ�� ����� �� �����ϴ�.", Toast.LENGTH_LONG).show();
									}
								}								
							}
						}
					}					
				}else{
					AlertDialog.Builder ab = null;
					ab = new AlertDialog.Builder( JoinActivity.this );
					ab.setMessage("���� ���̵� �ߺ�üũ�� ���ּ���.");
					ab.setPositiveButton("Ȯ��", null);
					ab.setTitle("���");
					ab.show();
				}
			}			
		});
        
        clearAll.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				// TODO Auto-generated method stub
				inputId.setText("");
				inputPw.setText("");
				inputRePw.setText("");
				inputEmail.setText("");				
			}
		});        
	}
	
	private boolean isNetworkAvailable() {
		boolean available = false;		
		ConnectivityManager conn = 
				(ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);		
		NetworkInfo netInfo = conn.getActiveNetworkInfo();		
		if(netInfo != null && netInfo.isAvailable()){
			available = true;
		}		
		return available;
    }
	
	public void onBackPressed(){
		AlertDialog.Builder ab = null;
		ab = new AlertDialog.Builder( JoinActivity.this );
		ab.setMessage("ȸ�������� �׸��Ͻðڽ��ϱ�?");
		ab.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				finish();
			}
		});
		ab.setNegativeButton(android.R.string.cancel, null);
		ab.setTitle("�� �������� �������� �մϴ�.");
		ab.show();
	}
}