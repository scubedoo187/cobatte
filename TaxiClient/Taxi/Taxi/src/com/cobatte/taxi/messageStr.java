package com.cobatte.taxi;

import java.io.Serializable;

public class messageStr implements Serializable{
	private static final long serialVersionUID = 1L;	
	private static String id;
	private static String tStr;
	private static String aStr;
	private static boolean aNew, tNew;
	/* taxi protocol
	 * �α��� �õ� = 0
	 * id�ߺ�üũ = 1
	 * ȸ������ �õ� = 2	 
	 * �������� = quit
	 * protocol ���� '\t'
	 */
	public messageStr()
	{
		aNew = false;
		tNew = false;
		aStr = "";
		tStr = "";
	}
	public void settStr(String s)//thread���� ���ڿ� �����Ѱ��
	{
		tNew = true;
		tStr = s;
	}
	
	public void setaStr(String s)//activity���� ���ڿ� �����Ѱ��
	{
		aNew = true;
		aStr = s;
	}
	
	public String gettStr()//thread���� �� ���ڿ� �������� 
	{
		tNew = false;
		return tStr;
	}
	
	public String getaStr()//activity���� �� ���ڿ� �������� 
	{
		aNew = false;
		return aStr;
	}
	
	public boolean aChange()//activity�� ���ڿ��� ���� ����Ǿ����� Ȯ��
	{
		return aNew;
	}
	
	public boolean tChange()//thread�� ���ڿ��� ���� ����Ǿ����� Ȯ��
	{
		return tNew;
	}
		
	public void setId(String s)
	{
		id = s;
	}
	
	public String getId()
	{
		return id;
	}
}
