package com.cobatte.taxi;

import java.io.Serializable;

	/* taxi protocol
	 * 로그인 시도 = 0
	 * id중복체크 = 1
	 * 회원가입 시도 = 2	 
	 * 소켓종료 = 9 "quit"
	 * protocol 구분 '\t'
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
	public void setThreadStr(String s){	//thread에서 문자열 저장한경우
		threadNewStr = true;
		threadStr = s;
	}
	
	public void setActivityStr(String s){	//activity에서 문자열 저장한경우
		activityNewStr = true;
		activityStr = s;
	}
	
	public String getThreadStr(){	//thread에서 온 문자열 가져갈때
		threadNewStr = false;
		return threadStr;
	}
	
	public String getActivityStr(){		//activity에서 온 문자열 가져갈때
		activityNewStr = false;
		return activityStr;
	}
	
	public boolean isActivityChange(){	//activity의 문자열이 새로 저장되었는지 확인
		return activityNewStr;
	}
	
	public boolean isThreadChange(){	//thread의 문자열이 새로 저장되었는지 확인
		return threadNewStr;
	}
		
	public void setId(String s){
		idValue = s;
	}
	
	public String getId(){
		return idValue;
	}
}
