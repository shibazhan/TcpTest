package tcpTesting;

import java.io.BufferedOutputStream;
import java.io.IOException;

public class Handler implements Runnable{
	
	private final String ip;
	private final int port;
	private final String message;
	
	private java.net.Socket socket = null;
	
	public Handler(final String ip, final int port, final String message){
		this.ip = ip;
		this.port = port;
		this.message = message;
	}
	
	@Override
	public void run() {
		try {
			// ����һ��tcp��������, ������ӳɹ����ۼƳɹ�����, ����֮
			socket = new java.net.Socket(ip, port);
			// 2014-2-25 �����������
			BufferedOutputStream bos = new BufferedOutputStream(socket.getOutputStream());
			byte[] ms = message.getBytes();
			bos.write(ms);
			bos.flush();
			ShareParam.refreshSuccess();
		} catch (Exception e) {
			ShareParam.refreshFailed();
		} finally{
			try {
				if(socket==null)
					return;
				socket.close();
			} catch (IOException e) {
			}
		}
	}

}
