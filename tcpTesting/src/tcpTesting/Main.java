package tcpTesting;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;


@SuppressWarnings("serial")
public class Main extends JFrame {
	//标签
	private JLabel head=new JLabel("TCP压力测试客户端");
	private JLabel computerIp=new JLabel("主机IP：");
	private JLabel port=new JLabel("端口：");
	private JLabel userNum=new JLabel("用户数量：");
	private JLabel type=new JLabel("模式：");
	private JLabel sendInterval=new JLabel("发送间隔：");
	private JLabel time=new JLabel("ms");
	private JLabel save=new JLabel();
    public static JLabel error=new JLabel();
	
	//输入文本框
    private JTextField TcomputerIp=new JTextField(6);
    private JTextField Tport=new JTextField(6);
    private JTextField TuserNum=new JTextField(6);
    private JComboBox<String> Ttype=new JComboBox<String>();
    private JTextField TsendInterval=new JTextField(6);
    //按钮
    private JButton text=new JButton("内容");
    private JButton start=new JButton("开始");
    private JButton stop=new JButton("停止");
    //容器
    private JPanel north=new JPanel();
    private JPanel headPane=new JPanel();
    private JPanel northPane=new JPanel();
    private JPanel centerPane=new JPanel();
    private JPanel southPane=new JPanel();
    //显示滚动表格
    private  JScrollPane jsp=new JScrollPane();
    private JPanel box=new JPanel();
    //内容对话框
    JDialog jd = new JDialog();
    JButton jb_save=new JButton("保存");
    JButton jb_ok=new JButton("确定");
    JTextArea message=new JTextArea();
    
	public Main(){
		//默认信息
		TcomputerIp.setText("127.0.0.1");
		Tport.setText("8888");
		TuserNum.setText("200");
		TsendInterval.setText("500");
		//总容器
		Container contentPane=this.getContentPane();
		this.repaint();
		contentPane.setLayout(new BorderLayout());
		//容器北部
		north.setLayout(new BorderLayout());
		//导航栏容器
		northPane.setLayout(new FlowLayout());
		//TCP压力测试客户端标签
		headPane.setLayout(new FlowLayout());
		//设置控件属性
		Ttype.addItem("应答模式");
		Ttype.addItem("间隔模式");
		head.setFont(new Font("Serif",Font.PLAIN,40));
		start.setBackground(Color.green);
		stop.setBackground(Color.red);
		text.setBackground(Color.yellow);
		//向标题栏容器中添加控件
		northPane.add(computerIp);
		northPane.add(TcomputerIp);
		northPane.add(port);
		northPane.add(Tport);
		northPane.add(userNum);
		northPane.add(TuserNum);
		northPane.add(type);
		northPane.add(Ttype);
		northPane.add(sendInterval);
		northPane.add(TsendInterval);
		northPane.add(time);
		northPane.add(text);
		northPane.add(start);
		northPane.add(stop);
		headPane.add(head);
		//容器北部添加
		north.add("North",headPane);
		north.add("Center",northPane);
		contentPane.add("North",north);
		
		//中间容器设置		
		centerPane.setLayout(new GridLayout(1,1));
		centerPane.add(jsp);
      	contentPane.add("Center",centerPane);
      	
      	//南部容器设置
		southPane.setLayout(new FlowLayout());
		southPane.add(error);
		contentPane.add("South",southPane);
		this.setSize(900, 600);
		new Set().set(this);
		start.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				//获取连接属性信息
				String ip=TcomputerIp.getText();
				int port=Integer.parseInt(Tport.getText());
				int numTasks=Integer.parseInt(TuserNum.getText());
				int interval=Integer.parseInt(TsendInterval.getText());
				String msg=save.getText();			
				
				try {
				    if(save.getText()==""){
				    	JOptionPane.showMessageDialog(null, "请编辑发送内容");
				    }else{
				    	if(Ttype.getSelectedItem().toString().equals("应答模式")){
				    		new Client(ip,port,numTasks,msg,"应答模式",0).start();
				    	}
				    	else{
				    		new Client(ip,port,numTasks,msg,Ttype.getSelectedItem().toString(),interval).start();
				    	}
				    	error.repaint();
					    error.setText(Client.connect_text);					    
					    error.setForeground(Color.red);
						box.setLayout(new BorderLayout());
						box.add("Center",Client.chart);
						jsp.setViewportView(box);
				    }
				} catch (Exception e1) {
					e1.printStackTrace();
				}

			}
		});
		stop.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				setVisible(false);
			}
		});
		text.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){

                jd.setBounds(300,150,500,200);
                jd.setTitle("请输入内容");
                jd.getContentPane().setLayout(new BorderLayout());
                jd.add("North",new JLabel("请输入内容："));
                jd.add("Center",message);
                JPanel jp=new JPanel();
                jp.setLayout(new FlowLayout(100,120,10));
                jp.add(jb_save);
                jp.add(jb_ok);
                jd.add("South",jp);
                jd.setModal(true);//确保弹出的窗口在其他窗口前面
                jd.setVisible(true);	
                

			}
		});
		jb_save.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
					save.setText(message.getText());
				    JOptionPane.showMessageDialog(null, "保存成功！");
			}
		});
		jb_ok.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
					jd.dispose();
			}
		});
	}
	public static void main(String[]args){
		new Main();	
	}

}
