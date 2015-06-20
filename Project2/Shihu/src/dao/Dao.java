package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

import bean.Question;
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

	public List<JSONObject> getPopularUserList(int popularUserNumber)
			throws SQLException {
		Connection con = null;
		Statement sm = null;
		ResultSet results = null;
		try {
			con = DriverManager.getConnection(url, dbUsername, dbPassword);
			sm = con.createStatement();
			results = sm
					.executeQuery("select * from user ORDER BY followerCount DESC LIMIT "
							+ popularUserNumber);
			List<JSONObject> popularUserList = new LinkedList<JSONObject>();
			while (results.next()) {
				JSONObject obj = new JSONObject();
				obj.put("userID", results.getString("userID"));
				obj.put("avatarPath", results.getString("avatarPath"));
				obj.put("username", results.getString("username"));
				obj.put("motto", results.getString("motto"));
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

	public Question getQuestionByID(int questionID) throws SQLException {
		Connection con = null;
		Statement sm = null;
		ResultSet results = null;
		try {
			con = DriverManager.getConnection(url, dbUsername, dbPassword);
			sm = con.createStatement();
			results = sm
					.executeQuery("select * from question where questionID='"
							+ questionID + "'");
			if (results.next()) {
				Question question = new Question();
				question.setQuestionID(Integer.parseInt(results
						.getString("questionID")));
				question.setUserID(Integer.parseInt(results.getString("userID")));
				question.setUsername(getUserByID(results.getString("userID"))
						.getUsername());
				question.setTitle(results.getString("title"));
				question.setContent(results.getString("content"));
				question.setTime(results.getString("questionTime"));
				return question;
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

	public int addQuestion(User user, String title, String content)
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
			int newQuestionID = sm
					.executeUpdate(
							"insert into question(userID, title, content) values('"
									+ userID + "', '" + title + "', '"
									+ content + "')",
							Statement.RETURN_GENERATED_KEYS);
			return newQuestionID;
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

	public List<JSONObject> getAnswer(int questionID, int startingIndex,
			int numberOfAnswers) throws SQLException {
		Connection con = null;
		Statement sm = null;
		ResultSet results = null;
		try {
			con = DriverManager.getConnection(url, dbUsername, dbPassword);
			sm = con.createStatement();
			results = sm
					.executeQuery("select * from answers where questionID='"
							+ questionID + "' ORDER BY answerID ASC LIMIT "
							+ startingIndex + "," + numberOfAnswers);
			List<JSONObject> answerList = new LinkedList<JSONObject>();
			while (results.next()) {
				JSONObject obj = new JSONObject();
				String userID = results.getString("userID");
				User user = getUserByID(userID);
				obj.put("answerID", results.getString("answerID"));
				obj.put("userID", userID);
				obj.put("username", user.getUsername());
				obj.put("avatarPath", user.getAvatarPath());
				obj.put("motto", user.getMotto());
				obj.put("content", results.getString("content"));
				obj.put("answerTime", results.getString("answerTime"));
				obj.put("replyCount", results.getString("replyCount"));
				answerList.add(obj);
			}
			return answerList;
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

	public JSONObject addAnswer(User user, int questionID, String content)
			throws SQLException {
		Connection con = null;
		PreparedStatement sm = null;
		ResultSet results = null;
		content = content.replace("'", "''");
		int userID = user.getUserID();
		int newAnswerID = 0;
		try {
			con = DriverManager.getConnection(url, dbUsername, dbPassword);
			sm = con.prepareStatement(
					"insert into answers(questionID, userID, content) values('"
							+ questionID + "', '" + userID + "', '" + content
							+ "')", Statement.RETURN_GENERATED_KEYS);
			sm.executeUpdate();
			results = sm.getGeneratedKeys();
			if (results.next()) {
				newAnswerID = results.getInt(1);
			}
			results.close();
			sm.executeUpdate("UPDATE question SET answerCount=answerCount+1 WHERE questionID='"
					+ questionID + "'");
			results = sm.executeQuery("select * from answers where answerID='"
					+ newAnswerID + "'");
			if (results.next()) {
				JSONObject obj = new JSONObject();
				obj.put("answerID", newAnswerID);
				obj.put("userID", userID);
				obj.put("username", user.getUsername());
				obj.put("avatarPath", user.getAvatarPath());
				obj.put("motto", user.getMotto());
				obj.put("content", results.getString("content"));
				obj.put("answerTime", results.getString("answerTime"));
				obj.put("replyCount", results.getString("replyCount"));
				return obj;
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

	public List<JSONObject> getReply(int answerID) throws SQLException {
		Connection con = null;
		Statement sm = null;
		ResultSet results = null;
		try {
			con = DriverManager.getConnection(url, dbUsername, dbPassword);
			sm = con.createStatement();
			results = sm.executeQuery("select * from replies where answerID='"
					+ answerID + "' ORDER BY replyID DESC");
			List<JSONObject> replyList = new LinkedList<JSONObject>();
			while (results.next()) {
				JSONObject obj = new JSONObject();
				String userID = results.getString("userID");
				User user = getUserByID(userID);
				obj.put("userID", userID);
				obj.put("username", user.getUsername());
				obj.put("avatarPath", user.getAvatarPath());
				obj.put("content", results.getString("content"));
				obj.put("replyTime", results.getString("replyTime"));
				replyList.add(obj);
			}
			return replyList;
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

	public List<JSONObject> addReply(User user, int answerID, String content)
			throws SQLException {
		Connection con = null;
		Statement sm = null;
		ResultSet results = null;
		content = content.replace("'", "''");
		int userID = user.getUserID();
		try {
			con = DriverManager.getConnection(url, dbUsername, dbPassword);
			sm = con.createStatement();
			sm.executeUpdate("insert into replies(answerID, userID, content) values('"
					+ answerID + "', '" + userID + "', '" + content + "')");
			sm.executeUpdate("UPDATE answers SET replyCount=replyCount+1 WHERE answerID='"
					+ answerID + "'");
			return getReply(answerID);
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

	public List<JSONObject> getAnswerListByUserID(int userID)
			throws SQLException {
		Connection con = null;
		Statement sm = null;
		ResultSet results = null;
		try {
			con = DriverManager.getConnection(url, dbUsername, dbPassword);
			sm = con.createStatement();
			results = sm.executeQuery("select * from answers where userID='"
					+ userID + "' ORDER BY answerID DESC");
			List<JSONObject> answerList = new LinkedList<JSONObject>();
			while (results.next()) {
				JSONObject obj = new JSONObject();
				String questionID = results.getString("questionID");
				Question question = getQuestionByID(Integer
						.parseInt(questionID));
				obj.put("questionID", questionID);
				obj.put("questionTitle", question.getTitle());
				obj.put("content", results.getString("content"));
				obj.put("answerTime", results.getString("answerTime"));
				answerList.add(obj);
			}
			return answerList;
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

	public List<Question> getQuestionListByUserID(int userID)
			throws SQLException {
		Connection con = null;
		Statement sm = null;
		ResultSet results = null;
		try {
			con = DriverManager.getConnection(url, dbUsername, dbPassword);
			sm = con.createStatement();
			results = sm.executeQuery("select * from question where userID='"
					+ userID + "' ORDER BY questionID DESC");
			List<Question> questionList = new LinkedList<Question>();
			while (results.next()) {
				Question question = new Question();
				question.setQuestionID(Integer.parseInt(results
						.getString("questionID")));
				question.setTitle(results.getString("title"));
				questionList.add(question);
			}
			return questionList;
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

	public List<Question> getPopularQuestionList(int numberOfPopularQuestions)
			throws SQLException {
		Connection con = null;
		Statement sm = null;
		ResultSet results = null;
		try {
			con = DriverManager.getConnection(url, dbUsername, dbPassword);
			sm = con.createStatement();
			results = sm
					.executeQuery("select * from question ORDER BY answerCount DESC LIMIT "
							+ numberOfPopularQuestions);
			List<Question> questionList = new LinkedList<Question>();
			while (results.next()) {
				Question question = new Question();
				question.setQuestionID(Integer.parseInt(results
						.getString("questionID")));
				question.setTitle(results.getString("title"));
				questionList.add(question);
			}
			return questionList;
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

	public List<Question> searchQuestionByKeyword(String keyword)
			throws SQLException {
		Connection con = null;
		Statement sm = null;
		ResultSet results = null;
		try {
			con = DriverManager.getConnection(url, dbUsername, dbPassword);
			sm = con.createStatement();
			results = sm
					.executeQuery("select * from question where title like '%"
							+ keyword + "%' ORDER BY answerCount DESC");
			List<Question> questionList = new LinkedList<Question>();
			while (results.next()) {
				Question question = new Question();
				question.setQuestionID(Integer.parseInt(results
						.getString("questionID")));
				question.setTitle(results.getString("title"));
				questionList.add(question);
			}
			return questionList;
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

	public List<User> searchUsernameByKeyword(String keyword)
			throws SQLException {
		Connection con = null;
		Statement sm = null;
		ResultSet results = null;
		try {
			con = DriverManager.getConnection(url, dbUsername, dbPassword);
			sm = con.createStatement();
			results = sm
					.executeQuery("select * from user where username like '%"
							+ keyword + "%' ORDER BY followerCount DESC");
			List<User> userList = new LinkedList<User>();
			while (results.next()) {
				User user = new User();
				user.setUserID(results.getInt("userID"));
				user.setUsername(results.getString("username"));
				user.setAvatarPath(results.getString("avatarPath"));
				user.setMotto(results.getString("motto"));
				userList.add(user);
			}
			return userList;
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
