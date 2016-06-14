package tcpTesting;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/*
 * �ͻ���
 */
public class Client {
	//���Ӵ�����
    private int port;//�˿ں�
    private int numTasks;//��������
    private String IP;//IP��ַ
    private String client_msg;//������Ϣ
    private String kind="Ӧ��ģʽ";//Ĭ����Ӧ��ģʽ
    private int dely;//���ʱ�� 
    public static String connect_text="���ڲ���......";
    
    public Client(){}
    public Client(String IP,int port,int numTasks,String msg,String kind,int dely){
    	this.port=port;
    	this.numTasks=numTasks;
    	this.IP=IP;
    	this.client_msg=msg;
    	this.kind=kind;
    	this.dely=dely;
    }
    
    //�������ݱ����Ϣ
    public static String[]table=null;
    private DefaultTableModel listRecords = null;  
    String[] title={"���","��Ӧ��ʱ(ms)","��������","�����ֽ�����","��������","�����ֽ�����"};
    static JTable chart=new JTable();//���ɵ���Ϣ����һ��JTable�ؼ�
    //��ʼ����title�Ǳ��������
    void init(){
    listRecords= new DefaultTableModel(null, this.title);
    chart.setModel(this.listRecords);   
    }
    

    //���Ӳ���

public void start() throws InterruptedException{

		ExecutorService pools = Executors.newCachedThreadPool();
		// ����POOL_SIZE������
		if(kind.equals("���ģʽ")){
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

		
		// �����̳߳���ֹ����, �ȴ�����ִ���Լ���δִ�е��������
		pools.shutdown();
		
		// ��ѯ�̳߳��е������Ƿ�ȫ������
		while(!pools.isTerminated()){
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
			}
		}
		
		//������ʾ���Ե���Ϣ
	connect_text = "����"+numTasks+"���û��������ӣ�"+
						"�ɹ���������:"+ShareParam.getSuccessed()+
						"ʧ�ܲ�������:"+ShareParam.getFailed();
		
	//�����Գɹ���������ʾ��JTable����
	init();
    table=new String[6];//�����Ϣ����          
	listRecords.insertRow(0,title); //��һ����ʾ��������
	table[2]="1";
	table[3]=String.valueOf(client_msg.getBytes().length);
	table[4]="1";
	table[5]=String.valueOf(client_msg.getBytes().length);
	for(int j=1;j<ShareParam.getSuccessed();j++){ 	
    	if(kind.equals("���ģʽ"))
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
