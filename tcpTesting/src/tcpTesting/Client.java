package tcpTesting;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/*
 * 客户端
 */
public class Client {
	//连接传参数
    private int port;//端口号
    private int numTasks;//任务数量
    private String IP;//IP地址
    private String client_msg;//测试消息
    private String kind="应答模式";//默认是应答模式
    private int dely;//间隔时间
    
    
    public int control=0;
    public static String connect_text;
    
    public Client(){}
    public Client(String IP,int port,int numTasks,String msg,String kind,int dely){
    	this.port=port;
    	this.numTasks=numTasks;
    	this.IP=IP;
    	this.client_msg=msg;
    	this.kind=kind;
    	this.dely=dely;
    }
    
    //定义数据表格信息
    public static String[]table=null;
    private DefaultTableModel listRecords = null;  
    String[] title={"序号","响应延时(ms)","接收数量","接收字节数量","发送数量","发送字节数量"};
    static JTable chart=new JTable();//生成的信息传给一个JTable控件
    //初始化，title是标题的数组
    void init(){
    listRecords= new DefaultTableModel(null, this.title);
    chart.setModel(this.listRecords);   
    }
    

    //连接测试

    public void connect() throws InterruptedException{
    	init();
        table=new String[6];//表格信息六列          
    	listRecords.insertRow(0,title); //第一行显示标题数据
		table[2]="1";
		table[3]=String.valueOf(client_msg.getBytes().length);
		table[4]="1";
		table[5]=String.valueOf(client_msg.getBytes().length);
		int i;
        for (i = 1; i <=numTasks; i++) {
        	//如果出现连接异常，跳出循环
        	if(control==1)
        		break;
        	//向发送池提交任务，进行测试
        	test();   	
        	connect_text=String.valueOf(i);
        	if(kind.equals("间隔模式"))
        	{
        		Thread.sleep(dely);
        		table[1]=String.valueOf(dely);  
        	}else{
        		table[1]="0";
        	}
        	table[0]=String.valueOf(i);	
        	
			listRecords.insertRow(i,table);
        }
    }
    

    
    
	public void test() {
		try {
			//1.创建客户端Socket，指定服务器地址和端口
			Socket socket=new Socket(IP, port);
			//2.获取输出流，向服务器端发送信息
			OutputStream os=socket.getOutputStream();//字节输出流
			PrintWriter pw=new PrintWriter(os);//将输出流包装为打印流
			pw.write(client_msg);
			pw.flush();
			socket.shutdownOutput();//关闭输出流
			//3.获取输入流，并读取服务器端的响应信息
			InputStream is=socket.getInputStream();
			BufferedReader br=new BufferedReader(new InputStreamReader(is));
			String info=null;
			while((info=br.readLine())!=null){
				System.out.println("我是客户端，服务器说："+info);
			}
			//4.关闭资源
//			br.close();
//			is.close();
			pw.close();
			os.close();
			//socket.close();
		} catch (Exception e) {
			control=1;
			e.printStackTrace();
		} 
	}
}
