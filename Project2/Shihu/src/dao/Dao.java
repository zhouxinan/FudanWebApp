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

	public JSONObject follow(User user, int toUserID) throws SQLException {
		int fromUserID = user.getUserID();
		Connection con = null;
		Statement sm = null;
		ResultSet results = null;
		try {
			con = DriverManager.getConnection(url, dbUsername, dbPassword);
			sm = con.createStatement();
			sm.executeUpdate("insert into follows(fromUserID, toUserID) values('"
					+ fromUserID + "', '" + toUserID + "')");
			sm.executeUpdate("UPDATE user SET followerCount=followerCount+1 WHERE userID="
					+ toUserID);
			sm.executeUpdate("UPDATE user SET followingCount=followingCount+1 WHERE userID="
					+ fromUserID);
			return getFollowInfo(user, toUserID);
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

	public JSONObject defollow(User user, int toUserID) throws SQLException {
		int fromUserID = user.getUserID();
		Connection con = null;
		Statement sm = null;
		ResultSet results = null;
		try {
			con = DriverManager.getConnection(url, dbUsername, dbPassword);
			sm = con.createStatement();
			sm.executeUpdate("delete from follows where fromUserID='"
					+ fromUserID + "' and toUserID='" + toUserID + "'");
			sm.executeUpdate("UPDATE user SET followerCount=followerCount-1 WHERE userID="
					+ toUserID);
			sm.executeUpdate("UPDATE user SET followingCount=followingCount-1 WHERE userID="
					+ fromUserID);
			return getFollowInfo(user, toUserID);
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
			results = sm
					.executeQuery("select * from follows where fromUserID='"
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
			results = sm
					.executeQuery("select count(*) from follows where fromUserID='"
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
			results = sm
					.executeQuery("select count(*) from follows where toUserID='"
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

	public JSONObject getFollowInfo(User user, int userID) throws SQLException {
		Connection con = null;
		Statement sm = null;
		ResultSet results = null;
		JSONObject returnObj = new JSONObject();
		try {
			con = DriverManager.getConnection(url, dbUsername, dbPassword);
			sm = con.createStatement();
			if (user != null) {
				results = sm
						.executeQuery("select * from follows where fromUserID='"
								+ user.getUserID()
								+ "' and toUserID='"
								+ userID + "'");
				if (results.next()) {
					returnObj.put("isFollowed", true);
				} else {
					returnObj.put("isFollowed", false);
				}
				results.close();
			}
			results = sm
					.executeQuery("select followerCount,followingCount from user where userID='"
							+ userID + "'");
			if (results.next()) {
				returnObj.put("followerCount",
						results.getString("followerCount"));
				returnObj.put("followingCount",
						results.getString("followingCount"));
			}
			results.close();
			results = sm
					.executeQuery("select * from follows where fromUserID='"
							+ userID + "' ORDER BY followTime DESC LIMIT 5");
			List<JSONObject> followingList = new LinkedList<JSONObject>();
			while (results.next()) {
				JSONObject obj = new JSONObject();
				String toUserID = results.getString("toUserID");
				obj.put("userID", toUserID);
				User toUser = getUserByID(toUserID);
				obj.put("avatarPath", toUser.getAvatarPath());
				followingList.add(obj);
			}
			results.close();
			returnObj.put("followingList", followingList);
			results = sm.executeQuery("select * from follows where toUserID='"
					+ userID + "' ORDER BY followTime DESC LIMIT 5");
			List<JSONObject> followerList = new LinkedList<JSONObject>();
			while (results.next()) {
				JSONObject obj = new JSONObject();
				String fromUserID = results.getString("fromUserID");
				obj.put("userID", fromUserID);
				User fromUser = getUserByID(fromUserID);
				obj.put("avatarPath", fromUser.getAvatarPath());
				followerList.add(obj);
			}
			returnObj.put("followerList", followerList);
			return returnObj;
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

	public List<JSONObject> getPopularUserList() throws SQLException {
		Connection con = null;
		Statement sm = null;
		ResultSet results = null;
		try {
			con = DriverManager.getConnection(url, dbUsername, dbPassword);
			sm = con.createStatement();
			results = sm
					.executeQuery("select userID,avatarPath from user ORDER BY followerCount DESC LIMIT 8");
			List<JSONObject> popularUserList = new LinkedList<JSONObject>();
			while (results.next()) {
				JSONObject obj = new JSONObject();
				obj.put("userID", results.getString("userID"));
				obj.put("avatarPath", results.getString("avatarPath"));
				popularUserList.add(obj);
			}
			return popularUserList;
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

	public boolean modifyPassword(User user, String oldPassword,
			String newPassword) throws SQLException {
		Connection con = null;
		Statement sm = null;
		ResultSet results = null;
		int userID = user.getUserID();
		try {
			con = DriverManager.getConnection(url, dbUsername, dbPassword);
			sm = con.createStatement();
			results = sm
					.executeQuery("select password from user where userID='"
							+ userID + "'");
			if (results.next()) {
				String password = results.getString("password");
				if (password.equals(oldPassword)) {
					results.close();
					sm.executeUpdate("UPDATE user SET password='" + newPassword
							+ "' WHERE userID=" + userID);
					return true;
				} else {
					return false;
				}
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

	public boolean modifyMotto(User user, String newMotto) throws SQLException {
		Connection con = null;
		Statement sm = null;
		ResultSet results = null;
		int userID = user.getUserID();
		try {
			con = DriverManager.getConnection(url, dbUsername, dbPassword);
			sm = con.createStatement();
			sm.executeUpdate("UPDATE user SET motto='" + newMotto
					+ "' WHERE userID=" + userID);
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

	public boolean modifyAvatarPath(User user, String newAvatarPath)
			throws SQLException {
		Connection con = null;
		Statement sm = null;
		ResultSet results = null;
		int userID = user.getUserID();
		try {
			con = DriverManager.getConnection(url, dbUsername, dbPassword);
			sm = con.createStatement();
			sm.executeUpdate("UPDATE user SET avatarPath='" + newAvatarPath
					+ "' WHERE userID=" + userID);
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

	public boolean addMessage(User user, String receiverUsername, String content)
			throws SQLException {
		Connection con = null;
		Statement sm = null;
		ResultSet results = null;
		int fromUserID = user.getUserID();
		content = content.replace("'", "''");
		try {
			con = DriverManager.getConnection(url, dbUsername, dbPassword);
			sm = con.createStatement();
			results = sm
					.executeQuery("select userID from user where username='"
							+ receiverUsername + "'");
			if (results.next()) {
				String toUserID = results.getString("userID");
				sm.executeUpdate("insert into messages(fromUserID, toUserID, content) values('"
						+ fromUserID
						+ "', '"
						+ toUserID
						+ "', '"
						+ content
						+ "')");
				return true;
			} else {
				return false;
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

	public boolean setMessageRead(User user, String messageID)
			throws SQLException {
		Connection con = null;
		Statement sm = null;
		ResultSet results = null;
		int toUserID = user.getUserID();
		try {
			con = DriverManager.getConnection(url, dbUsername, dbPassword);
			sm = con.createStatement();
			sm.executeUpdate("UPDATE messages SET isRead=1 WHERE toUserID='"
					+ toUserID + "' and messageID='" + messageID + "'");
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

	public List<JSONObject> getAllMessagesToUser(User user, boolean isRead)
			throws SQLException {
		Connection con = null;
		Statement sm = null;
		ResultSet results = null;
		int toUserID = user.getUserID();
		try {
			con = DriverManager.getConnection(url, dbUsername, dbPassword);
			sm = con.createStatement();
			results = sm.executeQuery("select * from messages where toUserID='"
					+ toUserID + "' and isRead='" + (isRead ? 1 : 0)
					+ "' ORDER BY sendTime DESC");
			List<JSONObject> messageList = new LinkedList<JSONObject>();
			while (results.next()) {
				JSONObject obj = new JSONObject();
				String fromUserID = results.getString("fromUserID");
				User fromUser = getUserByID(fromUserID);
				obj.put("userID", fromUserID);
				obj.put("username", fromUser.getUsername());
				obj.put("avatarPath", fromUser.getAvatarPath());
				obj.put("messageID", results.getString("messageID"));
				obj.put("content", results.getString("content"));
				messageList.add(obj);
			}
			return messageList;
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

	public List<JSONObject> getAllMessagesFromUser(User user)
			throws SQLException {
		Connection con = null;
		Statement sm = null;
		ResultSet results = null;
		int fromUserID = user.getUserID();
		try {
			con = DriverManager.getConnection(url, dbUsername, dbPassword);
			sm = con.createStatement();
			results = sm
					.executeQuery("select * from messages where fromUserID='"
							+ fromUserID + "' ORDER BY sendTime DESC");
			List<JSONObject> messageList = new LinkedList<JSONObject>();
			while (results.next()) {
				JSONObject obj = new JSONObject();
				String toUserID = results.getString("toUserID");
				User toUser = getUserByID(toUserID);
				obj.put("userID", toUserID);
				obj.put("username", toUser.getUsername());
				obj.put("avatarPath", toUser.getAvatarPath());
				obj.put("messageID", results.getString("messageID"));
				obj.put("content", results.getString("content"));
				messageList.add(obj);
			}
			return messageList;
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

	public JSONObject getMessageByID(User user, String messageID)
			throws SQLException {
		Connection con = null;
		Statement sm = null;
		ResultSet results = null;
		int userID = user.getUserID();
		JSONObject messageObj = new JSONObject();
		try {
			con = DriverManager.getConnection(url, dbUsername, dbPassword);
			sm = con.createStatement();
			sm.executeUpdate("UPDATE messages SET isRead=1 WHERE toUserID='"
					+ userID + "' and messageID='" + messageID + "'");
			results = sm
					.executeQuery("select * from messages where messageID='"
							+ messageID + "' and (fromUserID='" + userID
							+ "' or toUserID='" + userID + "')");
			if (results.next()) {
				String fromUserID = results.getString("fromUserID");
				String toUserID = results.getString("toUserID");
				User fromUser = getUserByID(fromUserID);
				User toUser = getUserByID(toUserID);
				messageObj.put("fromUsername", fromUser.getUsername());
				messageObj.put("toUsername", toUser.getUsername());
				messageObj.put("fromUserAvatarPath", fromUser.getAvatarPath());
				messageObj.put("toUserAvatarPath", toUser.getAvatarPath());
				messageObj.put("content", results.getString("content"));
			}
			return messageObj;
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

	public String getUnreadMessageCount(User user) throws SQLException {
		Connection con = null;
		Statement sm = null;
		ResultSet results = null;
		int userID = user.getUserID();
		try {
			con = DriverManager.getConnection(url, dbUsername, dbPassword);
			sm = con.createStatement();
			results = sm
					.executeQuery("select count(*) from messages where toUserID='"
							+ userID + "' and isRead='0'");
			if (results.next()) {
				String count = results.getString("count(*)");
				return count;
			} else {
				return null;
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
