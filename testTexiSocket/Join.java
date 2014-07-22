package com.example.puzzle;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Join extends Activity {

	EditText newid, newpw, pwcheck;
	Button idcheck, join;
	static String temp = "";
	private boolean flag = false;//id중복확인 버튼 눌러야만 회원가입이 가능
	messageStr ms;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.join);
        setTitle("회원가입");
        
		newid = (EditText)findViewById(R.id.newid);
		newpw = (EditText)findViewById(R.id.newpw);
		pwcheck = (EditText)findViewById(R.id.pwcheck);
		idcheck = (Button)findViewById(R.id.idcheck);
		join = (Button)findViewById(R.id.join);
		
		idcheck.setOnClickListener(new View.OnClickListener() {//id중복체크 리스너
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
		
				if(newid.getText().toString().equals(""))//newid칸이 빈칸인경우
				{
					AlertDialog.Builder al;
					al = new AlertDialog.Builder(Join.this);
					al.setTitle("알람");
					al.setMessage("id를 입력하세요.");
					al.setPositiveButton(android.R.string.ok, null);
					al.show();
					newid.setFocusable(true);
				}else
				{
					ms = new messageStr();
					sThread t = new sThread(ms);
					
					temp = "1\t";
					temp += newid.getText().toString();
					ms.setaStr(temp);
					
					if(isNetworkAvailable())
					{
						try
						{
							t.start();
						}
						catch(Exception e) 
						{
							e.printStackTrace();
						}
						
						while(true){
							if(ms.tChange())
							{
								temp = ms.gettStr();
								System.out.println("Join temp : " + temp);
								if(temp.equals("1"))//중복되는 id가 없을경우
								{
									AlertDialog.Builder al;
									al = new AlertDialog.Builder(Join.this);
									al.setTitle("중복되는 ID가 없습니다.");
									al.setMessage("ID를 사용하시겠습니까?");
									al.setCancelable(false);
									al.setPositiveButton("Yes", 
										new DialogInterface.OnClickListener() {
											public void onClick(DialogInterface dialog,	int whichButton) {
												newid.setEnabled(false);
												idcheck.setEnabled(false);
												flag = true;
												ms.setaStr("quit");//소켓종료
											}
										});
									al.setNegativeButton("No", 
										new DialogInterface.OnClickListener() {
											public void onClick(DialogInterface dialog,	int whichButton) {
												ms.setaStr("quit");//소켓종료
											}
										});
									AlertDialog dialog = al.create();	
									al.show();
								}else//중복되는 id가 있을경우
								{
									AlertDialog.Builder al;
									al = new AlertDialog.Builder(Join.this);
									al.setTitle("알람");
									al.setMessage("중복되는 ID가 있습니다.");
									al.setPositiveButton(android.R.string.ok, null);
									al.show();
									ms.setaStr("quit");//소켓종료
									newid.setFocusable(true);
								}
								break;
							}
						}
					}else
					{
						Toast.makeText(Join.this, "Network is not available", Toast.LENGTH_SHORT).show();
					}
				}
			}
		});
		
		join.setOnClickListener(new View.OnClickListener() {//회원가입 버튼 리스너
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				if(flag)
				{
					if(newpw.getText().toString().equals(""))
					{
						AlertDialog.Builder al;
						al = new AlertDialog.Builder(Join.this);
						al.setTitle("알람");
						al.setMessage("비밀번호를 입력하세요.");
						al.setPositiveButton(android.R.string.ok, null);
						al.show();
						newpw.setFocusable(true);
						
					}else if(pwcheck.getText().toString().equals(""))
					{
						AlertDialog.Builder al;
						al = new AlertDialog.Builder(Join.this);
						al.setTitle("알람");
						al.setMessage("비밀번호를 확인하세요.");
						al.setPositiveButton(android.R.string.ok, null);
						al.show();
						pwcheck.setFocusable(true);
						
					}else if(!pwcheck.getText().toString().equals(newpw.getText().toString()))
					{
						AlertDialog.Builder al;
						al = new AlertDialog.Builder(Join.this);
						al.setTitle("알람");
						al.setMessage("비밀번호가 일치하지 않습니다.");
						al.setPositiveButton(android.R.string.ok, null);
						al.show();
						pwcheck.setFocusable(true);
						
					}else
					{
						ms = new messageStr();
						sThread t = new sThread(ms);
						
						temp = "2";
						temp += "\t";
						temp += newid.getText().toString();
						temp += "\t";
						temp += newpw.getText().toString();
						ms.setaStr(temp);
						
						if(isNetworkAvailable())
						{
							try
							{
								t.start();
							}
							catch(Exception e) 
							{
								e.printStackTrace();
							}
							
							while(true){
								if(ms.tChange())
								{
									temp = ms.gettStr();
									System.out.println("회원가입 버튼 안 temp : " + temp);
									if(temp.equals("quit"))//회원가입 성공
									{
										System.out.println("회원가입성공!");
										finish();//다시 메인화면으로
										break;
									}else
									{
										Toast.makeText(Join.this, "Join error", Toast.LENGTH_SHORT).show();
									}
									
								}
							}
						}else
						{
							Toast.makeText(Join.this, "Network is not available", Toast.LENGTH_SHORT).show();
						}
					}
				}else
				{
					AlertDialog.Builder al;
					al = new AlertDialog.Builder(Join.this);
					al.setTitle("알람");
					al.setMessage("ID중복 확인을 하세요.");
					al.setPositiveButton(android.R.string.ok, null);
					al.show();
				}
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
    
    public boolean onKeyDown(int keyCode, KeyEvent event) {

    	switch (keyCode) {
    		case KeyEvent.KEYCODE_BACK:
    			new AlertDialog.Builder(this)
    			.setTitle("알람")
    			.setMessage("회원가입을 안하시겠습니까?")
    			.setPositiveButton("예", new DialogInterface.OnClickListener() {

    				@Override
    				public void onClick(DialogInterface dialog, int which) {
    					android.os.Process.killProcess(android.os.Process.myPid());
    				}
    			})
    			.setNegativeButton("아니오", null)
    			.show();
    			break;

    		default:
    			break;
    		}
    	return super.onKeyDown(keyCode, event);
    }
}