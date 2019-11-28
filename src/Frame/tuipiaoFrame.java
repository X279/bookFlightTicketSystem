package Frame;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import connectDatabase.connect;

public class tuipiaoFrame extends JFrame{
	
	private Mypanel contentPane;
	private JTable table;
	private Vector<Vector<String>> rowData = new Vector<Vector<String>>();
	private DefaultTableModel tableModel;
	private JTextField name;
	private JTextField idNum;
	private JScrollPane scrollPane;
	private ImageIcon icon = new ImageIcon("pictures\\界面背景.png");
	
	//一些数据
	private String fno;
	private String sp;
	private String ep;
	private String st;
	private String et;
	private String type;
	private String sno;
	private String tell;
	private int row;
	
	public void setup() {
		setTitle("退票窗口");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(930, 590);
		setLocationRelativeTo(null);
		setResizable(false);
		contentPane = new Mypanel(icon);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		Vector<String> columnName = new Vector<String>();
		columnName.add("航班号");
		columnName.add("出发地点");
		columnName.add("到达地点");
		columnName.add("出发时间");
		columnName.add("到达时间");
		columnName.add("舱位");
		columnName.add("座位号");
		columnName.add("联系方式");

		table = new JTable(rowData, columnName) {
			public boolean isCellEditable(int row, int column){
				return false;
				}
			};
		table.setCellSelectionEnabled(false);
		table.setRowSelectionAllowed(true);
		table.setFont(new Font("楷体",Font.PLAIN,17));
		table .getTableHeader().setReorderingAllowed(false);//表头不可移动
		table.getTableHeader().setResizingAllowed(false);//不允许手动改变列宽
		tableModel = (DefaultTableModel) table.getModel();
		
		table.addMouseListener(new MouseAdapter(){
			public void mouseClicked(MouseEvent event)
			{
				row = table.rowAtPoint(event.getPoint());
				fno = (String)tableModel.getValueAt(row, 0);
				sp = (String)tableModel.getValueAt(row, 1);
				ep = (String)tableModel.getValueAt(row, 2);
				st = (String)tableModel.getValueAt(row, 3);
				st = st.substring(0, 16);
				System.out.println(st);
				et = (String)tableModel.getValueAt(row, 4);
				type = (String)tableModel.getValueAt(row, 5);
				sno = (String)tableModel.getValueAt(row, 6);
				tell = (String)tableModel.getValueAt(row, 7);
			}
		});
		scrollPane = new JScrollPane(table);
		scrollPane.setOpaque(false);
		scrollPane.getViewport().setOpaque(false);
		scrollPane.setFont(new Font("楷体",Font.PLAIN,17));
		scrollPane.setBounds(15, 15, 890, 292);
		contentPane.add(scrollPane);
		
		JButton button = new JButton("退票");
		button.setFont(new Font("楷体",Font.PLAIN,17));
		button.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				int n = JOptionPane.showConfirmDialog(null, "您的退票信息为:\r\n" + 
						"	" + st + " - " + et + sp + "到" + ep + "\r\n" + 
						"	航班号：" + fno + "\r\n" + 
						"	座位：" + type + "舱 " + sno + "\r\n" + 
						"请确认信息是否正确！欢迎您再次预定！", 
						"确认机票信息",JOptionPane.YES_NO_OPTION);
				if(n == 0) {
					connect conn = new connect();
					conn.login();
					conn.deletePassengerInformation(fno,sno,idNum.getText(), st);
					boolean b = conn.addRefundInformation(fno + "-" + st + "-" + sno, idNum.getText(), name.getText(), tell);
					if(b) {
						JOptionPane.showMessageDialog(null, "退票成功!");
						tableModel.removeRow(row);
					}
					else {
						JOptionPane.showMessageDialog(null, "退票失败!", "失败",JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		});
		button.setBounds(590, 481, 123, 29);
		contentPane.add(button);
		
		JButton button2 = new JButton("查询");
		button2.setFont(new Font("楷体",Font.PLAIN,17));
		button2.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				connect conn = new connect();
				conn.login();
				conn.searchPassenger(name.getText(), idNum.getText());
				System.out.println(idNum.getText());
				rowData = conn.ticketInfo;
				tableModel = new DefaultTableModel(rowData, columnName);
				table.setModel(tableModel);
			}
		});
		button2.setBounds(425,481,123,29);
		contentPane.add(button2);
		
		JButton button3 = new JButton("返回");
		button3.setFont(new Font("楷体",Font.PLAIN,17));
		button3.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				main main = new main();
				main.init();
				dispose();
			}
		});
		button3.setBounds(250,481,123,29);
		contentPane.add(button3);
		
		JButton button4 = new JButton("改签");
		button4.setFont(new Font("楷体",Font.PLAIN,17));
		button4.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				connect conn = new connect();
				conn.login();
				conn.searchAllFlightInformation(sp, ep);
				gaiqianFrame frame = new gaiqianFrame(conn.flightInfo,idNum.getText(),st,fno,sno,name.getText());
				frame.setup();
			}
		});
		button4.setBounds(750,481,123,29);
		contentPane.add(button4);
		
		JLabel label = new JLabel("姓名");
		label.setFont(new Font("楷体",Font.PLAIN,17));
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setBounds(47, 366, 81, 21);
		contentPane.add(label);
		
		name = new JTextField("刘可欣");
		name.setFont(new Font("楷体",Font.PLAIN,17));
		name.setBounds(148, 363, 450, 27);
		contentPane.add(name);
		name.setColumns(10);
		
		JLabel label_1 = new JLabel("证件号码");
		label_1.setFont(new Font("楷体",Font.PLAIN,17));
		label_1.setHorizontalAlignment(SwingConstants.CENTER);
		label_1.setBounds(47, 421, 81, 21);
		contentPane.add(label_1);
		
		idNum = new JTextField("421022200011027625");
		idNum.setFont(new Font("楷体",Font.PLAIN,17));
		idNum.setBounds(148, 418, 450, 27);
		contentPane.add(idNum);
		idNum.setColumns(10);
		
		setVisible(true);
	}
}
