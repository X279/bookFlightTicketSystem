package connectDatabase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.OracleTypes;

public class connect {
	//连接数据库
	private Connection conn;
	private String user = "lkx";
	private String password = "123456";
	//航班信息
	public Vector<Vector<String>> flightInfo = new Vector<Vector<String>>();
	//乘客机票信息
	public Vector<Vector<String>> ticketInfo = new Vector<Vector<String>>();
	//机票价格及余量
	public String restOfEconomy;
	public String restOfBusiness;
	public String economyPrice;
	public String businessPrice;
	public ResultSet seat;
	
	public void login() {
		try {
			//加载数据库驱动
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		String DBUrl = "jdbc:oracle:thin:@localhost:1521:lkx";
		try {
			conn = DriverManager.getConnection(DBUrl,user,password);
			System.out.println("连接成功");
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	public void setUserAndKey(String user,String password) {
		this.user = user;
		this.password = password;
	}
	/**
	 * 查询当天航班信息
	 * @param splace 出发地点
	 * @param eplace 到达地点
	 * @param stime 出发日期
	 */
	public void getInformation(String splace,String eplace,String stime) {
		try {
			conn.setAutoCommit(false);
			OracleCallableStatement cstmt = (OracleCallableStatement)conn.prepareCall("{call get_flightInformation(?,?,?,?,?)");
			cstmt.setString(1, splace);
			cstmt.setString(2,eplace);
			cstmt.setString(3, stime + " 00:00");
			cstmt.setString(4, stime + " 23:59");
			cstmt.registerOutParameter(5, OracleTypes.CURSOR);
			cstmt.execute();
			ResultSet rs = (ResultSet)cstmt.getObject(5);
			while (rs.next()) {
				Vector<String> info = new Vector<String>();
				info.add(rs.getString(1));
				info.add(rs.getString(2));
				info.add(rs.getString(3));
				info.add(rs.getString(4));
				info.add(rs.getString(5).substring(11,16));
				info.add(rs.getString(6).substring(11,16));
				flightInfo.add(info);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	/**
	 * 查询某天某班航班的座位余量
	 * @param fno 航班号
	 * @param stime 出发时间
	 * @param seatType 舱位类型
	 */
	private void searchRest(String fno,String stime,String seatType) {
		try {
			OracleCallableStatement cstmt = (OracleCallableStatement)conn.prepareCall("{call searchRestOfSeat(?,?,?,?)");
			cstmt.setString(1, fno);
			cstmt.setString(2,stime);
			cstmt.setString(3, seatType);
			cstmt.registerOutParameter(4, OracleTypes.INTEGER);
			cstmt.execute();
			if(seatType.equals("经济"))
				restOfEconomy = String.valueOf(cstmt.getInt(4));
			else
				restOfBusiness = String.valueOf(cstmt.getInt(4));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * 查询当天该航班的价格
	 * @param fno 航班号
	 * @param stime 出发日期
	 * @param seatType 座位类型
	 */
	private void searchPrice(String fno,String stime,String seatType) {
		try {
			OracleCallableStatement cstmt = (OracleCallableStatement)conn.prepareCall("{call searchPrice(?,?,?,?)");
			cstmt.setString(1, fno);
			cstmt.setString(2,stime);
			cstmt.setString(3, seatType);
			cstmt.registerOutParameter(4, OracleTypes.CURSOR);
			cstmt.execute();
			ResultSet rs = (ResultSet)cstmt.getObject(4);
			while(rs.next()) {
				if(seatType.equals("经济"))
					economyPrice = rs.getString(1);
				else
					businessPrice = rs.getString(1);
				break;
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void searchRestAndPrice(String fno,String stime) {
		searchRest(fno, stime, "经济");
		searchRest(fno, stime, "商务");
		searchPrice(fno, stime, "经济");
		searchPrice(fno, stime, "商务");
	}
	
	/**
	 * 用户购买机票之后，向数据库里的乘客信息表里添加信息
	 * @param ID 乘客证件号码
	 * @param Fno 航班号
	 * @param stime 出发时间
	 * @param sno 座位号
	 * @param idType 证件类型
	 * @param name 乘客姓名
	 * @param tellphone 乘客电话号码
	 * @param seatType 座位类型
	 * @param babyOrchild 是否携带婴儿或儿童
	 * @param price 支付金额
	 */
	public void addPassenger(String ID,String Fno,String stime,String sno,String idType,
			String name,String tellphone,String seatType,String babyOrchild,String price) {
		try {
			OracleCallableStatement cstmt = (OracleCallableStatement)conn.prepareCall("{call addPassengerInformation(?,?,?,?,?,?,?,?,?,?)");
			cstmt.setString(1, ID);
			cstmt.setString(2, Fno);
			cstmt.setString(3, stime);
			cstmt.setString(4, sno);
			cstmt.setString(5, idType);
			cstmt.setString(6, name);
			cstmt.setString(7,tellphone);
			cstmt.setString(8, seatType);
			cstmt.setString(9, babyOrchild);
			cstmt.setString(10, "￥"+price);
			cstmt.execute();
			updateRestOfSeat(Fno,stime,sno,"已售");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	/**
	 * 乘客买票之后还要更新机票余量
	 * 乘客退票之后也需要更新机票余量
	 * @param Fno 航班号
	 * @param stime 出发时间
	 * @param sno 座位类型
	 */
	private void updateRestOfSeat(String Fno,String stime,String sno,String state) {
		try {
			OracleCallableStatement cstmt = (OracleCallableStatement)conn.prepareCall("{call updateRestOfTicket(?,?,?,?)");
			cstmt.setString(1, Fno);
			cstmt.setString(2, stime);
			cstmt.setString(3, sno);
			cstmt.setString(4, state);
			cstmt.execute();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	/**
	 * 查询该名乘客的所有机票信息
	 * @param name
	 * @param idNum
	 */
	public void searchPassenger(String name,String idNum) {
		try {
			OracleCallableStatement cstmt = (OracleCallableStatement)conn.prepareCall("{call searchPassengerInformation(?,?,?)");
			cstmt.setString(1, name);
			cstmt.setString(2, idNum);
			cstmt.registerOutParameter(3, OracleTypes.CURSOR);
			cstmt.execute();
			ResultSet rs = (ResultSet)cstmt.getObject(3);
			while (rs.next()) {
				Vector<String> info = new Vector<String>();
				info.add(rs.getString(1));
				info.add(rs.getString(2));
				info.add(rs.getString(3));
				info.add(rs.getString(4));
				info.add(rs.getString(5));
				info.add(rs.getString(6));
				info.add(rs.getString(7));
				info.add(rs.getString(8));
				for(String s:info) {
					System.out.print(s + " ");
					System.out.println();
				}
				ticketInfo.add(info);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * 乘客退票后需要从乘客信息表中删除乘客信息
	 * @param idNum 证件号码
	 * @param stime 出发时间
	 */
	public void deletePassengerInformation(String fno,String sno,String idNum,String stime) {
		try {
			System.out.println("退票中...");
			OracleCallableStatement cstmt = (OracleCallableStatement)conn.prepareCall("{call deletePassengerInformation(?,?)");
			cstmt.setString(1, idNum);
			cstmt.setString(2, stime);
			cstmt.execute();
			updateRestOfSeat(fno, stime, sno, "待售");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	/**
	 * 管理员新增航班信息
	 * @param fno
	 * @param fname
	 * @param splace
	 * @param eplace
	 * @param stime
	 * @param etime
	 */
	public boolean addFlightInformation(String fno,String fname,String splace,String eplace,
										String stime,String etime,String economyPrice,String businessPrice) {
		try {
			OracleCallableStatement cstmt = (OracleCallableStatement)conn.prepareCall("{call addFlightInformation(?,?,?,?,?,?)");
			cstmt.setString(1, fno);
			cstmt.setString(2, fname);
			cstmt.setString(3, splace);
			cstmt.setString(4, eplace);
			cstmt.setString(5, stime);
			cstmt.setString(6, etime);
			cstmt.execute();
			//添加经济舱座位
			for(int i = 1;i<=150;i++) {
				String sno;
				sno = "J" + i;
				addTicketInformation(fno, stime, sno, "经济", economyPrice);
			}
			//添加商务舱座位
			for(int i = 1;i<=150;i++) {
				String sno;
				sno = "S" + i;
				addTicketInformation(fno, stime, sno, "商务", businessPrice);
			}
			System.out.println("添加成功");
			return true;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * 管理人员增加航班信息后，自动每一班航班的座位信息添加
	 * @param fno
	 * @param stime
	 * @param sno
	 * @param type
	 * @param price
	 */
	private void addTicketInformation(String fno,String stime,String sno,String type,String price) {
		try {
			OracleCallableStatement cstmt = (OracleCallableStatement)conn.prepareCall("{call addTicketInformation(?,?,?,?,?)");
			cstmt.setString(1, fno);
			cstmt.setString(2, stime);
			cstmt.setString(3, sno);
			cstmt.setString(4, type);
			cstmt.setString(5, price);
			cstmt.execute();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	/**
	 * 查询状态为待售的经济舱或者商务舱的座位号
	 * @param fno
	 * @param stime
	 * @param type
	 */
	public void searchSno(String fno,String stime,String type) {
		try {
			OracleCallableStatement cstmt = (OracleCallableStatement)conn.prepareCall("{call searchSno(?,?,?,?)");
			cstmt.setString(1, fno);
			cstmt.setString(2, stime);
			cstmt.setString(3, type);
			cstmt.registerOutParameter(4, OracleTypes.CURSOR);
			cstmt.execute();
			seat = (ResultSet)cstmt.getObject(4);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public boolean addRefundInformation(String order,String idnum,String name,String tell) {
		try {
			OracleCallableStatement cstmt = (OracleCallableStatement)conn.prepareCall("{call addInformationIntoRefund(?,?,?,?)");
			cstmt.setString(1, order);
			cstmt.setString(2, idnum);
			cstmt.setString(3, name);
			cstmt.setString(4, tell);
			cstmt.execute();
			return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * 查询从某地到某地的所有航班信息
	 * @param splace
	 * @param eplace
	 */
	public void searchAllFlightInformation(String splace,String eplace) {
		try {
			OracleCallableStatement cstmt = (OracleCallableStatement)conn.prepareCall("{call getFlightInformation(?,?,?)");
			cstmt.setString(1, splace);
			cstmt.setString(2, eplace);
			cstmt.registerOutParameter(3, OracleTypes.CURSOR);
			cstmt.execute();
			ResultSet rs = (ResultSet)cstmt.getObject(3);
			while(rs.next()) {
				Vector<String> info = new Vector<String>();
				info.add(rs.getString(1));
				info.add(rs.getString(2));
				info.add(rs.getString(3));
				info.add(rs.getString(4));
				info.add(rs.getString(5).substring(0,16));
				info.add(rs.getString(6).substring(0,16));
				for(String s:info) {
					System.out.print(s + " ");
				}
				System.out.println();
				flightInfo.add(info);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * 更新乘客信息表
	 * @param id
	 * @param beforeStime
	 * @param beforeFno
	 * @param beforeSno
	 * @param fno
	 * @param stime
	 * @param sno
	 * @param type
	 * @param ifbabyorchild
	 * @param price
	 */
	public void updatePassengerInformaiton(String id,String beforeStime,String beforeFno,String beforeSno,
											String fno,String stime,String sno,String type,
											String ifbabyorchild,String price) {
		try {
			OracleCallableStatement cstmt = (OracleCallableStatement)conn.prepareCall("{call updatePassengerInfomation(?,?,?,?,?,?,?,?)");
			cstmt.setString(1, id);
			cstmt.setString(2, beforeStime);
			cstmt.setString(3, fno);
			cstmt.setString(4, stime);
			cstmt.setString(5, sno);
			cstmt.setString(6, type);
			cstmt.setString(7, ifbabyorchild);
			cstmt.setString(8, price);
			cstmt.execute();
			updateRestOfSeat(fno, stime, sno,"已售");
			updateRestOfSeat(beforeFno, beforeStime, beforeSno, "待售");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * 增加改签信息表信息
	 * @param order
	 * @param idnum
	 * @param oldfno
	 * @param newfno
	 * @param oldsno
	 * @param newsno
	 * @param name
	 */
	public void addChangeInformation(String order,String idnum,String oldfno,String newfno,String oldsno,String newsno,String name)
	{
		try {
			OracleCallableStatement cstmt = (OracleCallableStatement)conn.prepareCall("{call addChangeInformation(?,?,?,?,?,?,?)");
			cstmt.setString(1, order);
			cstmt.setString(2, idnum);
			cstmt.setString(3, oldfno);
			cstmt.setString(4, newfno);
			cstmt.setString(5, oldsno);
			cstmt.setString(6, newsno);
			cstmt.setString(7, name);
			cstmt.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
