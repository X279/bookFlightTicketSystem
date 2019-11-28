package Frame;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import connectDatabase.connect;

public class admiLogin extends JFrame{

	private ImageIcon icon = new ImageIcon("pictures\\预订.png");
	private Mypanel panel = new Mypanel(icon);
	private JTextField admin;
	private JPasswordField password;
	
	public void setFrame() {
		add(panel);
		setSize(709, 485);
		setResizable(false);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		panel.setLayout(null);
		
		JLabel label_0 = new JLabel("管理员登录");
		label_0.setFont( new Font("楷体",Font.BOLD,38) );
		label_0.setForeground(Color.BLACK);
		label_0.setBounds(100, 20,250, 60);	
		panel.add(label_0);
		
		JLabel label = new JLabel("账号");
		label.setFont(new Font("楷体",Font.PLAIN,17));
		label.setForeground(Color.BLACK);
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setBounds(47, 150, 81, 21);
		panel.add(label);
		
		admin = new JTextField("lkx");
		admin.setFont(new Font("楷体",Font.PLAIN,17));
		admin.setBounds(148, 150, 450, 27);
		panel.add(admin);
		admin.setColumns(10);
		
		JLabel label_1 = new JLabel("密码");
		label_1.setFont(new Font("楷体",Font.PLAIN,17));
		label_1.setHorizontalAlignment(SwingConstants.CENTER);
		label_1.setForeground(Color.BLACK);
		label_1.setBounds(47, 250, 81, 21);
		panel.add(label_1);
		
		password = new JPasswordField("123456");
		password.setEchoChar('*');
		password.setFont(new Font("楷体",Font.PLAIN,17));
		password.setBounds(148, 250, 450, 27);
		panel.add(password);
		password.setColumns(10);
		
		JButton login = new JButton("登录");
		login.setFont(new Font("楷体",Font.PLAIN,17));
		login.setBounds(200,350,100,29);
		login.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				String user = admin.getText();
				String key = password.getText();
				connect conn = new connect();
				conn.setUserAndKey(user, key);
				conn.login();
				new insertDataFrame().setFrame(conn);
				dispose();
			}
		});
		panel.add(login);
		
		JButton exit = new JButton("返回");
		exit.setFont(new Font("楷体",Font.PLAIN,17));
		exit.setBounds(450,350,100,29);
		exit.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				new main().init();
				dispose();
			}
		});
		panel.add(exit);
		setVisible(true);
	}
}
