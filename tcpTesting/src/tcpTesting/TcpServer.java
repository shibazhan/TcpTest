package tcpTesting;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class TcpServer {
	public static int count=0;
	static ServerThread serverThread=null;
	
	public static void main(String[] args) {
		try {
			//����������socket
			@SuppressWarnings("resource")
			ServerSocket serverSocket=new ServerSocket(8888);
			System.out.println("*****��������ʼ����******");
			Socket socket=null;
        while(true){
        	socket=serverSocket.accept();
        	 serverThread=new ServerThread(socket);	
            	serverThread.start();
            	count++;
            	System.out.println("�ͻ���������"+count);    
        	
        }					
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
