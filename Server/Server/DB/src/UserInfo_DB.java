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
	public String a; // return ��
	
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

	public void join(String Name, String Pw) // insert�� ȸ������
	{
		try
		{
			stmt.executeUpdate("insert into UserInfo values('"+Name+"', '"+Pw+"')"); // ���� �����ְ�
		}
		
		catch(Exception e)
		{
			System.out.println("Insert Error " + e);
		}
	}
	
	public String login(String Name, String Pw) // login�� ����� 0�϶�
	{
		try
		{
			rs = stmt.executeQuery("select * from UserInfo"); // java.Statement => java.ResultSet���� ��ȯ
			boolean alogin = false; // ���Ҷ� ������
			
			while(rs.next()) // DB�� ���ڵ带 ������
			{
				if(Name.toString().equals(rs.getString("Name")) && Pw.toString().equals(rs.getString("Pw"))) // �Է��� Name�� DB�� �ִ� Name�� �����Ѱ�? && �Է��� Pw�� DB�� �ִ� Pw�� �����Ѱ�?
					alogin = true; // ������ true
			}
			
			if(alogin)
				a = "0"; // true�̸� 0
			
			else
				a = "quit"; // false�̸� -1
		}
		
		catch (Exception e)
		{
			System.out.println("Login Error " + e);
		}
		
		return a;
	}
	
	public String overlap(String Name)//id�ߺ�üũ
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
			if(rs != null) // ResultSet ��ü�� �ݴ´�.
				rs.close();       
	      
			if(stmt != null) 
				stmt.close();       // Statement ��ü�� �ݴ´�.
	      
			if(conn != null) 
				conn.close(); // Connection ��ü�� �ݴ´�.
		}
		
		catch(Exception e)
		{
			System.out.println("Close Error" + e);
		}	
	}
}
