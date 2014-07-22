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
	private boolean flag = false;//id�ߺ�Ȯ�� ��ư �����߸� ȸ�������� ����
	messageStr ms;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.join);
        setTitle("ȸ������");
        
		newid = (EditText)findViewById(R.id.newid);
		newpw = (EditText)findViewById(R.id.newpw);
		pwcheck = (EditText)findViewById(R.id.pwcheck);
		idcheck = (Button)findViewById(R.id.idcheck);
		join = (Button)findViewById(R.id.join);
		
		idcheck.setOnClickListener(new View.OnClickListener() {//id�ߺ�üũ ������
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
		
				if(newid.getText().toString().equals(""))//newidĭ�� ��ĭ�ΰ��
				{
					AlertDialog.Builder al;
					al = new AlertDialog.Builder(Join.this);
					al.setTitle("�˶�");
					al.setMessage("id�� �Է��ϼ���.");
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
								if(temp.equals("1"))//�ߺ��Ǵ� id�� �������
								{
									AlertDialog.Builder al;
									al = new AlertDialog.Builder(Join.this);
									al.setTitle("�ߺ��Ǵ� ID�� �����ϴ�.");
									al.setMessage("ID�� ����Ͻðڽ��ϱ�?");
									al.setCancelable(false);
									al.setPositiveButton("Yes", 
										new DialogInterface.OnClickListener() {
											public void onClick(DialogInterface dialog,	int whichButton) {
												newid.setEnabled(false);
												idcheck.setEnabled(false);
												flag = true;
												ms.setaStr("quit");//��������
											}
										});
									al.setNegativeButton("No", 
										new DialogInterface.OnClickListener() {
											public void onClick(DialogInterface dialog,	int whichButton) {
												ms.setaStr("quit");//��������
											}
										});
									AlertDialog dialog = al.create();	
									al.show();
								}else//�ߺ��Ǵ� id�� �������
								{
									AlertDialog.Builder al;
									al = new AlertDialog.Builder(Join.this);
									al.setTitle("�˶�");
									al.setMessage("�ߺ��Ǵ� ID�� �ֽ��ϴ�.");
									al.setPositiveButton(android.R.string.ok, null);
									al.show();
									ms.setaStr("quit");//��������
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
		
		join.setOnClickListener(new View.OnClickListener() {//ȸ������ ��ư ������
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				if(flag)
				{
					if(newpw.getText().toString().equals(""))
					{
						AlertDialog.Builder al;
						al = new AlertDialog.Builder(Join.this);
						al.setTitle("�˶�");
						al.setMessage("��й�ȣ�� �Է��ϼ���.");
						al.setPositiveButton(android.R.string.ok, null);
						al.show();
						newpw.setFocusable(true);
						
					}else if(pwcheck.getText().toString().equals(""))
					{
						AlertDialog.Builder al;
						al = new AlertDialog.Builder(Join.this);
						al.setTitle("�˶�");
						al.setMessage("��й�ȣ�� Ȯ���ϼ���.");
						al.setPositiveButton(android.R.string.ok, null);
						al.show();
						pwcheck.setFocusable(true);
						
					}else if(!pwcheck.getText().toString().equals(newpw.getText().toString()))
					{
						AlertDialog.Builder al;
						al = new AlertDialog.Builder(Join.this);
						al.setTitle("�˶�");
						al.setMessage("��й�ȣ�� ��ġ���� �ʽ��ϴ�.");
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
									System.out.println("ȸ������ ��ư �� temp : " + temp);
									if(temp.equals("quit"))//ȸ������ ����
									{
										System.out.println("ȸ�����Լ���!");
										finish();//�ٽ� ����ȭ������
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
					al.setTitle("�˶�");
					al.setMessage("ID�ߺ� Ȯ���� �ϼ���.");
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
    			.setTitle("�˶�")
    			.setMessage("ȸ�������� ���Ͻðڽ��ϱ�?")
    			.setPositiveButton("��", new DialogInterface.OnClickListener() {

    				@Override
    				public void onClick(DialogInterface dialog, int which) {
    					android.os.Process.killProcess(android.os.Process.myPid());
    				}
    			})
    			.setNegativeButton("�ƴϿ�", null)
    			.show();
    			break;

    		default:
    			break;
    		}
    	return super.onKeyDown(keyCode, event);
    }
}