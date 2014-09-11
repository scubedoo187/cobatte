package com.cobatte.taxi;

import java.io.Serializable;

	/* taxi protocol
	 * �α��� �õ� = 0
	 * id�ߺ�üũ = 1
	 * ȸ������ �õ� = 2	 
	 * �������� = 9 "quit"
	 * protocol ���� '\t'
	 */

public class MsgString implements Serializable{		
	private static final long serialVersionUID = 1L;	
	private static String idValue; 
	private static String threadStr;
	private static String activityStr;
	private static boolean activityNewStr;
	private static boolean threadNewStr;

	public MsgString(){
		activityNewStr = false;
		threadNewStr = false;
		activityStr = "";
		threadStr = "";
	}
	public void setThreadStr(String s){	//thread���� ���ڿ� �����Ѱ��
		threadNewStr = true;
		threadStr = s;
	}
	
	public void setActivityStr(String s){	//activity���� ���ڿ� �����Ѱ��
		activityNewStr = true;
		activityStr = s;
	}
	
	public String getThreadStr(){	//thread���� �� ���ڿ� ��������
		threadNewStr = false;
		return threadStr;
	}
	
	public String getActivityStr(){		//activity���� �� ���ڿ� ��������
		activityNewStr = false;
		return activityStr;
	}
	
	public boolean isActivityChange(){	//activity�� ���ڿ��� ���� ����Ǿ����� Ȯ��
		return activityNewStr;
	}
	
	public boolean isThreadChange(){	//thread�� ���ڿ��� ���� ����Ǿ����� Ȯ��
		return threadNewStr;
	}
		
	public void setId(String s){
		idValue = s;
	}
	
	public String getId(){
		return idValue;
	}
}
