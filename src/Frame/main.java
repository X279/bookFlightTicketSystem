package Frame;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

/**
 * 
 * @author Lenovo
 * 主菜单，程序入口
 *
 */
public class main {

	JFrame frame = new JFrame("主菜单");
	JButton bt[] = new JButton[5];
	Mypanel panel;
	JLabel label = new JLabel("机票预订系统");
	ImageIcon bigicon;
	
	public void init() {
		//初始化主界面背景
		bigicon = new ImageIcon("pictures\\主界面.jpg");
		panel = new Mypanel(bigicon);
		bt[1] = new JButton("机票预订");
		bt[2] = new JButton("退票/改签");
		bt[3] = new JButton("退出系统");
		bt[4] = new JButton("添加数据");
		panel.setLayout(null);
		panel.add(bt[1]);
		panel.add(bt[2]);
		panel.add(bt[3]);
		panel.add(bt[4]);
		panel.add(label);
		
		label.setFont( new Font("楷体",Font.BOLD,38) );
		bt[1].addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				new buyTicketMainFrame().setFrame();
				frame.dispose();
			}
		});
		
		bt[2].addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				new tuipiaoFrame().setup();
				frame.dispose();
			}
		});
		
		
		
		bt[3].addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				System.exit(0);
			}
		});
		
		bt[4].addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				new admiLogin().setFrame();
				frame.dispose();
			}
		});
		bt[1].setBounds(40, 330, 120, 50);	
		bt[2].setBounds(190, 330, 120, 50);	
		bt[3].setBounds(340,330,120,50);
		bt[4].setBounds(490, 330, 120, 50);
		bt[1].setFont( new Font("楷体",Font.PLAIN,17) );
		bt[2].setFont( new Font("楷体",Font.PLAIN,17) );
		bt[3].setFont( new Font("楷体",Font.PLAIN,17) );
		bt[4].setFont( new Font("楷体",Font.PLAIN,17) );
		label.setBounds(200, 20,250, 60);	
		
		frame.add(panel);
		frame.setSize(650,430);
		frame.setVisible(true);
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new main().init();
	}

}
