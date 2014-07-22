package com.example.puzzle;

import java.io.Serializable;

public class messageStr implements Serializable{
	private static final long serialVersionUID = 1L;
	private static int level;//���� ����
	private static String id;
	private static String tStr;
	private static String aStr;
	private static boolean aNew, tNew;
	/* sildePuzzle protocol
	 * �α��� �õ� = 0
	 * id�ߺ�üũ = 1
	 * ȸ������ �õ� = 2
	 * �̱��÷��̾��� = d
	 * �̱��÷��̾��� ���� = 3
	 * ��Ƽ�÷��̾��� = f
	 * ��Ƽ�÷��̾��� �ʴ� = g
	 * �ʴ� ���� = h
	 * �ʴ� ���� = i
	 * ��Ƽ�÷��̾��� ���� = j
	 * ��Ƽ�÷��̾��� �� = k
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
	
	public void setLevel(int lv)//���� ���̵�����
	{
		level = lv;
	}
	
	public int getLevel()
	{
		return level;
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
