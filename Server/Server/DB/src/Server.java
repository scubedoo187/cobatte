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

public class Server{
	private ServerSocket  isServerSocket;
    Vector isVector = new Vector(); //  ������  �����ϴ�  ����

    void startServer(){
        try{
        	isServerSocket = new ServerSocket(13080);

            System.out.println("���������� �����Ǿ����ϴ�.");

            while(true){
            	int i = 0;
                Socket socket=isServerSocket.accept();
                System.out.println("Ŭ���̾�Ʈ�� ����Ǿ����ϴ�.");

                User_Thread pt = new User_Thread(socket); // Ŭ���̾�Ʈ�� ����ϴ� �����带 �����ϰ� �����Ų��
            	isVector.add(socket); // ���� ������ ����Ʈ�� ������ �߰��Ѵ�.
            	
            	pt.start();                 
                InetAddress inetaddr = socket.getInetAddress(); // �������� IP�� �˾Ƴ���
                System.out.println(inetaddr.getHostAddress() + " ���� �����ϼ˽��ϴ�."); // IP������
                System.out.println("���� ������ ��: " + isVector.size());
            }
        }        
        catch(Exception  e){
            System.out.println(e);
        }
    }

    public static void main(String[] args){
        LinkDatabase isLinkDatabase = new LinkDatabase();
        Server server = new Server();
        server.startServer(); // ������ �����Ѵ�.
        isLinkDatabase.close();
    }
    
    class User_Thread extends Thread{
        Socket isSocket; // ������ ���۷���
        private BufferedReader isReader;
        private PrintWriter isWriter;
        
        User_Thread(Socket socket) {
            this.isSocket=socket;
        }

        public void run(){
            try{
            	LinkDatabase db = new LinkDatabase();
                OutputStream out = isSocket.getOutputStream();
                InputStream in = isSocket.getInputStream();
                
                isReader = new BufferedReader(new InputStreamReader(in, "utf-8"));
                isWriter = new PrintWriter(new OutputStreamWriter(out, "utf-8"));
                String msg = null;
                
                while(true){
	                msg = isReader.readLine();
	                if(!msg.toString().equals("")){
		                StringTokenizer tok = new StringTokenizer(msg, "\t");
		                System.out.println(msg);
		                
		                int header = 0; // �Ϸù�ȣ
		                int i = 0; //�켱 ����տ� �ִ� ����� ���� int������ ����
		                String result = null; // ���ϰ�
		                String parameter[] = new String[5];
		                header = Integer.parseInt(tok.nextToken()); // ���� ����κ��� int������ ��ȯ
		                
		                while(tok.hasMoreTokens())
		                	parameter[i++] = tok.nextToken();
		                
		            	switch(header){
		            		case 0 : // ����� 0�϶� �α��� �õ�
		             			result = db.login(parameter[0], parameter[1]); // return �� 0 or -1
		             			isWriter.println(result); // Ŭ�� ����� ���ְ�
		            			isWriter.flush(); // ���� ����ְ�
		            			break;
		            			
		            		case 1 : // ����� 1�϶� id �ߺ�üũ
		            			result = db.checkOverlapId(parameter[0]); // return�� 1 or -1
		            			isWriter.println(result); // Ŭ�� ����� ���ְ�
		            			isWriter.flush(); // ���ۺ���ְ�
		            			break;
		  	            			
		            		case 2 : // ����� 2�϶� ȸ������ �õ�
		            			db.signup(parameter[0], parameter[1]); // �̸�, �н����带 DB�� ����
		            			isWriter.println("quit"); // Ŭ�� quit�� ���ְ�
		            			isWriter.flush(); // ���ۺ���ְ�
		            			break;
		            			
		            		case 9 : // ���� 
		            			isWriter.println("quit"); // quit �� ����
		            			isWriter.flush();	// ���� ����ְ�
		            			break;
		            			
		        			default :
		        				System.out.println("Not Found Header Number");
		        				break;
		            	}
		            	msg = "";
	                }
                }
            }
            
            catch(Exception  e){}
            
            finally{
                try{
                	InetAddress inetaddr = isSocket.getInetAddress();
                    isVector.remove(isSocket); // ���� ������ ����Ʈ���� ������ �����Ѵ�.
                    
                    if(isReader!=null)  
                    	isReader.close();

                    if(isWriter!=null)  
                    	isWriter.close();

                    if(isSocket!=null)  
                    	isSocket.close();

                    isReader=null;  
                    isWriter=null;  
                    isSocket=null;
                    // ���ϰ� IO Stream�� close
                    System.out.println(inetaddr.getHostAddress() + "�����ϼ˽��ϴ�.");
                    System.out.println("���� Ŭ���̾�Ʈ ��: "+ isVector.size());
                }
                catch(Exception  e) {}
            }
        }
    }
}