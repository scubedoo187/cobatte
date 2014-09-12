import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class LinkDatabase {
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
			System.out.println("Connect error : " + e);
		}
	}

	public void signup(String Name, String Password) {
		try {
			isStatement.executeUpdate("insert into UserInfo values('"+Name+"', '"+Password+"')");
		}
		
		catch(Exception e) {
			System.out.println("Insert Error " + e);
		}
	}
	
	public String login(String Name, String Password) {
		try {
			isResultSet = isStatement.executeQuery("select * from UserInfo");
			boolean checklogin = false; // 비교할때 쓸꺼임
			
			while(isResultSet.next()) {
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
			System.out.println("Login Error " + e);
		}
		
		return isResultValue;
	}
	
	public String checkOverlapId(String Name) {
		try {
			isResultSet = isStatement.executeQuery("select * from UserInfo");
			boolean alogin = false;
			
			while(isResultSet.next()) {
				if(Name.toString().equals(isResultSet.getString("id")))
					alogin = true;
			}
			
			if(alogin)
				isResultValue = "quit";
			
			else
				isResultValue = "1";
		}
		
		catch (Exception e) {
			System.out.println("Login Error " + e);
		}
		
		return isResultValue;
	}
	
	public void createRoom(String admin, String roomName, String place, int hour, int minute) {
		try {
			isStatement.executeUpdate("insert into roominfo(admin, roomname, place, minute, hour) "
					+ "values('"+admin+"', '"+roomName+"' ,'"+place+"', "+minute+", "+hour+")");
		}
		
		catch(Exception e) {
			System.out.println("Insert Error " + e);
		}
	}
	
	public void deleteRoom() {
		
	}
	
	public String roomList() {
		try {
			isResultSet = isStatement.executeQuery("select * from UserInfo");
			boolean checklogin = false; // 비교할때 쓸꺼임
			
			while(isResultSet.next()) {
				//if(Name.toString().equals(isResultSet.getString("id")) 
				//		&& Password.toString().equals(isResultSet.getString("passwd")))
					checklogin = true; // 맞으면 true
			}
			
			if(checklogin)
				isResultValue = "0"; // true이면 0
			
			else
				isResultValue = "quit"; // false이면 -1
		}
		
		catch (Exception e) {
			System.out.println("Login Error " + e);
		}
		
		return isResultValue;
	}
	
	public String roominfo() {
		return "k";
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
			System.out.println("Close Error" + e);
		}	
	}
}
