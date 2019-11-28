package Frame;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableModel;

import connectDatabase.connect;

public class flightFrame{
	
	//界面主要构件
	private JFrame frame = new JFrame("机票信息");
	private ImageIcon icon = new ImageIcon("pictures\\航班信息图片.jpg");
	private Mypanel contentPane;
	private JTable table;
	private JLabel splace;
	private JLabel eplace;
	private JLabel stime;
	private JLabel etime;
	private JTextField pname;
	private JComboBox<String> idType;
	private JTextField id_number;
	private JButton book;
	private JLabel fno_lable;
	private JLabel economy_money;
	private JLabel business_money;
	private JLabel economy_rest;
	private JLabel business_rest;
	private ButtonGroup buttonGroup;
	private JRadioButton ifEconomy;
	private JRadioButton ifBusiness;
	private JCheckBox ifChild;
	private JCheckBox ifBaby;
	private JTextField tellNum;
	
	//所需的数据
	private DefaultTableModel tableModel;
	public String date;
	private connect conn = new connect();
	private String totalPrice;
	private boolean child = false;
	private boolean baby = false;
	private String idtype;
	private String seatType;
	
	//表的列名与每一行的值
	private Vector<String> columnName = new Vector<String>();
	private Vector<Vector<String>> rowData = new Vector<Vector<String>>();
	
	//构造函数
	public flightFrame(String date,Vector<Vector<String>> rowData) {
		// TODO Auto-generated constructor stub
		this.date = date;
		this.rowData = rowData;
		conn.login();
	}
	
	public void setup() {
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(602,700);
		frame.setLocationRelativeTo(null);
		contentPane = new Mypanel(icon);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		frame.setContentPane(contentPane);
		contentPane.setLayout(null);
		
		//table初始化
		columnName.add("航班号");
		columnName.add("航班公司");
		columnName.add("出发地点");
		columnName.add("到达地点");
		columnName.add("出发时间");
		columnName.add("到达时间");
		table = new JTable(rowData, columnName) {
			public boolean isCellEditable(int row, int column){
				return false;
				}
			};
		tableModel = (DefaultTableModel) table.getModel();
		
		table .getTableHeader().setReorderingAllowed(false);//表头不可移动
		table.getTableHeader().setResizingAllowed(false);//不允许手动改变列宽
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBounds(15, 15, 561, 228);
		scrollPane.setOpaque(false);
		scrollPane.getViewport().setOpaque(false);
		contentPane.add(scrollPane);
		
		splace = new JLabel("出发地点");
		splace.setBounds(41, 265, 81, 21);
		contentPane.add(splace);
		
		eplace = new JLabel("到达地点");
		eplace.setBounds(175, 265, 81, 21);
		contentPane.add(eplace);
		
		stime = new JLabel("出发时间");
		stime.setBounds(357, 265, 81, 21);
		contentPane.add(stime);
		
		etime = new JLabel("到达时间");
		etime.setBounds(495, 265, 81, 21);
		contentPane.add(etime);
		
		JLabel pname_lable = new JLabel("乘客姓名");
		pname_lable.setFont(new Font("楷体",Font.PLAIN,17));
		pname_lable.setBounds(41, 410, 81, 21);
		contentPane.add(pname_lable);
		
		pname = new JTextField();
		pname.setText("刘可欣");
		pname.setBounds(137, 410, 385, 27);
		contentPane.add(pname);
		pname.setColumns(10);
		
		JLabel id_lable = new JLabel("证件类型");
		id_lable.setFont(new Font("楷体",Font.PLAIN,17));
		id_lable.setBounds(41, 460, 81, 21);
		contentPane.add(id_lable);
		
		idType = new JComboBox();
		idType.setModel(new DefaultComboBoxModel(new String[] {"中华人民共和国居民身份证", "中华人民共和国护照", "港澳台出行证"}));
		idType.setBounds(137, 460, 385, 27);
		contentPane.add(idType);
		
		JLabel idnumber_lable = new JLabel("证件号码");
		idnumber_lable.setFont(new Font("楷体",Font.PLAIN,17));
		idnumber_lable.setBounds(41, 515, 81, 21);
		contentPane.add(idnumber_lable);
		
		id_number = new JTextField("421022200011027625");
		id_number.setBounds(137, 515, 385, 27);
		contentPane.add(id_number);
		id_number.setColumns(10);
		
		JButton exit = new JButton("返回");
		exit.setBounds(350,641,100,29);
		exit.setFont(new Font("楷体",Font.PLAIN,17));
		contentPane.add(exit);
		exit.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				new buyTicketMainFrame().setFrame();
				frame.dispose();
			}
		});
		
		book = new JButton("提交");
		book.setBounds(490, 641, 100, 29);
		contentPane.add(book);
		book.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				System.out.println("date" + date);
				if(pname.getText() == null) {
					JOptionPane.showMessageDialog(null, "姓名不能为空", "注意",JOptionPane.WARNING_MESSAGE);
				}
				else if(id_number.getText() == null) {
					JOptionPane.showMessageDialog(null, "证件号不能为空", "注意",JOptionPane.WARNING_MESSAGE);
				}
				else if(tellNum.getText() == null) {
					JOptionPane.showMessageDialog(null, "联系电话不能为空", "注意",JOptionPane.WARNING_MESSAGE);
				}
				else {
					if(child && baby) {
						System.out.println("child and baby");
						double price1 = Double.parseDouble(totalPrice) * 0.5;
						double price2 = Double.parseDouble(totalPrice) * 0.1;
						totalPrice = String.valueOf(Double.parseDouble(totalPrice) + price1 + price2);
					}
					else if(child) {
						System.out.println("child");
						double price = Double.parseDouble(totalPrice) * 0.5;
						totalPrice = String.valueOf(Double.parseDouble(totalPrice) + price);
					}
					else if(baby) {
						System.out.println("baby");
						double price = Double.parseDouble(totalPrice) * 0.1;
						totalPrice = String.valueOf(Double.parseDouble(totalPrice) + price);
					}
					System.out.println("total price:"+totalPrice);
					String name = pname.getText();
					idtype = (String) idType.getSelectedItem();
					String id = id_number.getText();
					String tellphone = tellNum.getText();
					String bool;
					if(child&&baby) {
						bool = "一个儿童和一个婴儿";
					}
					else if(child) {
						bool = "一个儿童";
					}
					else if(baby) {
						bool = "一个婴儿";
					}
					else {
						bool = "否";
					}
					String sno = null;
					conn.searchSno(fno_lable.getText(), date + " " + stime.getText(), seatType);
					try {
						while(conn.seat.next()) {
							sno = conn.seat.getString(1);
						}
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					int n = JOptionPane.showConfirmDialog(null, "您预定的机票信息为:\r\n" + 
							"      " + date + " "+  stime.getText() + "-" + etime.getText() + " " +  splace.getText() + "到" + eplace.getText() + "\r\n" + 
							"      航班号：" + fno_lable.getText() + "\r\n" + 
							"      座位：" + seatType + "舱 " + sno + "\r\n" + 
							"      价格：￥" + totalPrice + "\r\n", 
							"确认机票信息",JOptionPane.YES_NO_OPTION);
					if(n == 1) {
						JOptionPane.showMessageDialog(null, "购买已取消", "购买取消",JOptionPane.ERROR_MESSAGE);
					}
					else if(n == 0) {
						conn.addPassenger(id, fno_lable.getText(), date + " " + stime.getText(), 
									  sno, idtype, name, tellphone, seatType, 
									  bool, totalPrice);
						JOptionPane.showMessageDialog(null, "购买成功");
						JOptionPane.showMessageDialog(null, "您的机票信息为:\r\n" + 
								"      " + date + " "+  stime.getText() + "-" + etime.getText() + " " +  splace.getText() + "到" + eplace.getText() + "\r\n" + 
								"      航班号：" + fno_lable.getText() + "\r\n" + 
								"      座位：" + seatType + "舱 " + sno + "\r\n" + 
								"      价格：￥" + totalPrice + "\r\n" + 
								"祝您旅行愉快！");
					}
				}
			}
		});
		
		fno_lable = new JLabel("航班号");
		fno_lable.setBackground(Color.WHITE);
		fno_lable.setBounds(41, 310, 81, 21);
		contentPane.add(fno_lable);
		
		economy_money = new JLabel("经济舱价格");
		economy_money.setBounds(41, 355, 165, 21);
		contentPane.add(economy_money);
		
		economy_rest = new JLabel("经济舱余量");
		economy_rest.setBounds(175, 310, 137, 21);
		contentPane.add(economy_rest);
		
		business_rest = new JLabel("商务舱余量");
		business_rest.setBounds(357, 310, 137, 21);
		contentPane.add(business_rest);
		
		business_money = new JLabel("商务舱价格");
		business_money.setBounds(357, 355, 164, 21);
		contentPane.add(business_money);
		
		ifEconomy = new JRadioButton("经济舱");
		ifEconomy.setOpaque(false);
		ifEconomy.setBounds(41, 610, 102, 29);
		contentPane.add(ifEconomy);
		
		ifBusiness = new JRadioButton("商务舱");
		ifBusiness.setOpaque(false);
		ifBusiness.setBounds(160, 610, 96, 29);
		contentPane.add(ifBusiness);
		
		buttonGroup = new ButtonGroup();
		buttonGroup.add(ifEconomy);
		buttonGroup.add(ifBusiness);
		
		ifChild = new JCheckBox("带儿童");
		ifChild.setOpaque(false);
		ifChild.setBounds(41, 641, 102, 29);
		contentPane.add(ifChild);
		
		ifBaby = new JCheckBox("带婴儿");
		ifBaby.setOpaque(false);
		ifBaby.setBounds(160, 641, 102, 29);
		contentPane.add(ifBaby);
		
		JLabel label = new JLabel("电话号码");
		label.setBounds(41, 565, 81, 21);
		label.setFont(new Font("楷体",Font.PLAIN,17));
		contentPane.add(label);
		
		tellNum = new JTextField("13697156291");
		tellNum.setBounds(137, 565, 385, 27);
		contentPane.add(tellNum);
		tellNum.setColumns(10);
		
		//对table鼠标单击事件的监听
		table.addMouseListener(new MouseAdapter(){
			public void mouseClicked(MouseEvent event)
			{
				int row = table.rowAtPoint(event.getPoint());
				String fno = (String) tableModel.getValueAt(row, 0);
				String sp = (String) tableModel.getValueAt(row, 2);
				String ep = (String) tableModel.getValueAt(row, 3);
				String st = (String) tableModel.getValueAt(row, 4);
				String et = (String) tableModel.getValueAt(row, 5);
				fno_lable.setText(fno);
				splace.setText(sp);
				eplace.setText(ep);
				stime.setText(st);
				etime.setText(et);
				conn.searchRestAndPrice(fno,date + " " + st);
				economy_rest.setText("经济舱余量" + conn.restOfEconomy);
				business_rest.setText("商务舱余量" + conn.restOfBusiness);
				economy_money.setText("经济舱价格" + conn.economyPrice);
				business_money.setText("商务舱价格" + conn.businessPrice);
			}
		});
		ifChild.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent arg0) {
				// TODO Auto-generated method stub
				if(ifChild.isSelected()) {
					child = true;
				}
				else {
					child = false;
				}
			}
        });
		ifBaby.addChangeListener(new ChangeListener() {
			
			@Override
			public void stateChanged(ChangeEvent e) {
				// TODO Auto-generated method stub
				if(ifBaby.isSelected()) {
					baby = true;
				}
				else {
					baby = false;
				}
			}
		});
		ifEconomy.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				// TODO Auto-generated method stub
				totalPrice = conn.economyPrice.substring(1);
				seatType = "经济";
			}
		});
		ifBusiness.addItemListener(new ItemListener() {
			
			@Override
			public void itemStateChanged(ItemEvent e) {
				// TODO Auto-generated method stub
				totalPrice = conn.businessPrice.substring(1);
				seatType = "商务";
			}
		});
		idType.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				idtype = (String)idType.getSelectedItem();
			}
		});
		setFont();
		frame.setVisible(true);
	}
	
	//设置字体
	private void setFont(){
		book.setFont(new Font("楷体",Font.PLAIN,17));
		splace.setFont(new Font("楷体",Font.PLAIN,17));
		eplace.setFont(new Font("楷体",Font.PLAIN,17));
		table.setFont(new Font("楷体",Font.PLAIN,17));
		stime.setFont(new Font("楷体",Font.PLAIN,17));
		etime.setFont(new Font("楷体",Font.PLAIN,17));
		idType.setFont(new Font("楷体",Font.PLAIN,17));
		pname.setFont(new Font("楷体",Font.PLAIN,17));
		id_number.setFont(new Font("楷体",Font.PLAIN,17));
		fno_lable.setFont(new Font("楷体",Font.PLAIN,17));
		economy_money.setFont(new Font("楷体",Font.PLAIN,17));
		business_money.setFont(new Font("楷体",Font.PLAIN,17));
		economy_rest.setFont(new Font("楷体",Font.PLAIN,17));
		business_rest.setFont(new Font("楷体",Font.PLAIN,17));
		ifEconomy.setFont(new Font("楷体",Font.PLAIN,17));
		ifBusiness.setFont(new Font("楷体",Font.PLAIN,17));
		ifBaby.setFont(new Font("楷体",Font.PLAIN,17));
		ifChild.setFont(new Font("楷体",Font.PLAIN,17));
		tellNum.setFont(new Font("楷体",Font.PLAIN,17));
	}
}
