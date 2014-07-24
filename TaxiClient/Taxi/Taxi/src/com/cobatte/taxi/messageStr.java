package com.cobatte.taxi;

import java.io.Serializable;

public class messageStr implements Serializable{
	private static final long serialVersionUID = 1L;	
	private static String id;
	private static String tStr;
	private static String aStr;
	private static boolean aNew, tNew;
	/* taxi protocol
	 * 로그인 시도 = 0
	 * id중복체크 = 1
	 * 회원가입 시도 = 2	 
	 * 소켓종료 = quit
	 * protocol 구분 '\t'
	 */
	public messageStr()
	{
		aNew = false;
		tNew = false;
		aStr = "";
		tStr = "";
	}
	public void settStr(String s)//thread에서 문자열 저장한경우
	{
		tNew = true;
		tStr = s;
	}
	
	public void setaStr(String s)//activity에서 문자열 저장한경우
	{
		aNew = true;
		aStr = s;
	}
	
	public String gettStr()//thread에서 온 문자열 가져갈때 
	{
		tNew = false;
		return tStr;
	}
	
	public String getaStr()//activity에서 온 문자열 가져갈때 
	{
		aNew = false;
		return aStr;
	}
	
	public boolean aChange()//activity의 문자열이 새로 저장되었는지 확인
	{
		return aNew;
	}
	
	public boolean tChange()//thread의 문자열이 새로 저장되었는지 확인
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
