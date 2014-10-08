package com.cobatte.taxi;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

public class SocketThread extends Thread {
	Socket socket = null;
	private static String str = "";
	private String tempStr;

	PrintWriter printWriter;
	BufferedReader buffReader;
	MsgString messageStr;

	public SocketThread(MsgString m) {
		messageStr = m;
	}

	public void run() {
		try {
			socket = new Socket("210.115.229.243", 13080);
			OutputStream out = socket.getOutputStream();
			InputStream in = socket.getInputStream();

			printWriter = new PrintWriter(new OutputStreamWriter(out, "utf-8"));
			buffReader = new BufferedReader(new InputStreamReader(in, "utf-8"));

			while (true) {
				if (messageStr.isActivityChange()) {
					str = messageStr.getActivityStr();
					printWriter.println(str);
					printWriter.flush();
					System.out.println("메시지 \t전송" + str);
					tempStr = buffReader.readLine();
					System.out.println("Server : " + tempStr);

					str = tempStr;
					messageStr.setThreadStr(str);
					System.out.println("str = tempStr : " + str);
					if (str.toString().equals("quit")) {
						System.out.println("Server : " + str);
						break;
					}
					str = "";
				}
				try {
					Thread.sleep(100);
				} catch (Exception e) {
				}
			}
		} catch (Exception e) {
		} finally {
			try {
				printWriter.close();
				buffReader.close();
				socket.close();
			} catch (Exception e) {
			}
		}
	}
}