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
	public String isResultValue; // return 값
	
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

	public String signup(String Name, String Password) {
		isResultValue = "2";
		
		try {
			isStatement.executeUpdate("insert into UserInfo values('"+Name+"', '"+Password+"')");
		}
		
		catch(Exception e) {
			log.error("Insert Error " + e);
		}
		
		return isResultValue;
	}
	
	public String login(String Name, String Password) {
		try {
			isResultSet = isStatement.executeQuery("select * from UserInfo");
			boolean checklogin = false; // 비교할때 쓸꺼임
			
			while (isResultSet.next()) {
				if(Name.toString().equals(isResultSet.getString("id")) 
						&& Password.toString().equals(isResultSet.getString("passwd")))
					checklogin = true; // 맞으면 true
			}
			
			if(checklogin)
				isResultValue = "0"; // true이면 0
			
			else
				isResultValue = "quit"; // false이면 -1
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
	
	public String createRoom(String admin, String roomName, String place, String hour, String minute) {
		isResultValue = "3";
		
		try {
			isStatement.executeUpdate("insert into roominfo(admin, roomname, place, hour, minute) "
					+ "values('"+admin+"', '"+roomName+"' ,'"+place+"' ,'"+hour+"' ,'"+minute+"')");
		}
		
		catch(Exception e) {
			log.error("RoomCreate Error " + e);
		}
		
		return isResultValue;
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
	
	public String emptyRoom(String admin, String id) {
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
		String isEmpty = emptyRoom(admin, id);
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
	
	public String roominfo(String id) {
		isResultValue = "7";
		try {
			isResultSet = isStatement.executeQuery("select * from roominfo "
					+ "where admin=\"" + id + "\" or user1=\"" + id 
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
				isResultValue = "방이 존재하지 않습니다.";
		}
		
		catch (Exception e) {
			log.error("roominfo Error" + e);
		}
		
		return isResultValue;
	}
	
	public void leaveRoom(String admin, String id) {
		String user = findUser(id);
		
		try {
			if (user.toString().equals(admin)) {
				isStatement.executeUpdate("delete from roominfo where admin=\"" + id + "\"");
			}else if (user.toString().equals("user1")){
				isStatement.executeUpdate("update roominfo set user1=\"" + null + "\""
						+ " where user1=\"" + id + "\"");
			}else if (user.toString().equals("user2")){
				isStatement.executeUpdate("update roominfo set user2=\"" + null + "\""
						+ " where user2=\"" + id + "\"");
			}else if (user.toString().equals("user3")){
				isStatement.executeUpdate("update roominfo set user3=\"" + null + "\""
						+ " where user3=\"" + id + "\"");
			}
			
		}
		
		catch (Exception e) {
			log.error("leaveRoom Error" + e);
		}
		
	}
	
	public String findUser(String id) {
		try {
			isResultSet = isStatement.executeQuery("select admin, user1, user2, user3 "
					+ "from roominfo "
					+ "where admin=\"" + id + "\" or user1=\"" + id 
					+ "\" or user2=\"" + id + "\" or user3=\"" + id + "\"");
			
			if (isResultSet.next()) {
				if (!isResultSet.getString("admin").toString().equals(id)) {
					isResultValue = "admin";
				}else if (!isResultSet.getString("user1").toString().equals(id)) {
					isResultValue = "user1";
				}else if (!isResultSet.getString("user2").toString().equals(id)) {
					isResultValue = "user2";
				}else if (!isResultSet.getString("user3").toString().equals(id)) {
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
			if(isResultSet != null) // ResultSet 객체를 닫는다.
				isResultSet.close();       
	      
			if(isStatement != null) 
				isStatement.close();       // Statement 객체를 닫는다.
	      
			if(isConnect != null) 
				isConnect.close(); // Connection 객체를 닫는다.
		}
		
		catch(Exception e) {
			log.error("Close Error" + e);
		}	
	}
}
