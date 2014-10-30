import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import org.apache.log4j.Logger;

public class LinkDatabase {
	static Logger log = Logger.getLogger(LinkDatabase.class.getName());
	Connection isConnect = null;
	Statement isStatement = null;
	ResultSet isResultSet = null;
	public String isResultValue; // return °ª
	
	public LinkDatabase() {
		connect();
	}
	
	public void connect() {
		String url = "jdbc:mysql://localhost:3306/Taxi";
		String isDatabaseId = "root";
		String isDatabasePassword = "2550";
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			this.isConnect = DriverManager.getConnection(url, isDatabaseId, isDatabasePassword);
			this.isStatement = this.isConnect.createStatement();
		}
		
		catch(Exception e) {
			log.error("Connect error : " + e);
		}
	}

	public void signup(String Name, String Password) {
		try {
			isStatement.executeUpdate("insert into UserInfo values('"+Name+"', '"+Password+"')");
		}
		
		catch(Exception e) {
			log.error("Insert Error " + e);
		}
	}
	
	public String login(String Name, String Password) {
		try {
			isResultSet = isStatement.executeQuery("select * from UserInfo");
			boolean checklogin = false; // ºñ±³ÇÒ¶§ ¾µ²¨ÀÓ
			
			while (isResultSet.next()) {
				if(Name.toString().equals(isResultSet.getString("id")) 
						&& Password.toString().equals(isResultSet.getString("passwd")))
					checklogin = true; // ¸ÂÀ¸¸é true
			}
			
			if(checklogin)
				isResultValue = "0"; // trueÀÌ¸é 0
			
			else
				isResultValue = "quit"; // falseÀÌ¸é -1
		}
		
		catch (Exception e) {
			log.error("Login Error " + e);
		}
		
		return isResultValue;
	}
	
	public String checkOverlapId(String Name) {
		try {
			isResultSet = isStatement.executeQuery("select * from UserInfo");
			boolean alogin = false;
			
			while (isResultSet.next()) {
				if(Name.toString().equals(isResultSet.getString("id")))
					alogin = true;
			}
			
			if(alogin)
				isResultValue = "quit";
			
			else
				isResultValue = "1";
		}
		
		catch (Exception e) {
			log.error("IdCheck Error " + e);
		}
		
		return isResultValue;
	}
	
	public void createRoom(String admin, String roomName, String place, int hour, int minute) {
		try {
			isStatement.executeUpdate("insert into RoomInfo(admin, roomname, place, minute, hour) "
					+ "values('"+admin+"', '"+roomName+"' ,'"+place+"', "+minute+", "+hour+")");
		}
		
		catch(Exception e) {
			log.error("RoomCreate Error " + e);
		}
	}
	
	public void deleteRoom() {
		
	}
	
	public String roomList() {
		try {
			isResultSet = isStatement.executeQuery("select * from RoomInfo");
			isResultValue = "";
			
			while (isResultSet.next()) {
				int numberOfPeople = 0;
				isResultValue += isResultSet.getString("admin") + "\t"
								+ isResultSet.getString("roomname") + "\t"
								+ isResultSet.getString("place") + "\t"
								+ isResultSet.getString("hour") + "\t"
								+ isResultSet.getString("minute") + "\t";
				
				if(isResultSet.getString("user1") == null)
					numberOfPeople++;
				if(isResultSet.getString("user2") == null)
					numberOfPeople++;
				if(isResultSet.getString("user3") == null)
					numberOfPeople++;
				
				isResultValue += String.valueOf(4-numberOfPeople) + "\t";
			}
		}
		
		catch (Exception e) {
			log.error("RoomList Error " + e);
		}
		
		return isResultValue;
	}
	
	public String selectUserInRoom(String admin, String id) {
		try {
			isResultSet = isStatement.executeQuery("select user1, user2, user3"
					+ " from RoomInfo where admin=\"" + admin + "\"");
			
			while (isResultSet.next()) {
				if (isResultSet.getString("user1") == null) {
					isResultValue = "user1";

				}else if (isResultSet.getString("user2") == (null)) {
					isResultValue = "user2";
					
				}else if (isResultSet.getString("user3") == (null)) {
					isResultValue = "user3";
					
				}else
					isResultValue = "full";
			}
		}
		
		catch (Exception e) {
			log.error("selectUserInRoom Error " + e);
		}
		
		return isResultValue;
	}
	
	public String enterARoom(String admin, String id) {
		String isEmpty = selectUserInRoom(admin, id);
		isResultValue = "5";
		
		try {
			if (isEmpty.toString().equals("user1")) {
				isStatement.executeUpdate("update roominfo set user1=\"" + id + "\""
						+ " where admin=\"" + admin + "\"");
			}else if (isEmpty.toString().equals("user2")) {
				isStatement.executeUpdate("update roominfo set user2=\"" + id + "\""
						+ " where admin=\"" + admin + "\"");
			}else if (isEmpty.toString().equals("user3")) {
				isStatement.executeUpdate("update roominfo set user3=\"" + id + "\""
						+ " where admin=\"" + admin + "\"");
			}else if (isEmpty.toString().equals("full")) {
				isResultValue = "full";
			}
		}
		
		catch (Exception e) {
			log.error("EnterARoom Error " + e);
		}
		
		return isResultValue;
	}
	
	public String roominfo() {
		//¹æ Á¤º¸
		return "k";
	}
	
	public void close() {
		try {
			if(isResultSet != null) // ResultSet °´Ã¼¸¦ ´Ý´Â´Ù.
				isResultSet.close();       
	      
			if(isStatement != null) 
				isStatement.close();       // Statement °´Ã¼¸¦ ´Ý´Â´Ù.
	      
			if(isConnect != null) 
				isConnect.close(); // Connection °´Ã¼¸¦ ´Ý´Â´Ù.
		}
		
		catch(Exception e) {
			log.error("Close Error" + e);
		}	
	}
}
