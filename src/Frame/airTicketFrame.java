package Frame;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.EmptyBorder;

public class airTicketFrame implements ActionListener ,ItemListener{
	private JFrame frame = new JFrame("机票购买");
	private JPanel contentPane;
	//机票信息的显示，包括出行时间，航班号，座位类型，价格
	private JLabel month_day = new JLabel("11-02");
	private JLabel time = new JLabel("11:55-14:15");
	private JLabel splace = new JLabel("武汉");
	private JLabel eplace = new JLabel("北京");
	private JLabel seat = new JLabel("经济舱");
	private JLabel money = new JLabel("￥2670");
	private JLabel fno = new JLabel("WU8207");
	//旅客身份信息以及注意事项
	private JTextArea info = new JTextArea();
	private JTextArea attention = new JTextArea();
	//购票按钮、添加出行人信息按钮
	private JButton addInfo = new JButton("添加出行人信息");
	private JButton buy = new JButton("购买");
	//选择是否带儿童或者婴儿
	private JCheckBox ifChild = new JCheckBox("带儿童");
	private JCheckBox ifBaby = new JCheckBox("带婴儿");
	//text的滚动条
	private JScrollPane scrollPane = new JScrollPane(info,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
	private JScrollPane scrollPane1 = new JScrollPane(attention,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
	//携带儿童婴儿注意事项
	private String childAttention = "携带儿童所需注意事项：\r\n" + 
			"年龄规定：2岁（含）~12岁（不含）\r\n" +
			"票价：成人普通票价的50%，免机场管理建设费，燃油费为成人的50%;\r\n" +
			"证件：身份证、护照、户口簿\r\n" +
			"每位成人旅客（满18周岁）最多携带2名儿童;\r\n" +
			"儿童旅客必须有同舱位成人（满18周岁）陪伴乘机，无成人陪伴儿童请提前联系航空公司进行预订。";
	private String babyAttention = "携带婴儿注意事项：\r\n" + 
			"年龄规定：14天（含）~2岁（不含）\r\n" + 
			"票价：成人普通票价的10%，免机场管理建设费、燃油费;\r\n" + 
			"证件：身份证、护照、户口簿、出生证明\r\n" + 
			"每位成人旅客最多携带1名婴儿;\r\n" + 
			"婴儿票不提供座位，如需单独占用座位，可为婴儿购买儿童票;";
	
	public airTicketFrame(String time,String splaceString,String eplaceString,String seat) {
		this.time.setText(time);
		this.splace.setText(splaceString);
		this.eplace.setText(eplaceString);
		this.seat.setText(seat);
	}
	
	//界面初始化
	public void setFrame() {
		frame.setResizable(false);
		frame.setBounds(100, 100, 850, 590);
		frame.setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		frame.setContentPane(contentPane);
		
		month_day.setFont(new Font("宋体", Font.PLAIN, 15));
		money.setFont(new Font("宋体", Font.PLAIN, 15));
		time.setFont(new Font("宋体", Font.PLAIN, 15));
		fno.setFont(new Font("宋体", Font.PLAIN, 15));
		seat.setFont(new Font("宋体", Font.PLAIN, 15));
		eplace.setFont(new Font("宋体", Font.PLAIN, 15));
		splace.setFont(new Font("宋体", Font.PLAIN, 15));
		ifChild.setFont(new Font("宋体", Font.PLAIN, 15));
		ifBaby.setFont(new Font("宋体", Font.PLAIN, 15));
		
		info.setText("出行人信息：");
		info.setFont(new Font("宋体", Font.PLAIN, 17));
		info.setEditable(false);
		
		attention.setFont(new Font("宋体", Font.PLAIN, 17));
		attention.setEditable(false);
		attention.setText("注意事项：");
		
		buy.addActionListener(this);
		addInfo.addActionListener(this);
		
		ifChild.addItemListener(this);
		ifBaby.addItemListener(this);
		
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(60)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING, false)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(scrollPane1, GroupLayout.PREFERRED_SIZE, 586, GroupLayout.PREFERRED_SIZE)
							.addGap(18)
							.addComponent(buy, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(ifChild)
							.addGap(18)
							.addComponent(ifBaby))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 579, GroupLayout.PREFERRED_SIZE)
							.addGap(18)
							.addComponent(addInfo))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(month_day)
							.addGap(35)
							.addComponent(time)
							.addGap(35)
							.addComponent(splace)
							.addGap(35)
							.addComponent(eplace)
							.addGap(35)
							.addComponent(seat)
							.addGap(35)
							.addComponent(money)
							.addGap(35)
							.addComponent(fno)))
					.addGap(18))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(30)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
						.addComponent(addInfo)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addComponent(month_day)
								.addComponent(time)
								.addComponent(splace)
								.addComponent(eplace)
								.addComponent(seat)
								.addComponent(money)
								.addComponent(fno))
							.addGap(18)
							.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 263, GroupLayout.PREFERRED_SIZE)))
					.addGap(27)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
								.addComponent(ifChild)
								.addComponent(ifBaby))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(scrollPane1, GroupLayout.PREFERRED_SIZE, 124, GroupLayout.PREFERRED_SIZE))
						.addComponent(buy))
					.addGap(75))
		);
		contentPane.setLayout(gl_contentPane);
		frame.setVisible(true);
	}
	
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		if(arg0.getSource() == buy) {
			System.out.println("购买机票");
		}
		else if(arg0.getSource() == addInfo){
			System.out.println("添加出行人信息");
		}
	}


	@Override
	public void itemStateChanged(ItemEvent arg0) {
		// TODO Auto-generated method stub
		if(arg0.getSource() == ifChild) {
			if(ifChild.isSelected()) {
				System.out.println("选择添加儿童");
				attention.append(childAttention);
			}
			else {
				if(attention.getText().contains(childAttention)) {
					attention.setText(attention.getText().replace(childAttention, ""));
				}
			}
		}
		else if(arg0.getSource() == ifBaby) {
			if(ifBaby.isSelected()) {
				System.out.println("选择添加婴儿");
				attention.append(babyAttention);
			}
			else {
				if(attention.getText().contains(babyAttention)) {
					attention.setText(attention.getText().replace(babyAttention, ""));
				}
			}
		}
	}

}
