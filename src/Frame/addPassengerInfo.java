package Frame;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.GroupLayout.Alignment;
import javax.swing.border.EmptyBorder;

public class addPassengerInfo {
	
	private JFrame frame = new JFrame("添加出行人信息");
	private JPanel contentPane;
	//证件号码与出行人姓名编辑框
	private JTextField cerNum = new JTextField();
	private JTextField name = new JTextField();
	
	private JComboBox certificatesType = new JComboBox();
	private JButton confirm = new JButton("确认");
	//出行人信息
	private String idNum;
	private String passengerName;
	
	public void setFrame() {
		frame.setTitle("添加出行人信息");
		frame.setLocationRelativeTo(null);
		frame.setBounds(100, 100, 466, 275);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		frame.setContentPane(contentPane);
		//标签
		JLabel label = new JLabel("证件类型");
		label.setFont(new Font("宋体", Font.PLAIN, 15));
		JLabel label_1 = new JLabel("证件号码*");
		label_1.setFont(new Font("宋体", Font.PLAIN, 15));
		JLabel label_2 = new JLabel("出行人姓名");
		label_2.setFont(new Font("宋体", Font.PLAIN, 15));
		
		cerNum.setColumns(10);
		name.setColumns(10);
		cerNum.setFont(new Font("宋体", Font.PLAIN, 15));
		name.setFont(new Font("宋体", Font.PLAIN, 15));
		confirm.setFont(new Font("宋体", Font.PLAIN, 15));
		
		certificatesType.setFont(new Font("宋体", Font.PLAIN, 15));
		certificatesType.addItem("中华人民共和国居民身份证");
		certificatesType.addItem("港澳台通行证");
		certificatesType.addItem("护照");
		
		confirm.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				idNum = cerNum.getText();
				passengerName = name.getText();
				frame.dispose();
			}
		});
		//布局
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(26)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addComponent(label)
						.addComponent(label_1)
						.addComponent(label_2))
					.addGap(18)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING, false)
						.addComponent(name)
						.addComponent(cerNum)
						.addComponent(certificatesType, 0, 300, Short.MAX_VALUE))
					.addContainerGap(24, Short.MAX_VALUE))
				.addGroup(Alignment.TRAILING, gl_contentPane.createSequentialGroup()
					.addContainerGap(320, Short.MAX_VALUE)
					.addComponent(confirm)
					.addGap(69))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(41)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(label)
						.addComponent(certificatesType, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(label_1)
						.addComponent(cerNum, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(label_2)
						.addComponent(name, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addComponent(confirm)
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		contentPane.setLayout(gl_contentPane);
		frame.setVisible(true);
	}
	
	public String getIDNum() {
		return this.idNum;
	}
	
	public String getName() {
		return this.passengerName;
	}
}
