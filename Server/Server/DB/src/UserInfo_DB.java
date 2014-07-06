import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class UserInfo_DB 
{
	Connection conn = null;
	Statement stmt = null;
	ResultSet rs = null;
	
	private String Name;
	private String Pw;
	public String a; // return 값
	
	public UserInfo_DB()
	{
		connect();
	}
	
	public void connect()
	{
		String url = "jdbc:mysql://localhost:3306/DB?autoReconnect=true";
		String validationQuery = "select 1";
		String user = "root";
		String dbpw = "jmwa1308-1";
		
		try
		{
			Class.forName("com.mysql.jdbc.Driver");
			this.conn = DriverManager.getConnection(url, user, dbpw);
			this.stmt = this.conn.createStatement();
		}
		
		catch(Exception e)
		{
			System.out.println("Connect error : " + e);
		}
	}

	public void join(String Name, String Pw) // insert문 회원가입
	{
		try
		{
			stmt.executeUpdate("insert into UserInfo values('"+Name+"', '"+Pw+"')"); // 쿼리 날려주공
		}
		
		catch(Exception e)
		{
			System.out.println("Insert Error " + e);
		}
	}
	
	public String login(String Name, String Pw) // login문 헤더가 0일때
	{
		try
		{
			rs = stmt.executeQuery("select * from UserInfo"); // java.Statement => java.ResultSet으로 변환
			boolean alogin = false; // 비교할때 쓸꺼임
			
			while(rs.next()) // DB의 레코드를 가져옴
			{
				if(Name.toString().equals(rs.getString("Name")) && Pw.toString().equals(rs.getString("Pw"))) // 입력한 Name과 DB에 있는 Name이 동일한가? && 입력한 Pw와 DB에 있는 Pw가 동일한가?
					alogin = true; // 맞으면 true
			}
			
			if(alogin)
				a = "0"; // true이면 0
			
			else
				a = "quit"; // false이면 -1
		}
		
		catch (Exception e)
		{
			System.out.println("Login Error " + e);
		}
		
		return a;
	}
	
	public String overlap(String Name)//id중복체크
	{
		try
		{
			rs = stmt.executeQuery("select * from UserInfo");
			boolean alogin = false;
			
			while(rs.next())
			{
				if(Name.toString().equals(rs.getString("Name")))
					alogin = true;
			}
			
			if(alogin)
				a = "quit";
			
			else
				a = "1";
		}
		
		catch (Exception e)
		{
			System.out.println("Login Error " + e);
		}
		
		return a;
	}
	
	public void close()
	{
		try
		{
			if(rs != null) // ResultSet 객체를 닫는다.
				rs.close();       
	      
			if(stmt != null) 
				stmt.close();       // Statement 객체를 닫는다.
	      
			if(conn != null) 
				conn.close(); // Connection 객체를 닫는다.
		}
		
		catch(Exception e)
		{
			System.out.println("Close Error" + e);
		}	
	}
}
