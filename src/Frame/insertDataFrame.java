package Frame;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import connectDatabase.connect;

public class insertDataFrame extends JFrame{

	private Mypanel contentPane;
	private JTable table;
	private Vector<Vector<String>> rowData = new Vector<Vector<String>>();
	private DefaultTableModel tableModel;
	private JScrollPane scrollPane;
	private ImageIcon icon = new ImageIcon("pictures\\界面背景.png");
	private int row = 0;
	
	public void setFrame(connect conn) {
		setTitle("管理航班信息");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(930, 550);
		setLocationRelativeTo(null);
		setResizable(false);
		contentPane = new Mypanel(icon);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		Vector<String> columnName = new Vector<String>();
		columnName.add("航班号");
		columnName.add("航班名称");
		columnName.add("出发地点");
		columnName.add("到达地点");
		columnName.add("出发时间");
		columnName.add("到达时间");
		columnName.add("经济舱价格");
		columnName.add("商务舱价格");
		
		tableModel = new DefaultTableModel(rowData,columnName);
		table = new JTable(tableModel);
		table.setFont(new Font("楷体",Font.PLAIN,17));
		table .getTableHeader().setReorderingAllowed(false);//表头不可移动
		table.getTableHeader().setResizingAllowed(false);//不允许手动改变列宽
		
		scrollPane = new JScrollPane(table);
		scrollPane.setOpaque(false);
		scrollPane.getViewport().setOpaque(false);
		scrollPane.setFont(new Font("楷体",Font.PLAIN,17));
		scrollPane.setBounds(15, 15, 890, 292);
		contentPane.add(scrollPane);
		
		JButton add = new JButton("增加行");
		add.setFont(new Font("楷体",Font.PLAIN,17));
		add.setBounds(650,360,100,29);
		add.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				row += 1;
				Vector<String> data = new Vector<String>();
				rowData.add(data);
				tableModel = new DefaultTableModel(rowData,columnName);
				table.setModel(tableModel);
			}
		});
		contentPane.add(add);
		
		JButton subtract = new JButton("减少行");
		subtract.setFont(new Font("楷体",Font.PLAIN,17));
		subtract.setBounds(800,360,100,29);
		subtract.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(row == 0) {
					JOptionPane.showMessageDialog(null, "无法删除", "错误",JOptionPane.WARNING_MESSAGE);
				}
				else {
					rowData.remove(row-1);
					tableModel = new DefaultTableModel(rowData,columnName);
					table.setModel(tableModel);
					row -= 1;
				}
			}
		});
		contentPane.add(subtract);
		
		JButton insert = new JButton("确认添加");
		insert.setFont(new Font("楷体",Font.PLAIN,17));
		insert.setBounds(650,400,100,29);
		insert.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				boolean rs = false;
				for(int i = 0;i<rowData.size();i++) {
					String fno = rowData.get(i).get(0);
					String fname = rowData.get(i).get(1);
					String sp = rowData.get(i).get(2);
					String ep = rowData.get(i).get(3);
					String st = rowData.get(i).get(4);
					String et = rowData.get(i).get(5);
					String ecoPrice = rowData.get(i).get(6);
					String busPrice = rowData.get(i).get(7);
					connect conn1 = new connect();
					conn1.login();
					conn1.addFlightInformation(fno, fname, sp, ep, st, et, ecoPrice, busPrice);
				}
//				if(rs) {
//						JOptionPane.showConfirmDialog(null,"添加成功！","成功",JOptionPane.YES_OPTION);
//					}
//					else {
//						JOptionPane.showMessageDialog(null, "添加失败", "失败",JOptionPane.ERROR_MESSAGE);
//					}
			}
		});
		contentPane.add(insert);
		
		JButton exit = new JButton("返回");
		exit.setFont(new Font("楷体",Font.PLAIN,17));
		exit.setBounds(800,400,100,29);
		exit.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				new admiLogin().setFrame();
				dispose();
			}
		});
		contentPane.add(exit);
		
		setVisible(true);
	}
}