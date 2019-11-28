package Frame;

import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import java.util.Date;

import com.eltima.components.ui.DatePicker;

import connectDatabase.connect;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Locale;

public class buyTicketMainFrame {
	
	private JFrame myJFrame = new JFrame("查询机票");
	private DatePicker datePicker;
	private JButton serach = new JButton("查询机票");
	private JButton choose = new JButton("确定");
	private JButton exit = new JButton("返回");
	private JComboBox<String> spComboBox;
	private JComboBox<String> epComboBox;
	private ImageIcon icon = new ImageIcon("pictures\\预订.png");
	private Mypanel panel = new Mypanel(icon);
	//数据库查询
	private connect conn;
	
	//记录顾客选择的起始地点和时间
	private String startPlace;
	private String endPlace;
	private Date datetime;
	
	//得到日期
	private static DatePicker getDatePicker() {
		DatePicker datepick;
        // 格式
        String DefaultFormat = "yyyy-MM-dd";
        // 当前时间
        Date date = new Date();
        // 字体
        Font font = new Font("Times New Roman", Font.BOLD, 14);

        Dimension dimension = new Dimension(177, 24);
        //构造方法（初始时间，时间显示格式，字体，控件大小）
        datepick = new DatePicker(date, DefaultFormat, font, dimension);

        datepick.setLocation(137, 83);//设置起始位置

        // 设置国家为中国
        datepick.setLocale(Locale.CHINA);
        // 设置时钟面板可见
        datepick.setTimePanleVisible(true);
        return datepick;
	}
	
	public void setFrame() {
		myJFrame.setTitle("机票查询系统");
		panel.setLayout(null);
		//实例化日期选择器
		datePicker = getDatePicker();
		JLabel lblNewLabel = new JLabel("出发城市");
		JLabel lblNewLabel_1 = new JLabel("到达城市");
		
		spComboBox = new JComboBox<String>();
		spComboBox.setModel(new DefaultComboBoxModel<String>(new String[] {"北京", "天津", "上海", "深圳", "重庆", "武汉", "广州", "香港（中国）"}));
		spComboBox.setEditable(true);
		spComboBox.setBounds(75, 142, 107, 27);
		
		epComboBox = new JComboBox<String>();
		epComboBox.setModel(new DefaultComboBoxModel<String>(new String[] {"上海", "天津", "北京", "深圳", "武汉", "重庆", "香港（中国）"}));
		epComboBox.setEditable(true);
		epComboBox.setBounds(485, 142, 107, 27);

		JLabel label = new JLabel("去往");
		lblNewLabel.setFont( new Font("楷体",Font.PLAIN,17));
		lblNewLabel_1.setFont( new Font("楷体",Font.PLAIN,17));
		label.setFont( new Font("楷体",Font.PLAIN,17));
		spComboBox.setFont( new Font("楷体",Font.PLAIN,17));
		epComboBox.setFont( new Font("楷体",Font.PLAIN,17));
		datePicker.setFont( new Font("楷体",Font.PLAIN,17));
		choose.setFont( new Font("楷体",Font.PLAIN,17));
		exit.setFont( new Font("楷体",Font.PLAIN,17));
		serach.setFont( new Font("楷体",Font.PLAIN,17));
		
		panel.add(lblNewLabel);
		panel.add(lblNewLabel_1);
		panel.add(label);
		panel.add(spComboBox);
		panel.add(epComboBox);
		panel.add(datePicker);
		panel.add(choose);
		panel.add(serach);
		panel.add(exit);
		
		lblNewLabel.setBounds(84, 88, 81, 21);
		label.setBounds(302, 88, 81, 21);
		lblNewLabel_1.setBounds(506, 88, 81, 21);
		datePicker.setBounds(100, 240, 150, 30);
		choose.setBounds(400,240,150,30);
		serach.setBounds(100,340,150,30);
		exit.setBounds(400,340,150,30);
		
		myJFrame.add(panel);
		setListener();
		myJFrame.setSize(709, 485);
		myJFrame.setResizable(false);
		myJFrame.setLocationRelativeTo(null);
		myJFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		myJFrame.setVisible(true);
	}
	
	private void setListener() {
		/**
		 * 点击选择时间，得到用户选择的想购买机票的时间
		 */
		choose.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				datetime = (Date) datePicker.getValue();
			}
		});
		/**
		 * 在这里得到所有的信息，包括时间，出发地点以及到达地点，连接数据库开始查询
		 */
		serach.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				//得到信息
				startPlace = (String)spComboBox.getEditor().getItem();
				endPlace = (String)epComboBox.getEditor().getItem();
				datetime = (Date)datePicker.getValue();
				String str = datetime.toString();
				String[] s = str.split(" ");
				String year = s[5];
				String month = transToChinese(s[1]);
				String day = s[2];
				String week = transToChinese(s[0]);
				System.out.println("您选择了" + year + "年" 
										+ month + "月" + day + "日" + week 
										+ "从" + startPlace + "到" + endPlace + "的机票");
				//查询，返回一个map,map的元素值是一个list,每一个list代表返回的结果集中的一行
				conn = new connect();
				conn.login();
				conn.getInformation(startPlace,endPlace,year+"-"+month+"-"+day);
				flightFrame flightFrame = new flightFrame(year+"-"+month+"-"+day,conn.flightInfo);
				flightFrame.setup();
				myJFrame.dispose();
			}
		});
		exit.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				main main = new main();
				main.init();
				myJFrame.dispose();
			}
		});
	}
	
	/**
	 * 将英文缩写表达的月份和星期转换为需要的表达
	 */
	private String transToChinese(String str) {
		switch(str) {
		case "Jan":
			return "01";
		case "Feb":
			return "02";
		case "Mar":
			return "03";
		case "Apr":
			return "04";
		case "May":
			return "05";
		case "Jun":
			return "06";
		case "Jul":
			return "07";
		case "Aug":
			return "08";
		case "Sept":
			return "09";
		case "Oct":
			return "10";
		case "Nov":
			return "11";
		case "Dec":
			return "12";
		case "Mon":
			return "星期一";
		case "Tue":
			return "星期二";
		case "Web":
			return "星期三";
		case "Thu":
			return "星期四";
		case "Fri":
			return "星期五";
		case "Sat":
			return "星期六";
		case "Sun":
			return "星期日";
			default:
				return "非法表达";
		}
	}
}
