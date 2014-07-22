package com.example.puzzle;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity {

	int Size;
	EditText id, pw;
	Button login, join;
	messageStr ms;
	private static String temp = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        setTitle("슬라이드 퍼즐");
       
		id = (EditText)findViewById(R.id.idbox);
		pw = (EditText)findViewById(R.id.pwbox);
		login = (Button)findViewById(R.id.login);
		join = (Button)findViewById(R.id.join);
		
		
		login.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				ms = new messageStr();
				sThread t = new sThread(ms);
				
				
				if(id.getText().toString().equals(""))
				{
					AlertDialog.Builder al;
					al = new AlertDialog.Builder(MainActivity.this);
					al.setTitle("알람");
					al.setMessage("id를 입력하세요.");
					al.setPositiveButton(android.R.string.ok, null);
					al.show();
					id.setFocusable(true);

				}else if(pw.getText().toString().equals(""))
				{
					AlertDialog.Builder al;
					al = new AlertDialog.Builder(MainActivity.this);
					al.setTitle("알람");
					al.setMessage("비밀번호를 입력하세요.");
					al.setPositiveButton(android.R.string.ok, null);
					al.show();
					pw.setFocusable(true);
				}else
				{

					temp = "0";
					temp += "\t";
					temp += id.getText().toString();
					temp += "\t";
					temp += pw.getText().toString();
					ms.setaStr(temp);
					ms.setId(id.getText().toString());
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
								System.out.println("메인액티비티 temp : " + temp);
								if(temp.toString().equals("0"))
								{
									System.out.println("메인액티비티 temp : " + ms.gettStr());
									Intent intent = new Intent(MainActivity.this, Mode.class);
									intent.putExtra("message", ms);
									startActivity(intent);
									finish();
									break;
									
								}else
								{
									AlertDialog.Builder al;
									al = new AlertDialog.Builder(MainActivity.this);
									al.setTitle("알람");
									al.setMessage("ID 혹은 비밀번호가 맞지 않습니다.");
									al.setPositiveButton(android.R.string.ok, null);
									al.show();
									ms.setaStr("quit");
									break;
									
								}
							}
						}
					
					}else
					{
						Toast.makeText(MainActivity.this, "Network is not available", Toast.LENGTH_SHORT).show();
					}
				}
				
			}
		});
             
        join.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(MainActivity.this, Join.class);
				startActivity(intent);
				
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
    			.setTitle("프로그램 종료")
    			.setMessage("프로그램을 종료 하시겠습니까?")
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