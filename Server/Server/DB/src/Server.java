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
    Vector isVector = new Vector(); //  소켓을  관리하는  벡터

    void startServer(){
        try{
        	isServerSocket = new ServerSocket(13080);

            System.out.println("서버소켓이 생성되었습니다.");

            while(true){
            	int i = 0;
                Socket socket=isServerSocket.accept();
                System.out.println("클라이언트와 연결되었습니다.");

                User_Thread pt = new User_Thread(socket); // 클라이언트와 통신하는 스레드를 생성하고 실행시킨다
            	isVector.add(socket); // 소켓 관리자 리스트에 소켓을 추가한다.
            	
            	pt.start();                 
                InetAddress inetaddr = socket.getInetAddress(); // 접속자의 IP를 알아낸다
                System.out.println(inetaddr.getHostAddress() + " 님이 접속하셧습니다."); // IP찍어쥬공
                System.out.println("현재 접속자 수: " + isVector.size());
            }
        }        
        catch(Exception  e){
            System.out.println(e);
        }
    }

    public static void main(String[] args){
        LinkDatabase isLinkDatabase = new LinkDatabase();
        Server server = new Server();
        server.startServer(); // 서버를 실행한다.
        isLinkDatabase.close();
    }
    
    class User_Thread extends Thread{
        Socket isSocket; // 소켓의 레퍼런스
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
		                
		                int header = 0; // 일련번호
		                int i = 0; //우선 가장앞에 있는 헤더를 따로 int형으로 저장
		                String result = null; // 리턴값
		                String parameter[] = new String[5];
		                header = Integer.parseInt(tok.nextToken()); // 앞으 헤더부분을 int형으로 변환
		                
		                while(tok.hasMoreTokens())
		                	parameter[i++] = tok.nextToken();
		                
		            	switch(header){
		            		case 0 : // 헤더가 0일때 로그인 시도
		             			result = db.login(parameter[0], parameter[1]); // return 값 0 or -1
		             			isWriter.println(result); // 클라에 결과값 쏴주고
		            			isWriter.flush(); // 버퍼 비워주고
		            			break;
		            			
		            		case 1 : // 헤더가 1일때 id 중복체크
		            			result = db.checkOverlapId(parameter[0]); // return값 1 or -1
		            			isWriter.println(result); // 클라에 결과값 쏴주고
		            			isWriter.flush(); // 버퍼비워주고
		            			break;
		  	            			
		            		case 2 : // 헤더가 2일때 회원가입 시도
		            			db.signup(parameter[0], parameter[1]); // 이름, 패스워드를 DB에 저장
		            			isWriter.println("quit"); // 클라에 quit값 쏴주고
		            			isWriter.flush(); // 버퍼비워주고
		            			break;
		            			
		            		case 9 : // 종료 
		            			isWriter.println("quit"); // quit 값 전송
		            			isWriter.flush();	// 버퍼 비워주고
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
                    isVector.remove(isSocket); // 소켓 관리자 리스트에서 소켓을 제거한다.
                    
                    if(isReader!=null)  
                    	isReader.close();

                    if(isWriter!=null)  
                    	isWriter.close();

                    if(isSocket!=null)  
                    	isSocket.close();

                    isReader=null;  
                    isWriter=null;  
                    isSocket=null;
                    // 소켓과 IO Stream을 close
                    System.out.println(inetaddr.getHostAddress() + "종료하셧습니다.");
                    System.out.println("현재 클라이언트 수: "+ isVector.size());
                }
                catch(Exception  e) {}
            }
        }
    }
}