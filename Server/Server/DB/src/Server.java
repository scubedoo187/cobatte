import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.StringTokenizer;
import java.util.Vector;

public class Server
{
	private ServerSocket  server;
    Vector V = new Vector(); //  ������  �����ϴ�  ����


    void  startServer()
    {
        try
        {
            server = new ServerSocket(13080);
            System.out.println("���������� �����Ǿ����ϴ�.");

            while(true)
            {
            	int i = 0;
                Socket socket=server.accept();
                System.out.println("Ŭ���̾�Ʈ�� ����Ǿ����ϴ�.");

                User_Thread pt = new User_Thread(socket); // Ŭ���̾�Ʈ�� ����ϴ� �����带 �����ϰ� �����Ų��
            	V.add(socket); // ���� ������ ����Ʈ�� ������ �߰��Ѵ�.
            	System.out.println(V.size());
            	pt.start();                 
                InetAddress inetaddr = socket.getInetAddress(); // �������� IP�� �˾Ƴ���
                System.out.println(inetaddr.getHostAddress() + " ���� �����ϼ˽��ϴ�."); // IP������
                System.out.println("���� ������ ��: " + V.size()); // ���� �����ϰ� �ִ� Ŭ���̾�Ʈ�� ���� ȭ�鿡 ����Ѵ�.
            }
        }
        
        catch(Exception  e)
        {
            System.out.println(e);
        }
    }

    public static void main(String[] args)
    {
        UserInfo_DB db = new UserInfo_DB();
        Server server = new Server();
        server.startServer(); // ������ �����Ѵ�.
        db.close();
    }
    
    class User_Thread extends Thread // Ŭ���̾�Ʈ�� ����ϴ� ������ Ŭ����
    {
        Socket socket; // ������ ���۷���
        private BufferedReader reader;
        private PrintWriter writer;
        
        User_Thread(Socket socket) // ������
        {          
            this.socket=socket;
        }

        public void run()
        {
            try
            {
                UserInfo_DB db = new UserInfo_DB();
                OutputStream out = socket.getOutputStream();
                InputStream in = socket.getInputStream();
                
                reader = new BufferedReader(new InputStreamReader(in, "utf-8"));
                writer = new PrintWriter(new OutputStreamWriter(out, "utf-8"));
                String  msg = null;
                
                while(true)// Ŭ���̾�Ʈ�κ��� ���� �޽����� ���� ������ ����
                {
	                msg = reader.readLine();
	                if(!msg.toString().equals(""))
	                {
		                StringTokenizer tok = new StringTokenizer(msg, "\t");
		                System.out.println(msg);
		                
		                int header = 0; // �Ϸù�ȣ
		                String result = null; // ���ϰ�
		                String parameter[] = new String[5];
		                int i = 0; //�켱 ����տ� �ִ� ����� ���� int������ ����
		                header = Integer.parseInt(tok.nextToken()); // ���� ����κ��� int������ ��ȯ
		                
		                while(tok.hasMoreTokens())//Ŭ���̾�Ʈ�κ��� ���� �޽����� ��ū�� ���� ������.
		                {
		                	parameter[i++] = tok.nextToken();
		                }
		                
		            	switch(header) // ��ó�� ����κ��� Switch��
		            	{
		            		case 0 : // ����� 0�϶� �α��� �õ�
		             			result = db.login(parameter[0], parameter[1]); // return �� 0 or -1
		             			writer.println(result); // Ŭ�� ����� ���ְ�
		            			writer.flush(); // ���� ����ְ�
		            			break;
		            			
		            		case 1 : // ����� 1�϶� id �ߺ�üũ
		            			result = db.overlap(parameter[0]); // return�� 1 or -1
		            			writer.println(result); // Ŭ�� ����� ���ְ�
		            			writer.flush(); // ���ۺ���ְ�
		            			break;
		  	            			
		            		case 2 : // ����� 2�϶� ȸ������ �õ�
		            			db.join(parameter[0], parameter[1]); // �̸�, �н����带 DB�� ����
		            			writer.println("quit"); // Ŭ�� quit�� ���ְ�
		            			writer.flush(); // ���ۺ���ְ�
		            			break;
		            			
		        			default :
		        				System.out.println("Not Found Header Number");
		        				break;
		            	}
		            	msg = "";
	                }
                }
            }
            
            catch(Exception  e)
            {
            }
            
            finally // Ŭ���̾�Ʈ�� ������ ��������
            {
                try
                {
                	InetAddress inetaddr = socket.getInetAddress();
                    V.remove(socket); // ���� ������ ����Ʈ���� ������ �����Ѵ�.
                    
                    if(reader!=null)  
                    	reader.close();

                    if(writer!=null)  
                    	writer.close();

                    if(socket!=null)  
                    	socket.close();

                    reader=null;  
                    writer=null;  
                    socket=null;
                    // ���ϰ� IO Stream�� close
                    

                    System.out.println(inetaddr.getHostAddress() + "�����ϼ˽��ϴ�.");
                    System.out.println("���� Ŭ���̾�Ʈ ��: "+ V.size());
                }
                
                catch(Exception  e)
                {
                }
            }
        }
    }
}