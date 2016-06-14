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
	//��ǩ
	private JLabel head=new JLabel("TCPѹ�����Կͻ���");
	private JLabel computerIp=new JLabel("����IP��");
	private JLabel port=new JLabel("�˿ڣ�");
	private JLabel userNum=new JLabel("�û�������");
	private JLabel type=new JLabel("ģʽ��");
	private JLabel sendInterval=new JLabel("���ͼ����");
	private JLabel time=new JLabel("ms");
	private JLabel save=new JLabel();
    public static JLabel error=new JLabel();
	
	//�����ı���
    private JTextField TcomputerIp=new JTextField(6);
    private JTextField Tport=new JTextField(6);
    private JTextField TuserNum=new JTextField(6);
    private JComboBox<String> Ttype=new JComboBox<String>();
    private JTextField TsendInterval=new JTextField(6);
    //��ť
    private JButton text=new JButton("����");
    private JButton start=new JButton("��ʼ");
    private JButton stop=new JButton("ֹͣ");
    //����
    private JPanel north=new JPanel();
    private JPanel headPane=new JPanel();
    private JPanel northPane=new JPanel();
    private JPanel centerPane=new JPanel();
    private JPanel southPane=new JPanel();
    //��ʾ�������
    private  JScrollPane jsp=new JScrollPane();
    private JPanel box=new JPanel();
    //���ݶԻ���
    JDialog jd = new JDialog();
    JButton jb_save=new JButton("����");
    JButton jb_ok=new JButton("ȷ��");
    JTextArea message=new JTextArea();
    
	public Main(){
		//Ĭ����Ϣ
		TcomputerIp.setText("127.0.0.1");
		Tport.setText("8888");
		TuserNum.setText("200");
		TsendInterval.setText("500");
		//������
		Container contentPane=this.getContentPane();
		this.repaint();
		contentPane.setLayout(new BorderLayout());
		//��������
		north.setLayout(new BorderLayout());
		//����������
		northPane.setLayout(new FlowLayout());
		//TCPѹ�����Կͻ��˱�ǩ
		headPane.setLayout(new FlowLayout());
		//���ÿؼ�����
		Ttype.addItem("Ӧ��ģʽ");
		Ttype.addItem("���ģʽ");
		head.setFont(new Font("Serif",Font.PLAIN,40));
		start.setBackground(Color.green);
		stop.setBackground(Color.red);
		text.setBackground(Color.yellow);
		//���������������ӿؼ�
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
		//�����������
		north.add("North",headPane);
		north.add("Center",northPane);
		contentPane.add("North",north);
		
		//�м���������		
		centerPane.setLayout(new GridLayout(1,1));
		centerPane.add(jsp);
      	contentPane.add("Center",centerPane);
      	
      	//�ϲ���������
		southPane.setLayout(new FlowLayout());
		southPane.add(error);
		contentPane.add("South",southPane);
		this.setSize(900, 600);
		new Set().set(this);
		start.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				//��ȡ����������Ϣ
				String ip=TcomputerIp.getText();
				int port=Integer.parseInt(Tport.getText());
				int numTasks=Integer.parseInt(TuserNum.getText());
				int interval=Integer.parseInt(TsendInterval.getText());
				String msg=save.getText();			
				
				try {
				    if(save.getText()==""){
				    	JOptionPane.showMessageDialog(null, "��༭��������");
				    }else{
				    	if(Ttype.getSelectedItem().toString().equals("Ӧ��ģʽ")){
				    		new Client(ip,port,numTasks,msg,"Ӧ��ģʽ",0).start();
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
                jd.setTitle("����������");
                jd.getContentPane().setLayout(new BorderLayout());
                jd.add("North",new JLabel("���������ݣ�"));
                jd.add("Center",message);
                JPanel jp=new JPanel();
                jp.setLayout(new FlowLayout(100,120,10));
                jp.add(jb_save);
                jp.add(jb_ok);
                jd.add("South",jp);
                jd.setModal(true);//ȷ�������Ĵ�������������ǰ��
                jd.setVisible(true);	
                

			}
		});
		jb_save.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
					save.setText(message.getText());
				    JOptionPane.showMessageDialog(null, "����ɹ���");
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
