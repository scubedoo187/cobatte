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

import org.apache.log4j.Logger;

public class Server{
	static Logger log = Logger.getLogger(Server.class.getName());
	private ServerSocket  isServerSocket;
	final int SERVER_LOGIN = 0;
	final int SERVER_IDCHECK = 1;
	final int SERVER_SINGUP = 2;
	final int SERVER_CREATEROOM = 3;
	final int SERVER_ROOMLIST = 4;
	final int SERVER_ENTERROOM = 5;
	final int SERVER_LEAVEROOM = 6;
	final int SERVER_ROOMINFO = 7;
	final int SERVER_QUIT = 9;
	final int SERVER_PORTNUMBER = 13080;
    Vector isVector = new Vector();

    void startServer() {
        try {
        	isServerSocket = new ServerSocket(SERVER_PORTNUMBER);

        	log.info("������ ����Ǿ����ϴ�.");
        	
            while (true) {
                Socket socket = isServerSocket.accept();
                log.info("Ŭ���̾�Ʈ�� ����Ǿ����ϴ�.");

                UserThread pt = new UserThread(socket);
            	isVector.add(socket);
            	
            	pt.start();                 
                InetAddress inetaddr = socket.getInetAddress();
                log.info(inetaddr.getHostAddress() + " ���� �����ϼ˽��ϴ�.");
                log.info("���� ������ ��: " + isVector.size());
            }
        }
        
        catch (Exception  e) {
            log.error("���� ���� ����");
        }
    }

    public static void main(String[] args) {
        LinkDatabase isLinkDatabase = new LinkDatabase();
        Server server = new Server();
        server.startServer();
        isLinkDatabase.close();
    }
    
    class UserThread extends Thread {
        Socket isSocket;
        private BufferedReader isReader;
        private PrintWriter isWriter;
        
        UserThread(Socket socket) {
            this.isSocket=socket;
        }

        public void run() {
            try {
            	LinkDatabase db = new LinkDatabase();
                OutputStream out = isSocket.getOutputStream();
                InputStream in = isSocket.getInputStream();
                
                isReader = new BufferedReader(new InputStreamReader(in, "utf-8"));
                isWriter = new PrintWriter(new OutputStreamWriter(out, "utf-8"));
                String msg = null;
                
                while (true) {
	                msg = isReader.readLine();
	                
	                if (!msg.toString().equals("")) {
		                StringTokenizer tok = new StringTokenizer(msg, "\t");
		                log.info(msg);
		                
		                int header = 0;
		                int i = 0;
		                String result = null;
		                String parameter[] = new String[5];
		                header = Integer.parseInt(tok.nextToken());
		                
		                while (tok.hasMoreTokens())
		                	parameter[i++] = tok.nextToken();
		                
		            	switch (header) {
		            		case SERVER_LOGIN :
		             			result = db.login(parameter[0], parameter[1]);
		             			if (result.equals("0")) {
		             				result = db.roominfo(parameter[0]);
		             				if (result.equals("0")) {
			             				isWriter.println(result);
				            			isWriter.flush();
		             				}else {
		             					isWriter.println("exist");
				            			isWriter.flush();
		             				}
		             			}else {
			             			isWriter.println(result);
			            			isWriter.flush();
		             			}
		            			break;
		            			
		            		case SERVER_IDCHECK :
		            			result = db.checkOverlapId(parameter[0]);
		            			isWriter.println(result);
		            			isWriter.flush();
		            			break;
		  	            			
		            		case SERVER_SINGUP :
		            			db.signup(parameter[0], parameter[1]);
		            			isWriter.println("quit");
		            			isWriter.flush();
		            			break;
		            			
		            		case SERVER_CREATEROOM :
		            			result = db.createRoom(parameter[0], parameter[1], parameter[2],
		            						parameter[3], parameter[4]);
		            			isWriter.println(result);
		            			isWriter.flush();
		            			break;
		            			
		            		case SERVER_ROOMLIST :
		            			result = db.roomList();
		            			isWriter.println(result);
		            			isWriter.flush();
		            			break;
		            			
		            		case SERVER_ENTERROOM :
		            			result = db.enterARoom(parameter[0], parameter[1]);
		            			isWriter.println(result);
		            			isWriter.flush();
		            			break;
		            			
		            		case SERVER_LEAVEROOM :
		            			db.leaveRoom(parameter[0], parameter[1]);
		            			isWriter.println("6");
		            			isWriter.flush();
		            			break;
		            			
		            		case SERVER_ROOMINFO :
		            			result = db.roominfo(parameter[0]);
		            			isWriter.println(result);
		            			isWriter.flush();
		            			break;
		            			
		            		case SERVER_QUIT :
		            			isWriter.println("quit");
		            			isWriter.flush();
		            			break;
		            			
		        			default :
		        				log.error("Not Found Header Number");
		        				break;
		            	}
		            	msg = "";
	                }
                }
            }
            
            catch (Exception e) {
            	log.fatal("Ŭ���̾�Ʈ ���� ����");
            }
            
            finally {
            	try {
                	InetAddress inetaddr = isSocket.getInetAddress();
                    isVector.remove(isSocket); // ���� ������ ����Ʈ���� ������ �����Ѵ�.
                    
                    if (isReader != null)  
                    	isReader.close();

                    if (isWriter != null)  
                    	isWriter.close();

                    if (isSocket != null)  
                    	isSocket.close();

                    isReader = null;  
                    isWriter = null;  
                    isSocket = null;
                    // ���ϰ� IO Stream�� close
                    log.info(inetaddr.getHostAddress() + "�����ϼ˽��ϴ�.");
                    log.info("���� Ŭ���̾�Ʈ ��: " + isVector.size());
                }
                catch (Exception e) {
                	log.error("���� ������ ����");
                }
            }
        }
    }
}