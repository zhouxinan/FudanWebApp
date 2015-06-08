package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

import bean.User;
import exception.LoginServletException;

import org.json.*;

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

	public User login(String email, String password) throws SQLException,
			LoginServletException {
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
				user.setAvatarPath(results.getString("avatarPath"));
				user.setMotto(results.getString("motto"));
				return user;
			} else {
				throw new LoginServletException("邮箱或密码错误");
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
			throws SQLException, LoginServletException {
		Connection con = null;
		Statement sm = null;
		ResultSet results = null;
		try {
			con = DriverManager.getConnection(url, dbUsername, dbPassword);
			sm = con.createStatement();
			results = sm.executeQuery("select * from user where username='"
					+ username + "'");
			if (results.next()) {
				throw new LoginServletException("用户名已被注册");
			}
			results.close();
			results = sm.executeQuery("select * from user where email='"
					+ email + "'");
			if (results.next()) {
				throw new LoginServletException("邮箱已被注册");
			}
			results.close();
			sm.executeUpdate("insert into user(username, password, email, avatarPath, motto) values('"
					+ username
					+ "', '"
					+ password
					+ "', '"
					+ email
					+ "', 'default.jpg', '这个人太懒，什么都没说……')"); // default avatar
																// path is
			// default.jpg
			results = sm.executeQuery("select * from user where email='"
					+ email + "'");
			if (results.next()) {
				User user = new User();
				user.setUserID(results.getInt("userID"));
				user.setUsername(username);
				user.setEmail(email);
				user.setAvatarPath(results.getString("avatarPath"));
				user.setMotto(results.getString("motto"));
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

	public int addNewQuestion(User user, String title, String content)
			throws SQLException {
		int userID = user.getUserID();
		title = title.replace("'", "''");
		content = content.replace("'", "''");
		Connection con = null;
		Statement sm = null;
		ResultSet results = null;
		try {
			con = DriverManager.getConnection(url, dbUsername, dbPassword);
			sm = con.createStatement();
			sm.executeUpdate("insert into question(userID, title, content) values('"
					+ userID + "', '" + title + "', '" + content + "')");
			results = sm.executeQuery("select * from question where userID='"
					+ userID + "' ORDER BY questionTime DESC LIMIT 1");
			if (results.next()) {
				return results.getInt("questionID");
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
		return -1; // default error return value
	}

	public User getUserByID(String userID) throws SQLException {
		Connection con = null;
		Statement sm = null;
		ResultSet results = null;
		try {
			con = DriverManager.getConnection(url, dbUsername, dbPassword);
			sm = con.createStatement();
			results = sm.executeQuery("select * from user where userID='"
					+ userID + "'");
			if (results.next()) {
				User user = new User();
				user.setUserID(results.getInt("userID"));
				user.setUsername(results.getString("username"));
				user.setEmail(results.getString("email"));
				user.setAvatarPath(results.getString("avatarPath"));
				user.setMotto(results.getString("motto"));
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

	public boolean follow(User user, int toUserID) throws SQLException {
		int fromUserID = user.getUserID();
		Connection con = null;
		Statement sm = null;
		ResultSet results = null;
		try {
			con = DriverManager.getConnection(url, dbUsername, dbPassword);
			sm = con.createStatement();
			sm.executeUpdate("insert into follows(fromUserID, toUserID) values('"
					+ fromUserID + "', '" + toUserID + "')");
			return true;
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
		return false;
	}

	public boolean defollow(User user, int toUserID) throws SQLException {
		int fromUserID = user.getUserID();
		Connection con = null;
		Statement sm = null;
		ResultSet results = null;
		try {
			con = DriverManager.getConnection(url, dbUsername, dbPassword);
			sm = con.createStatement();
			sm.executeUpdate("delete from follows where fromUserID='"
					+ fromUserID + "' and toUserID='" + toUserID + "'");
			return true;
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
		return false;
	}

	public boolean checkFollow(User user, int toUserID) throws SQLException {
		int fromUserID = user.getUserID();
		Connection con = null;
		Statement sm = null;
		ResultSet results = null;
		try {
			con = DriverManager.getConnection(url, dbUsername, dbPassword);
			sm = con.createStatement();
			results = sm
					.executeQuery("select * from follows where fromUserID='"
							+ fromUserID + "' and toUserID='" + toUserID + "'");
			if (results.next()) {
				return true;
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
		return false;
	}

	public List<JSONObject> getFollowers(int toUserID) throws SQLException {
		Connection con = null;
		Statement sm = null;
		ResultSet results = null;
		try {
			con = DriverManager.getConnection(url, dbUsername, dbPassword);
			sm = con.createStatement();
			results = sm.executeQuery("select * from follows where toUserID='"
					+ toUserID + "' ORDER BY followTime DESC LIMIT 5");
			List<JSONObject> followerList = new LinkedList<JSONObject>();
			while (results.next()) {
				JSONObject obj = new JSONObject();
				String fromUserID = results.getString("fromUserID");
				obj.put("userID", fromUserID);
				User fromUser = getUserByID(fromUserID);
				obj.put("avatarPath", fromUser.getAvatarPath());
				followerList.add(obj);
			}
			return followerList;
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

	public List<JSONObject> getFollowing(int fromUserID) throws SQLException {
		Connection con = null;
		Statement sm = null;
		ResultSet results = null;
		try {
			con = DriverManager.getConnection(url, dbUsername, dbPassword);
			sm = con.createStatement();
			results = sm.executeQuery("select * from follows where fromUserID='"
					+ fromUserID + "' ORDER BY followTime DESC LIMIT 5");
			List<JSONObject> followingList = new LinkedList<JSONObject>();
			while (results.next()) {
				JSONObject obj = new JSONObject();
				String toUserID = results.getString("toUserID");
				obj.put("userID", toUserID);
				User toUser = getUserByID(toUserID);
				obj.put("avatarPath", toUser.getAvatarPath());
				followingList.add(obj);
			}
			return followingList;
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
	
	public int getFollowingCount(int fromUserID) throws SQLException {
		Connection con = null;
		Statement sm = null;
		ResultSet results = null;
		try {
			con = DriverManager.getConnection(url, dbUsername, dbPassword);
			sm = con.createStatement();
			results = sm.executeQuery("select count(*) from follows where fromUserID='"
					+ fromUserID + "'");
			if (results.next()) {
				return Integer.parseInt(results.getString("count(*)"));
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
		return -1; // default error return value
	}
	
	public int getFollowerCount(int toUserID) throws SQLException {
		Connection con = null;
		Statement sm = null;
		ResultSet results = null;
		try {
			con = DriverManager.getConnection(url, dbUsername, dbPassword);
			sm = con.createStatement();
			results = sm.executeQuery("select count(*) from follows where toUserID='"
					+ toUserID + "'");
			if (results.next()) {
				return Integer.parseInt(results.getString("count(*)"));
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
		return -1; // default error return value
	}
}
