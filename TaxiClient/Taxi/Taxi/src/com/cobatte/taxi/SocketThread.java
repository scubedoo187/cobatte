package com.cobatte.taxi;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

public class SocketThread extends Thread{
	
	Socket socket = null;
	private static String str="";
	private static String temp;
	
	PrintWriter pw;
	BufferedReader br;
	messageStr ms;
	
	public SocketThread(messageStr m)
	{
		ms = m;
	}

	public void run()
	{
		try
		{	
			socket = new Socket("210.115.229.245", 13080);
			
			OutputStream out = socket.getOutputStream();
			InputStream in = socket.getInputStream();
			
			pw = new PrintWriter(new OutputStreamWriter(out, "utf-8"));
			br = new BufferedReader(new InputStreamReader(in, "utf-8"));

			while(true){

				if(ms.aChange()){

					str = ms.getaStr();
					pw.println(str);
					pw.flush();
					System.out.println("�޽��� \t����" + str);	
					temp = br.readLine();	
					System.out.println("Server : " + temp);
						
					str = temp;
					ms.settStr(str);
					System.out.println("str = temp : " + str);
					if(str.toString().equals("quit"))
					{
						System.out.println("Server : " + str);
						break;
					}
					str = "";//�ʱ�ȭ
				}				
				try{Thread.sleep(100);}
				catch(Exception e){}
			}
		}catch(Exception e) { }
		finally{
			try{
				pw.close();
				br.close();
				socket.close();
			
				
			}catch( Exception e) { e.getStackTrace(); }
		}
	}
}
