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

import org.json.JSONObject;

public class Dao {
	private static String driver = "com.mysql.jdbc.Driver";
	String url = "jdbc:mysql://127.0.0.1:3306/chat?useUnicode=true&amp;characterEncoding=UTF-8&amp;";

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

	public List<JSONObject> getMessages(int lastReceivedMessageID)
			throws SQLException {
		Connection con = null;
		Statement sm = null;
		ResultSet results = null;
		try {
			con = DriverManager.getConnection(url, dbUsername, dbPassword);
			sm = con.createStatement();
			results = sm
					.executeQuery("select * from message where messageID > "
							+ lastReceivedMessageID);
			List<JSONObject> messageList = new LinkedList<JSONObject>();
			while (results.next()) {
				JSONObject returnObj = new JSONObject();
				returnObj.put("messageID", results.getString("messageID"));
				returnObj.put("username", results.getString("username"));
				returnObj.put("messageText", results.getString("messageText"));
				returnObj.put("time", results.getString("note_time"));
				returnObj.put("avatar", "touxiang.jpg");
				messageList.add(returnObj);
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

	public JSONObject getLastMessage() throws SQLException {
		Connection con = null;
		Statement sm = null;
		ResultSet results = null;
		try {
			con = DriverManager.getConnection(url, dbUsername, dbPassword);
			sm = con.createStatement();
			results = sm
					.executeQuery("SELECT * FROM message ORDER BY messageID DESC LIMIT 1;");
			while (results.next()) {
				JSONObject returnObj = new JSONObject();
				returnObj.put("messageID", results.getString("messageID"));
				returnObj.put("username", results.getString("username"));
				returnObj.put("messageText", results.getString("messageText"));
				returnObj.put("time", results.getString("note_time"));
				returnObj.put("avatar", "touxiang.jpg");
				return returnObj;
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

	public void saveMessage(String username, String messageText)
			throws SQLException {
		Connection con = null;
		Statement sm = null;
		messageText = messageText.replace("'", "''");
		try {
			con = DriverManager.getConnection(url, dbUsername, dbPassword);
			sm = con.createStatement();
			Date date = new Date();
			SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			String dateString = sim.format(date);
			sm.executeUpdate("insert into message(username, messageText, note_time) values('"
					+ username
					+ "', '"
					+ messageText
					+ "', '"
					+ dateString
					+ "')");
			sm.close();
			con.close();
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
		}
	}
}
