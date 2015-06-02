package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import bean.User;
import exception.RegistryException;

public class Dao {
	private static String driver = "com.mysql.jdbc.Driver";
	String url = "jdbc:mysql://127.0.0.1:3306/Shihu?useUnicode=true&amp;characterEncoding=UTF-8&amp;";

	// your username and password
	String dbUsername = "root";
	String dbPassword = "123456";

	private static Dao dao;

	private Dao() {
	};

	public static Dao getInstance() {
		if (dao == null) {
			dao = new Dao();
		}

		return dao;
	}

	static {
		try {
			Class.forName(driver).newInstance();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public User login(String email, String password) throws SQLException {
		Connection con = null;
		Statement sm = null;
		ResultSet results = null;
		try {
			con = DriverManager.getConnection(url, dbUsername, dbPassword);
			sm = con.createStatement();
			results = sm.executeQuery("select * from user where email='"
					+ email + "' and password = '" + password + "'");
			if (results.next()) {
				User user = new User();
				user.setUserID(results.getInt("userID"));
				user.setUsername(results.getString("username"));
				user.setEmail(email);
				return user;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (sm != null) {
				sm.close();
			}
			if (con != null) {
				con.close();
			}
			if (results != null) {
				results.close();
			}
		}
		return null;
	}

	public User register(String username, String email, String password)
			throws SQLException, RegistryException {
		Connection con = null;
		Statement sm = null;
		ResultSet results = null;
		try {
			con = DriverManager.getConnection(url, dbUsername, dbPassword);
			sm = con.createStatement();
			results = sm.executeQuery("select * from user where username='"
					+ username + "'");
			if (results.next()) {
				throw new RegistryException("用户名已被注册");
			}
			results.close();
			results = sm.executeQuery("select * from user where email='"
					+ email + "'");
			if (results.next()) {
				throw new RegistryException("邮箱已被注册");
			}
			results.close();
			sm.executeUpdate("insert into user(username, password, email) values('"
					+ username + "', '" + password + "', '" + email + "')");
			results = sm.executeQuery("select * from user where email='"
					+ email + "' and password = '" + password + "'");
			if (results.next()) {
				User user = new User();
				user.setUserID(results.getInt("userID"));
				user.setUsername(username);
				user.setEmail(email);
				return user;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (sm != null) {
				sm.close();
			}
			if (con != null) {
				con.close();
			}
			if (results != null) {
				results.close();
			}
		}
		return null;
	}
}
