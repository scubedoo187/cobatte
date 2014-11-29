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
	final private String DB_URL = "jdbc:mysql://localhost:3306/Taxi";
	final private String DB_ID = "root";
	final private String DB_PASSWORD = "2550";
	public String isResultValue; // return °ª
	
	public LinkDatabase() {
		connect();
	}
	
	public void connect() {
		String url = DB_URL;
		String isDatabaseId = DB_ID;
		String isDatabasePassword = DB_PASSWORD;
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			this.isConnect = DriverManager.getConnection(url, isDatabaseId, isDatabasePassword);
			this.isStatement = this.isConnect.createStatement();
		}
		
		catch(Exception e) {
			log.error("Connect error : " + e);
		}
	}

	public String signup (String name, String password) {
		isResultValue = "2";
		
		try {
			isStatement.executeUpdate("Insert into userInfo Values('"
					+ name + "', '" + password + "')");
		}
		
		catch (Exception e) {
			log.error("Insert Error " + e);
		}
		
		return isResultValue;
	}
	
	public String login (String name, String password) {
		try {
			isResultSet = isStatement.executeQuery("Select * From userInfo");
			boolean checklogin = false;
			
			while (isResultSet.next()) {
				if (name.toString().equals(isResultSet.getString("id")) 
						&& password.toString().equals(isResultSet.getString("passwd")))
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
	
	public String checkOverlapId(String name) {
		try {
			isResultSet = isStatement.executeQuery("Select * From userInfo");
			boolean alogin = false;
			
			while (isResultSet.next()) {
				if (name.toString().equals(isResultSet.getString("id")))
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
	
	public String createRoom(String admin, String roomName, String place, String hour, String minute) {
		isResultValue = "3";
		
		try {
			isStatement.executeUpdate("Insert into roomInfo(admin, roomname, place, hour, minute) "
					+ "Values('"+admin+"', '"+roomName+"' ,'"+place+"' ,'"+hour+"' ,'"+minute+"')");
		}
		
		catch (Exception e) {
			log.error("RoomCreate Error " + e);
		}
		
		return isResultValue;
	}
	
	public String roomList() {
		try {
			isResultSet = isStatement.executeQuery("Select * From roomInfo");
			isResultValue = "";
			
			while (isResultSet.next()) {
				int numberOfPeople = 0;
				isResultValue += isResultSet.getString("admin") + "\t"
								+ isResultSet.getString("roomname") + "\t"
								+ isResultSet.getString("place") + "\t"
								+ isResultSet.getString("hour") + "\t"
								+ isResultSet.getString("minute") + "\t";
				
				if (isResultSet.getString("user1") == null)
					numberOfPeople++;
				if (isResultSet.getString("user2") == null)
					numberOfPeople++;
				if (isResultSet.getString("user3") == null)
					numberOfPeople++;
				
				isResultValue += String.valueOf(4-numberOfPeople) + "\t";
			}
		}
		
		catch (Exception e) {
			log.error("RoomList Error " + e);
		}
		
		return isResultValue;
	}
	
	public String emptyRoom(String admin, String id) {
		try {
			isResultSet = isStatement.executeQuery("Select user1, user2, user3"
					+ " From roomInfo Where admin=\"" + admin + "\"");
			
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
		String isEmpty = emptyRoom(admin, id);
		isResultValue = "5";
		
		try {
			if (isEmpty.toString().equals("user1")) {
				isStatement.executeUpdate("Update roomInfo Set user1=\"" + id + "\""
						+ " Where admin=\"" + admin + "\"");
			}else if (isEmpty.toString().equals("user2")) {
				isStatement.executeUpdate("Update roomInfo Set user2=\"" + id + "\""
						+ " Where admin=\"" + admin + "\"");
			}else if (isEmpty.toString().equals("user3")) {
				isStatement.executeUpdate("Update roominfo Set user3=\"" + id + "\""
						+ " Where admin=\"" + admin + "\"");
			}else if (isEmpty.toString().equals("full")) {
				isResultValue = "full";
			}
		}
		
		catch (Exception e) {
			log.error("EnterARoom Error " + e);
		}
		
		return isResultValue;
	}
	
	public String roominfo(String id) {
		try {
			isResultSet = isStatement.executeQuery("Select * From roomInfo "
					+ "Where admin=\"" + id + "\" or user1=\"" + id 
					+ "\" or user2=\"" + id + "\" or user3=\"" + id + "\"");
			
			if (isResultSet.next()) {
				isResultValue = isResultSet.getString("admin") + "\t";
				isResultValue += isResultSet.getString("user1") + "\t";
				isResultValue += isResultSet.getString("user2") + "\t";
				isResultValue += isResultSet.getString("user3") + "\t";
				isResultValue += isResultSet.getString("roomname") + "\t";
				isResultValue += isResultSet.getString("place") + "\t";
				isResultValue += isResultSet.getString("hour") + "\t";
				isResultValue += isResultSet.getString("minute");
			}else
				isResultValue = "0";
		}
		
		catch (Exception e) {
			log.error("roominfo Error" + e);
		}
		
		return isResultValue;
	}
	
	public void leaveRoom(String admin, String id) {
		String user = findUser(id);
		
		try {
			if (user.toString().equals("admin")) {
				isStatement.executeUpdate("Delete From roomInfo Where admin=\"" + id + "\"");
			}else if (user.toString().equals("user1")){
				isStatement.executeUpdate("Update roomInfo Set user1=" + null
						+ " Where user1=\"" + id + "\"");
			}else if (user.toString().equals("user2")){
				isStatement.executeUpdate("Update roomInfo Set user2=" + null
						+ " Where user2=\"" + id + "\"");
			}else if (user.toString().equals("user3")){
				isStatement.executeUpdate("Update roomInfo Set user3=" + null
						+ " Where user3=\"" + id + "\"");
			}
			
		}
		
		catch (Exception e) {
			log.error("leaveRoom Error" + e);
		}
		
	}
	
	public String findUser(String id) {
		try {
			isResultSet = isStatement.executeQuery("Select admin, user1, user2, user3 "
					+ "From roomInfo "
					+ "Where admin=\"" + id + "\" or user1=\"" + id 
					+ "\" or user2=\"" + id + "\" or user3=\"" + id + "\"");
			
			if (isResultSet.next()) {
				if (isResultSet.getString("admin").toString().equals(id)) {
					isResultValue = "admin";
				}else if (isResultSet.getString("user1").toString().equals(id)) {
					isResultValue = "user1";
				}else if (isResultSet.getString("user2").toString().equals(id)) {
					isResultValue = "user2";
				}else if (isResultSet.getString("user3").toString().equals(id)) {
					isResultValue = "user3";
				}
			}
		}
		
		catch (Exception e) {
			log.error("findUser Error" + e);
		}
		
		return isResultValue;
	}
	
	public void close() {
		try {
			if (isResultSet != null) // ResultSet °´Ã¼¸¦ ´Ý´Â´Ù.
				isResultSet.close();       
	      
			if (isStatement != null) 
				isStatement.close();       // Statement °´Ã¼¸¦ ´Ý´Â´Ù.
	      
			if (isConnect != null) 
				isConnect.close(); // Connection °´Ã¼¸¦ ´Ý´Â´Ù.
		}
		
		catch (Exception e) {
			log.error("Close Error" + e);
		}	
	}
}
