package tcpTesting;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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
    public static String connect_text="正在测试......";
    
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

public void start() throws InterruptedException{

		ExecutorService pools = Executors.newCachedThreadPool();
		// 创建POOL_SIZE个并发
		if(kind.equals("间隔模式")){
			for (int i = 0; i < numTasks; i++) {
				Thread.sleep(dely);
				pools.execute(new Handler(IP,port,client_msg)); 
				
			}
		}
		else{
			for (int i = 0; i < numTasks; i++) {
				
				pools.execute(new Handler(IP,port,client_msg)); 
				
			}
		}

		
		// 发起线程池终止请求, 等待正在执行以及尚未执行的任务结束
		pools.shutdown();
		
		// 轮询线程池中的任务是否全部结束
		while(!pools.isTerminated()){
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
			}
		}
		
		//用于显示测试的信息
	connect_text = "共有"+numTasks+"个用户请求连接！"+
						"成功并发数量:"+ShareParam.getSuccessed()+
						"失败并发数量:"+ShareParam.getFailed();
		
	//将测试成功的数据显示在JTable表中
	init();
    table=new String[6];//表格信息六列          
	listRecords.insertRow(0,title); //第一行显示标题数据
	table[2]="1";
	table[3]=String.valueOf(client_msg.getBytes().length);
	table[4]="1";
	table[5]=String.valueOf(client_msg.getBytes().length);
	for(int j=1;j<ShareParam.getSuccessed();j++){ 	
    	if(kind.equals("间隔模式"))
    	{
    		table[1]=String.valueOf(dely);  
    	}else{
    		table[1]="0";
    	}
    	table[0]=String.valueOf(j);	    	
		listRecords.insertRow(j,table);
	}
		ShareParam.resetShareParam();
	}
    
}
